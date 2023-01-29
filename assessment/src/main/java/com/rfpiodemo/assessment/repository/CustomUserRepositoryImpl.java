package com.rfpiodemo.assessment.repository;

import static com.mongodb.client.model.Filters.eq;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.rfpiodemo.assessment.dto.CountryIncomeDTO;
import com.rfpiodemo.assessment.dto.UserCountDTO;
import com.rfpiodemo.assessment.model.User;
import com.rfpiodemo.assessment.util.Constants;
import com.rfpiodemo.assessment.util.DocumentToUserConverter;


@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> findUsersByZipCode(String zipcode) {

		MongoCollection<Document> collection = mongoTemplate.getCollection(Constants.USER);
		List<Document> documentList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
	
		FindIterable<Document> iterable = collection.find(eq(Constants.ADDRESS_ZIPCODE, zipcode));
		iterable.forEach(documentList::add);
		DocumentToUserConverter.convert(documentList, userList);
		return userList;
	}

	public List<User> findUsersByVehiclePreference(String vehicle) {

		MongoCollection<Document> collection = mongoTemplate.getCollection(Constants.USER);
		List<Document> documentList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
	
		FindIterable<Document> iterable = collection.find(eq(Constants.PREFERENCE_VEHICLE, vehicle));
		iterable.forEach(documentList::add);
		DocumentToUserConverter.convert(documentList, userList);
		return userList;
	}
	public List<CountryIncomeDTO> getCountrywiseIncome() {
		GroupOperation groupByCountry = group(Constants.ADDRESS_COUNTRY)
				  .sum(Constants.INCOME).as(Constants.COUNTRY_iNCOME);
				SortOperation soryByCountryIncome = sort(Sort.by(Direction.DESC, Constants.COUNTRY_iNCOME));
				ProjectionOperation projectStage = Aggregation.project().andExpression(Constants.UNDERSCORE_ID).as(Constants.COUNTRY)
						.andExpression(Constants.COUNTRY_iNCOME).as(Constants.COUNTRY_iNCOME);
				Aggregation aggregation = newAggregation(
				  groupByCountry, soryByCountryIncome,projectStage);
				AggregationResults<CountryIncomeDTO> result = mongoTemplate.aggregate(
				  aggregation, User.class, CountryIncomeDTO.class);
				return result.getMappedResults();
	}

	public UserCountDTO getMinMaxUserCountCountries() {
		GroupOperation sumZips = group(Constants.ADDRESS_COUNTRY).count().as(Constants.USER_COUNT);
		SortOperation sortByCount = sort(Direction.ASC, Constants.USER_COUNT);
		GroupOperation groupFirstAndLast = group().first(Constants.UNDERSCORE_ID).as(Constants.MIN_USER_COUNTRY)
		  .first(Constants.USER_COUNT).as(Constants.MIN_USER_COUNT).last(Constants.UNDERSCORE_ID).as(Constants.MAX_USER_COUNTRY)
		  .last(Constants.USER_COUNT).as(Constants.MAX_USER_COUNT);
	
		Aggregation aggregation = newAggregation(sumZips, sortByCount, groupFirstAndLast);
	
		AggregationResults<UserCountDTO> result = mongoTemplate
		  .aggregate(aggregation, User.class, UserCountDTO.class);
		return result.getUniqueMappedResult();
	}

	@Override
	public List<User> getByCityVehichleIncome(String city, String vehicle, Double income) {
		Query query = new Query();
		query.addCriteria(Criteria.where(Constants.INCOME).gte(income).and(Constants.PREFERENCE_VEHICLE).is(vehicle)
									.and(Constants.ADDRESS_CITY).is(city)).with(Sort.by(Sort.Direction.DESC, Constants.INCOME));
		return mongoTemplate.find(query, User.class);
	}
}
