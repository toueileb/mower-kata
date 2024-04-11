package fr.harington.models.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public enum Instruction {

    RIGHT('D'),
    LEFT('G'),
    FORWARD('A');

    private final char instructionCode;


    public static Instruction getInstructionByCode(char instructionCode) {
        return Arrays.stream(Instruction.values()).filter(instruction -> instruction.getInstructionCode() == instructionCode)
                .findFirst().orElseThrow();
    }

}
