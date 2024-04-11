package fr.harington.parser;

import fr.harington.constant.MowerConstants;
import fr.harington.exception.MowerParserException;
import fr.harington.models.Coordinates;
import fr.harington.models.Lawn;
import fr.harington.validator.MowerLineValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LawnLineParser implements LineParserStrategy<Lawn> {
    private final MowerLineValidator mowerLineValidator;

    @Override
    public Lawn parseLine(String lawnLine) throws MowerParserException {
        if (!mowerLineValidator.validateMowerLawn(lawnLine)) {
            throw new MowerParserException("Invalid lawn format " + lawnLine);
        }

        var elements = lawnLine.split(MowerConstants.SPACE_STRING);
        var xCoordinate = Integer.parseInt(elements[0]);
        var yCoordinate = Integer.parseInt(elements[1]);
        var coordinates = Coordinates.builder().x(xCoordinate).y(yCoordinate).build();
        return Lawn.builder().maxPosition(coordinates).build();
    }
}
