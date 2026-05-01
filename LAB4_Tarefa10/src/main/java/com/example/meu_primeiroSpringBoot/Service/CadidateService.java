
package com.example.meu_primeiroSpringBoot.Service;
import com.example.meu_primeiroSpringBoot.dto.DtoLogin;
import com.example.meu_primeiroSpringBoot.Security.JwtToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.meu_primeiroSpringBoot.Exception.ExceptionDadosDuplicado;
import com.example.meu_primeiroSpringBoot.Repository.CadidateRepository;
import com.example.meu_primeiroSpringBoot.model.Candidate;

@Service
public class CadidateService {

    @Autowired
    private CadidateRepository cadidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CadidateService(CadidateRepository cadidateRepository){
        this.cadidateRepository = cadidateRepository;
    }
    
    public ResponseEntity<?> registerCadidate(Candidate request){
        cadidateRepository.findByEmail(request.getEmail())
        .ifPresent((user)->{
            throw new ExceptionDadosDuplicado();
        });
        return ResponseEntity.ok().body(cadidateRepository.save(request));
    }

    // Listar todos os candidatos
    public ResponseEntity<?> getAllCadidates() {
        return ResponseEntity.ok().body(cadidateRepository.findAll());
    }

    // Buscar candidato por ID
    public ResponseEntity<?> getCadidateById(Long id) {
        return cadidateRepository.findById(id)
            .map(candidate -> ResponseEntity.ok().body(candidate))
            .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar candidato
    public ResponseEntity<?> updateCadidate(Long id, Candidate request) {
        return cadidateRepository.findById(id)
            .map(candidate -> {
                candidate.setUsername(request.getUsername());
                candidate.setName(request.getName());
                candidate.setEmail(request.getEmail());
                candidate.setPassword(request.getPassword());
                candidate.setDescription(request.getDescription());
                candidate.setCurriculo(request.getCurriculo());
                cadidateRepository.save(candidate);
                return ResponseEntity.ok().body(candidate);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Deletar candidato
    public ResponseEntity<?> deleteCadidate(Long id) {
        return cadidateRepository.findById(id)
            .map(candidate -> {
                cadidateRepository.delete(candidate);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // realizar autenticação login
    public ResponseEntity<?> login(DtoLogin request) {
        Optional<Candidate> opt = cadidateRepository.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Email"));
        }
        Candidate candidate = opt.get();
        if (!passwordEncoder.matches(request.getSenha(), candidate.getPassword())) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Email ou senha incorretos"));
        }
        String token = JwtToken.generateToken(request.getEmail());
        return ResponseEntity.ok().body(token);
    }
}
