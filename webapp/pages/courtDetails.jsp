<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${court.courtName} - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/courtDetails.css">
</head>
<body>

<%@ include file="/includes/header.jsp" %>

<div class="breadcrumb-wrap">
    <div class="breadcrumb-container">
        <a href="${pageContext.request.contextPath}/home" class="breadcrumb-link">Home</a>
        <span class="breadcrumb-separator">›</span>
        <a href="${pageContext.request.contextPath}/courts" class="breadcrumb-link">Courts</a>
        <span class="breadcrumb-separator">›</span>
        <span class="breadcrumb-current">${court.courtName}</span>
    </div>
</div>

<main class="court-detail-page">
    <div class="court-detail-container">

        <div class="detail-message success-message"
             style="${empty bookingSuccessMessage ? 'display:none;' : 'display:block;'}">
            ${bookingSuccessMessage}
        </div>

        <div class="detail-message error-message"
             style="${empty bookingErrorMessage ? 'display:none;' : 'display:block;'}">
            ${bookingErrorMessage}
        </div>

        <div class="court-detail-grid">

            <section class="court-detail-left">
                <div class="court-detail-image-card">
                    <img src="${empty court.imagePath ? pageContext.request.contextPath.concat('/photos/default-court.jpg') : pageContext.request.contextPath.concat('/photos/').concat(court.imagePath)}"
                         alt="${court.courtName}">
                </div>

                <div class="court-detail-info-card">
                    <span class="court-detail-tag">${court.sportName}</span>
                    <h1>${court.courtName}</h1>

                    <p class="court-detail-location">
                        ${empty court.venueName ? 'Location not set' : court.venueName}
                    </p>

                    <p class="court-detail-description">
                        ${empty court.surfaceType ? 'Well-maintained court for matches and practice.' : court.surfaceType}
                    </p>

                    <div class="court-detail-meta">
                        <div class="court-detail-meta-box">
                            <span>Price</span>
                            <strong>Rs. ${court.pricePerHour}/hr</strong>
                        </div>

                        <div class="court-detail-meta-box">
                            <span>Capacity</span>
                            <strong>${court.courtCapacity} Players</strong>
                        </div>

                        <div class="court-detail-meta-box">
                            <span>Opening Hours</span>
                            <strong>6:00 AM - 9:00 PM</strong>
                        </div>

                        <div class="court-detail-meta-box">
                            <span>Sport</span>
                            <strong>${court.sportName}</strong>
                        </div>
                    </div>
                </div>
            </section>

            <aside class="court-detail-right">
                <div class="booking-card">
                    <h2>Slot Selection</h2>
                    <p class="booking-subtext">
                        Pick a date and view live slot availability. You need to login before confirming a booking.
                    </p>

                    <form action="${pageContext.request.contextPath}/bookCourt" method="post" class="booking-form" id="bookingForm">
                        <input type="hidden" name="courtId" value="${courtId}">
                        <input type="hidden" name="bookingDate" id="bookingDate" value="${selectedDate}">
                        <input type="hidden" name="timeSlotId" id="timeSlotId" value="">

                        <div class="booking-group">
                            <label>Pick a date</label>

                            <div class="calendar-card">
                                <div class="calendar-header">
                                    <button type="button" class="calendar-nav-btn" id="prevMonthBtn">&#10094;</button>
                                    <h3 id="calendarMonthYear">Month Year</h3>
                                    <button type="button" class="calendar-nav-btn" id="nextMonthBtn">&#10095;</button>
                                </div>

                                <div class="calendar-weekdays">
                                    <span>Sun</span>
                                    <span>Mon</span>
                                    <span>Tue</span>
                                    <span>Wed</span>
                                    <span>Thu</span>
                                    <span>Fri</span>
                                    <span>Sat</span>
                                </div>

                                <div class="calendar-grid" id="calendarGrid"></div>
                            </div>

                            <p class="selected-date-preview" id="selectedDatePreview">
                                Showing slots for: ${selectedDate}
                            </p>
                        </div>

                        <div class="booking-group">
                            <label>Pick a time</label>

                            <div class="slot-legend">
                                <span><i class="legend-dot booked-dot"></i>Booked</span>
                                <span><i class="legend-dot available-dot"></i>Available</span>
                                <span><i class="legend-dot selected-dot"></i>Selected</span>
                            </div>

                            <div class="detail-message error-message"
                                 style="${empty slotErrorMessage ? 'display:none;' : 'display:block;'}">
                                ${slotErrorMessage}
                            </div>

                            <div class="slot-grid">
                                <c:forEach var="slot" items="${slotList}">
                                    <button type="button"
                                            class="slot-btn ${slot.booked ? 'booked' : 'available'}"
                                            data-slot-id="${slot.timeSlotId}"
                                            ${slot.booked ? 'disabled="disabled"' : ''}>
                                        <span>${slot.startTime} - ${slot.endTime}</span>
                                        <small>Rs. ${court.pricePerHour}/hr</small>
                                    </button>
                                </c:forEach>
                            </div>

                            <p class="guest-note"
                               style="${empty slotList ? 'display:block;' : 'display:none;'}">
                                No slots found for this date.
                            </p>
                        </div>

                        <button type="submit"
                                class="book-now-btn"
                                style="${isLoggedIn ? 'display:flex;' : 'display:none;'}">
                            Confirm Booking
                        </button>

                        <a href="${pageContext.request.contextPath}/login"
                           class="login-book-btn"
                           id="loginToBookLink"
                           style="${isLoggedIn ? 'display:none;' : 'display:flex;'}">
                            Login to Book
                        </a>

                        <p class="guest-note"
                           style="${isLoggedIn ? 'display:none;' : 'display:block;'}">
                            You can browse available dates and time slots, but you need to login before confirming your booking.
                        </p>
                    </form>
                </div>
            </aside>

        </div>
    </div>
