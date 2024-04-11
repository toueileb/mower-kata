package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.enums.Instruction;
import fr.harington.validator.MowerLineValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructionLineParserTest {

    @Mock
    private MowerLineValidator mowerLineValidator;
    @InjectMocks
    private InstructionLineParser instructionLineParser;

    @Nested
    class TestParseLine_OK {
        @Test
        @DisplayName("should parse instruction line correctly")
        void should_parseInstructionLine_correctly() throws MowerParserException {
            // Given
            var instructionLine = "GDAGD";
            when(mowerLineValidator.validateMowerInstructions(instructionLine)).thenReturn(true);

            // When
            List<Instruction> parsedInstructionLine = instructionLineParser.parseLine(instructionLine);

            // Then
            assertAll("Instruction line parsing", () -> assertNotNull(parsedInstructionLine, "Parsed instruction line" +
                            " is null"), () -> assertEquals(5, parsedInstructionLine.size(), "Incorrect number of " +
                            "instructions"), () -> assertEquals(Instruction.LEFT, parsedInstructionLine.get(0),
                            "First " +
                            "instruction should be LEFT"), () -> assertEquals(Instruction.RIGHT,
                            parsedInstructionLine.get(1)
                            , "Second instruction should be " + "RIGHT"), () -> assertEquals(Instruction.FORWARD,
                            parsedInstructionLine.get(2), "Third instruction should be " + "FORWARD"),
                    () -> assertEquals(Instruction.LEFT, parsedInstructionLine.get(3), "Fourth instruction should be " +
                            "LEFT"), () -> assertEquals(Instruction.RIGHT, parsedInstructionLine.get(4), "Fifth " +
                            "instruction should be RIGHT"));
        }

    }

    @Nested
    class TestParseLine_KO {
        @Test
        @DisplayName("should throw MowerParserException for invalid instruction line format")
        void should_throwMowerParserException_forInvalidInstructionLineFormat() {
            var instructionLine = "invalid";
            assertThrows(MowerParserException.class, () -> instructionLineParser.parseLine(instructionLine));
        }

    }

}
