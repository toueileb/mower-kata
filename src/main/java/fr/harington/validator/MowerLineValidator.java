package fr.harington.validator;

import java.util.regex.Pattern;

public class MowerLineValidator {

    private static final Pattern VALID_MOWER_PATTERN = Pattern.compile("\\d+ \\d+ [NSEW]"); // Regular expression for
    // validate coordinates and orientations
    private static final Pattern VALID_INSTRUCTIONS_PATTERN = Pattern.compile("[DGA]+"); // Regular expression for
    // validate instructions
    private static final Pattern VALID_LAWN_PATTERN = Pattern.compile("\\d+ \\d+"); // Regular expression for valid
    // lawn coordinates

    public boolean validateMowerPosition(String mower) {
        return VALID_MOWER_PATTERN.matcher(mower).matches();
    }


    public boolean validateMowerInstructions(String instructions) {
        return VALID_INSTRUCTIONS_PATTERN.matcher(instructions).matches();
    }

    public boolean validateMowerLawn(String lawn) {
        return VALID_LAWN_PATTERN.matcher(lawn).matches();
    }
}
