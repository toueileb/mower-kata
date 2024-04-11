package fr.harington.constant;

import fr.harington.models.Coordinates;
import fr.harington.models.enums.Orientation;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class MowerConstants {

    public static final String SPACE_STRING = " ";

    public final Map<Orientation, Orientation> RIGHT_ROTATION_MAP = new HashMap<>();
    public final Map<Orientation, Orientation> LEFT_ROTATION_MAP = new HashMap<>();

    public final Map<Orientation, Coordinates> ORIENTATION_DELTAS = new HashMap<>();

    static {
        RIGHT_ROTATION_MAP.put(Orientation.NORTH, Orientation.EAST);
        RIGHT_ROTATION_MAP.put(Orientation.EAST, Orientation.SOUTH);
        RIGHT_ROTATION_MAP.put(Orientation.SOUTH, Orientation.WEST);
        RIGHT_ROTATION_MAP.put(Orientation.WEST, Orientation.NORTH);

        LEFT_ROTATION_MAP.put(Orientation.NORTH, Orientation.WEST);
        LEFT_ROTATION_MAP.put(Orientation.EAST, Orientation.NORTH);
        LEFT_ROTATION_MAP.put(Orientation.SOUTH, Orientation.EAST);
        LEFT_ROTATION_MAP.put(Orientation.WEST, Orientation.SOUTH);

        ORIENTATION_DELTAS.put(Orientation.NORTH, Coordinates.builder().x(0).y(1).build());
        ORIENTATION_DELTAS.put(Orientation.EAST, Coordinates.builder().x(1).y(0).build());
        ORIENTATION_DELTAS.put(Orientation.SOUTH, Coordinates.builder().x(0).y(-1).build());
        ORIENTATION_DELTAS.put(Orientation.WEST, Coordinates.builder().x(-1).y(0).build());


    }
}
