package com.cr.articlesjava.utils;

public interface DataError {
    /**
     * Enum representing different types of network errors
     */
    enum NetworkError implements DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        CANCELLED,
        UNKNOWN
    }

    /**
     * Enum representing different types of local storage-related errors
     */
    enum Local implements DataError {
        DISK_FULL,
        UNKNOWN
    }

}
