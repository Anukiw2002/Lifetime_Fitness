<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking Settings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generalStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookingConstraints.css">
</head>
<body>
<jsp:include page="../common/verticalNavBar.jsp" />
<div class="main-content">
    <div class="container">
        <div class="flex justify-end items-center mb-4">
            <button class="btn btn-primary" onclick="location.href='/booking/viewBlockedDate'">
                <i class="fas fa-calendar-xmark"></i> Blocked Sessions
            </button>
        </div>
        <c:if test="${param.status eq 'updateSuccess'}">
            <div class="alert alert-success">
                Booking constraints updated successfully!
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <h2 class="text-center">Booking Settings</h2>
            </div>
            <div class="card-body">
                <!-- Separate form for blocking dates -->
                <div class="card blocked-dates-section">
                    <h3>Block Time Slots</h3>
                    <c:if test="${not empty blockDateError}">
                        <div class="alert alert-danger">
                                ${blockDateError}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/booking/blockDate" method="POST" id="blockDateForm">
                        <div class="form-group">
                            <label class="form-label">Date</label>
                            <input type="date" name="blockDate" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label class="full-day-check">
                                <input type="checkbox" name="isFullDay" id="isFullDay">
                                <span>Block full day</span>
                            </label>
                        </div>

                        <div class="time-fields" id="timeFields">
                            <div class="form-group">
                                <label class="form-label">Start Time</label>
                                <input type="time" name="startTime" id="startTime" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label class="form-label">End Time</label>
                                <input type="time" name="endTime" id="endTime" class="form-control" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Reason</label>
                            <input type="text" name="reason" class="form-control" placeholder="Enter reason for blocking (e.g., Maintenance, Holiday)" required>
                        </div>

                        <div class="form-buttons">
                            <button type="button" class="btn btn-secondary" onclick="resetBlockDateForm()">Clear</button>
                            <button type="submit" class="btn btn-primary">Block Time Slot</button>
                        </div>
                    </form>
                </div>

                <!-- Form for updating constraints -->
                <div class="card blocked-dates-section">
                <form action="${pageContext.request.contextPath}/booking/updateConstraints" method="POST" id="constraintsForm">
                    <c:if test="${constraints != null}">
                        <input type="hidden" name="constraintId" value="${constraints.constraintId}">
                    </c:if>

                    <div class="settings-container">

                        <div class="setting-row">
                            <div class="setting-left">
                                Allow bookings up to
                                <input type="number" name="maxBookingAdvanceValue" value="${constraints.maxBookingAdvanceWeeks > 0 ? constraints.maxBookingAdvanceWeeks : 1}"
                                       min="0" class="form-control setting-input">
                                <select name="maxBookingAdvanceUnit" class="form-control setting-input">
                                    <option value="weeks" selected>weeks</option>
                                    <option value="months">months</option>
                                </select>
                                before the session begins
                            </div>
                        </div>

                        <div class="setting-row">
                            <div class="setting-left">
                                Maximum number of members per slot
                                <input type="number" name="maxBookingsPerSlot" value="${constraints.maxBookingsPerSlot > 0 ? constraints.maxBookingsPerSlot : 50}"
                                       min="1" class="form-control setting-input">
                            </div>
                            <label class="switch">
                                <input type="checkbox" checked class="setting-toggle" id="maxBookingsToggle">
                                <span class="slider"></span>
                            </label>
                        </div>
                        <input type="hidden" id="maxBookingsEnabled" name="maxBookingsEnabled" value="true">
                    </div>

                    <div class="card-footer">
                        <div class="flex justify-end gap-md">
                            <button type="button" class="btn btn-secondary" onclick="window.history.back();">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Helper function to convert minutes to appropriate display units
    function displayTimeValue(minutes) {
        if (minutes % (24 * 60) === 0) {
            return {
                value: minutes / (24 * 60),
                unit: 'days'
            };
        } else if (minutes % 60 === 0) {
            return {
                value: minutes / 60,
                unit: 'hours'
            };
        } else {
            return {
                value: minutes,
                unit: 'minutes'
            };
        }
    }

    // Set appropriate time units for pre-filled values
    function setInitialTimeUnits() {
        const maxBookingAdvanceWeeks = ${constraints.maxBookingAdvanceWeeks > 0 ? constraints.maxBookingAdvanceWeeks : 1};
        if (maxBookingAdvanceWeeks > 0) {
            if (maxBookingAdvanceWeeks % 4 === 0) {
                document.querySelector('input[name="maxBookingAdvanceValue"]').value = maxBookingAdvanceWeeks / 4;
                document.querySelector('select[name="maxBookingAdvanceUnit"]').value = 'months';
            } else {
                document.querySelector('input[name="maxBookingAdvanceValue"]').value = maxBookingAdvanceWeeks;
                document.querySelector('select[name="maxBookingAdvanceUnit"]').value = 'weeks';
            }
        }

        if (${constraints.maxBookingsPerSlot <= 0}) {
            document.getElementById('maxBookingsToggle').checked = false;
            updateInputStates(document.querySelectorAll('input[name="maxBookingsPerSlot"]'), false);
        }
    }

    // Handle toggle changes
    function setupToggleHandlers() {
        document.querySelectorAll('.setting-toggle').forEach(toggle => {
            const row = toggle.closest('.setting-row');
            const inputs = row.querySelectorAll('.setting-input');

            updateInputStates(inputs, toggle.checked);

            toggle.addEventListener('change', (e) => {
                updateInputStates(inputs, e.target.checked);

                if (toggle.id === 'maxBookingsToggle') {
                    document.getElementById('maxBookingsEnabled').value = e.target.checked;
                }
            });
        });
    }

    function updateInputStates(inputs, enabled) {
        inputs.forEach(input => {
            input.disabled = !enabled;
            if (!enabled && input.type === 'number') {
                input.dataset.previousValue = input.value;
                input.value = input.getAttribute('min') || '0';
            } else if (enabled && input.dataset.previousValue) {
                input.value = input.dataset.previousValue;
                delete input.dataset.previousValue;
            }
        });
    }

    // Validate number inputs
    document.querySelectorAll('input[type="number"]').forEach(input => {
        input.addEventListener('change', function() {
            const min = parseInt(this.getAttribute('min') || 0);
            if (this.value < min) {
                this.value = min;
            }
        });
    });

    // Form submission for constraints
    document.getElementById('constraintsForm').addEventListener('submit', function(e) {
        e.preventDefault();

        if (!document.getElementById('maxBookingsToggle').checked) {
            document.querySelector('input[name="maxBookingsPerSlot"]').value = '0';
        }

        document.querySelectorAll('.setting-input').forEach(input => {
            input.disabled = false;
        });

        if (validateSettings()) {
            this.submit();
        }
    });

    function validateSettings() {
        if (document.getElementById('maxBookingsToggle').checked) {
            const maxBookingsPerSlot = parseInt(document.querySelector('input[name="maxBookingsPerSlot"]').value);
            if (maxBookingsPerSlot <= 0) {
                alert('Maximum bookings per slot must be greater than 0');
                return false;
            }
        }
        return true;
    }

    // Handle full day toggle for blocked dates
    document.getElementById('isFullDay').addEventListener('change', function() {
        const timeFields = document.getElementById('timeFields');
        timeFields.style.display = this.checked ? 'none' : 'flex';

        if (this.checked) {
            document.getElementById('startTime').removeAttribute('required');
            document.getElementById('endTime').removeAttribute('required');
        } else {
            document.getElementById('startTime').setAttribute('required', 'true');
            document.getElementById('endTime').setAttribute('required', 'true');
        }
    });

    // Initialize the page
    document.addEventListener('DOMContentLoaded', () => {
        setupToggleHandlers();
        if (${constraints != null}) {
            setInitialTimeUnits();
        }
    });

    //To block past dates in the calendar
    document.addEventListener('DOMContentLoaded', function() {
        var today = new Date();
        var yyyy = today.getFullYear();
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var dd = String(today.getDate()).padStart(2, '0');
        var minDate = yyyy + '-' + mm + '-' + dd;
        document.querySelector('input[name="blockDate"]').setAttribute('min', minDate);
    });

    //If today is selected make sure that the end time is after start time
    document.addEventListener('DOMContentLoaded', function() {
        const dateInput = document.querySelector('input[name="blockDate"]');
        const startTimeInput = document.getElementById('startTime');
        const endTimeInput = document.getElementById('endTime');

        function pad(n) { return n < 10 ? '0' + n : n; }

        function setTimeLimitsForToday() {
            const now = new Date();
            const minTime = pad(now.getHours()) + ':' + pad(now.getMinutes());
            startTimeInput.min = minTime;
            endTimeInput.min = minTime;
        }

        function resetTimeLimits() {
            startTimeInput.min = '00:00';
            endTimeInput.min = '00:00';
        }

        dateInput.addEventListener('change', function() {
            const selectedDate = new Date(this.value);
            const today = new Date();
            today.setHours(0,0,0,0);

            if (selectedDate.getTime() === today.getTime()) {
                setTimeLimitsForToday();
            } else {
                resetTimeLimits();
            }
        });

        // On page load, if today is already selected
        if (dateInput.value) {
            const selectedDate = new Date(dateInput.value);
            const today = new Date();
            today.setHours(0,0,0,0);
            if (selectedDate.getTime() === today.getTime()) {
                setTimeLimitsForToday();
            }
        }
    });

    document.getElementById('blockDateForm').addEventListener('submit', function(e) {
        const isFullDay = document.getElementById('isFullDay').checked;
        const dateInput = document.querySelector('input[name="blockDate"]');
        const startTimeInput = document.getElementById('startTime');
        const endTimeInput = document.getElementById('endTime');
        const reasonInput = document.querySelector('input[name="reason"]');

        // Clear previous error if any
        let errorMsg = '';

        // Validate required fields
        if (!dateInput.value) {
            errorMsg = 'Date is required.';
        } else if (!isFullDay) {
            if (!startTimeInput.value || !endTimeInput.value) {
                errorMsg = 'Start and end time are required unless blocking full day.';
            } else if (startTimeInput.value >= endTimeInput.value) {
                errorMsg = 'End time must be after start time.';
            } else {
                // If today, check if start time is in the past
                const today = new Date();
                const selectedDate = new Date(dateInput.value);
                if (selectedDate.toDateString() === today.toDateString()) {
                    const now = today.getHours() + ':' + String(today.getMinutes()).padStart(2, '0');
                    if (startTimeInput.value < now) {
                        errorMsg = 'Start time must be after the current time for today.';
                    }
                }
            }
        }
        if (!reasonInput.value.trim()) {
            errorMsg = 'Reason is required.';
        }

        if (errorMsg) {
            e.preventDefault();
            // Show error above the form
            let alertDiv = document.querySelector('.block-date-error');
            if (!alertDiv) {
                alertDiv = document.createElement('div');
                alertDiv.className = 'alert alert-danger block-date-error';
                this.parentNode.insertBefore(alertDiv, this);
            }
            alertDiv.textContent = errorMsg;
            return false;
        }
    });


</script>
</body>
</html>