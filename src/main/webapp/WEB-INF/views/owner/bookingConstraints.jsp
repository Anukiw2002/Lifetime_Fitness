<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <div class="card">
            <div class="card-header">
                <h2 class="text-center">Booking Settings</h2>
            </div>
            <div class="card-body">
                <div class="settings-container">
                    <div class="setting-row">
                        <div class="setting-left">
                            Allow members to cancel bookings
                            <input type="number" value="0" min="0" class="form-control setting-input">
                            <select class="form-control setting-input">
                                <option>Hours</option>
                                <option>Days</option>
                            </select>
                            before appointment time
                        </div>
                        <label class="switch">
                            <input type="checkbox" checked class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Allow members to reschedule bookings
                            <input type="number" value="0" min="0" class="form-control setting-input">
                            <select class="form-control setting-input">
                                <option>Hours</option>
                                <option>Days</option>
                            </select>
                            before appointment time
                        </div>
                        <label class="switch">
                            <input type="checkbox" checked class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Maximum number of reschedules per booking
                            <input type="number" value="2" min="0" class="form-control setting-input">
                        </div>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Allow members to book up to
                            <input type="number" value="2" min="1" class="form-control setting-input">
                            <select class="form-control setting-input">
                                <option>weeks</option>
                                <option>months</option>
                            </select>
                            prior to a session
                        </div>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Allow bookings up to
                            <input type="number" value="2" min="0" class="form-control setting-input">
                            hours before the session begins
                        </div>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Allow bookings after session start time
                        </div>
                        <label class="switch">
                            <input type="checkbox" class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Show number of bookings per slot on the schedule
                        </div>
                        <label class="switch">
                            <input type="checkbox" class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Maximum number of active bookings per member
                            <input type="number" value="14" min="1" class="form-control setting-input">
                        </div>
                        <label class="switch">
                            <input type="checkbox" checked class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Maximum of
                            <input type="number" value="7" min="1" class="form-control setting-input">
                            bookings per
                            <select class="form-control setting-input">
                                <option>week</option>
                                <option>month</option>
                            </select>
                        </div>
                        <label class="switch">
                            <input type="checkbox" checked class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Maximum number of bookings per slot
                            <input type="number" value="50" min="1" class="form-control setting-input">
                        </div>
                        <label class="switch">
                            <input type="checkbox" checked class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>

                    <div class="setting-row">
                        <div class="setting-left">
                            Automated notifications for cancellations and reschedules
                        </div>
                        <label class="switch">
                            <input type="checkbox" class="setting-toggle">
                            <span class="slider"></span>
                        </label>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <div class="flex justify-end gap-md">
                    <button class="btn btn-secondary">Cancel</button>
                    <button class="btn btn-primary" onclick="saveSettings()">Save Changes</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
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
            });
        });
    }

    // Update input states based on toggle
    function updateInputStates(inputs, enabled) {
        inputs.forEach(input => {
            input.disabled = !enabled;
            // Reset values when disabled
            if (!enabled && input.type === 'number') {
                input.value = input.getAttribute('min') || '0';
            }
        });
    }

    // Validate number inputs
    document.querySelectorAll('input[type="number"]').forEach(input => {
        input.addEventListener('change', function() {
            const min = parseInt(this.getAttribute('min'));
            if (this.value < min) {
                this.value = min;
            }
        });
    });

    // Save settings
    function saveSettings() {
        const settings = {};

        document.querySelectorAll('.setting-row').forEach(row => {
            const toggle = row.querySelector('.setting-toggle');
            const inputs = row.querySelectorAll('.setting-input');
            const text = row.textContent.trim().split('\n')[0];

            if (toggle) {
                settings[text] = {
                    enabled: toggle.checked,
                    values: Array.from(inputs).map(input => input.value)
                };
            } else {
                settings[text] = {
                    values: Array.from(inputs).map(input => input.value)
                };
            }
        });

        console.log('Settings to save:', settings);
        return settings;
    }

    // Validate settings
    function validateSettings() {
        const maxBookingsPerSlot = parseInt(document.querySelector('input[value="50"]').value);
        const maxActiveBookings = parseInt(document.querySelector('input[value="14"]').value);

        if (maxBookingsPerSlot < maxActiveBookings) {
            alert('Maximum bookings per slot should be greater than or equal to maximum active bookings per member');
            return false;
        }
        return true;
    }

    // Initialize the toggle handlers when the page loads
    document.addEventListener('DOMContentLoaded', () => {
        setupToggleHandlers();
    });
</script>
</body>
</html>