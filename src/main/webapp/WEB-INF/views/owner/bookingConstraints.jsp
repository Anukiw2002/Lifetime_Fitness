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
                <!-- Updated form action to use updateConstraints -->
                <form id="constraintsForm" action="${pageContext.request.contextPath}/booking/updateConstraints" method="POST">
                    <!-- Add hidden field for constraint ID if it exists -->
                    <c:if test="${constraints != null}">
                        <input type="hidden" name="constraintId" value="${constraints.constraintId}">
                    </c:if>

                    <div class="settings-container">
                        <div class="setting-row">
                            <div class="setting-left">
                                Allow members to cancel bookings
                                <input type="number" name="cancelLimitValue" value="${constraints.cancelLimitMinutes > 0 ? constraints.cancelLimitMinutes : 0}"
                                       min="0" class="form-control setting-input">
                                <select name="cancelLimitUnit" class="form-control setting-input">
                                    <option value="minutes" selected>Minutes</option>
                                    <option value="hours">Hours</option>
                                    <option value="days">Days</option>
                                </select>
                                before appointment time
                            </div>
                            <label class="switch">
                                <input type="checkbox" checked class="setting-toggle" id="cancelToggle">
                                <span class="slider"></span>
                            </label>
                        </div>

                        <div class="setting-row">
                            <div class="setting-left">
                                Allow members to reschedule bookings
                                <input type="number" name="rescheduleLimitValue" value="${constraints.rescheduleLimitMinutes > 0 ? constraints.rescheduleLimitMinutes : 0}"
                                       min="0" class="form-control setting-input">
                                <select name="rescheduleLimitUnit" class="form-control setting-input">
                                    <option value="minutes" selected>Minutes</option>
                                    <option value="hours">Hours</option>
                                    <option value="days">Days</option>
                                </select>
                                before appointment time
                            </div>
                            <label class="switch">
                                <input type="checkbox" checked class="setting-toggle" id="rescheduleToggle">
                                <span class="slider"></span>
                            </label>
                        </div>

                        <div class="setting-row">
                            <div class="setting-left">
                                Allow members to book up to
                                <input type="number" name="minBookingGapValue" value="${constraints.minBookingGapMins > 0 ? constraints.minBookingGapMins : 0}"
                                       min="1" class="form-control setting-input">
                                <select name="minBookingGapUnit" class="form-control setting-input">
                                    <option value="minutes" selected>minutes</option>
                                    <option value="hours">hours</option>
                                </select>
                                prior to a session
                            </div>
                        </div>

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
                                Show number of bookings per slot on the schedule
                            </div>
                            <label class="switch">
                                <input type="checkbox" name="showBookingCount" class="setting-toggle"
                                ${constraints.showBookingCount ? 'checked' : ''}>
                                <span class="slider"></span>
                            </label>
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

                        <!-- Hidden fields to handle toggle states -->
                        <input type="hidden" id="cancelEnabled" name="cancelEnabled" value="true">
                        <input type="hidden" id="rescheduleEnabled" name="rescheduleEnabled" value="true">
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

