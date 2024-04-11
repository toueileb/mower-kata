package fr.harington.processor;

import fr.harington.constant.MowerConstants;
import fr.harington.exception.MowerProcessorException;
import fr.harington.models.Coordinates;
import fr.harington.models.MowerPosition;
import fr.harington.models.enums.ErrorMessage;
import fr.harington.models.enums.Instruction;
import fr.harington.models.enums.Orientation;

public class MowerSingleInstructionProcessor {

    private Coordinates moveMowerForward(final MowerPosition mowerPosition, final Coordinates maxCoordinates) throws MowerProcessorException {
        var currentCoordinates = mowerPosition.getMowerCoordinates();
        var coordinates = MowerConstants.ORIENTATION_DELTAS.get(mowerPosition.getMowerOrientation());

        if (coordinates == null) {
            throw new MowerProcessorException(ErrorMessage.INCORRECT_POSITION.getMessage());
        }

        int newX = currentCoordinates.getX() + coordinates.getX();
        int newY = currentCoordinates.getY() + coordinates.getY();

        var nextCoordinates = Coordinates.builder().x(newX).y(newY).build();

        // If the new coordinates are outside the lawn, keep the last known position
        if (maxCoordinates.isOutOfLawnBounds(nextCoordinates)) {
            return nextCoordinates;
        }
        return currentCoordinates;
        
    }


    private Orientation rotateRight(final Orientation orientation) throws MowerProcessorException {

        var rotatedOrientation = MowerConstants.RIGHT_ROTATION_MAP.get(orientation);
        if (rotatedOrientation == null) {
            throw new MowerProcessorException(ErrorMessage.INCORRECT_ORIENTATION.getMessage());
        }
        return rotatedOrientation;
    }

    private Orientation rotateLeft(final Orientation orientation) throws MowerProcessorException {
        var rotatedOrientation = MowerConstants.LEFT_ROTATION_MAP.get(orientation);
        if (rotatedOrientation == null) {
            throw new MowerProcessorException(ErrorMessage.INCORRECT_ORIENTATION.getMessage());
        }
        return rotatedOrientation;
    }

    public MowerPosition executeInstruction(final MowerPosition mowerPosition, final Instruction instruction,
                                            final Coordinates maxCoordinates) throws MowerProcessorException {
        try {
            return switch (instruction) {
                case FORWARD -> mowerPosition.toBuilder()
                        .mowerCoordinates(this.moveMowerForward(mowerPosition, maxCoordinates)).build();
                case RIGHT -> mowerPosition.toBuilder()
                        .mowerOrientation(this.rotateRight(mowerPosition.getMowerOrientation())).build();
                case LEFT -> mowerPosition.toBuilder()
                        .mowerOrientation(this.rotateLeft(mowerPosition.getMowerOrientation())).build();
            };
        } catch (Exception e) {
            throw new MowerProcessorException("Error executing instruction: " + e.getMessage());
        }
    }

}

