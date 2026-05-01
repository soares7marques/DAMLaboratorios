package com.example.meu_primeiroSpringBoot.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.meu_primeiroSpringBoot.model.Candidate;

@Repository
public interface CadidateRepository extends JpaRepository<Candidate,Long>{

    Optional<Candidate> findByEmail(String email);
}
