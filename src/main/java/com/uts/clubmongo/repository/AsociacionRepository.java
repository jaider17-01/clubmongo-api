package com.uts.clubmongo.repository;
import com.uts.clubmongo.model.Asociacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AsociacionRepository extends MongoRepository<Asociacion, String> {}