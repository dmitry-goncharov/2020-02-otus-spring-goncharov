package ru.gonch.spring.service;

import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Service;
import ru.gonch.spring.model.ActionType;
import ru.gonch.spring.model.InputAction;

@Service
public class InputActionFilter implements GenericSelector<InputAction> {
    @Override
    public boolean accept(InputAction source) {
        return source.getType() == ActionType.CLICK;
    }
}
