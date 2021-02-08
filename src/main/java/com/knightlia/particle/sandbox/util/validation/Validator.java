package com.knightlia.particle.sandbox.util.validation;

import java.util.List;
import java.util.function.Supplier;

import com.knightlia.particle.sandbox.model.request.NicknameRequest;
import com.knightlia.particle.sandbox.model.request.MessageRequest;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Validator {

    public String validateNicknameRequest(NicknameRequest nicknameRequest) {
        try {
            validateField("Nickname", nicknameRequest::getNickname, asList(ValidatorType::nullCheck, ValidatorType::stringLength));
            return null;
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    public String validateMessageRequest(MessageRequest messageRequest) {
        try {
            validateField("Sender", messageRequest::getSender, asList(ValidatorType::nullCheck, ValidatorType::stringLength));
            validateField("Message", messageRequest::getMessage, singletonList(ValidatorType::nullCheck));
            validateField("Time", messageRequest::getTime, asList(ValidatorType::nullCheck, ValidatorType:: greaterThanZero));
            return null;
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    private <T> void validateField(String field, Supplier<T> supplier, List<FieldValidator<T>> validators) throws ValidationException {
        for (FieldValidator<T> validator : validators) {
            validator.validateField(field, supplier.get());
        }
    }
}
