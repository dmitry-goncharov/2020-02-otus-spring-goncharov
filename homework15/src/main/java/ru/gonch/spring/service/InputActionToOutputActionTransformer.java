package ru.gonch.spring.service;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.InputAction;
import ru.gonch.spring.model.OutputAction;

@Service
public class InputActionToOutputActionTransformer implements GenericTransformer<InputAction, OutputAction> {
    @Override
    public OutputAction transform(InputAction source) {
        OutputAction outputAction = new OutputAction();
        outputAction.setTimestamp(source.getTimestamp());
        outputAction.setUserId(source.getUserId());
        outputAction.setBookId(source.getBookId());
        return outputAction;
    }
}
