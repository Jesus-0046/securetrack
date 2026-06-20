package com.securetrack.dto.response;

import com.securetrack.entity.Incidence;
import com.securetrack.entity.IncidenceStatus;
import com.securetrack.entity.Priority;
import java.time.LocalDateTime;

public class IncidenceResponse {

    private Long id;
    private String title;
    private String description;
    private IncidenceStatus status;
    private Priority priority;
    private Long assignedToId;
    private String assignedToUsername;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    // Constructor vacío
    public IncidenceResponse() {
    }

    // Constructor que convierte entidad a DTO
    public IncidenceResponse(Incidence incidence) {
        this.id = incidence.getId();
        this.title = incidence.getTitle();
        this.description = incidence.getDescription();
        this.status = incidence.getStatus();
        this.priority = incidence.getPriority();
        this.assignedToId = incidence.getAssignedTo() != null ? incidence.getAssignedTo().getId() : null;
        this.assignedToUsername = incidence.getAssignedTo() != null ? incidence.getAssignedTo().getUsername() : null;
        this.createdById = incidence.getCreatedBy() != null ? incidence.getCreatedBy().getId() : null;
        this.createdByUsername = incidence.getCreatedBy() != null ? incidence.getCreatedBy().getUsername() : null;
        this.createdAt = incidence.getCreatedAt();
        this.updatedAt = incidence.getUpdatedAt();
        this.resolvedAt = incidence.getResolvedAt();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IncidenceStatus getStatus() {
        return status;
    }

    public void setStatus(IncidenceStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getAssignedToUsername() {
        return assignedToUsername;
    }

    public void setAssignedToUsername(String assignedToUsername) {
        this.assignedToUsername = assignedToUsername;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}