package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.enums.Instruction;
import fr.harington.validator.MowerLineValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InstructionLineParser implements LineParserStrategy<List<Instruction>> {
    private final MowerLineValidator mowerLineValidator;

    @Override
    public List<Instruction> parseLine(String instructionLine) throws MowerParserException {

        if (!mowerLineValidator.validateMowerInstructions(instructionLine)) {
            throw new MowerParserException("Invalid instruction format " + instructionLine);
        }

        return instructionLine.chars()
                .mapToObj(instruction -> Instruction.getInstructionByCode((char) instruction))
                .collect(Collectors.toList());
    }
}
