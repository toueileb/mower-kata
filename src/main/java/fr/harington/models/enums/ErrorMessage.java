package fr.harington.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    ERROR_FILE_NOT_FOUND("File not found"),
    INCORRECT_ORIENTATION("Incorrect orientation"),
    INCORRECT_POSITION("Incorrect position"),
    WRONG_FILE_FORMAT_MESSAGE("Wrong file format. Only .txt files are allowed.");

    private final String message;

}
