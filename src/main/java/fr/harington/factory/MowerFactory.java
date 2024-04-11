package fr.harington.factory;

import fr.harington.executor.MowerScenarioExecutor;
import fr.harington.models.Lawn;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.Instruction;
import fr.harington.parser.*;
import fr.harington.processor.MowerMultiInstructionProcessor;
import fr.harington.processor.MowerSingleInstructionProcessor;
import fr.harington.scenario.MowerScenarioTester;
import fr.harington.validator.MowerLineValidator;
import fr.harington.validator.MowerScenarioValidator;

import java.util.List;

public class MowerFactory {

    public static MowerScenarioExecutor createMowerScenarioExecutor() {
        return new MowerScenarioExecutor(createMowerParser(), createMowerMultiInstructionProcessor(),
                createMowerScenarioValidator());
    }

    public static MowerMultiInstructionProcessor createMowerMultiInstructionProcessor() {
        return new MowerMultiInstructionProcessor(createMowerSingleInstructionProcessor());
    }

    public static MowerScenarioValidator createMowerScenarioValidator() {
        return new MowerScenarioValidator();
    }

    public static MowerSingleInstructionProcessor createMowerSingleInstructionProcessor() {
        return new MowerSingleInstructionProcessor();
    }

    private static MowerParser createMowerParser() {
        return new MowerParser(new MowerLineParser(createMowerPositionLineParser(), createLawnLineParser(),
                createInstructionLineParser()));
    }

    public static MowerScenarioTester createMowerScenario() {
        return new MowerScenarioTester(createMowerScenarioExecutor());
    }

    public static LineParserStrategy<MowerPosition> createMowerPositionLineParser() {
        return new MowerPositionLineParser(createMowerLineValidator());
    }

    public static MowerLineValidator createMowerLineValidator() {
        return new MowerLineValidator();
    }

    public static LineParserStrategy<Lawn> createLawnLineParser() {
        return new LawnLineParser(createMowerLineValidator());
    }

    public static LineParserStrategy<List<Instruction>> createInstructionLineParser() {
        return new InstructionLineParser(createMowerLineValidator());
    }
}
