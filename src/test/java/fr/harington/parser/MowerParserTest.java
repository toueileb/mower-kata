package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.ComposedInstruction;
import fr.harington.models.Coordinates;
import fr.harington.models.Lawn;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.Orientation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MowerParserTest {

    protected static final String INPUT_FILES_PATH = "./src/test/resources/";
    private static final Coordinates MOWER_MAX_COORDINATES = Coordinates.builder().x(5).y(5).build();
    private static final Coordinates MOWER_INSID_COORDINATES = Coordinates.builder().x(1).y(2).build();
    @Mock
    private MowerLineParser mowerLineParser;
    @InjectMocks
    private MowerParser mowerParser;

    @Nested
    class TestParseFile_OK {
        @Test
        @DisplayName("should parse file correctly")
        void should_parseFile_correctly() throws FileNotFoundException, MowerParserException {
            // Given
            var inputFile = new File(INPUT_FILES_PATH + "single_valid_instruction.txt");
            when(mowerLineParser.parseLawnLine(any())).thenReturn(Lawn.builder().maxPosition(MOWER_MAX_COORDINATES).build());
            when(mowerLineParser.parseMowerPositionLine(any())).thenReturn(new MowerPosition(MOWER_INSID_COORDINATES,
                    Orientation.NORTH));
            when(mowerLineParser.parseInstructionLine(any())).thenReturn(List.of());

            // When
            var result = mowerParser.parseFile(inputFile);

            // Then
            assertAll("File parsing", () -> assertNotNull(result, "Parsed result is null"), () -> assertEquals(1,
                    result.getScenarioItems().size(), "Incorrect number of scenario items"), () -> {
                var pair = result.getScenarioItems().get(0);
                assertAll("First scenario item", () -> assertEquals(new MowerPosition(MOWER_INSID_COORDINATES,
                                Orientation.NORTH), pair.getLeft(), "Incorrect mower position"),
                        () -> assertEquals(new ComposedInstruction(List.of()), pair.getRight(), "Incorrect composed " +
                                "instruction"));
            });
        }
    }

    @Nested
    class TestParseFile_KO {
        @Test
        @DisplayName("should throw MowerParserException when lawn line is missing")
        void should_throwMowerParserException_whenLawnLineMissing() throws MowerParserException {
            var inputFile = new File(INPUT_FILES_PATH + "invalid_lawn_format.txt");
            when(mowerLineParser.parseLawnLine(any())).thenThrow(MowerParserException.class);
            assertThrows(MowerParserException.class, () -> mowerParser.parseFile(inputFile));
        }

        @Test
        @DisplayName("should throw MowerParserException when position line is missing")
        void should_throwMowerParserException_whenPositionLineMissing() throws MowerParserException {
            var inputFile = new File(INPUT_FILES_PATH + "invalid_mower_position.txt");
            when(mowerLineParser.parseLawnLine("5 5")).thenReturn(Lawn.builder()
                    .maxPosition(MOWER_MAX_COORDINATES).build());
            when(mowerLineParser.parseMowerPositionLine(any())).thenThrow(MowerParserException.class);
            assertThrows(MowerParserException.class, () -> mowerParser.parseFile(inputFile));
        }

        @Test
        @DisplayName("should throw MowerParserException when instruction line is missing")
        void should_throwMowerParserException_whenInstructionLineMissing() throws MowerParserException {
            var inputFile = new File(INPUT_FILES_PATH + "invalid_mower_instructions_format.txt");
            when(mowerLineParser.parseLawnLine("5 5")).thenReturn(Lawn.builder().maxPosition(MOWER_MAX_COORDINATES)
                    .build());
            when(mowerLineParser.parseMowerPositionLine("1 2 N")).thenReturn(new MowerPosition(Coordinates
                    .builder().x(1).y(2).build(), Orientation.NORTH));
            when(mowerLineParser.parseInstructionLine(any())).thenThrow(MowerParserException.class);
            assertThrows(MowerParserException.class, () -> mowerParser.parseFile(inputFile));
        }
    }
}
