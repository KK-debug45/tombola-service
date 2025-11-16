package com.tombola.tombola_service.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cartella {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tutti i 15 numeri della cartella
    @ElementCollection
    private List<Integer> numeri = new ArrayList<>(); 
    
    // Lista per tracciare i numeri segnati
    @ElementCollection
    private List<Integer> numeriSegnati = new ArrayList<>(); 
    
    // Lista delle vincite già ottenute (es. ["AMBO_0", "TERNO_1"])
    @ElementCollection
    private List<String> vinciteOttenute = new ArrayList<>();
    
    // *** Qui aggiungerai la logica di segnatura e verifica vincite ***
}
