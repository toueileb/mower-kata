package fr.harington.processor;

import fr.harington.exception.MowerProcessorException;
import fr.harington.models.Coordinates;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.Instruction;
import fr.harington.models.enums.Orientation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MowerSingleInstructionProcessorTest {

    private static final Coordinates MOWER_MAX_COORDINATES = Coordinates.builder().x(5).y(5).build();
    private static final Coordinates MOWER_INSID_COORDINATES = Coordinates.builder().x(1).y(1).build();
    @InjectMocks
    private MowerSingleInstructionProcessor mowerSingleInstructionProcessor;

    @Nested
    class TestExecuteInstruction_OK {
        @Test
        @DisplayName("should move mower forward correctly")
        void should_moveMowerForward_correctly() throws MowerProcessorException {
            // Given
            var initialPosition = new MowerPosition(MOWER_INSID_COORDINATES, Orientation.NORTH);

            // When
            var actualPosition = mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                    Instruction.FORWARD, MOWER_MAX_COORDINATES);

            // Then
            assertAll("Move mower forward correctly",
                    () -> assertNotNull(actualPosition, "Actual position is null"),
                    () -> assertEquals(Coordinates.builder().x(1).y(2).build(), actualPosition.getMowerCoordinates(),
                            "Incorrect coordinates after moving forward"),
                    () -> assertEquals(Orientation.NORTH, actualPosition.getMowerOrientation(),
                            "Incorrect orientation after moving forward")
            );
        }

        @Test
        @DisplayName("should move mower right correctly")
        void should_moveMowerRight_correctly() throws MowerProcessorException {
            // Given
            var initialPosition = new MowerPosition(MOWER_INSID_COORDINATES, Orientation.NORTH);

            // When
            var actualPosition = mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                    Instruction.RIGHT, MOWER_MAX_COORDINATES);

            // Then
            assertAll("Move mower right correctly",
                    () -> assertNotNull(actualPosition, "Actual position is null"),
                    () -> assertEquals(Coordinates.builder().x(1).y(1).build(), actualPosition.getMowerCoordinates(),
                            "Incorrect coordinates after moving right"),
                    () -> assertEquals(Orientation.EAST, actualPosition.getMowerOrientation(),
                            "Incorrect orientation after moving right")
            );
        }

        @Test
        @DisplayName("should move mower left correctly")
        void should_moveMowerLeft_correctly() throws MowerProcessorException {
            // Given
            var initialPosition = new MowerPosition(MOWER_INSID_COORDINATES, Orientation.EAST);

            // When
            var actualPosition = mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                    Instruction.LEFT, MOWER_MAX_COORDINATES);

            // Then
            assertAll("Move mower left correctly",
                    () -> assertNotNull(actualPosition, "Actual position is null"),
                    () -> assertEquals(Coordinates.builder().x(1).y(1).build(), actualPosition.getMowerCoordinates(),
                            "Incorrect coordinates after moving left"),
                    () -> assertEquals(Orientation.NORTH, actualPosition.getMowerOrientation(),
                            "Incorrect orientation after moving left")
            );
        }
    }

    @Nested
    class TestExecuteInstruction_KO {

        @Test
        @DisplayName("should throw MowerProcessorException when invalid mower position")
        void should_executeInstruction_and_throw_MowerProcessorException_when_invalid_position() {

            // When Then
            assertThrows(MowerProcessorException.class,
                    () -> mowerSingleInstructionProcessor.executeInstruction(null,
                            Instruction.LEFT, MOWER_MAX_COORDINATES));

        }

        @Test
        @DisplayName("should throw MowerProcessorException when invalid mower orientation")
        void should_executeInstruction_and_throw_MowerProcessorException_when_invalid_orientation() {
            // Given
            var initialPosition = new MowerPosition(MOWER_MAX_COORDINATES, null);

            // When Then
            assertThrows(MowerProcessorException.class,
                    () -> mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                            Instruction.LEFT, MOWER_MAX_COORDINATES));

        }

        @Test
        @DisplayName("should throw MowerProcessorException when invalid mower instructions")
        void should_executeInstruction_and_throw_MowerProcessorException_when_invalid_instructions() {
            // Given
            var initialPosition = new MowerPosition(MOWER_MAX_COORDINATES, Orientation.NORTH);

            // When Then
            assertThrows(MowerProcessorException.class,
                    () -> mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                            null, MOWER_MAX_COORDINATES));

        }

        @Test
        @DisplayName("should throw MowerProcessorException when invalid max coordinates")
        void should_executeInstruction_and_throw_MowerProcessorException_when_invalid_maw_coordinates() {
            // Given
            var initialPosition = new MowerPosition(MOWER_MAX_COORDINATES, Orientation.NORTH);

            // When Then
            assertThrows(MowerProcessorException.class,
                    () -> mowerSingleInstructionProcessor.executeInstruction(initialPosition,
                            Instruction.FORWARD, null));

        }

    }
}
