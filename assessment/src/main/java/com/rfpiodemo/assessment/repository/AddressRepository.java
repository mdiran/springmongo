package com.rfpiodemo.assessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rfpiodemo.assessment.model.Address;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

}