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
        BAD_REQUEST,
        UNKNOWN
    }

    /**
     * Enum representing different types of local storage-related errors
     */
    enum Local implements DataError {
        DISK_FULL,
        UNKNOWN
    }

    static DataError.NetworkError mapErrorWithStatusCode(int statusCode) {
        switch (statusCode) {
            case 400: return DataError.NetworkError.BAD_REQUEST;
            case 401: return DataError.NetworkError.UNAUTHORIZED;
            case 408: return DataError.NetworkError.REQUEST_TIMEOUT;
            case 409: return DataError.NetworkError.CONFLICT;
            case 413: return DataError.NetworkError.PAYLOAD_TOO_LARGE;
            case 429: return DataError.NetworkError.TOO_MANY_REQUESTS;
            case 500: return DataError.NetworkError.SERVER_ERROR;
            default: return DataError.NetworkError.UNKNOWN;
        }
    }

    static DataError.NetworkError mapExceptionWithThrowable(Throwable throwable) {
        if (throwable instanceof java.net.UnknownHostException) {
            return DataError.NetworkError.NO_INTERNET;
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            return DataError.NetworkError.REQUEST_TIMEOUT;
        } else if (throwable instanceof java.net.ConnectException) {
            return DataError.NetworkError.NO_INTERNET;
        } else if (throwable instanceof java.io.IOException) {
            return DataError.NetworkError.UNKNOWN;
        } else {
            return DataError.NetworkError.UNKNOWN;
        }
    }

}
