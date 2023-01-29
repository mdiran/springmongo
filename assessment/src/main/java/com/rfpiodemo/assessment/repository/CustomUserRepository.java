package com.rfpiodemo.assessment.repository;

import java.util.List;

import com.rfpiodemo.assessment.dto.CountryIncomeDTO;
import com.rfpiodemo.assessment.dto.UserCountDTO;
import com.rfpiodemo.assessment.model.User;

public interface CustomUserRepository {
	public List<User> findUsersByZipCode(String zipOrPostalCode);
	public List<User> findUsersByVehiclePreference(String vehicle);
	public List<CountryIncomeDTO> getCountrywiseIncome();
	public UserCountDTO getMinMaxUserCountCountries();
	public List<User> getByCityVehichleIncome(String city, String vehicle, Double income);
}
