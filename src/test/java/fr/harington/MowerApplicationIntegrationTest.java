package fr.harington;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MowerApplicationIntegrationTest {

    @Nested
    class TestMain {
        @DisplayName("Should process multiple files and print the correct final positions")
        @ParameterizedTest
        @CsvSource(value = {
                "src/test/resources/valid_multi_instructions.txt:[1 3 N, 5 1 E]",
                "src/test/resources/single_valid_instruction.txt:[1 3 N]"
        }, delimiter = ':')
        void should_processMultipleFilesAndPrintCorrectFinalPositions(final String instructionsFilePath,
                                                                      final String expectedFinalPositions) {
            var fileInputStream = new ByteArrayInputStream((instructionsFilePath + "\nN\n").getBytes());
            System.setIn(fileInputStream);
            var outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            MowerApplication.main();
            Assertions.assertTrue(outputStream.toString().contains(expectedFinalPositions));
        }

        @ParameterizedTest
        @DisplayName("Should process multiple files and print the the error message")
        @CsvSource(value = {
                "src/test/resources/empty_file.txt,empty_file.txt is empty !",
                "src/test/resources/instructions.csv,Wrong file format. Only .txt files are allowed.",
                "src/test/resources/invalid_lawn_format.txt,Invalid lawn format 5 H",
                "src/test/resources/invalid_mower_instructions_format.txt,Invalid instruction format AADAADADDP",
                "src/test/resources/invalid_mower_max_position.txt,Invalid mower position format 1 M N",
                "src/test/resources/invalid_mower_position.txt,Invalid mower position format 1 2 K",
                "src/test/resources/invalid_multi_instructions.txt,Invalid instruction format GAGAGAG0A",

        }, delimiter = ',')
        void should_throwMowerException_givenTestFile_whenRunMain(final String instructionsFilePath,
                                                                  final String expectedErrorMessage) {
            var fileInputStream = new ByteArrayInputStream((instructionsFilePath + "\nN\n").getBytes());
            System.setIn(fileInputStream);

            var outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            MowerApplication.main();
            Assertions.assertTrue(outputStream.toString().contains(expectedErrorMessage));
        }
    }
}
