package fr.harington.processor;

import fr.harington.exception.MowerProcessorException;
import fr.harington.models.*;
import fr.harington.models.enums.Instruction;
import fr.harington.models.enums.Orientation;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MowerMultiInstructionProcessorTest {

    private static final Coordinates MOWER_MAX_COORDINATES = Coordinates.builder().x(5).y(5).build();
    private static final Coordinates COORDINATES_1_2 = Coordinates.builder().x(1).y(2).build();
    private static final Coordinates COORDINATES_3_3 = Coordinates.builder().x(3).y(3).build();
    private static final Coordinates COORDINATES_1_3 = Coordinates.builder().x(1).y(3).build();

    @Mock
    private MowerSingleInstructionProcessor mowerSingleInstructionProcessor;

    @InjectMocks
    private MowerMultiInstructionProcessor mowerMultiInstructionProcessor;

    private static MowerModel createMowerModel() {
        var mowerModel = new MowerModel();
        var lawn = Lawn.builder().maxPosition(MOWER_MAX_COORDINATES).build();
        mowerModel.setLawn(lawn);

        var scenarioItems = new ArrayList<Pair<MowerPosition, ComposedInstruction>>();
        var position1 = new MowerPosition(COORDINATES_1_2, Orientation.NORTH);
        var composedInstruction1 = new ComposedInstruction(List.of(Instruction.LEFT, Instruction.FORWARD,
                Instruction.LEFT, Instruction.FORWARD,
                Instruction.LEFT, Instruction.FORWARD, Instruction.LEFT, Instruction.FORWARD,
                Instruction.FORWARD));
        scenarioItems.add(Pair.of(position1, composedInstruction1));

        var position2 = new MowerPosition(COORDINATES_3_3, Orientation.EAST);
        var composedInstruction2 = new ComposedInstruction(List.of(Instruction.FORWARD, Instruction.FORWARD,
                Instruction.RIGHT, Instruction.FORWARD,
                Instruction.FORWARD, Instruction.RIGHT, Instruction.FORWARD, Instruction.RIGHT,
                Instruction.RIGHT, Instruction.FORWARD));
        scenarioItems.add(Pair.of(position2, composedInstruction2));

        mowerModel.setScenarioItems(scenarioItems);
        return mowerModel;
    }

    @Test
    @DisplayName("should execute instructions for all mowers and return final positions")
    void should_executeInstructions_forAllMowers_andReturnFinalPositions() throws MowerProcessorException {

        var mowerModel = createMowerModel();

        var expectedPosition1 = new MowerPosition(COORDINATES_1_3, Orientation.NORTH);
        var expectedPosition2 = new MowerPosition(COORDINATES_1_3, Orientation.NORTH);
        var expectedPositions = List.of(MowerPosition.formatMowerPosition(expectedPosition1),
                MowerPosition.formatMowerPosition(expectedPosition2));

        when(mowerSingleInstructionProcessor.executeInstruction(any(), any(), any()))
                .thenReturn(new MowerPosition(COORDINATES_1_3, Orientation.NORTH));

        var actualPositions = mowerMultiInstructionProcessor.executeInstructions(mowerModel);

        assertEquals(expectedPositions, actualPositions);
    }

    @Test
    @DisplayName("should execute instructions and throws MowerProcessorException for null mower model")
    void should_executeInstructions_and_throw_MowerProcessorException_when_invalid_MowerModel() {
        MowerModel mowerModel = null;
        assertThrows(MowerProcessorException.class,
                () -> mowerMultiInstructionProcessor.executeInstructions(mowerModel));
    }
}

