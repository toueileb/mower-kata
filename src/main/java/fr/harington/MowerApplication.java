package fr.harington;

import fr.harington.exception.MowerException;
import fr.harington.factory.MowerFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@RequiredArgsConstructor
public class MowerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MowerApplication.class);

    public static void main(String... args) {

        var scanner = new Scanner(System.in);
        var continueProcessingFile = true;

        do {
            LOGGER.info("Please provide the instructions file path: ");
            var instructionsFilePath = scanner.nextLine();
            try {
                var mowerScenarioExecutor = MowerFactory.createMowerScenarioExecutor();
                var mowerFinalPositions = mowerScenarioExecutor.execute(new File(instructionsFilePath));
                LOGGER.info("Final  mower positions : {} ", mowerFinalPositions.toString());

            } catch (MowerException | FileNotFoundException ex) {
                LOGGER.error("Error during execution: {}", ex.getMessage());
            }

            LOGGER.info("Do you want to continue and process another file? (Y/N)");
            continueProcessingFile = scanner.nextLine().trim().equalsIgnoreCase("Y");

        } while (continueProcessingFile);

        LOGGER.info("Terminating program execution.");
        scanner.close();

    }

}

