package io.github.wesleyosantos91.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    @DisplayName("[domain] - should throw execption with message 'Exception running the method'")
    void testSomethingShouldThrowException() {

        String expectedMessage = "Exception running the method";

        Throwable exception = assertThrows(BusinessException.class, () -> {
            throw new BusinessException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("[domain] - should throw execption with message 'Exception running the method'")
    void testSomethingShouldThrowExceptionWith2Params() {

        String expectedMessage = "Exception running the method";

        Throwable exception = assertThrows(BusinessException.class, () -> {
            throw new BusinessException(expectedMessage, new Exception());
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

}