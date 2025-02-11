package fr.eni.tp.controller;

import fr.eni.tp.bo.Enchere;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/encheres")
public class EnchereController {

    private final EnchereService enchereService;

    public EnchereController(EnchereService enchereService) {
        this.enchereService = enchereService;
    }

    // Récupérer toutes les enchères
    @GetMapping
    public List<Enchere> getAllEncheres() {
        return enchereService.getAllEncheres();
    }

    // Récupérer une enchère par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Enchere> getEnchereById(@PathVariable int id) {
        Optional<Enchere> enchere = enchereService.getEnchereById(id);
        return enchere.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajouter une enchère
    @PostMapping
    public Enchere createEnchere(@RequestBody Enchere enchere) {
        return enchereService.createEnchere(enchere);
    }

    // Modifier une enchère
    @PutMapping("/{id}")
    public ResponseEntity<Enchere> updateEnchere(@PathVariable int id, @RequestBody Enchere enchere) {
        return enchereService.updateEnchere(id, enchere)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer une enchère
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnchere(@PathVariable int id) {
        if (enchereService.deleteEnchere(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
