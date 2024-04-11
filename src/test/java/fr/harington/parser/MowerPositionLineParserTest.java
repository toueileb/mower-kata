package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.enums.Orientation;
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
class MowerPositionLineParserTest {

    @Mock
    private MowerLineValidator mowerLineValidator;
    @InjectMocks
    private MowerPositionLineParser mowerPositionLineParser;

    @Nested
    class TestParseLine_OK {
        @Test
        @DisplayName("should parse mower position line correctly")
        void should_parseMowerPositionLine_correctly() throws MowerParserException {
            // Given
            var mowerLine = "1 2 N";
            when(mowerLineValidator.validateMowerPosition(mowerLine)).thenReturn(true);

            // When
            var parsedMowerLine = mowerPositionLineParser.parseLine(mowerLine);

            // Then
            assertAll("Mower position parsing", () -> assertNotNull(parsedMowerLine, "Parsed mower position is null")
                    , () -> assertEquals(1, parsedMowerLine.getMowerCoordinates().getX(), "Incorrect X coordinate"),
                    () -> assertEquals(2, parsedMowerLine.getMowerCoordinates().getY(), "Incorrect Y coordinate"),
                    () -> assertEquals(Orientation.NORTH, parsedMowerLine.getMowerOrientation(), "Incorrect " +
                            "orientation"));
        }

    }

    @Nested
    class TestParseLine_KO {
        @Test
        @DisplayName("should throw MowerParserException for invalid mower position line format")
        void should_throwMowerParserException_forInvalidMowerPositionLineFormat() {
            var mowerLine = "invalid";
            assertThrows(MowerParserException.class, () -> mowerPositionLineParser.parseLine(mowerLine));
        }

    }
}