<script>
    // Helper function to convert minutes to appropriate display units
    function displayTimeValue(minutes) {
        if (minutes % (24 * 60) === 0) {
            // Convert to days
            return {
                value: minutes / (24 * 60),
                unit: 'days'
            };
        } else if (minutes % 60 === 0) {
            // Convert to hours
            return {
                value: minutes / 60,
                unit: 'hours'
            };
        } else {
            // Keep as minutes
            return {
                value: minutes,
                unit: 'minutes'
            };
        }
    }

    // Set appropriate time units for pre-filled values
    function setInitialTimeUnits() {
        // Handle cancel limit
        const cancelLimitMinutes = ${constraints.cancelLimitMinutes > 0 ? constraints.cancelLimitMinutes : 0};
        if (cancelLimitMinutes > 0) {
            const cancelTimeDisplay = displayTimeValue(cancelLimitMinutes);
            document.querySelector('input[name="cancelLimitValue"]').value = cancelTimeDisplay.value;
            document.querySelector('select[name="cancelLimitUnit"]').value = cancelTimeDisplay.unit;
        }

        // Handle reschedule limit
        const rescheduleLimitMinutes = ${constraints.rescheduleLimitMinutes > 0 ? constraints.rescheduleLimitMinutes : 0};
        if (rescheduleLimitMinutes > 0) {
            const rescheduleTimeDisplay = displayTimeValue(rescheduleLimitMinutes);
            document.querySelector('input[name="rescheduleLimitValue"]').value = rescheduleTimeDisplay.value;
            document.querySelector('select[name="rescheduleLimitUnit"]').value = rescheduleTimeDisplay.unit;
        }

        // Handle min booking gap
        const minBookingGapMins = ${constraints.minBookingGapMins > 0 ? constraints.minBookingGapMins : 0};
        if (minBookingGapMins > 0) {
            const bookingGapDisplay = displayTimeValue(minBookingGapMins);
            document.querySelector('input[name="minBookingGapValue"]').value = bookingGapDisplay.value;
            if (bookingGapDisplay.unit === 'hours' || bookingGapDisplay.unit === 'minutes') {
                document.querySelector('select[name="minBookingGapUnit"]').value = bookingGapDisplay.unit;
            }
        }

        // Handle max booking advance weeks (could convert between weeks and months)
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

        // Set initial toggle states based on existing values
        if (cancelLimitMinutes <= 0) {
            document.getElementById('cancelToggle').checked = false;
            updateInputStates(document.querySelectorAll('input[name="cancelLimitValue"], select[name="cancelLimitUnit"]'), false);
        }

        if (rescheduleLimitMinutes <= 0) {
            document.getElementById('rescheduleToggle').checked = false;
            updateInputStates(document.querySelectorAll('input[name="rescheduleLimitValue"], select[name="rescheduleLimitUnit"]'), false);
        }

        if (${constraints.maxBookingsPerSlot <= 0}) {
            document.getElementById('maxBookingsToggle').checked = false;
            updateInputStates(document.querySelectorAll('input[name="maxBookingsPerSlot"]'), false);
        }
    }

    // Handle toggle changes and disable/enable corresponding inputs
    function setupToggleHandlers() {
        document.querySelectorAll('.setting-toggle').forEach(toggle => {
            const row = toggle.closest('.setting-row');
            const inputs = row.querySelectorAll('.setting-input');

            // Set initial state
            updateInputStates(inputs, toggle.checked);

            // Add change event listener
            toggle.addEventListener('change', (e) => {
                updateInputStates(inputs, e.target.checked);

                // Update hidden field values for form submission
                if (toggle.id === 'cancelToggle') {
                    document.getElementById('cancelEnabled').value = e.target.checked;
                } else if (toggle.id === 'rescheduleToggle') {
                    document.getElementById('rescheduleEnabled').value = e.target.checked;
                } else if (toggle.id === 'maxBookingsToggle') {
                    document.getElementById('maxBookingsEnabled').value = e.target.checked;
                }
            });
        });
    }

    // Update input states based on toggle
    function updateInputStates(inputs, enabled) {
        inputs.forEach(input => {
            input.disabled = !enabled;
            // Reset values when disabled but don't show it visually
            if (!enabled && input.type === 'number') {
                input.dataset.previousValue = input.value; // Store the previous value
                input.value = input.getAttribute('min') || '0';
            } else if (enabled && input.dataset.previousValue) {
                // Restore previous value if available
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

    // Form submission preparation and validation
    document.getElementById('constraintsForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default submission to do our own validation

        // Set default values for disabled toggles before submission
        if (!document.getElementById('cancelToggle').checked) {
            document.querySelector('input[name="cancelLimitValue"]').value = '0';
        }

        if (!document.getElementById('rescheduleToggle').checked) {
            document.querySelector('input[name="rescheduleLimitValue"]').value = '0';
        }

        if (!document.getElementById('maxBookingsToggle').checked) {
            document.querySelector('input[name="maxBookingsPerSlot"]').value = '0';
        }

        // Re-enable all inputs to ensure they're included in form submission
        document.querySelectorAll('.setting-input').forEach(input => {
            input.disabled = false;
        });

        // Now validate
        if (validateSettings()) {
            this.submit(); // Submit only if validation passes
        }
    });

    // Validate settings
    function validateSettings() {
        // Validate only enabled constraints
        if (document.getElementById('maxBookingsToggle').checked) {
            const maxBookingsPerSlot = parseInt(document.querySelector('input[name="maxBookingsPerSlot"]').value);
            if (maxBookingsPerSlot <= 0) {
                alert('Maximum bookings per slot must be greater than 0');
                return false;
            }
        }

        return true;
    }

    // Initialize the page when loaded
    document.addEventListener('DOMContentLoaded', () => {
        setupToggleHandlers();

        // Set the appropriate time units for pre-filled values
        if (${constraints != null}) {
            setInitialTimeUnits();
        }
    });
</script>
</body>
</html>