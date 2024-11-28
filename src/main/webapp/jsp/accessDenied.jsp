<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link href="${pageContext.request.contextPath}/css/404.css" rel="stylesheet">
</head>

<body>
<section class="page_404">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-10 col-sm-offset-1 text-center">
                    <div class="four_zero_four_bg">
                        <h1 class="text-center">404</h1>
                    </div>

                    <div class="contant_box_404">
                        <h3 class="h2">
                            Look like you're lost
                        </h3>

                        <p>The page you are looking for is not available!</p>

                        <%
                            String referer = request.getHeader("Referer");
                            if (referer != null && !referer.isEmpty()) {
                        %>
                        <a href="<%= referer %>" class="link_404">Go Back to Previous Page</a>
                        <%
                        } else {
                        %>
                        <a href="${pageContext.request.contextPath}/landingPage" class="link_404">Go to Home</a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>

</html>
