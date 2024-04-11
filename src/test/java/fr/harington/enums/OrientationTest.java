package fr.harington.enums;

import fr.harington.models.enums.Orientation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrientationTest {

    @ParameterizedTest
    @EnumSource(Orientation.class)
    void should_get_orientation_by_code(final Orientation expectedOrientation) {
        var expectedOrientationCode = expectedOrientation.getOrientationCode();
        assertEquals(expectedOrientation, Orientation.getOrientationByCode(expectedOrientationCode));
    }

    @ParameterizedTest
    @ValueSource(chars = {
            'G',
            'H',
            'X',
            'R'
    })
    void should_throw_exception_for_invalid_orientation_code(final char givenOrientationCode) {
        assertThrows(NoSuchElementException.class, () -> Orientation.getOrientationByCode(givenOrientationCode));
    }
}
