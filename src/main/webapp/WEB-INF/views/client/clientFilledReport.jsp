<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/generalStyles.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/report.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .print-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-bottom: 20px;
        }

        .print-button:hover {
            background-color: #45a049;
        }

        /* Print styles */
        @media print {
            @page {
                size: A4;
                margin: 0.5cm;
            }

            html, body {
                width: 100%;
                height: 100%;
                margin: 0 !important;
                padding: 0 !important;
                /* Base font size for the entire document */
                font-size: 14pt !important;
            }

            *, *:before, *:after {
                box-sizing: border-box;
            }

            body * {
                visibility: hidden;
            }

            /* Reset any existing margins or padding that might affect alignment */
            .main-content, .report-container, #printable-report {
                margin: 0 !important;
                padding: 0 !important;
            }

            .report-container {
                visibility: visible;
                position: absolute;
                left: 0;
                top: 0;
                width: 100%;
                margin: 0 !important;
                padding: 0.5cm !important;
                color: black !important;
                font-size: 14pt !important;
            }

            .report-container * {
                visibility: visible;
                color: black !important;
            }

            #printable-report {
                width: 100%;
                margin: 0 !important;
                padding: 0 !important;
            }

            .print-button,
            nav,
            .main-navbar,
            .footer {
                display: none !important;
            }

            h1.report-title {
                text-align: center;
                margin: 0 0 20px 0 !important;
                page-break-after: avoid;
                font-size: 22pt !important;
                font-weight: bold;
            }

            .section-title {
                text-align: left !important;
                margin: 15px 0 5px 0 !important;
                border-bottom: 1px solid black;
                padding: 0 0 5px 0 !important;
                font-weight: bold;
                page-break-after: avoid;
                font-size: 18pt !important;
            }

            .form-section {
                margin: 0 0 15px 0 !important;
                padding: 0 !important;
                page-break-inside: avoid;
                font-size: 14pt !important;
            }

            .form-section label {
                display: block;
                text-align: left !important;
                margin: 0 0 10px 0 !important;
                padding: 0 !important;
                font-size: 14pt !important;
                line-height: 1.4 !important;
            }

            input[readonly] {
                border: none;
                background: transparent;
                padding: 0 !important;
                margin: 0 !important;
                width: auto;
                font-family: inherit;
                color: black !important;
                font-size: 14pt !important;
                font-weight: bold;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin: 15px 0 !important;
                padding: 0 !important;
                page-break-inside: auto;
                font-size: 14pt !important;
            }

            tr {
                page-break-inside: avoid;
            }

            table, th, td {
                border: 1px solid black;
            }

            th {
                font-size: 16pt !important;
                font-weight: bold;
                padding: 8px !important;
                text-align: left !important;
                margin: 0 !important;
            }

            td {
                padding: 8px !important;
                text-align: left !important;
                margin: 0 !important;
                font-size: 14pt !important;
            }

            td input {
                font-size: 14pt !important;
            }

            .text-center {
                text-align: center !important;
            }
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/client/clientVerticalNavbar.jsp" />
<div class="main-content">
    <div class="report-container">
        <button onclick="printReport()" class="print-button"><i class="fas fa-print"></i> Print Report</button>

        <%
            Map<String, Object> reportDetails = (Map<String, Object>) request.getAttribute("reportDetails");
            List<Map<String, Object>> exercises = (List<Map<String, Object>>) request.getAttribute("exercises");
            if (reportDetails == null) {
        %>
        <p class="text-center">No report details available.</p>
        <%
        } else {
        %>
        <div id="printable-report">
            <h1 class="report-title">USER REPORT</h1>
            <!-- Basic Information -->
            <h3 class="section-title">Basic Information</h3>
            <div class="form-section">
                <label>Name: <input type="text" name="name" value="<%= reportDetails.get("name") %>" readonly></label>
                <label>Age: <input type="number" name="age" value="<%= reportDetails.get("age") %>" readonly></label>
                <label>Program No: <input type="text" name="program_no" value="<%= reportDetails.get("program_no") %>" readonly></label>
            </div>

            <!-- Dates -->
            <h3 class="section-title">Program Duration</h3>
            <div class="form-section">
                <label>Starting Date: <input type="date" name="starting_date" value="<%= reportDetails.get("starting_date") %>" readonly></label>
                <label>Expire Date: <input type="date" name="expire_date" value="<%= reportDetails.get("expire_date") %>" readonly></label>
            </div>

            <!-- Measurements -->
            <h3 class="section-title">Health Metrics</h3>
            <div class="form-section">
                <label>Maximum Heart Rate: <input type="number" name="max_heart_rate" value="<%= reportDetails.get("max_heart_rate") %>" readonly></label>
                <label>65% BPM: <input type="number" name="bpm_65" value="<%= reportDetails.get("bpm_65") %>" readonly></label>
                <label>75% BPM: <input type="number" name="bpm_75" value="<%= reportDetails.get("bpm_75") %>" readonly></label>
                <label>85% BPM: <input type="number" name="bpm_85" value="<%= reportDetails.get("bpm_85") %>" readonly></label>
                <label>Waist Circumference: <input type="number" name="waist_circumference" value="<%= reportDetails.get("waist_circumference") %>" readonly></label>
                <label>Body Weight: <input type="number" name="body_weight" value="<%= reportDetails.get("body_weight") %>" readonly></label>
                <label>Height: <input type="number" name="height" value="<%= reportDetails.get("height") %>" readonly></label>
                <label>Fat %: <input type="number" name="fat_percentage" value="<%= reportDetails.get("fat_percentage") %>" readonly></label>
                <label>BMR: <input type="number" name="bmr" value="<%= reportDetails.get("bmr") %>" readonly></label>
            </div>

            <!-- Goal and Remarks -->
            <h3 class="section-title">Program Details</h3>
            <div class="form-section">
                <label>Goal: <input type="text" name="goal" value="<%= reportDetails.get("goal") %>" readonly></label>
                <label>Warm-Up: <input type="text" name="warm_up" value="<%= reportDetails.get("warm_up") %>" readonly></label>
                <label>Flexibility: <input type="text" name="flexibility" value="<%= reportDetails.get("flexibility") %>" readonly></label>
                <label>Cardio: <input type="text" name="cardio" value="<%= reportDetails.get("cardio") %>" readonly></label>
                <label>Remarks: <input type="text" name="remarks" value="<%= reportDetails.get("remarks") %>" readonly></label>
                <label>Target Weight: <input type="number" name="target_weight" value="<%= reportDetails.get("target_weight") %>" readonly></label>
            </div>

            <!-- Exercises Table -->
            <h3 class="section-title">Weight Log</h3>
            <table class="report-table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Weight</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (exercises != null && !exercises.isEmpty()) {
                        for (Map<String, Object> exercise : exercises) {
                %>
                <tr>
                    <td><input type="date" value="<%= exercise.get("exercise_date") %>" readonly></td>
                    <td><input type="number" value="<%= exercise.get("weight") %>" readonly></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="2" class="text-center">No exercises found for this report.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <%
            }
        %>
    </div>
</div>

<script>
    function printReport() {
        window.print();
    }
</script>
</body>
</html>