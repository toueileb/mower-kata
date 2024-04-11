package fr.harington.models;

import fr.harington.models.enums.Instruction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ComposedInstruction {
    private List<Instruction> instructions;

}
