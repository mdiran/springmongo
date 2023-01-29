package com.rfpiodemo.assessment.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rfpiodemo.assessment.model.Address;
import com.rfpiodemo.assessment.model.User;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

}