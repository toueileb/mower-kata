package fr.harington.validator;

import fr.harington.exception.MowerFileValidationException;
import fr.harington.models.enums.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MowerScenarioValidatorTest {
    @InjectMocks
    private MowerScenarioValidator validator;
    @Mock
    private File instructionsFile;

    @Test
    @DisplayName("should_throw_exception_for_nonexistent_file")
    void should_throw_exception_for_nonexistent_file() {
        lenient().when(instructionsFile.exists()).thenReturn(false);
        var exception = assertThrows(MowerFileValidationException.class,
                () -> validator.validateFile(instructionsFile));
        assertEquals(ErrorMessage.ERROR_FILE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should_throw_exception_for_empty_file")
    void should_throw_exception_for_empty_file() {
        when(instructionsFile.exists()).thenReturn(true);
        when(instructionsFile.isFile()).thenReturn(true);
        when(instructionsFile.length()).thenReturn(0L);
        var exception = assertThrows(MowerFileValidationException.class,
                () -> validator.validateFile(instructionsFile));
        assertEquals("Instructions file with path " + instructionsFile.getAbsolutePath() + " is empty !",
                exception.getMessage());
    }

    @Test
    @DisplayName("should_throw_exception_for_non_txt_file")
    void should_throw_exception_for_non_txt_file() {
        when(instructionsFile.exists()).thenReturn(true);
        when(instructionsFile.isFile()).thenReturn(true);
        when(instructionsFile.length()).thenReturn(10L);
        when(instructionsFile.getAbsolutePath()).thenReturn("test.pdf");
        var exception = assertThrows(MowerFileValidationException.class,
                () -> validator.validateFile(instructionsFile));
        assertEquals("Wrong file format. Only .txt files are allowed.", exception.getMessage());
    }

    @Test
    @DisplayName("should_not_throw_exception_for_valid_file")
    void should_not_throw_exception_for_valid_file() {
        when(instructionsFile.exists()).thenReturn(true);
        when(instructionsFile.isFile()).thenReturn(true);
        when(instructionsFile.length()).thenReturn(10L);
        when(instructionsFile.getAbsolutePath()).thenReturn("test.txt");
        assertDoesNotThrow(() -> validator.validateFile(instructionsFile));
    }
}
