package fr.harington.models;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Data
public class MowerModel {
    private Lawn lawn;
    private List<Pair<MowerPosition, ComposedInstruction>> scenarioItems = new ArrayList<>();
}
