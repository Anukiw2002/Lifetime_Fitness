<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Onboarding - Availability and Schedule</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/selfOnboarding.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/typography.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="header">
            <div class="progress-steps">
                <div class="step completed">
                    <div class="step-number">1</div>
                    <span>Basic Information</span>
                </div>
                <div class="step-line1"></div>
                <div class="step completed">
                    <div class="step-number">2</div>
                    <span>Professional Bio</span>
                </div>
                <div class="step-line1"></div>
                <div class="step active">
                    <div class="step-number">3</div>
                    <span>Availability and Schedule</span>
                </div>
                <div class="step-line2"></div>
                <div class="step">
                    <div class="step-number">4</div>
                    <span>Payment Preferences</span>
                </div>
            </div>

            <div class="content-card">
                <h1>Weekly Availability</h1>
                <div class="form-section">
                    <div class="form-group">
                        <div class="availability-grid">
                            <div class="time-slots">
                                <div class="day-header">Time</div>
                                <div class="time-slot">4:00 AM - 8:00 AM</div>
                                <div class="time-slot">8:00 AM - 12:00 PM</div>
                                <div class="time-slot">12:00 PM - 4:00 PM</div>
                                <div class="time-slot">4:00 PM - 8:00 PM</div>
                                <div class="time-slot">8:00 PM - 12:00 midnight</div>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Mon</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="mon-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="mon-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="mon-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="mon-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="mon-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Tue</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="tue-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="tue-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="tue-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="tue-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="tue-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Wed</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="wed-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="wed-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="wed-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="wed-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="wed-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Thu</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="thu-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="thu-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="thu-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="thu-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="thu-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Fri</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="fri-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="fri-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="fri-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="fri-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="fri-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Sat</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sat-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sat-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sat-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sat-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sat-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>

                            <div class="day-column">
                                <div class="day-header">Sun</div>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sun-morning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sun-midmorning">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sun-afternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sun-lateafternoon">
                                    <span class="checkmark"></span>
                                </label>
                                <label class="availability-checkbox">
                                    <input type="checkbox" name="availability" value="sun-evening">
                                    <span class="checkmark"></span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="button-group">
                    <button type="button" class="back-button" onclick="window.location.href='/selfOnboarding/step2'">Back</button>
                    <button type="submit" class="next-button">Continue</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const photoPreview = document.getElementById('photoPreview');
        const photoInput = document.getElementById('profilePhoto');

        photoPreview.addEventListener('click', function() {
            photoInput.click();
        });

        photoInput.addEventListener('change', function(e) {
            if (e.target.files && e.target.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    photoPreview.style.backgroundImage = `url(${e.target.result})`;
                    photoPreview.innerHTML = '';
                }
                reader.readAsDataURL(e.target.files[0]);
            }
        });
    });
</script>
</body>
</html>