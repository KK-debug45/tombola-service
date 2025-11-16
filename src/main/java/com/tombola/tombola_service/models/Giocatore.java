package com.tombola.tombola_service.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    // Un giocatore ha più cartelle (OneToMany)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cartella> cartelle = new ArrayList<>();
}
