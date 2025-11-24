package org.example.controller;
import org.example.dto.DnaRequest;
import org.example.dto.DnaResponse;
import org.example.service.MutantDetector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MutantControllerTest {

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantController mutantController;

    @Test
    void checkMutant_WhenDnaIsMutant_ReturnsOk() {
        // Arrange
        String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(mutantDna);
        when(mutantDetector.analyzeDna(any(String[].class))).thenReturn(true);

        // Act
        ResponseEntity<DnaResponse> response = mutantController.checkMutant(dnaRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isMutant());
    }

    @Test
    void checkMutant_WhenDnaIsHuman_ReturnsForbidden() {
        // Arrange
        String[] humanDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(humanDna);
        when(mutantDetector.analyzeDna(any(String[].class))).thenReturn(false);

        // Act
        ResponseEntity<DnaResponse> response = mutantController.checkMutant(dnaRequest);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(false, response.getBody().isMutant());
    }

    @Test
    void checkMutant_WhenDnaIsInvalid_ThrowsException() {
        // Arrange
        String[] invalidDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTGX"}; // Carácter inválido 'X'
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(invalidDna);

        // Act & Assert
        // Este test debería lanzar una excepción de validación (400 Bad Request)
        // Si usas @Valid en el controlador, Spring Boot lanzará automáticamente la excepción.
        // Para probar esto, necesitarías un test de integración con @SpringBootTest y MockMvc.
    }
}
