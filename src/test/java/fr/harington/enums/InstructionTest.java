package fr.harington.enums;

import fr.harington.models.enums.Instruction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstructionTest {

    @ParameterizedTest
    @EnumSource(Instruction.class)
    void should_get_orientation_by_code(final Instruction expectedInstruction) {
        var expectedInstructionCode = expectedInstruction.getInstructionCode();
        assertEquals(expectedInstruction, Instruction.getInstructionByCode(expectedInstructionCode));
    }

    @ParameterizedTest
    @ValueSource(chars = {
            'J',
            'H',
            'X',
            'R'
    })
    void should_throw_exception_for_invalid_instruction_code(final char givenInstructionCode) {
        assertThrows(NoSuchElementException.class, () -> Instruction.getInstructionByCode(givenInstructionCode));
    }

}
