<%@ page import="org.example.demo2.servlet.VideoController" %>
<%@ page import="org.example.demo2.model.VideoModel" %>

<%
  // Fetch the video ID from the request
  String videoId = request.getParameter("id");
  VideoModel video = null;

  if (videoId != null) {
    // Find the video by ID
    for (VideoModel v : VideoController.getAllVideos()) {
      if (v.getId() == Integer.parseInt(videoId)) {
        video = v;
        break;
      }
    }
  }

  if (video == null) {
    out.println("Video not found!");
    return;
  }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Play Video</title>
</head>
<body>
<h1>${video.name}</h1>
<p>${video.description}</p>
<video width="640" height="360" controls>
  <source src="${video.url}" type="video/mp4">
  Your browser does not support the video tag.
</video>
</body>
</html>
