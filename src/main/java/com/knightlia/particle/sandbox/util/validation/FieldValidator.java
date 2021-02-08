package com.knightlia.particle.sandbox.util.validation;

@FunctionalInterface
public interface FieldValidator<T> {
    void validateField(String name, T field) throws ValidationException;
}
