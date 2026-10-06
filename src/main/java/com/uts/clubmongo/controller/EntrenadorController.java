package com.uts.clubmongo.controller;
import com.uts.clubmongo.model.Entrenador;
import com.uts.clubmongo.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
public class EntrenadorController {
    private final EntrenadorRepository repository;

    @GetMapping
    public List<Entrenador> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Entrenador obtener(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrenador crear(@RequestBody Entrenador entrenador) { return repository.save(entrenador); }

    @PutMapping("/{id}")
    public Entrenador actualizar(@PathVariable String id, @RequestBody Entrenador entrenador) {
        entrenador.setId(id);
        return repository.save(entrenador);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { repository.deleteById(id); }
}