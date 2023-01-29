package com.rfpiodemo.assessment.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rfpiodemo.assessment.dto.CountryIncomeDTO;
import com.rfpiodemo.assessment.dto.PreferenceDTO;
import com.rfpiodemo.assessment.dto.UserCountDTO;
import com.rfpiodemo.assessment.dto.UserDTO;
import com.rfpiodemo.assessment.model.Preference;
import com.rfpiodemo.assessment.model.User;
import com.rfpiodemo.assessment.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private final UserService userService = null;
	
	@Autowired
	ModelMapper modelMapper = null;
	
	/**
	 * Returns all the users in the system.
	 * 
	 * @return
	 */
	@GetMapping(value = "")
	public List<UserDTO> getAllUsers() {
		LOG.info("Getting all users in the system.");
		return userService.getAllUsers().stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}
	
	/**
	 * Returns the user identified by the userId.
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/{userId}")
	public UserDTO getUserByUserId(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		return modelMapper.map(userService.getUser(userId), UserDTO.class);
	}
	
	/**
	 * Creates a user in the system.
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = "/create")
	public UserDTO addNewUsers(@RequestBody UserDTO user) {
		LOG.info("Saving user with ID: {}.", user.getUserId());
		return modelMapper.map(userService.addNewUsers(modelMapper.map(user, User.class)),UserDTO.class);
	}

	/**
	 * Removes the user identified by the userId from system.
	 * 
	 * @param userId
	 */
	@DeleteMapping(value = "/remove/{userId}")
	public void removeUser(@PathVariable String userId) {
		LOG.info("Deleting user with ID: {}.", userId);
		userService.removeUser(userId);
	}
	
	/**
	 * Removes all the users in the system.
	 * 
	 */
	@DeleteMapping(value = "/removeAll")
	public void removeAllUsers() {
		LOG.info("Deleting all users in the system.");
		userService.removeAllUsers();
	}
	
	/**
	 * Fetches all the settings for a user identified by the userId.
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/settings/{userId}")
	public Map<String, String> getAllUserSettings(@PathVariable String userId) {
		return userService.getAllUserSettings(userId);
	}
	
	/**
	 * Fetches a particular settings entry for a user identified by the userId and key.
	 * 
	 * @param userId
	 * @param key
	 * @return
	 */
	@GetMapping(value = "/settings/{userId}/{key}")
	public String getUserSettingByKey(@PathVariable String userId, @PathVariable String key) {
		return userService.getUserSettingByKey(userId, key);
	}
	
	/**
	 * Adds settings entry for a user identified by the userId.
	 * 
	 * @param userId
	 * @param key
	 * @param value
	 * @return
	 */
	@PostMapping(value = "/settings/{userId}")
	public String addUserSetting(@PathVariable String userId, @RequestParam String key, @RequestParam String value) {
		return userService.addUserSetting(userId, key,value);
	}

	/**
	 * Updates the preference for an user identified by the userId.
	 * 
	 * @param userId
	 * @param preference
	 * @return
	 */
	@PutMapping(value = "/preference/{userId}")
	public UserDTO updateUserPreference(@PathVariable String userId, @RequestBody PreferenceDTO preference) {
		return modelMapper.map(userService.updateUserPreference(userId, modelMapper.map(preference, Preference.class)), UserDTO.class);
	}
	
	/**
	 * Fetches all users belonging to a particular zipcode.
	 * 
	 * @param zipCode
	 * @return
	 */
	@GetMapping(value = "/zipcode/{zipCode}")
	public List<UserDTO> getUsersByZipcode(@PathVariable String zipCode) {
		LOG.info("Getting users with zipcode: {}.", zipCode);
		List<User> users = userService.getUsersByZipCode(zipCode);
		System.out.println("users list size ="+users.size() +"zipcode ="+zipCode);
		return users.stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}
	
	/**
	 * Fetches all users having a particular vehicle preference identified by vehicle.
	 * 
	 * @param vehicle
	 * @return
	 */
	@GetMapping(value = "/preference/{vehicle}")
	public List<UserDTO> getUsersByVehiclePreference(@PathVariable String vehicle) {
		LOG.info("Getting user with preference of vehicle: {}.", vehicle);
		List<User> users = userService.findUsersByVehiclePreference(vehicle);
		System.out.println("users list size ="+users.size() +"zipcode ="+vehicle);
		return users.stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}
	
	/**
	 * Fetches all users falling in range between minimum and maximum income.
	 * 
	 * @param minIncome
	 * @param maxIncome
	 * @return
	 */
	@GetMapping(value = "/income/{minIncome}/{maxIncome}")
	public List<UserDTO> getUsersByIncomeRange(@PathVariable Double minIncome, @PathVariable Double maxIncome) {
		LOG.info("Getting users with Income range: {}, {}",minIncome,maxIncome) ;
		return userService.findByIncomeRange( minIncome,  maxIncome).stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}
	
	/**
	 * Fetches countrywise income.
	 * 
	 * @return
	 */
	@GetMapping(value = "/income")
	public List<CountryIncomeDTO> getCountrywiseIncome() {
		LOG.info("Getting countrywise income.") ;
		return userService.getCountrywiseIncome();
	}
	
	/**
	 * Fetches the countries having minimum and maximum user counts.
	 * 
	 * @return
	 */
	@GetMapping(value = "/country/minmaxusers")
	public UserCountDTO getMinMaxUserCountCountries() {
		LOG.info("Getting countries having minimum and maximum user counts.") ;
		return userService.getMinMaxUserCountCountries();
	}
	
	/**
	 * Fetches users that belong to particular city and having a specific vehicle preference and have a minimum income specified by city,vehicle and income.
	 * 
	 * @param city
	 * @param vehicle
	 * @param income
	 * @return
	 */
	@GetMapping(value = "/income/{city}/{vehicle}/{income}")
	public List<UserDTO> getByCityVehichleIncome(@PathVariable String city, @PathVariable String vehicle, @PathVariable Double income) {
		LOG.info("Getting users with vehicle, city, income: {}.", vehicle);
		List<User> users = userService.getByCityVehichleIncome(city, vehicle, income);
		return users.stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}
}
