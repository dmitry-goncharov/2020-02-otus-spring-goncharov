package ru.gonch.spring.service;

import org.junit.jupiter.api.Test;
import ru.gonch.spring.model.ActionType;
import ru.gonch.spring.model.InputAction;
import ru.gonch.spring.model.OutputAction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InputActionToOutputActionTransformerTest {
    @Test
    void transformerTest() {
        InputAction inputAction = new InputAction();
        inputAction.setType(ActionType.CLICK);
        inputAction.setTimestamp(10L);
        inputAction.setUserId(20L);
        inputAction.setBookId(30L);

        OutputAction outputAction = new InputActionToOutputActionTransformer().transform(inputAction);

        assertNotNull(outputAction);
        assertEquals(inputAction.getTimestamp(), outputAction.getTimestamp());
        assertEquals(inputAction.getUserId(), outputAction.getUserId());
        assertEquals(inputAction.getBookId(), outputAction.getBookId());
    }
}
