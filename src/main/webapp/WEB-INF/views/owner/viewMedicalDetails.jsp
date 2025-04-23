<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical History Details - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/medicalHistory.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <style>
        .answer-display {
            font-weight: bold;
            color: white;
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="form-wrapper">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">

        <h2>Medical and User Details</h2>

        <div class="question-group">
            <div class="question">
                <div class="question-content">
                    <label>1. Have you had a significant medical condition, surgical operation or surgery?</label>
                    <span class="answer-display">${medical_history.medicalCondition}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>2. Do you take any medication on a regular basis?</label>
                    <span class="answer-display">${medical_history.takeMedication}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>3. Do you suffer from chest pain either at rest or during exercise?</label>
                    <span class="answer-display">${medical_history.chestPain}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>4. Do you suffer from back pain?</label>
                    <span class="answer-display">${medical_history.backPain}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>5. Do you have any bone or joint problem that could be aggravated by exercise?</label>
                    <span class="answer-display">${medical_history.boneJointProblem}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>6. Do you suffer from low/high blood pressure?</label>
                    <span class="answer-display">${medical_history.bloodPressure}</span>
                </div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>7. Do you suffer from diabetes?</label>
                    <span class="answer-display">${medical_history.diabetes}</span>
                </div>
            </div>

            <div class="question no-radio">
                <label>8. How would you rate your own level of stress? At work? At home?</label>
                <div class="answer-display">${medical_history.stressLevel}</div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>9. Do you smoke?</label>
                    <span class="answer-display">${medical_history.smoking}</span>
                </div>
            </div>

            <div class="question no-radio">
                <label>10. What is your current level of activity?</label>
                <div class="answer-display">${medical_history.activityLevel}</div>
            </div>

            <div class="question no-radio">
                <label>11. What are the objectives of participating in structured exercises?</label>
                <div class="answer-display">${medical_history.exerciseObjectives}</div>
            </div>

            <div class="question">
                <div class="question-content">
                    <label>12. Do you have any other medical condition that hasn't been mentioned above?</label>
                    <span class="answer-display">${medical_history.otherConditions}</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>