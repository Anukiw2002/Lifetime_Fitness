package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.demo2.util.DBConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/uploadVideo")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class UploadVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "videos";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the uploadVideo JSP page
        request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String videoName = request.getParameter("videoName");
        String videoDescription = request.getParameter("videoDescription");
        Part videoFile = request.getPart("videoFile");

        // Log the received data
        System.out.println("Received form data:");
        System.out.println("Video Name: " + videoName);
        System.out.println("Description: " + videoDescription);

        // Validate the inputs
        if (videoName == null || videoName.isEmpty() || videoDescription == null || videoDescription.isEmpty() || videoFile == null) {
            // Log validation error
            System.err.println("Validation Error: Missing required fields.");
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        // Define the upload directory
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;


        // Save the uploaded file to the server
        String fileName = videoFile.getSubmittedFileName();
        String filePath = uploadPath + File.separator + fileName;

        try {
            videoFile.write(filePath);
        } catch (IOException e) {
            System.err.println("Error saving the uploaded file: " + e.getMessage());
            request.setAttribute("errorMessage", "Failed to save the video file. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
            return;
        }

        // Save video metadata to the database
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                System.err.println("Error: Database connection is null.");
                request.setAttribute("errorMessage", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
                return;
            }

            String sql = "INSERT INTO videos (name, description) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, videoName);
                statement.setString(2, videoDescription);

                // Log the SQL query being executed
                System.out.println("Executing SQL Query: " + statement);

                // Execute the insert and check if the data was successfully inserted
                int rowsAffected = statement.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    // Log success
                    System.out.println("Video successfully uploaded!");
                    request.setAttribute("successMessage", "Video successfully uploaded!");
                    request.getRequestDispatcher("/WEB-INF/views/owner/contentManagement.jsp").forward(request, response);
                } else {
                    // Log failure
                    System.err.println("Error: No rows were inserted into the database.");
                    request.setAttribute("errorMessage", "Failed to upload the video. Please try again.");
                    request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            // Log SQL exceptions with detailed information
            System.err.println("SQL Exception occurred:");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error saving video metadata: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/owner/uploadVideo.jsp").forward(request, response);
        }
    }
}
