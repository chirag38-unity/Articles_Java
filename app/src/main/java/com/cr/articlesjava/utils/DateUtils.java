package com.cr.articlesjava.utils;

import android.util.Log;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * A utility class for handling date conversions and formatting.
 * Provides methods to:
 * - Convert an ISO 8601 date string to a formatted date string (e.g., "07 Mar 2025").
 * - Convert an ISO 8601 date string to a relative time string (e.g., "10 hours ago").
 */
public class DateUtils {

    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    /**
     * Converts an ISO 8601 date string (e.g., "2025-03-07T11:00:59Z") into a formatted date string (e.g., "07 Mar 2025").
     * Uses the default timezone (UTC).
     *
     * @param dateString The ISO 8601 formatted date string.
     * @return A formatted date string ("dd MMM yyyy") or "Invalid Date" if input is null or malformed.
     */
    public static String convertDateDMY(String dateString) {
        return convertDateDMY(dateString, DEFAULT_TIMEZONE);
    }

    /**
     * Converts an ISO 8601 date string into a formatted date string, using the specified timezone.
     *
     * @param dateString The ISO 8601 formatted date string.
     * @param timeZone   The timezone ID (e.g., "Asia/Kolkata", "America/New_York").
     * @return A formatted date string ("dd MMM yyyy") or "Invalid Date" if input is null or malformed.
     */
    public static String convertDateDMY(String dateString, String timeZone) {
        if (dateString == null || dateString.trim().isEmpty()) {
            Log.e("DateUtils","Error: Input date string is null or empty.");
            return "Invalid Date";
        }
        try {
            Instant instant = Instant.parse(dateString);
            return FORMATTER.withZone(ZoneId.of(timeZone)).format(instant);
        } catch (DateTimeParseException e) {
            Log.e("DateUtils", "Error: Invalid date format. Expected ISO 8601 format. Given: " + dateString);
            return "Invalid Date";
        } catch (Exception e) {
            Log.e("DateUtils","Unexpected error while parsing date: " + e.getMessage());
            return "Invalid Date";
        }
    }

    /**
     * Converts an ISO 8601 date string into a human-readable relative time string (e.g., "10 hours ago", "3 days ago").
     *
     * @param dateString The ISO 8601 formatted date string.
     * @return A relative time string (e.g., "5 minutes ago", "2 days ago") or "Invalid Date" if input is null or malformed.
     */
    public static String convertToRelativeTime(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return "Invalid Date";
        }
        try {
            Instant instant = Instant.parse(dateString);
            return getRelativeTime(instant);
        } catch (DateTimeParseException e) {
            Log.e("DateUtils","Error: Invalid date format. Expected ISO 8601 format. Given: " + dateString);
            return "Invalid Date";
        } catch (Exception e) {
            Log.e("DateUtils","Unexpected error while parsing date: " + e.getMessage());
            return "Invalid Date";
        }
    }

    /**
     * Calculates and returns a relative time description based on the difference between the given Instant and the current time.
     *
     * @param instant The Instant representing the past time.
     * @return A human-readable relative time string (e.g., "2 hours ago", "1 day ago").
     */
    private static String getRelativeTime(Instant instant) {
        Instant now = Instant.now();
        Duration duration = Duration.between(instant, now);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (seconds < 3600) {
            return (seconds / 60) + " minutes ago";
        } else if (seconds < 86400) {
            return (seconds / 3600) + " hours ago";
        } else if (seconds < 2592000) {
            return (seconds / 86400) + " days ago";
        } else if (seconds < 31536000) {
            return (seconds / 2592000) + " months ago";
        } else {
            return (seconds / 31536000) + " years ago";
        }
    }

}
