<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical History - Lifetime Fitness</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/medicalHistory.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
</head>
<body>
<div class="container">
    <div class="form-wrapper">
        <img src="${pageContext.request.contextPath}/images/LogoWhite.png" alt="Lifetime Fitness" class="logo">

        <div class="progress-steps">
            <div class="step completed">
                <div class="step-number">1</div>
                <span>General details</span>
            </div>
            <div class="step-line1"></div>
            <div class="step active">
                <div class="step-number">2</div>
                <span>Medical History</span>
            </div>
            <div class="step-line2"></div>
            <div class="step">
                <div class="step-number">3</div>
                <span>Membership plan</span>
            </div>
        </div>

        <form class="medical-form"action="${pageContext.request.contextPath}/signup/step3" method="post">
            <div class="question-group">
                <div class="question">
                    <div class="question-content">
                        <label>1. Have you had a significant medical condition, surgical operation or surgery ?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q1" value="yes"> Yes</label>
                        <label><input type="radio" name="q1" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>2. Do you take any medication on a regular basis ?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q2" value="yes"> Yes</label>
                        <label><input type="radio" name="q2" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>3. Do you suffer from chest pain either at rest or during exercise ?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q3" value="yes"> Yes</label>
                        <label><input type="radio" name="q3" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>4. Do you suffer from back pain?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q4" value="yes"> Yes</label>
                        <label><input type="radio" name="q4" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>5. Do you have any bone or joint problem that could be aggravated by exercise ?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q5" value="yes"> Yes</label>
                        <label><input type="radio" name="q5" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>6. Do you suffer from low/high blood pressure ?</label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q6" value="yes"> Yes</label>
                        <label><input type="radio" name="q6" value="no"> No</label>
                    </div>
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>7. Do you suffer from diabetes ? </label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q7" value="yes"> Yes</label>
                        <label><input type="radio" name="q7" value="no"> No</label>
                    </div>
                </div>

                <div class="question no-radio">
                    <label>8. How would you rate your own level of stress ? At work ? At home ?</label>
                    <input type="text" class="form-input" name="q8">
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>9. Do you smoke ? </label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q9" value="yes"> Yes</label>
                        <label><input type="radio" name="q9" value="no"> No</label>
                    </div>
                </div>

                <div class="question no-radio">
                    <label>10. What is your current level of activity ?</label>
                    <input type="text" class="form-input" name="q10">
                </div>

                <div class="question no-radio">
                    <label>11. What are the objectives of participating in structured exercises?</label>
                    <input type="text" class="form-input" name="q11">
                </div>

                <div class="question">
                    <div class="question-content">
                        <label>12. Do you have any other medical condition that hasn't been mentioned above ? </label>
                    </div>
                    <div class="radio-group">
                        <label><input type="radio" name="q12" value="yes"> Yes</label>
                        <label><input type="radio" name="q12" value="no"> No</label>
                    </div>
                </div>
            </div>

            <button type="submit" class="primary-button">Save and continue</button>
        </form>
    </div>
</div>
</body>
</html>