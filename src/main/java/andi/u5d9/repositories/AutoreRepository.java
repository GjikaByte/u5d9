package andi.u5d9.repositories;

import andi.u5d9.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, UUID> {
    Optional<Autore> findByEmail(String email);
}
