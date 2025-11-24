package org.example.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.validator.ValidDnaSequence;

@Getter
@Setter
@Schema(description = "Para verificar si un ADN es mutante")
public class DnaRequest {
    @Schema(
            description = "Secuencia de ADN representada como matriz cuadrada de Strings",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true
    )

    @ValidDnaSequence
    private String[] dna;
}
