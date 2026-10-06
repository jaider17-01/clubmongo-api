package com.uts.clubmongo.repository;
import com.uts.clubmongo.model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JugadorRepository extends MongoRepository<Jugador, String> {}