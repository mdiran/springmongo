package com.rfpiodemo.assessment.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rfpiodemo.assessment.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query(value = "{ 'userId' : ?0}")
	Optional<User> findByUserId(String userId);

	
	@Query("{income : {$gte : ?0, $lte : ?1}}")
	List<User> findByIncomeRange(Double minSalary, Double maxSalary, Sort sort);
}