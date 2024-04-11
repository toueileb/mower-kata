package fr.harington.parser;

import fr.harington.constant.MowerConstants;
import fr.harington.exception.MowerParserException;
import fr.harington.models.Coordinates;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.Orientation;
import fr.harington.validator.MowerLineValidator;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MowerPositionLineParser implements LineParserStrategy<MowerPosition> {
    private final MowerLineValidator mowerLineValidator;

    @Override
    public MowerPosition parseLine(String mowerLine) throws MowerParserException {
        if (!mowerLineValidator.validateMowerPosition(mowerLine)) {
            throw new MowerParserException("Invalid mower position format " + mowerLine);
        }

        var elements = mowerLine.split(MowerConstants.SPACE_STRING);
        var xCoordinate = Integer.parseInt(elements[0]);
        var yCoordinate = Integer.parseInt(elements[1]);
        var orientation = Orientation.getOrientationByCode(elements[2].charAt(0));
        var coordinates = Coordinates.builder().x(xCoordinate).y(yCoordinate).build();
        return new MowerPosition(coordinates, orientation);
    }
}
