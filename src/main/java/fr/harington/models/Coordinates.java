package fr.harington.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Coordinates {

    private int x;
    private int y;


    public boolean isOutOfLawnBounds(Coordinates pCoordinates) {
        return pCoordinates.getX() >= 0
                && pCoordinates.getY() >= 0
                && pCoordinates.getX() <= this.x
                && pCoordinates.getY() <= this.y;
    }

}

