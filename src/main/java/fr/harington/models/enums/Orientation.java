package fr.harington.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@ToString
public enum Orientation {

    NORTH('N'),
    EAST('E'),
    WEST('W'),
    SOUTH('S');

    private final char orientationCode;


    public static Orientation getOrientationByCode(char orientationCode) {
        return Arrays.stream(Orientation.values()).filter(orientation -> orientation.getOrientationCode() == orientationCode)
                .findFirst().orElseThrow();
    }

}