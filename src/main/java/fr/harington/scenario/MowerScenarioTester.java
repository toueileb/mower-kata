package fr.harington.scenario;

import fr.harington.exception.MowerException;
import fr.harington.exception.MowerParserException;
import fr.harington.executor.MowerScenarioExecutor;
import fr.harington.models.enums.ErrorMessage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MowerScenarioTester {

    private final MowerScenarioExecutor mowerScenarioExecutor;

    public void testMowerScenario(String instructionsFilePath, List<String> expectedPositions) throws MowerException,
            IOException {

        var instructionsFile = new File(instructionsFilePath);

        if (!instructionsFile.exists()) {
            throw new MowerException("Instructions file with path " + instructionsFilePath + " does not exist !");
        }

        if (instructionsFile.length() == 0) {
            throw new MowerException("Instructions file with path " + instructionsFilePath + " is empty !");

        }

        if (!instructionsFilePath.endsWith("txt")) {
            throw new MowerParserException(ErrorMessage.WRONG_FILE_FORMAT_MESSAGE.getMessage());
        }


        var actualPositions = mowerScenarioExecutor.execute(instructionsFile);


        var errorMessages = new ArrayList<String>();

        if (expectedPositions.size() != actualPositions.size()) {
            errorMessages.add("Expected positions size (" + expectedPositions.size() + ") is different from actual " +
                    "positions size (" + actualPositions.size() + ")");
            throw new MowerException("Mower scenario not validated : \n" + printMowerScenario(errorMessages));
        }

        for (int i = 0; i < expectedPositions.size(); i++) {

            var expectedPosition = expectedPositions.get(i);
            var actualPosition = actualPositions.get(i);

            if (!expectedPosition.equals(actualPosition)) {
                errorMessages.add("Expected position #" + i + " : we was expecting " + expectedPosition + " but was " +
                        actualPosition);
            }
        }

        if (!errorMessages.isEmpty()) {
            throw new MowerException("Mower scenario not validated : \n" + printMowerScenario(errorMessages));
        }

    }

    private String printMowerScenario(List<String> errorMessages) {
        return String.join("\n", errorMessages);
    }

}
