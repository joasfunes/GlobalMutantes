package org.example.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.dto.DnaRequest;
import org.example.dto.DnaResponse;
import org.example.service.MutantDetector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mutant")
public class MutantController {
    private final MutantDetector mutantDetector;

    public MutantController(MutantDetector mutantDetector) {
        this.mutantDetector = mutantDetector;
    }


    @PostMapping
    @Operation(
            summary = "Verificar si un ADN es mutante",
            description = "Recibe una secuencia de ADN y determina si pertenece a un mutante. Un humano es mutante si tiene MÁS DE UNA secuencia de 4 letras iguales en dirección horizontal, vertical u oblicua (diagonal)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Es mutante",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DnaResponse.class),
                            examples = @ExampleObject(value = "{\"mutant\": true}")
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Es humano",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DnaResponse.class),
                            examples = @ExampleObject(value = "{\"mutant\": false}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos - ADN nulo, vacío, matriz no cuadrada o caracteres inválidos",
                    content = @Content
            )
    })



    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantDetector.analyzeDna(dnaRequest.getDna());
        DnaResponse dnaResponse = new DnaResponse(isMutant);
        if (isMutant) {
            return ResponseEntity.ok(dnaResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
        }
    }

}
