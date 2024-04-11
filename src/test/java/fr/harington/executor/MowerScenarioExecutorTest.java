package fr.harington.executor;

import fr.harington.exception.MowerFileValidationException;
import fr.harington.exception.MowerParserException;
import fr.harington.exception.MowerProcessorException;
import fr.harington.models.*;
import fr.harington.models.enums.Instruction;
import fr.harington.models.enums.Orientation;
import fr.harington.parser.MowerParser;
import fr.harington.processor.MowerMultiInstructionProcessor;
import fr.harington.validator.MowerScenarioValidator;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MowerScenarioExecutorTest {

    @Mock
    private MowerParser mowerParser;
    @Mock
    private MowerMultiInstructionProcessor mowerMultiInstructionProcessor;
    @Mock
    private MowerScenarioValidator mowerScenarioValidator;
    @InjectMocks
    private MowerScenarioExecutor mowerScenarioExecutor;
    @Mock
    private File mockFile;

    @Test
    @DisplayName("should throw exception for invalid file")
    void should_throw_exception_for_invalid_file() throws MowerFileValidationException {
        doThrow(MowerFileValidationException.class).when(mowerScenarioValidator).validateFile(any());
        assertThrows(MowerFileValidationException.class, () ->
                mowerScenarioExecutor.execute(mockFile)
        );
    }

    @Test
    @DisplayName("should throw exception for parser exception")
    void should_throw_exception_for_parser_exception() throws MowerParserException, FileNotFoundException {
        when(mowerParser.parseFile(any())).thenThrow(MowerParserException.class);
        assertThrows(MowerParserException.class, () ->
                mowerScenarioExecutor.execute(mockFile)
        );
    }

    @Test
    @DisplayName("should_throw_exception_for_processor_exception")
    void should_throw_exception_for_processor_exception() throws MowerProcessorException {
        when(mowerMultiInstructionProcessor.executeInstructions(any())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () ->
                mowerScenarioExecutor.execute(mockFile)
        );
    }

    @Test
    @DisplayName("should_return_expected_output")
    void should_return_expected_output() throws MowerFileValidationException, MowerParserException,
            FileNotFoundException, MowerProcessorException {
        var mowerModel = createMockMowerModel();
        var expectedOutput = List.of("1 3 N", "5 1 E");
        when(mowerParser.parseFile(any())).thenReturn(mowerModel);
        when(mowerMultiInstructionProcessor.executeInstructions(any())).thenReturn(expectedOutput);
        var result = mowerScenarioExecutor.execute(mockFile);
        assertTrue(result.containsAll(expectedOutput));
    }

    private MowerModel createMockMowerModel() {
        var mowerModel = new MowerModel();
        var lawn = Lawn.builder().maxPosition(Coordinates.builder().x(5).y(5).build()).build();
        mowerModel.setLawn(lawn);
        var scenarioItems = List.of(
                Pair.of(new MowerPosition(Coordinates.builder().x(1).y(2).build(), Orientation.NORTH),
                        new ComposedInstruction(List.of(Instruction.LEFT, Instruction.FORWARD, Instruction.LEFT,
                                Instruction.FORWARD,
                                Instruction.LEFT, Instruction.FORWARD, Instruction.LEFT, Instruction.FORWARD,
                                Instruction.FORWARD))),
                Pair.of(new MowerPosition(Coordinates.builder().x(3).y(3).build(), Orientation.EAST),
                        new ComposedInstruction(List.of(Instruction.FORWARD, Instruction.FORWARD, Instruction.RIGHT,
                                Instruction.FORWARD,
                                Instruction.FORWARD, Instruction.RIGHT, Instruction.FORWARD, Instruction.RIGHT,
                                Instruction.RIGHT, Instruction.FORWARD))));
        mowerModel.setScenarioItems(scenarioItems);
        return mowerModel;
    }
}
