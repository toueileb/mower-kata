package fr.harington.factory;

import fr.harington.executor.MowerScenarioExecutor;
import fr.harington.parser.InstructionLineParser;
import fr.harington.parser.LawnLineParser;
import fr.harington.parser.MowerPositionLineParser;
import fr.harington.processor.MowerMultiInstructionProcessor;
import fr.harington.processor.MowerSingleInstructionProcessor;
import fr.harington.scenario.MowerScenarioTester;
import fr.harington.validator.MowerLineValidator;
import fr.harington.validator.MowerScenarioValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MowerFactoryTest {

    @Test
    @DisplayName("should create MowerScenarioExecutor")
    void should_create_MowerScenarioExecutor() {
        var executor = MowerFactory.createMowerScenarioExecutor();
        assertAll(() -> assertNotNull(executor), () -> assertInstanceOf(MowerScenarioExecutor.class, executor));
    }

    @Test
    @DisplayName("should create MowerMultiInstructionProcessor")
    void should_create_MowerMultiInstructionProcessor() {
        var processor = MowerFactory.createMowerMultiInstructionProcessor();
        assertAll(
                () -> assertNotNull(processor),
                () -> assertInstanceOf(MowerMultiInstructionProcessor.class, processor)
        );
    }

    @Test
    @DisplayName("should create MowerScenarioValidator")
    void should_create_MowerScenarioValidator() {
        var validator = MowerFactory.createMowerScenarioValidator();
        assertAll(
                () -> assertNotNull(validator),
                () -> assertInstanceOf(MowerScenarioValidator.class, validator)
        );
    }

    @Test
    @DisplayName("should create MowerSingleInstructionProcessor")
    void should_create_MowerSingleInstructionProcessor() {
        var processor = MowerFactory.createMowerSingleInstructionProcessor();
        assertAll(
                () -> assertNotNull(processor),
                () -> assertInstanceOf(MowerSingleInstructionProcessor.class, processor)
        );
    }


    @Test
    @DisplayName("should create MowerPositionLineParser")
    void should_create_MowerPositionLineParser() {
        var positionLineParser = MowerFactory.createMowerPositionLineParser();
        assertAll(
                () -> assertNotNull(positionLineParser),
                () -> assertInstanceOf(MowerPositionLineParser.class, positionLineParser)
        );
    }

    @Test
    @DisplayName("should create MowerLineValidator")
    void should_create_MowerLineValidator() {
        var validator = MowerFactory.createMowerLineValidator();
        assertAll(
                () -> assertNotNull(validator),
                () -> assertInstanceOf(MowerLineValidator.class, validator)
        );
    }

    @Test
    @DisplayName("should create LawnLineParser")
    void should_create_LawnLineParser() {
        var lawnLineParser = MowerFactory.createLawnLineParser();
        assertAll(
                () -> assertNotNull(lawnLineParser),
                () -> assertInstanceOf(LawnLineParser.class, lawnLineParser)
        );
    }

    @Test
    @DisplayName("should create InstructionLineParser")
    void should_create_InstructionLineParser() {
        var instructionLineParser = MowerFactory.createInstructionLineParser();
        assertAll(
                () -> assertNotNull(instructionLineParser),
                () -> assertInstanceOf(InstructionLineParser.class, instructionLineParser)
        );
    }

    @Test
    @DisplayName("should create MowerScenarioTester")
    void should_create_MowerScenarioTester() {
        var mowerScenarioTester = MowerFactory.createMowerScenario();
        assertAll(
                () -> assertNotNull(mowerScenarioTester),
                () -> assertInstanceOf(MowerScenarioTester.class, mowerScenarioTester)
        );
    }
}
