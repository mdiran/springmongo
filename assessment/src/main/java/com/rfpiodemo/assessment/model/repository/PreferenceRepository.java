package com.rfpiodemo.assessment.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rfpiodemo.assessment.model.Preference;

@Repository
public interface PreferenceRepository extends MongoRepository<Preference, String> {
}