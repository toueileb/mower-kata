package fr.harington.executor;

import fr.harington.exception.MowerFileValidationException;
import fr.harington.exception.MowerParserException;
import fr.harington.exception.MowerProcessorException;
import fr.harington.parser.MowerParser;
import fr.harington.processor.MowerMultiInstructionProcessor;
import fr.harington.validator.MowerScenarioValidator;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
public class MowerScenarioExecutor {

    private final MowerParser parser;
    private final MowerMultiInstructionProcessor processor;
    private final MowerScenarioValidator mowerScenarioValidator;

    public List<String> execute(final File file) throws MowerFileValidationException, MowerParserException,
            FileNotFoundException, MowerProcessorException {
        // validate the input file
        mowerScenarioValidator.validateFile(file);
        // parse model
        var model = parser.parseFile(file);
        // execute instructions
        return processor.executeInstructions(model);
    }

}


