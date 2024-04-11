package fr.harington.processor;

import fr.harington.exception.MowerProcessorException;
import fr.harington.models.MowerModel;
import fr.harington.models.MowerPosition;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Data
public class MowerMultiInstructionProcessor {

    private final MowerSingleInstructionProcessor mowerSingleInstructionProcessor;

    public List<String> executeInstructions(MowerModel mowerModel) throws MowerProcessorException {
        List<MowerPosition> actualPositions = new ArrayList<>();

        try {
            mowerModel.getScenarioItems().forEach(scenario -> {
                MowerPosition lastPosition = scenario.getLeft();

                for (var mowerInstruction : scenario.getRight().getInstructions()) {
                    try {
                        lastPosition = mowerSingleInstructionProcessor.executeInstruction(lastPosition,
                                mowerInstruction,
                                mowerModel.getLawn().getMaxPosition());
                    } catch (Exception e) {
                        throw new RuntimeException("Error executing instruction: " + e.getMessage(), e);
                    }
                }
                actualPositions.add(lastPosition);
            });
        } catch (Exception e) {
            throw new MowerProcessorException("Error executing instructions: " + e.getMessage());
        }

        return actualPositions.stream().map(MowerPosition::formatMowerPosition).toList();
    }

}

