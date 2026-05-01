package com.example.meu_primeiroSpringBoot.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
// Lombok removido, métodos gerados manualmente
@Entity
@Table(name = "Candidate")
public class Candidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @NotBlank(message = "Nome é obrigatório")
    private String username;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @NotBlank(message = "Nome é obrigatório")
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
  
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email não pode começar com número")
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Column(nullable = false)
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Column(nullable = true)
    private String curriculo;
    public String getCurriculo() { return curriculo; }
    public void setCurriculo(String curriculo) { this.curriculo = curriculo; }

    @CreationTimestamp
    private LocalDateTime data;
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

}
