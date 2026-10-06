package com.uts.clubmongo.repository;
import com.uts.clubmongo.model.Competicion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CompeticionRepository extends MongoRepository<Competicion, String> {}