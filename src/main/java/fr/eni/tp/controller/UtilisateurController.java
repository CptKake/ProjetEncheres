package fr.eni.tp.controller;

import fr.eni.tp.bo.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs") // URL de base pour les utilisateurs
public class UtilisateurController<UtilisateurService> {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // 🔹 1. Récupérer tous les utilisateurs
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    // 🔹 2. Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 3. Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok(savedUtilisateur);
    }

    // 🔹 4. Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur newUtilisateur) {
        return utilisateurService.getUtilisateurById(id).map(utilisateur -> {
            utilisateur.setPseudo(newUtilisateur.getPseudo());
            utilisateur.setLastName(newUtilisateur.getLastName());
            utilisateur.setFirstName(newUtilisateur.getFirstName());
            utilisateur.setEmail(newUtilisateur.getEmail());
            utilisateur.setPhone(newUtilisateur.getPhone());
            utilisateur.setStreet(newUtilisateur.getStreet());
            utilisateur.setPostalCode(newUtilisateur.getPostalCode());
            utilisateur.setCity(newUtilisateur.getCity());
            utilisateur.setCredit(newUtilisateur.getCredit());
            utilisateur.setAdmin(newUtilisateur.getAdmin());

            Utilisateur updatedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
            return ResponseEntity.ok(updatedUtilisateur);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 5. Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
