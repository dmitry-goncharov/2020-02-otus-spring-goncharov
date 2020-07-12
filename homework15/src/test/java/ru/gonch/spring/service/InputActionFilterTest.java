package ru.gonch.spring.service;

import org.junit.jupiter.api.Test;
import ru.gonch.spring.model.ActionType;
import ru.gonch.spring.model.InputAction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputActionFilterTest {
    @Test
    void clickFilterTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.CLICK);

        assertTrue(new InputActionFilter().accept(inputAction));
    }

    @Test
    void doubleClickFilterTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.DOUBLE_CLICK);

        assertFalse(new InputActionFilter().accept(inputAction));
    }

    @Test
    void longClickFilterTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.LONG_CLICK);

        assertFalse(new InputActionFilter().accept(inputAction));
    }

    @Test
    void scrollFilterTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.SCROLL);

        assertFalse(new InputActionFilter().accept(inputAction));
    }

    @Test
    void swipeFilterTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.SWIPE);

        assertFalse(new InputActionFilter().accept(inputAction));
    }
}
