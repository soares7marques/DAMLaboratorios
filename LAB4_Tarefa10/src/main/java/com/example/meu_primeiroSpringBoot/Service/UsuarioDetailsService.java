package com.example.meu_primeiroSpringBoot.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.meu_primeiroSpringBoot.Repository.CadidateRepository;
import com.example.meu_primeiroSpringBoot.model.Candidate;

public class UsuarioDetailsService  implements UserDetailsService{
    
        private final CadidateRepository utilizadorRepository;
    

    public UsuarioDetailsService(CadidateRepository utilizadorRepository){
          this.utilizadorRepository = utilizadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        
        Candidate utilizador = utilizadorRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
        return User.builder()
            .password(utilizador.getPassword())
            .roles("username").build();
    }  
}
