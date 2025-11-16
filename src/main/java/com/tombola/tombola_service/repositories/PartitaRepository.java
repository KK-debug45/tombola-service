package com.tombola.tombola_service.repositories;

import com.tombola.tombola_service.models.Partita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartitaRepository extends JpaRepository<Partita, String> {
    // Spring fornisce automaticamente tutti i metodi CRUD (save, findById, delete, ecc.)
}
