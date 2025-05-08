package com.jeroka.auth.controller;

import com.jeroka.auth.dto.PlanRequestDTO;
import com.jeroka.auth.dto.PlanResponseDTO;
import com.jeroka.auth.service.PlanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.jeroka.auth.mapper.PlanMapper;
import com.jeroka.auth.model.Plan;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/plans")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return ResponseEntity.ok(PlanMapper.toDTOs(plans));
    }

    @GetMapping("/active")
    public ResponseEntity<List<PlanResponseDTO>> getActivePlans() {
        List<Plan> plans = planService.getActivePlans();
        return ResponseEntity.ok(PlanMapper.toDTOs(plans));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getPlanById(@PathVariable Long id) {
        Plan plan = planService.getPlanById(id);
        return ResponseEntity.ok(PlanMapper.toDTO(plan));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponseDTO> createPlan(
            @Valid @RequestBody PlanRequestDTO requestDTO) {
        Plan createdPlan = planService.createPlan(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlanMapper.toDTO(createdPlan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponseDTO> updatePlan(
            @PathVariable @NotNull(message = "L'ID du plan ne peut pas être null") Long id,
            @Valid @RequestBody PlanRequestDTO requestDTO) {
        Plan updatedPlan = planService.updatePlan(id, requestDTO);
        return ResponseEntity.ok(PlanMapper.toDTO(updatedPlan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlan(
            @PathVariable @NotNull(message = "L'ID du plan ne peut pas être null") Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur est survenue : " + e.getMessage());
    }
} 