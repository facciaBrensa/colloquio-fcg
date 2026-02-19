package com.facciabrensa.colloquio.fcg.repository;

import com.facciabrensa.colloquio.fcg.entity.UtenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtenteRepository extends JpaRepository<UtenteEntity, Long> {

    @Query("""
        select u
        from UtenteEntity u
        where (:nome IS NULL OR u.nome = :nome)
        and (:cognome IS NULL OR u.cognome = :cognome)
        """)
    List<UtenteEntity> findByNomeAndCognome(@Param("nome") String nome, @Param("cognome") String cognome);
}