</main>

<%@ include file="/includes/footer.jsp" %>

<script>
    const calendarGrid = document.getElementById("calendarGrid");
    const calendarMonthYear = document.getElementById("calendarMonthYear");
    const prevMonthBtn = document.getElementById("prevMonthBtn");
    const nextMonthBtn = document.getElementById("nextMonthBtn");

    const selectedDateInput = document.getElementById("bookingDate");
    const selectedDatePreview = document.getElementById("selectedDatePreview");
    const timeSlotIdInput = document.getElementById("timeSlotId");
    const bookingForm = document.getElementById("bookingForm");
    const loginToBookLink = document.getElementById("loginToBookLink");

    const slotButtons = document.querySelectorAll(".slot-btn.available");

    const today = new Date();
    const todayOnly = new Date(today.getFullYear(), today.getMonth(), today.getDate());

    // Past dates disabled
    const allowPastDates = false;

    let selectedDate = selectedDateInput.value
        ? new Date(selectedDateInput.value + "T00:00:00")
        : new Date();

    let currentMonth = selectedDate.getMonth();
    let currentYear = selectedDate.getFullYear();

    function formatDateForInput(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        return year + "-" + month + "-" + day;
    }

    function formatDateForDisplay(date) {
        return date.toLocaleDateString("en-US", {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "numeric"
        });
    }

    function updateLoginLink() {
        if (!loginToBookLink) return;

        let loginUrl = "${pageContext.request.contextPath}/login";
        const selectedDateValue = selectedDateInput.value;
        const selectedSlotValue = timeSlotIdInput.value;
        const courtIdValue = "${courtId}";

        const params = new URLSearchParams();

        if (courtIdValue) {
            params.append("courtId", courtIdValue);
        }

        if (selectedDateValue) {
            params.append("bookingDate", selectedDateValue);
        }

        if (selectedSlotValue) {
            params.append("timeSlotId", selectedSlotValue);
        }

        if ([...params].length > 0) {
            loginUrl += "?" + params.toString();
        }

        loginToBookLink.setAttribute("href", loginUrl);
    }

    function renderCalendar(month, year) {
        calendarGrid.innerHTML = "";

        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const daysInPrevMonth = new Date(year, month, 0).getDate();

        const monthName = new Date(year, month).toLocaleString("en-US", {
            month: "long",
            year: "numeric"
        });

        calendarMonthYear.textContent = monthName;

        for (let i = firstDay - 1; i >= 0; i--) {
            const prevDate = document.createElement("button");
            prevDate.type = "button";
            prevDate.className = "calendar-date other-month";
            prevDate.textContent = daysInPrevMonth - i;
            prevDate.disabled = true;
            calendarGrid.appendChild(prevDate);
        }

        for (let day = 1; day <= daysInMonth; day++) {
            const dateButton = document.createElement("button");
            dateButton.type = "button";
            dateButton.className = "calendar-date";
            dateButton.textContent = day;

            const currentDateOnly = new Date(year, month, day);

            if (!allowPastDates && currentDateOnly < todayOnly) {
                dateButton.classList.add("past-date");
                dateButton.disabled = true;
            }

            if (
                day === today.getDate() &&
                month === today.getMonth() &&
                year === today.getFullYear()
            ) {
                dateButton.classList.add("today-date");
            }

            if (
                selectedDate &&
                day === selectedDate.getDate() &&
                month === selectedDate.getMonth() &&
                year === selectedDate.getFullYear()
            ) {
                dateButton.classList.add("selected-date");
            }

            if (!(currentDateOnly < todayOnly && !allowPastDates)) {
                dateButton.addEventListener("click", function() {
                    selectedDate = new Date(year, month, day);
                    const newDateValue = formatDateForInput(selectedDate);

                    selectedDateInput.value = newDateValue;
                    selectedDatePreview.textContent = "Showing slots for: " + formatDateForDisplay(selectedDate);

                    window.location.href =
                        "${pageContext.request.contextPath}/courtDetails?courtId=${courtId}&bookingDate=" + newDateValue;
                });
            }

            calendarGrid.appendChild(dateButton);
        }

        const totalCells = firstDay + daysInMonth;
        const nextMonthCells = 42 - totalCells;

        for (let day = 1; day <= nextMonthCells; day++) {
            const nextDate = document.createElement("button");
            nextDate.type = "button";
            nextDate.className = "calendar-date other-month";
            nextDate.textContent = day;
            nextDate.disabled = true;
            calendarGrid.appendChild(nextDate);
        }
    }

    prevMonthBtn.addEventListener("click", function() {
        currentMonth--;

        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }

        renderCalendar(currentMonth, currentYear);
    });

    nextMonthBtn.addEventListener("click", function() {
        currentMonth++;

        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }

        renderCalendar(currentMonth, currentYear);
    });

    slotButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            slotButtons.forEach(function(btn) {
                btn.classList.remove("selected");
            });

            button.classList.add("selected");
            timeSlotIdInput.value = button.getAttribute("data-slot-id");
            updateLoginLink();
        });
    });

    bookingForm.addEventListener("submit", function(e) {
        const isLoggedIn = ${isLoggedIn ? 'true' : 'false'};

        if (!selectedDateInput.value || !timeSlotIdInput.value) {
            e.preventDefault();
            alert("Please select both a date and a time slot.");
            return;
        }

        if (!isLoggedIn) {
            e.preventDefault();
            window.location.href = loginToBookLink.getAttribute("href");
        }
    });

    renderCalendar(currentMonth, currentYear);
    updateLoginLink();

    if (selectedDateInput.value) {
        selectedDatePreview.textContent =
            "Showing slots for: " + formatDateForDisplay(new Date(selectedDateInput.value + "T00:00:00"));
    }
</script>
</body>
</html>