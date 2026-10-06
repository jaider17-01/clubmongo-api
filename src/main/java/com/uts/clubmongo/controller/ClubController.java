package com.uts.clubmongo.controller;
import com.uts.clubmongo.model.Club;
import com.uts.clubmongo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clubes")
@RequiredArgsConstructor
public class ClubController {
    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;
    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;

    @GetMapping
    public List<Club> listar() { return clubRepository.findAll(); }

    @GetMapping("/{id}")
    public Club obtener(@PathVariable String id) {
        return clubRepository.findById(id).orElseThrow(() -> new RuntimeException("Club no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Club crear(@RequestBody Club club) { return clubRepository.save(club); }

    @PutMapping("/{id}")
    public Club actualizar(@PathVariable String id, @RequestBody Club club) {
        club.setId(id);
        return clubRepository.save(club);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { clubRepository.deleteById(id); }

    @GetMapping("/stats")
    public Map<String, Object> estadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalClubs", clubRepository.count());
        stats.put("totalEntrenadores", entrenadorRepository.count());
        stats.put("totalJugadores", jugadorRepository.count());
        stats.put("totalAsociaciones", asociacionRepository.count());
        stats.put("totalCompeticiones", competicionRepository.count());
        return stats;
    }
}