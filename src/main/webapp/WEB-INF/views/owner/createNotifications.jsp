<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Notification</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ownerCreateNotification.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2 class="text-center">Create Notification</h2>
                <p class="text-center text-muted">Send notifications to customers and instructors</p>
            </div>

            <div class="card-body">
                <form action="createNotificationRedirection" method="POST" id="notificationForm">
                    <div class="grid grid-2">
                        <div class="form-group">
                            <label class="form-label" for="recipients">Recipients*</label>
                            <select id="recipients" name="targetGroup" class="form-control" required>
                                <option value="">Select recipients</option>
                                <option value="customers">All Customers</option>
                                <option value="instructors">All Instructors</option>
                                <option value="both">Both Customers & Instructors</option>
                            </select>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="form-label" for="subject">Message Subject*</label>
                        <input type="text"
                               id="subject"
                               name="notificationTitle"
                               class="form-control"
                               placeholder="Enter message subject"
                               required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="message">Message Content*</label>
                        <div class="message-box">
                            <textarea
                                    id="message"
                                    name="notificationMessage"
                                    class="form-control"
                                    placeholder="Type your message here..."
                                    rows="6"
                                    required></textarea>
                            <div class="character-count">
                                <span id="charCount">0</span>/500 characters
                            </div>
                        </div>
                    </div>

                    <div class="preview-box" id="previewBox" style="display: none;">
                        <div class="preview-header">
                            <h3>Message Preview</h3>
                        </div>
                        <div class="preview-content">
                            <p><strong>Subject:</strong> <span id="previewSubject"></span></p>
                            <p><strong>To:</strong> <span id="previewRecipients"></span></p>
                            <p><strong>Priority:</strong> <span id="previewPriority"></span></p>
                            <p><strong>Message:</strong></p>
                            <p id="previewMessage"></p>
                        </div>
                    </div>

                    <div class="form-actions mt-4">
                        <button type="button" class="btn btn-secondary" onclick="window.location.href='memberManagement'">Cancel</button>
                        <button type="submit" class="btn btn-primary">Send Notification</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="toast" id="toast">Notification sent successfully!</div>
</div>

<script>
    document.getElementById('message').addEventListener('input', function() {
        const maxLength = 500;
        const currentLength = this.value.length;
        const charCountSpan = document.getElementById('charCount');

        charCountSpan.textContent = currentLength;

        if (currentLength > maxLength) {
            this.value = this.value.substring(0, maxLength);
            charCountSpan.textContent = maxLength;
        }
    });

    document.getElementById('notificationForm').addEventListener('submit', function(e) {
        if (!confirm('Are you sure you want to send this notification?')) {
            e.preventDefault();
        }
    });
</script>
</body>
</html>