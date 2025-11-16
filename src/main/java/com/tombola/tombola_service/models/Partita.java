package com.tombola.tombola_service.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Partita {
    @Id
    private String id; // ID partita personalizzato
    
    private boolean inCorso = false;

    // Lista dei 90 numeri disponibili (che verranno mescolati all'inizio)
    @ElementCollection
    private List<Integer> numeriEstrattiDisponibili = new ArrayList<>(); 

    // Lista dei numeri già usciti finora (per la visualizzazione sul tabellone)
    @ElementCollection
    private List<Integer> numeriUsciti = new ArrayList<>(); 

    // Una partita ha molti giocatori
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Giocatore> giocatori = new ArrayList<>();
}
