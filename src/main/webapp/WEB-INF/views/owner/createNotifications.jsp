<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Message Form</title>
    <style>
        /* Style for the container */
        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
        }

        /* Style for the message box */
        .message-box {
            width: 100%;
            height: 150px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            margin-bottom: 20px;
        }

        /* Style for buttons */
        .button-container {
            display: flex;
            justify-content: space-between;
        }

        .button-container button {
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .button-container button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Textarea for message input -->
    <textarea class="message-box" placeholder="Type your message here..."></textarea>

    <!-- Buttons below the message box -->
    <div class="button-container">
        <button id="button1">Button 1</button>
        <button id="button2">Button 2</button>
        <button id="button3">Button 3</button>
    </div>
</div>

</body>
</html>
