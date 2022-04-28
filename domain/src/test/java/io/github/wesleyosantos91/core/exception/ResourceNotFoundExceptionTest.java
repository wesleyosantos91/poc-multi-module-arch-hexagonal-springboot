package io.github.wesleyosantos91.core.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("[domain] - should throw execption with message 'Exception running the method'")
    void testSomethingShouldThrowException() {

        String expectedMessage = "Exception running the method";

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(expectedMessage);
        });


        assertEquals(expectedMessage, exception.getMessage());
    }

}