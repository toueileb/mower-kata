package fr.harington.parser;


import fr.harington.exception.MowerParserException;
import fr.harington.models.Lawn;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.Instruction;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MowerLineParser {

    private final LineParserStrategy<MowerPosition> mowerPositionLineParser;
    private final LineParserStrategy<Lawn> lawnLineParser;
    private final LineParserStrategy<List<Instruction>> instructionLineParser;

    // Implement the parse methods using the appropriate strategy
    public MowerPosition parseMowerPositionLine(String mowerLine) throws MowerParserException {
        return mowerPositionLineParser.parseLine(mowerLine);
    }

    public Lawn parseLawnLine(String lawnLine) throws MowerParserException {
        return lawnLineParser.parseLine(lawnLine);
    }

    public List<Instruction> parseInstructionLine(String instructionLine) throws MowerParserException {
        return instructionLineParser.parseLine(instructionLine);
    }

}

