<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Video</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewVideos.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">
</head>
<body>
<div class="main-content">
    <jsp:include page="../common/verticalNavBar.jsp" />

    <div class="container">
        <div class="card">
            <h2 class="text-center mb-4">Upload a Video</h2>

            <!-- Display error message if any -->
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>

            <!-- Form for uploading a video -->
            <form action="${pageContext.request.contextPath}/UploadVideo" method="post" id="uploadVideoForm" enctype="multipart/form-data">

                <!-- Video Name -->
                <div class="form-group">
                    <label class="form-label" for="videoName">Video Name:</label>
                    <input type="text"
                           id="videoName"
                           name="videoName"
                           class="form-control"
                           placeholder="Enter the video name"
                           required />
                </div>

                <!-- Video Description -->
                <div class="form-group">
                    <label class="form-label" for="videoDescription">Video Description:</label>
                    <textarea id="videoDescription"
                              name="videoDescription"
                              class="form-control"
                              placeholder="Enter a short description"
                              required></textarea>
                </div>

                <!-- Video URL -->
                <div class="form-group">
                    <label class="form-label" for="videoUrl">Video URL (e.g. YouTube, Vimeo):</label>
                    <input type="url"
                           id="videoUrl"
                           name="videoUrl"
                           class="form-control"
                           placeholder="https://..."
                           required />
                </div>

                <!-- Video Image -->
                <div class="form-group">
                    <label class="form-label" for="videoImage">Upload Video Image:</label>
                    <input type="file"
                           id="videoImage"
                           name="videoImage"
                           class="form-control"
                           accept="image/*"
                           required />
                </div>

                <!-- Submit Button -->
                <div class="flex justify-center mt-4">
                    <button type="submit" class="btn btn-primary">Upload Video</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
