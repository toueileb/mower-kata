package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.Lawn;
import fr.harington.validator.MowerLineValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LawnLineParserTest {

    @Mock
    private MowerLineValidator mowerLineValidator;
    @InjectMocks
    private LawnLineParser lawnLineParser;

    @Nested
    class TestParseLine_OK {
        @Test
        @DisplayName("should parse lawn line correctly")
        void should_parseLawnLine_correctly() throws MowerParserException {
            // Given
            var lawnLine = "5 5";
            when(mowerLineValidator.validateMowerLawn(lawnLine)).thenReturn(true);

            // When
            Lawn parsedLawnLine = lawnLineParser.parseLine(lawnLine);

            // Then
            assertAll("Lawn line parsing",
                    () -> assertNotNull(parsedLawnLine, "Parsed lawn line is null"),
                    () -> assertEquals(5, parsedLawnLine.getMaxPosition().getX(),
                            "Incorrect maximum X position"),
                    () -> assertEquals(5, parsedLawnLine.getMaxPosition().getY(),
                            "Incorrect maximum Y position")
            );
        }
    }

    @Nested
    class TestParseLine_KO {
        @Test
        @DisplayName("should throw MowerParserException for invalid lawn line format")
        void should_throwMowerParserException_forInvalidLawnLineFormat() {
            var lawnLine = "invalid";
            assertThrows(MowerParserException.class, () -> lawnLineParser.parseLine(lawnLine));
        }

    }

}
