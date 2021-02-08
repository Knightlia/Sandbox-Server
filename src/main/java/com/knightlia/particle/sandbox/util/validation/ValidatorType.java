package com.knightlia.particle.sandbox.util.validation;

public class ValidatorType {

    public static <T> void nullCheck(String name, T field) throws ValidationException {
        if (field == null) {
            throw new ValidationException(name + " should not be null.");
        }
    }

    public static void stringLength(String name, String field) throws ValidationException {
        if (field.length() <= 0 || field.length() > 100) {
            throw new ValidationException(name + " should not have more than 100 characters.");
        }
    }

    public static void greaterThanZero(String name, long field) throws ValidationException {
        if (field <= 0) {
            throw new ValidationException(name + " should be greater than 0.");
        }
    }
}
