package com.uts.clubmongo.repository;
import com.uts.clubmongo.model.Entrenador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EntrenadorRepository extends MongoRepository<Entrenador, String> {}