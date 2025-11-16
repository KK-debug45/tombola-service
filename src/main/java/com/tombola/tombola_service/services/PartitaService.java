package com.tombola.tombola_service.services;

import com.tombola.tombola_service.models.Partita;
import com.tombola.tombola_service.models.Giocatore;
import com.tombola.tombola_service.models.Cartella;
import com.tombola.tombola_service.repositories.PartitaRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PartitaService {

    // Spring inietta PartitaRepository automaticamente (@Autowired non è più necessario)
    private final PartitaRepository partitaRepository;

    public PartitaService(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }
    
    // Altri repository per Cartella e Giocatore andrebbero qui, ma per ora usiamo solo Partita
    // ...
    // Continuazione di PartitaService.java

    /**
     * Crea e salva una nuova partita nel database H2.
     * @return La Partita appena creata.
     */
    public Partita creaNuovaPartita() {
        Partita partita = new Partita();
        
        // Genera un ID univoco
        partita.setId(UUID.randomUUID().toString());
        
        // Inizializza il "mazzo" da 1 a 90, lo mescola e lo salva
        List<Integer> numeri = IntStream.rangeClosed(1, 90).boxed().collect(Collectors.toList());
        Collections.shuffle(numeri);
        partita.setNumeriEstrattiDisponibili(numeri);
        partita.setInCorso(true); // La partita è pronta
        
        return partitaRepository.save(partita);
    }

    /**
     * Recupera una partita dal database.
     * @param id L'ID della partita.
     * @return La partita, se trovata, altrimenti null.
     */
    public Partita trovaPartitaById(String id) {
        return partitaRepository.findById(id).orElse(null);
    }

    // ... Metodo per aggiungere il giocatore e le cartelle (da implementare)
    // ...

        // Continuazione di PartitaService.java

    /**
     * Estrae il prossimo numero disponibile per la partita specificata.
     * @param idPartita L'ID della partita.
     * @return Il numero estratto, o -1 se finiti.
     */
    public synchronized Integer estraiProssimoNumero(String idPartita) {
        Partita partita = trovaPartitaById(idPartita);
        if (partita == null || !partita.isInCorso()) {
            throw new RuntimeException("Partita non trovata o non in corso.");
        }

        List<Integer> disponibili = partita.getNumeriEstrattiDisponibili();
        
        if (disponibili.isEmpty()) {
            partita.setInCorso(false);
            partitaRepository.save(partita);
            return -1; // Nessun numero rimanente
        }
        
        // Estrae il primo numero dalla lista mescolata e lo rimuove
        Integer numeroEstratto = disponibili.remove(0); 
        
        // Aggiorna l'elenco dei numeri usciti
        partita.getNumeriUsciti().add(numeroEstratto); 
        
        // Salva lo stato della partita nel database H2
        partitaRepository.save(partita); 
        
        // *** Qui andrebbe la logica per segnare il numero su tutte le cartelle ***
        // (Che implementeremo meglio in PartitaController quando chiamiamo l'estrazione)
        
        return numeroEstratto;
    }
}
