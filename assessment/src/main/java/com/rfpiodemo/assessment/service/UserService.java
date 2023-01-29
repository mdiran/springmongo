package com.rfpiodemo.assessment.service;

import java.util.List;
import java.util.Map;

import com.rfpiodemo.assessment.dto.CountryIncomeDTO;
import com.rfpiodemo.assessment.dto.UserCountDTO;
import com.rfpiodemo.assessment.model.Preference;
import com.rfpiodemo.assessment.model.User;

public interface UserService {

	public List<User> getAllUsers();	
	public void removeAllUsers();
	public User getUser(String userId);	
	public void removeUser(String userId);
	public List<User> getUsersByZipCode(String zipCode);
	public List<User> findUsersByVehiclePreference(String vehicle);
	public User addNewUsers(User user);	
	public Map<String, String> getAllUserSettings(String userId);
	public String getUserSettingByKey(String userId, String key);	
	public String addUserSetting(String userId, String key, String value);
	public User updateUserPreference(String userId, Preference preference);
	public List<User> findByIncomeRange(Double minIncome, Double maxIncome);
	public List<CountryIncomeDTO> getCountrywiseIncome();
	public UserCountDTO getMinMaxUserCountCountries();
	public List<User> getByCityVehichleIncome(String city, String vehicle, Double income);
}
