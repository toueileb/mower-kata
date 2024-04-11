package fr.harington.parser;

import fr.harington.exception.MowerParserException;
import fr.harington.models.ComposedInstruction;
import fr.harington.models.MowerModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
@Setter
@RequiredArgsConstructor
public class MowerParser {

    private final MowerLineParser mowerLineParser;
    private Scanner scanner;

    public MowerModel parseFile(File file) throws FileNotFoundException, MowerParserException {
        var mowerModel = new MowerModel();

        scanner = new Scanner(file);
        readLawn(mowerModel);
        while (scanner.hasNext()) {
            readPositionAndFollowingInstructions(mowerModel);
        }
        scanner.close();

        return mowerModel;
    }

    private void readLawn(MowerModel mowerModel) throws MowerParserException {

        if (this.scanner.hasNext()) {
            String lawnLine = this.scanner.nextLine();
            mowerModel.setLawn(mowerLineParser.parseLawnLine(lawnLine));
        }

    }

    private void readPositionAndFollowingInstructions(MowerModel mowerModel) throws MowerParserException {
        if (scanner.hasNext()) {

            var position = mowerLineParser.parseMowerPositionLine(scanner.nextLine());
            if (scanner.hasNext()) {
                var instructions = mowerLineParser.parseInstructionLine(scanner.nextLine());
                mowerModel.getScenarioItems().add(
                        Pair.of(position,
                                ComposedInstruction.builder().instructions(instructions).build()));
            } else {
                throw new MowerParserException("Expected instructions not found");
            }
        } else {
            throw new MowerParserException("Expected position not found");
        }
    }

}

