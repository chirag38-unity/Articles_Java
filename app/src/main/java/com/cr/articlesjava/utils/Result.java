package com.cr.articlesjava.utils;

/**
 * Generic class representing either a success result or an error result
 */
public abstract class Result<D, E extends DataError> {

    private Result() {}

    /**
     * Represents a successful result containing data
     */
    public static final class Success<D, E extends DataError> extends Result<D, E> {
        private final D data;

        public Success(D data) {
            this.data = data;
        }

        public D getData() {
            return data;
        }
    }

    /**
     * Represents an error result containing an error type
     */
    public static final class Error<D, E extends DataError> extends Result<D, E> {
        private final E error;

        public Error(E error) {
            this.error = error;
        }

        public E getError() {
            return error;
        }
    }
}
