package com.securetrack.controller;

import com.securetrack.dto.request.IncidenceRequest;
import com.securetrack.dto.request.UpdateIncidenceRequest;
import com.securetrack.dto.response.IncidenceResponse;
import com.securetrack.entity.IncidenceStatus;
import com.securetrack.entity.Priority;
import com.securetrack.service.IncidenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidences")
@Tag(name = "Incidencias", description = "Endpoints para gestión de incidencias")
@SecurityRequirement(name = "Bearer Authentication")
public class IncidenceController {

    private final IncidenceService incidenceService;

    public IncidenceController(IncidenceService incidenceService) {
        this.incidenceService = incidenceService;
    }

    @Operation(summary = "Crear nueva incidencia", description = "Crea una incidencia y la asigna al usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidencia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping
    public ResponseEntity<IncidenceResponse> createIncidence(
            @Valid @RequestBody IncidenceRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        IncidenceResponse response = incidenceService.createIncidence(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todas las incidencias", description = "Devuelve lista completa de incidencias")
    @GetMapping
    public ResponseEntity<List<IncidenceResponse>> getAllIncidences() {
        List<IncidenceResponse> responses = incidenceService.getAllIncidences();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener incidencia por ID", description = "Busca una incidencia específica por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<IncidenceResponse> getIncidenceById(
            @Parameter(description = "ID de la incidencia") @PathVariable Long id) {
        IncidenceResponse response = incidenceService.getIncidenceById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Filtrar por estado", description = "Devuelve incidencias filtradas por estado (OPEN, IN_PROGRESS, RESOLVED, CLOSED)")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<IncidenceResponse>> getIncidencesByStatus(
            @Parameter(description = "Estado de la incidencia") @PathVariable IncidenceStatus status) {
        List<IncidenceResponse> responses = incidenceService.getIncidencesByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Filtrar por prioridad", description = "Devuelve incidencias filtradas por prioridad (LOW, MEDIUM, HIGH, CRITICAL)")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<IncidenceResponse>> getIncidencesByPriority(
            @Parameter(description = "Prioridad de la incidencia") @PathVariable Priority priority) {
        List<IncidenceResponse> responses = incidenceService.getIncidencesByPriority(priority);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar por usuario asignado", description = "Devuelve incidencias asignadas a un usuario específico")
    @GetMapping("/assigned/{username}")
    public ResponseEntity<List<IncidenceResponse>> getIncidencesByAssignedUser(
            @Parameter(description = "Nombre de usuario") @PathVariable String username) {
        List<IncidenceResponse> responses = incidenceService.getIncidencesByAssignedUser(username);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar por palabra clave", description = "Busca incidencias que contengan la palabra en título o descripción")
    @GetMapping("/search")
    public ResponseEntity<List<IncidenceResponse>> searchIncidences(
            @Parameter(description = "Palabra clave a buscar") @RequestParam String keyword) {
        List<IncidenceResponse> responses = incidenceService.searchIncidences(keyword);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Actualizar incidencia", description = "Modifica una incidencia existente. USER solo puede modificar las suyas")
    @PutMapping("/{id}")
    public ResponseEntity<IncidenceResponse> updateIncidence(
            @Parameter(description = "ID de la incidencia") @PathVariable Long id,
            @Valid @RequestBody UpdateIncidenceRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        IncidenceResponse response = incidenceService.updateIncidence(id, request, username);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Asignar incidencia a usuario", description = "Solo ADMIN puede reasignar incidencias")
    @PatchMapping("/{incidenceId}/assign/{userId}")
    public ResponseEntity<IncidenceResponse> assignIncidence(
            @Parameter(description = "ID de la incidencia") @PathVariable Long incidenceId,
            @Parameter(description = "ID del usuario al que asignar") @PathVariable Long userId,
            Authentication authentication) {
        String adminUsername = authentication.getName();
        IncidenceResponse response = incidenceService.assignIncidence(incidenceId, userId, adminUsername);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar incidencia", description = "Solo ADMIN puede eliminar incidencias")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidence(
            @Parameter(description = "ID de la incidencia") @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        incidenceService.deleteIncidence(id, username);
        return ResponseEntity.noContent().build();
    }
}