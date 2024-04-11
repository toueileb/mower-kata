package fr.harington.models;

import fr.harington.models.enums.Orientation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class MowerPosition {
    private Coordinates mowerCoordinates;
    private Orientation mowerOrientation;

    public static String formatMowerPosition(MowerPosition mowerPosition) {
        return mowerPosition.getMowerCoordinates().getX()
                + " "
                + mowerPosition.getMowerCoordinates().getY()
                + " "
                + mowerPosition.getMowerOrientation().getOrientationCode();
    }

}

