package com.rfpiodemo.assessment.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.rfpiodemo.assessment.dto.CountryIncomeDTO;
import com.rfpiodemo.assessment.dto.UserCountDTO;
import com.rfpiodemo.assessment.exception.ResourceExistsException;
import com.rfpiodemo.assessment.exception.UserResourceException;
import com.rfpiodemo.assessment.model.Preference;
import com.rfpiodemo.assessment.model.User;
import com.rfpiodemo.assessment.model.repository.AddressRepository;
import com.rfpiodemo.assessment.model.repository.CustomUserRepository;
import com.rfpiodemo.assessment.model.repository.PreferenceRepository;
import com.rfpiodemo.assessment.model.repository.UserRepository;
import com.rfpiodemo.assessment.util.Constants;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository = null;
	
	@Autowired
	private AddressRepository addressRepository = null;
	
	@Autowired
	PreferenceRepository preferenceRepository = null;
	
	@Autowired
	private CustomUserRepository customUserRepository = null;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void removeAllUsers() {
		LOG.info("Deleting all users.");
		addressRepository.deleteAll();
		preferenceRepository.deleteAll();
		userRepository.deleteAll();
	}

	public void removeUser(String userId) {
		userRepository.deleteById(getUser(userId).getId());
	}
	public User getUser(String userId) {
		return (userRepository.findByUserId(userId)).map(x -> x).orElseThrow(() -> new UserResourceException(Constants.USER_NOT_FOUND));
	}

	public List<User> getUsersByZipCode(String zipCode) {
		return customUserRepository.findUsersByZipCode(zipCode);
	}
	
	public List<User> findUsersByVehiclePreference(String vehicle) {
		return customUserRepository.findUsersByVehiclePreference(vehicle);
	}
	
	public User addNewUsers(User user) {
		if(userRepository.findByUserId(user.getUserId()).isPresent())  
			throw new ResourceExistsException(Constants.USERID_ALREADY_EXISTS);
		return (User) userRepository.save(user);
	}
	
	public Map<String, String> getAllUserSettings(String userId) {
		User user = getUser(userId);
		Map<String, String> settings = user.getUserSettings();
		
		if (settings.isEmpty()) {
			throw new UserResourceException(Constants.SETTINGS_NO_ENTRY_FOUND);
		}
		
		return settings;
	}
	
	public String getUserSettingByKey(String userId, @PathVariable String key) {
		String value = getUser(userId).getUserSettings().get(key);
		
		if (value == null) {
			throw new UserResourceException(Constants.SETTINGS_KEY_NOT_FOUND);
		} 
		
		return value;
	}
	
	public String addUserSetting(String userId, String key, String value) {
		User user = getUser(userId);
		user.getUserSettings().put(key, value);
		userRepository.save(user);
		return Constants.KEY_SUCCESSFULLY_ADDED;
	}

	@Override
	public User updateUserPreference(String userId, Preference preference) {
		User user = getUser(userId);
		user.setPreference(preference);
		return (User) userRepository.save(user);
	}
	
	@Override
	public List<User> findByIncomeRange(Double minIncome, Double maxIncome) {
		Sort sort = Sort.by(Direction.ASC, Constants.INCOME);
		return userRepository.findByIncomeRange(minIncome, maxIncome, sort);
	}

	public List<CountryIncomeDTO> getCountrywiseIncome() {
		return customUserRepository.getCountrywiseIncome();
	}
	
	@Override
	public UserCountDTO getMinMaxUserCountCountries() {
		return customUserRepository.getMinMaxUserCountCountries();
	}
	
	@Override
	public List<User> getByCityVehichleIncome(String city, String vehicle, Double income) {
		return customUserRepository.getByCityVehichleIncome(city, vehicle, income);
	}
}
