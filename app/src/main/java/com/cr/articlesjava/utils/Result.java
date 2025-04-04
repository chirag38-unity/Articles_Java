package com.cr.articlesjava.utils;

import java.util.function.Consumer;

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

        @Override
        public Result<D, E> onSuccess(Consumer<? super D> action) {
            if (action != null) action.accept(data);
            return this;
        }

        @Override
        public Result<D, E> onError(Consumer<? super E> action) {
            return this; // No-op: This is a success state, so no error
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

        @Override
        public Result<D, E> onSuccess(Consumer<? super D> action) {
            return this; // No-op: This is an error state, so no success action
        }

        @Override
        public Result<D, E> onError(Consumer<? super E> action) {
            if (action != null) action.accept(error);
            return this;
        }
    }

    /**
     * Define abstract methods to be overridden in subclasses
     */
    public abstract Result<D, E> onSuccess(Consumer<? super D> action);
    public abstract Result<D, E> onError(Consumer<? super E> action);

}
