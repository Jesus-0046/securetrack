package com.securetrack.repository;

import com.securetrack.entity.Incidence;
import com.securetrack.entity.IncidenceStatus;
import com.securetrack.entity.Priority;
import com.securetrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenceRepository extends JpaRepository<Incidence, Long> {

    // Buscar por estado
    List<Incidence> findByStatus(IncidenceStatus status);

    // Buscar por prioridad
    List<Incidence> findByPriority(Priority priority);

    // Buscar por estado y prioridad
    List<Incidence> findByStatusAndPriority(IncidenceStatus status, Priority priority);

    // Buscar por usuario asignado
    List<Incidence> findByAssignedTo(User user);

    // Buscar por usuario creador
    List<Incidence> findByCreatedBy(User user);

    // Paginación
    Page<Incidence> findAll(Pageable pageable);

    // Buscar por palabra clave en título o descripción
    @Query("SELECT i FROM Incidence i WHERE " +
            "LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Incidence> searchByKeyword(@Param("keyword") String keyword);
}