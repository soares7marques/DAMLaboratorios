
package com.example.meu_primeiroSpringBoot.Controller;
import com.example.meu_primeiroSpringBoot.dto.DtoLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.meu_primeiroSpringBoot.Service.CadidateService;
import com.example.meu_primeiroSpringBoot.model.Candidate;
import com.example.meu_primeiroSpringBoot.dto.CandidateDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/candidate")
public class ControllerCandidato {

    private CadidateService cadidateService;
    
    public ControllerCandidato(CadidateService cadidateService){
        this.cadidateService = cadidateService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> SalveCadidate(@Valid @RequestBody CandidateDTO dto) {
        try {
            Candidate entity = new Candidate();
            entity.setUsername(dto.getUsername());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setDescription(dto.getDescription());
            entity.setCurriculo(dto.getCurriculo());
            var result = cadidateService.registerCadidate(entity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos os candidatos
    @GetMapping
    public ResponseEntity<?> getAllCadidates() {
        return cadidateService.getAllCadidates();
    }

    // Buscar candidato por ID
    @GetMapping("/Buscar/{id}")
    public ResponseEntity<?> getCadidateById(@PathVariable Long id) {
        return cadidateService.getCadidateById(id);
    }

    // Atualizar candidato
    @PutMapping("/Atualizar/{id}")
    public ResponseEntity<?> updateCadidate(@PathVariable Long id, @Valid @RequestBody CandidateDTO dto) {
        Candidate entity = new Candidate();
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setDescription(dto.getDescription());
        entity.setCurriculo(dto.getCurriculo());
        return cadidateService.updateCadidate(id, entity);
    }

    // Deletar candidato
    @DeleteMapping("/Deletar/{id}")
    public ResponseEntity<?> deleteCadidate(@PathVariable Long id) {
        return cadidateService.deleteCadidate(id);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody DtoLogin dto) {
        return cadidateService.login(dto);
    }
    
}
