package org.example.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    @Hidden // Esto oculta este endpoint de Swagger UI
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("message", "API de Detección de Mutantes");
        response.put("endpoints", Map.of(
                "verificar_mutante", "POST /mutant",
                "estadisticas", "GET /stats",
                "documentacion", "GET /swagger-ui/index.html"
        ));
        return response;
    }
}
