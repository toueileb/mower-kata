package fr.harington.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MowerLineValidatorTest {

    @InjectMocks
    private MowerLineValidator validator;

    @Nested
    class TestValidateMowerPosition_OK {
        @Test
        @DisplayName("should validate mower position format")
        void should_validate_mower_position_format() {
            assertAll("Validate mower position format",
                    () -> assertTrue(validator.validateMowerPosition("1 2 N")),
                    () -> assertFalse(validator.validateMowerPosition("A B C"))
            );
        }

        @Test
        @DisplayName("should validate mower instructions format")
        void should_validate_mower_instructions_format() {
            assertAll("Validate mower instructions format",
                    () -> assertTrue(validator.validateMowerInstructions("GDA")),
                    () -> assertFalse(validator.validateMowerInstructions("123"))
            );
        }

        @Test
        @DisplayName("should validate lawn position format")
        void should_validate_lawn_position_format() {
            assertAll("Validate lawn position format",
                    () -> assertTrue(validator.validateMowerLawn("5 5")),
                    () -> assertFalse(validator.validateMowerLawn("A B"))
            );
        }

    }


    @Nested
    class TestValidateMowerPosition_KO {
        @ParameterizedTest
        @ValueSource(strings = {"1 2 3 N", "1 2", "N 1 2"})
        @DisplayName("should return false for invalid mower position format")
        void should_return_false_for_invalid_mower_position_format(String invalidPosition) {
            assertFalse(validator.validateMowerPosition(invalidPosition));
        }

        @ParameterizedTest
        @ValueSource(strings = {"GDA1", "G D A"})
        @DisplayName("should return false for invalid mower instructions format")
        void should_return_false_for_invalid_mower_instructions_format(String invalidInstructions) {
            assertFalse(validator.validateMowerInstructions(invalidInstructions));
        }

        @ParameterizedTest
        @ValueSource(strings = {"5", "5 5 5"})
        @DisplayName("should return false for invalid lawn position format")
        void should_return_false_for_invalid_lawn_position_format(String invalidLawn) {
            assertFalse(validator.validateMowerLawn(invalidLawn));
        }

    }
}
