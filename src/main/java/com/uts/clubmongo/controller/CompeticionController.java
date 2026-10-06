package com.uts.clubmongo.controller;
import com.uts.clubmongo.model.Competicion;
import com.uts.clubmongo.repository.CompeticionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/competiciones")
@RequiredArgsConstructor
public class CompeticionController {
    private final CompeticionRepository repository;

    @GetMapping
    public List<Competicion> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Competicion obtener(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Competición no encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Competicion crear(@RequestBody Competicion competicion) { return repository.save(competicion); }

    @PutMapping("/{id}")
    public Competicion actualizar(@PathVariable String id, @RequestBody Competicion competicion) {
        competicion.setId(id);
        return repository.save(competicion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { repository.deleteById(id); }
}