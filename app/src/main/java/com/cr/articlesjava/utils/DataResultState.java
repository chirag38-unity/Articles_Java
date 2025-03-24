package com.cr.articlesjava.utils;

/**
 * Represents different UI states for data loading operations
 */
public abstract class DataResultState<T> {

    private DataResultState() {}

    /**
     * Represents an idle state before any operation has started
     */
    public static final class Idle<T> extends DataResultState<T> {}

    /**
     * Represents a loading state when fetching data
     */
    public static final class Loading<T> extends DataResultState<T> {}

    /**
     * Represents a successful state containing data
     */
    public static final class Success<T> extends DataResultState<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    /**
     * Represents an error state containing an error type
     */
    public static final class Error<T> extends DataResultState<T> {
        private final DataError error;

        public Error(DataError error) {
            this.error = error;
        }

        public DataError getError() {
            return error;
        }
    }
}

