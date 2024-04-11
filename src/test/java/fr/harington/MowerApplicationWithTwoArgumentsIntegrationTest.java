package fr.harington;

import fr.harington.exception.MowerException;
import fr.harington.exception.MowerParserException;
import fr.harington.models.enums.ErrorMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MowerApplicationWithTwoArgumentsIntegrationTest {

    private static final String FILE_PATH = "./src/test/resources/";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void should_throw_MowerException_when_no_Arguments_Passed() {
        var args = new String[]{};
        assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        var mowerException = assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        assertEquals("Mower scenario file path is expected as first parameter and expected positions as" +
                " second parameter", mowerException.getMessage());
    }

    @Test
    void should_throw_MowerException_when_one_Argument_Passed() {
        var testFilePath = FILE_PATH + "valid_multi_instructions.txt";
        var args = new String[]{testFilePath};
        assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        var mowerException = assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        assertEquals("Mower scenario file path is expected as first parameter and expected positions as" +
                " second parameter", mowerException.getMessage());
    }

    @Test
    void should_validate_Arguments_when_valid_Arguments_Passed() throws MowerException, IOException {

        // Given
        String instructionsFilePath = FILE_PATH + "valid_multi_instructions.txt";
        String expectedPositions = "1 3 N,5 1 E";
        String[] args = {instructionsFilePath, expectedPositions};

        // When
        MowerApplicationWithTwoArguments.main(args);

        // Then
        Assertions.assertTrue(outContent.toString().contains("Mower Scenario validated with success!"));

    }

    @Test
    void should_throw_MowerException_when_empty_File() {
        // Given
        var args = new String[]{FILE_PATH + "empty_file.txt", "1 2 N"};
        //When Then
        assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        // Given
        var mowerException = assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Instructions file with path ./src/test/resources/empty_file.txt is empty !",
                mowerException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_File_Format() {
        // Given  When
        var args = new String[]{FILE_PATH + "instructions.csv", "1 2 N"};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));

        // Then
        assertThrows(MowerParserException.class, () -> MowerApplicationWithTwoArguments.main(args));
        assertEquals(ErrorMessage.WRONG_FILE_FORMAT_MESSAGE.getMessage(), actualException.getMessage());
    }

    @Test
    void should_throw_MowerException_when_invalid_Expected_Positions() {
        // Given When
        var instructionsFilePath = FILE_PATH + "valid_multi_instructions.txt";
        var expectedPositions = "1 3 N,invalid,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        assertEquals("Mower scenario not validated : \n" +
                        "Expected positions size (3) is different from actual positions size (2)",
                actualException.getMessage());
    }

    @Test
    void should_throw_MowerException_when_file_Not_Found() {
        // Given When
        var args = new String[]{"nonexistentfile.txt", "1 2 N"};
        var actualException = assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertThrows(MowerException.class, () -> MowerApplicationWithTwoArguments.main(args));
        assertEquals("Instructions file with path nonexistentfile.txt does not exist !",
                actualException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_Instructions() {
        // Given When
        var instructionsFilePath = FILE_PATH + "invalid_multi_instructions.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Invalid instruction format GAGAGAG0A", actualException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_Lawn_Format() {
        // Given When
        var instructionsFilePath = FILE_PATH + "invalid_lawn_format.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Invalid lawn format 5 H", actualException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_Mower_Max_Position() {

        //
        var instructionsFilePath = FILE_PATH + "invalid_mower_max_position.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Invalid mower position format 1 M N", actualException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_Mower_Orientation() {

        // Given When
        var instructionsFilePath = FILE_PATH + "invalid_mower_position.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Invalid mower position format 1 2 K", actualException.getMessage());
    }

    @Test
    void should_throw_MowerParserException_when_invalid_Mower_Instructions() {

        // Given When
        var instructionsFilePath = FILE_PATH + "invalid_mower_instructions_format.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        var actualException = assertThrows(MowerParserException.class,
                () -> MowerApplicationWithTwoArguments.main(args));
        // Then
        assertEquals("Invalid instruction format AADAADADDP", actualException.getMessage());
    }

    @Test
    void should_validate_Multiple_Expected_Positions_when_valid_Arguments_Passed() throws MowerException, IOException {

        // Given When
        var instructionsFilePath = FILE_PATH + "valid_multi_instructions.txt";
        var expectedPositions = "1 3 N,5 1 E";
        var args = new String[]{instructionsFilePath, expectedPositions};
        MowerApplicationWithTwoArguments.main(args);
        // Then
        Assertions.assertTrue(outContent.toString().contains("Mower Scenario validated with success!"));
    }


    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
