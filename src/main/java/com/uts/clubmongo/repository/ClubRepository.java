package com.uts.clubmongo.repository;
import com.uts.clubmongo.model.Club;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClubRepository extends MongoRepository<Club, String> {}