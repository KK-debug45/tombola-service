package com.tombola.tombola_service.controllers;

import com.tombola.tombola_service.models.Partita;
import com.tombola.tombola_service.services.PartitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica che questa classe gestisce gli endpoint REST
@RequestMapping("/api/partite") // Percorso base per tutti gli endpoint in questa classe
public class PartitaController {

    private final PartitaService partitaService;

    // Spring inietta PartitaService automaticamente
    public PartitaController(PartitaService partitaService) {
        this.partitaService = partitaService;
    }

        // ... I metodi di gestione delle richieste andranno qui ...
        // Continuazione di PartitaController.java

    /**
     * Endpoint: POST /api/partite
     * Crea una nuova partita e la salva nel DB H2.
     * @return La partita creata.
     */
    @PostMapping
    public ResponseEntity<Partita> creaPartita() {
        Partita nuovaPartita = partitaService.creaNuovaPartita();
        return ResponseEntity.ok(nuovaPartita);
    }

    // Continuazione di PartitaController.java

    /**
     * Endpoint: POST /api/partite/{id}/estrai
     * Estrae il prossimo numero e lo restituisce.
     * @param id L'ID della partita.
     * @return Il numero estratto o un messaggio di errore.
     */
    @PostMapping("/{id}/estrai")
    public ResponseEntity<?> estraiNumero(@PathVariable String id) {
        try {
            Integer numero = partitaService.estraiProssimoNumero(id);
            
            if (numero == -1) {
                return ResponseEntity.ok("FINE PARTITA: Tutti i numeri sono stati estratti.");
            }
            
            // Risposta HTTP 200 OK con il numero estratto
            return ResponseEntity.ok(numero); 
            
        } catch (RuntimeException e) {
            // Se la partita non esiste o non è in corso
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


        // Continuazione di PartitaController.java

    /**
     * Endpoint: GET /api/partite/{id}
     * Mostra lo stato attuale della partita (numeri usciti e partecipanti).
     * @param id L'ID della partita.
     * @return L'oggetto Partita completo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Partita> getStatoPartita(@PathVariable String id) {
        Partita partita = partitaService.trovaPartitaById(id);
        
        if (partita == null) {
            // Risposta HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(partita);
    }

}
