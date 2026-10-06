package com.uts.clubmongo.service;

import com.uts.clubmongo.model.*;
import com.uts.clubmongo.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Concentra las reglas que en JPA resolvían las anotaciones y la BD:
 *  - integridad referencial (FK "no action" / ON DELETE CASCADE) -> aquí, a mano
 *  - Jugador pertenece a UN solo club (semántica real de @OneToMany)
 */
@Service
public class ClubService {

    private final ClubRepository clubs;
    private final JugadorRepository jugadores;
    private final AsociacionRepository asociaciones;
    private final CompeticionRepository competiciones;

    public ClubService(ClubRepository clubs, JugadorRepository jugadores,
                       AsociacionRepository asociaciones, CompeticionRepository competiciones) {
        this.clubs = clubs;
        this.jugadores = jugadores;
        this.asociaciones = asociaciones;
        this.competiciones = competiciones;
    }

    public static ResponseStatusException noEncontrado(String tipo, String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, tipo + " no encontrado: " + id);
    }

    // ---------- CRUD de Club ----------

    public List<Club> listar() {
        return clubs.findAll();
    }

    public Club obtener(String id) {
        return clubs.findById(id).orElseThrow(() -> noEncontrado("Club", id));
    }

    public Club crear(ClubRequest r) {
        return guardar(new Club(), r);
    }

    public Club actualizar(String id, ClubRequest r) {
        return guardar(obtener(id), r);
    }

    public void eliminar(String id) {
        obtener(id);
        clubs.deleteById(id); // no se borran jugadores/asociación/competiciones: solo el club
    }

    private Club guardar(Club club, ClubRequest r) {
        club.setNombre(r.nombre());
        club.setEntrenador(r.entrenador());

        // @ManyToOne: el club apunta a UNA asociación (opcional)
        if (r.asociacionId() == null || r.asociacionId().isBlank()) {
            club.setAsociacion(null);
        } else {
            club.setAsociacion(asociaciones.findById(r.asociacionId())
                    .orElseThrow(() -> noEncontrado("Asociación", r.asociacionId())));
        }

        // @OneToMany: cada jugador solo puede estar en un club
        List<Jugador> plantel = resolver(jugadores, r.jugadoresIds(), "Jugador");
        validarJugadoresLibres(club.getId(), plantel);
        club.setJugadores(plantel);

        // @ManyToMany: sin restricción de exclusividad
        club.setCompeticiones(resolver(competiciones, r.competicionesIds(), "Competición"));

        return clubs.save(club);
    }

    // ---------- Equivalente a FK "no action" y a ON DELETE CASCADE ----------

    /** FK por defecto "no action": no se borra una asociación con clubes afiliados. */
    public void eliminarAsociacion(String id) {
        asociaciones.findById(id).orElseThrow(() -> noEncontrado("Asociación", id));
        boolean enUso = clubs.findAll().stream()
                .anyMatch(c -> c.getAsociacion() != null && id.equals(c.getAsociacion().getId()));
        if (enUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: hay clubes afiliados a esta asociación");
        }
        asociaciones.deleteById(id);
    }

    /** Equivalente a ON DELETE CASCADE sobre la relación: se quita el jugador de su club. */
    public void eliminarJugador(String id) {
        for (Club c : clubs.findAll()) {
            List<Jugador> restantes = c.getJugadores().stream()
                    .filter(j -> !id.equals(j.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (restantes.size() != c.getJugadores().size()) {
                c.setJugadores(restantes);
                clubs.save(c);
            }
        }
        jugadores.deleteById(id);
    }

    /** Quita la competición de todos los clubes que participaban en ella. */
    public void eliminarCompeticion(String id) {
        for (Club c : clubs.findAll()) {
            List<Competicion> restantes = c.getCompeticiones().stream()
                    .filter(x -> !id.equals(x.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (restantes.size() != c.getCompeticiones().size()) {
                c.setCompeticiones(restantes);
                clubs.save(c);
            }
        }
        competiciones.deleteById(id);
    }

    // ---------- Auxiliares ----------

    private <T> List<T> resolver(MongoRepository<T, String> repo, List<String> ids, String tipo) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> unicos = new LinkedHashSet<>(ids);
        List<T> encontrados = repo.findAllById(unicos);
        if (encontrados.size() != unicos.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Alguno de los ids de " + tipo + " no existe: " + unicos);
        }
        return new ArrayList<>(encontrados);
    }

    private void validarJugadoresLibres(String clubId, List<Jugador> plantel) {
        Set<String> ids = plantel.stream().map(Jugador::getId).collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return;
        }
        for (Club otro : clubs.findAll()) {
            if (Objects.equals(otro.getId(), clubId)) {
                continue;
            }
            for (Jugador j : otro.getJugadores()) {
                if (ids.contains(j.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "El jugador " + j.getId() + " ya pertenece al club " + otro.getNombre());
                }
            }
        }
    }
}
