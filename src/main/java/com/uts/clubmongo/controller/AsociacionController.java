package com.uts.clubmongo.controller;
import com.uts.clubmongo.model.Asociacion;
import com.uts.clubmongo.repository.AsociacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/asociaciones")
@RequiredArgsConstructor
public class AsociacionController {
    private final AsociacionRepository repository;

    @GetMapping
    public List<Asociacion> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Asociacion obtener(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Asociación no encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Asociacion crear(@RequestBody Asociacion asociacion) { return repository.save(asociacion); }

    @PutMapping("/{id}")
    public Asociacion actualizar(@PathVariable String id, @RequestBody Asociacion asociacion) {
        asociacion.setId(id);
        return repository.save(asociacion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) { repository.deleteById(id); }
}