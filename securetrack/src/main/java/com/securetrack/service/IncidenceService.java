package com.securetrack.service;

import com.securetrack.dto.request.IncidenceRequest;
import com.securetrack.dto.request.UpdateIncidenceRequest;
import com.securetrack.dto.response.IncidenceResponse;
import com.securetrack.entity.IncidenceStatus;
import com.securetrack.entity.Priority;

import java.util.List;

public interface IncidenceService {

    IncidenceResponse createIncidence(IncidenceRequest request, String username);

    IncidenceResponse getIncidenceById(Long id);

    List<IncidenceResponse> getAllIncidences();

    List<IncidenceResponse> getIncidencesByStatus(IncidenceStatus status);

    List<IncidenceResponse> getIncidencesByPriority(Priority priority);

    List<IncidenceResponse> getIncidencesByAssignedUser(String username);

    List<IncidenceResponse> searchIncidences(String keyword);

    IncidenceResponse updateIncidence(Long id, UpdateIncidenceRequest request, String username);

    void deleteIncidence(Long id, String username);

    IncidenceResponse assignIncidence(Long incidenceId, Long userId, String adminUsername);
}