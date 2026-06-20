package com.securetrack.dto.request;

import com.securetrack.entity.IncidenceStatus;
import com.securetrack.entity.Priority;
import jakarta.validation.constraints.Size;

public class UpdateIncidenceRequest {

    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    private String description;

    private IncidenceStatus status;

    private Priority priority;

    // Constructor vacío
    public UpdateIncidenceRequest() {
    }

    // Getters y Setters
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
}