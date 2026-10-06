package com.uts.clubmongo.controller;
import com.uts.clubmongo.model.Jugador;
import com.uts.clubmongo.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {
    private final JugadorRepository repository;

    @GetMapping
    public List<Jugador> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Jugador obtener(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jugador crear(@RequestBody Jugador jugador) { return repository.save(jugador); }

    @PutMapping("/{id}")
    public Jugador actualizar(@PathVariable String id, @RequestBody Jugador jugador) {
        jugador.setId(id);
        return repository.save(jugador);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { repository.deleteById(id); }
}