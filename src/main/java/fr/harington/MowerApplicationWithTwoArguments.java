package fr.harington;

import fr.harington.exception.MowerException;
import fr.harington.factory.MowerFactory;
import fr.harington.scenario.MowerScenarioTester;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class MowerApplicationWithTwoArguments {
    private static final Logger LOGGER = LoggerFactory.getLogger(MowerApplicationWithTwoArguments.class);

    public static void main(String... args) throws MowerException, IOException {

        if (args.length != 2) {
            throw new MowerException("Mower scenario file path is expected as first parameter and expected positions as"
                    + " second parameter");
        }

        String instructionsFilePath = args[0];

        var expectedFinalPositions = Arrays.stream(args[1].split(",")).toList();

        MowerScenarioTester mowerScenarioTester = MowerFactory.createMowerScenario();
        mowerScenarioTester.testMowerScenario(instructionsFilePath, expectedFinalPositions);
        LOGGER.info("Mower Scenario validated with success!");
    }

}

