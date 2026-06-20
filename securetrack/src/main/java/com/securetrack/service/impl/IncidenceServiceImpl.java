package com.securetrack.service.impl;

import com.securetrack.dto.request.IncidenceRequest;
import com.securetrack.dto.request.UpdateIncidenceRequest;
import com.securetrack.dto.response.IncidenceResponse;
import com.securetrack.entity.*;
import com.securetrack.exception.BadRequestException;
import com.securetrack.exception.ResourceNotFoundException;
import com.securetrack.exception.UnauthorizedException;
import com.securetrack.repository.IncidenceRepository;
import com.securetrack.repository.UserRepository;
import com.securetrack.service.IncidenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenceServiceImpl implements IncidenceService {

    private final IncidenceRepository incidenceRepository;
    private final UserRepository userRepository;

    public IncidenceServiceImpl(IncidenceRepository incidenceRepository, UserRepository userRepository) {
        this.incidenceRepository = incidenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public IncidenceResponse createIncidence(IncidenceRequest request, String username) {
        User createdBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Incidence incidence = new Incidence(
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
        );
        incidence.setCreatedBy(createdBy);
        incidence.setAssignedTo(createdBy); // Por defecto, el creador se asigna a sí mismo
        incidence.setStatus(IncidenceStatus.OPEN);

        Incidence saved = incidenceRepository.save(incidence);
        return new IncidenceResponse(saved);
    }

    @Override
    public IncidenceResponse getIncidenceById(Long id) {
        Incidence incidence = incidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidence", "id", id));
        return new IncidenceResponse(incidence);
    }

    @Override
    public List<IncidenceResponse> getAllIncidences() {
        return incidenceRepository.findAll()
                .stream()
                .map(IncidenceResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidenceResponse> getIncidencesByStatus(IncidenceStatus status) {
        return incidenceRepository.findByStatus(status)
                .stream()
                .map(IncidenceResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidenceResponse> getIncidencesByPriority(Priority priority) {
        return incidenceRepository.findByPriority(priority)
                .stream()
                .map(IncidenceResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidenceResponse> getIncidencesByAssignedUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return incidenceRepository.findByAssignedTo(user)
                .stream()
                .map(IncidenceResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidenceResponse> searchIncidences(String keyword) {
        return incidenceRepository.searchByKeyword(keyword)
                .stream()
                .map(IncidenceResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public IncidenceResponse updateIncidence(Long id, UpdateIncidenceRequest request, String username) {
        Incidence incidence = incidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidence", "id", id));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Verificar permisos: solo ADMIN o el usuario asignado pueden modificar
        if (currentUser.getRole() != Role.ADMIN &&
                !incidence.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to update this incidence");
        }

        // Actualizar solo los campos proporcionados
        if (request.getTitle() != null) {
            incidence.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            incidence.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            incidence.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            incidence.setPriority(request.getPriority());
        }

        Incidence updated = incidenceRepository.save(incidence);
        return new IncidenceResponse(updated);
    }

    @Override
    public void deleteIncidence(Long id, String username) {
        Incidence incidence = incidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidence", "id", id));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Solo ADMIN puede eliminar incidencias
        if (currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only administrators can delete incidences");
        }

        incidenceRepository.delete(incidence);
    }

    @Override
    public IncidenceResponse assignIncidence(Long incidenceId, Long userId, String adminUsername) {
        User admin = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", adminUsername));

        // Solo ADMIN puede reasignar
        if (admin.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only administrators can assign incidences");
        }

        Incidence incidence = incidenceRepository.findById(incidenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Incidence", "id", incidenceId));

        User newAssignee = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        incidence.setAssignedTo(newAssignee);
        Incidence updated = incidenceRepository.save(incidence);
        return new IncidenceResponse(updated);
    }
}