package fr.harington.validator;

import fr.harington.exception.MowerFileValidationException;
import fr.harington.models.enums.ErrorMessage;

import java.io.File;

public class MowerScenarioValidator {

    public void validateFile(File instructionsFile) throws MowerFileValidationException {

        if (!instructionsFile.isFile()) {
            throw new MowerFileValidationException(ErrorMessage.ERROR_FILE_NOT_FOUND.getMessage());
        }

        if (!instructionsFile.exists()) {
            throw new MowerFileValidationException("Instructions file with path " + instructionsFile.getAbsolutePath()
                    + " does not exist !");
        }

        if (instructionsFile.length() == 0) {
            throw new MowerFileValidationException("Instructions file with path " + instructionsFile.getAbsolutePath()
                    + " is empty !");

        }

        if (!instructionsFile.getAbsolutePath().endsWith("txt")) {
            throw new MowerFileValidationException(ErrorMessage.WRONG_FILE_FORMAT_MESSAGE.getMessage());
        }
    }
}
