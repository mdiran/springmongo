package com.rfpiodemo.assessment.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

import com.rfpiodemo.assessment.model.Address;
import com.rfpiodemo.assessment.model.PhoneNumber;
import com.rfpiodemo.assessment.model.Preference;
import com.rfpiodemo.assessment.model.User;

public class DocumentToUserConverter {

	public static void convert(List<Document> list, List<User> userList) {
		for(Document document: list) {
			User user = new User();
			user.setName(document.getString(Constants.NAME));
			user.setUserId(document.getString(Constants.USERID));
			
			PhoneNumber phoneNumber = new PhoneNumber();
			Document phoneNumberDocument = (Document) document.get(Constants.PHONE_NUMBER);
			phoneNumber.setMobileNumber(phoneNumberDocument.getLong("mobileNumber"));
			phoneNumber.setAccessCode(phoneNumberDocument.getInteger("accessCode"));
			phoneNumber.setAreaCode(phoneNumberDocument.getInteger("areaCode"));
			phoneNumber.setCountryCode(phoneNumberDocument.getInteger("countryCode"));
			phoneNumber.setLocalNumber(phoneNumberDocument.getInteger("localNumber"));
			user.setPhoneNumber(phoneNumber);
			
			Address address = new Address();
			Document addressDocument = (Document) document.get(Constants.ADDRESS);
			address.setAddressline(addressDocument.getString(Constants.ADDRESS_LINE));
			address.setCity(addressDocument.getString(Constants.CITY));
			address.setStateOrProvince(addressDocument.getString(Constants.STATE_OR_PROVINCE));
			address.setCountry(addressDocument.getString(Constants.COUNTRY));
			address.setZipcode(addressDocument.getString(Constants.ZIP_CODE));
			user.setAddress(address);

			Map<String, String> userSttings = new HashMap<String, String>();
			Document settingsDocument = (Document) document.get(Constants.USER_SETTINGS);
			settingsDocument.forEach((prop, value) -> userSttings.put(prop, value.toString()));			
			user.setUserSettings(userSttings);
			
			Preference preference = new Preference();
			Document preferenceDocument = (Document) document.get(Constants.PREFERENCE);
			preference.setColors((List<String>)preferenceDocument.get(Constants.COLORS));
			preference.setDress(preferenceDocument.getString(Constants.DRESS));
			preference.setFood(preferenceDocument.getString(Constants.FOOD));
			preference.setVehicle(preferenceDocument.getString(Constants.VEHICLE));
			user.setPreference(preference);

			user.setIncome(document.getDouble(Constants.INCOME));
			userList.add(user);
		}
	}

}
