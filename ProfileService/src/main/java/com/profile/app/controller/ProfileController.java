package com.profile.app.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.profile.app.exception.AddUserException;
import com.profile.app.model.UserProfile;
import com.profile.app.service.ProfileServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200/"})
public class ProfileController {

	@Autowired
	private ProfileServiceImpl profileServiceImpl;
	
	Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@PostMapping("/user")
	@ApiOperation("This method is used to add profile in database of user/merchant as per role")
	public UserProfile addNewCustomerProfile(@RequestBody UserProfile userProfile) throws AddUserException {
		
		logger.trace("Add user profile method accesd");
		String check = null;
		try {
			List<UserProfile> mobileNo = profileServiceImpl.findByMobileNo(userProfile.getMobileNo());
			check = "mobile no is present";
			if(mobileNo.isEmpty()) {
				check="Email is already present";
				List<UserProfile> email = profileServiceImpl.findByEmail(userProfile.getEmail());
				if(email.isEmpty()) {
					return profileServiceImpl.addNewCustomerProfile(userProfile);
				}
				logger.error(check);
			}
			logger.error(check);
			throw new AddUserException(check);
			
		} catch (AddUserException e) {
			userProfile.setEmail(check);
		}
		
		return userProfile;

	}

	
	@GetMapping("/user")
	@ApiOperation("This method is used to get all profiles")
	public List<UserProfile> getAllProfiles() {
		logger.trace("Get all user method accesed");
		
		List<UserProfile> profileList = profileServiceImpl.getAllProfiles();
		return profileList;
	}

	
	@GetMapping("/user/{userId}")
	@ApiOperation("This method is used to get profile as per the userId ")
	public UserProfile getByProfileId(@PathVariable("userId") String Id) {
		logger.trace("Get profile by ID method accessed");
		
		UserProfile userProfile = profileServiceImpl.getByProfileId(Id);
		return userProfile;
	}

	
	@PutMapping("/update/user/{userId}")
	@ApiOperation("This method is used to update user profile")
	public UserProfile updateProfile(@RequestBody UserProfile userProfile,
										@PathVariable("userId") String userId) {
		logger.trace("Update user method accessed");
		
		return profileServiceImpl.updateProfile(userProfile,userId);
	}

	
	@DeleteMapping("/user/{userId}")
	@ApiOperation("This method is used to delete user account by userId ")
	public String deleteProfile(@PathVariable("userId") String userId) {
		
		logger.trace("delete method accesed");
		try {
			UserProfile profile = profileServiceImpl.getByProfileId(userId);
		}
		catch(Exception exception) {
			logger.error("Profile with the given id is not present");
			return "user not found";
		}
		return profileServiceImpl.deleteProfile(userId);
	}

	
	@GetMapping("/user/mobileno/{mobileNo}")
	@ApiOperation("This method is used to get profile as per the mobileNo ")
	public UserProfile  findByMobileNo(@PathVariable("mobileNo") Long mobileNo) {
		logger.trace("Get user by mobile number method accesed");
		try {
			UserProfile profile =  profileServiceImpl.findByMobileNo(mobileNo).get(0);
			return profile;

		} catch (Exception e) {
			
		}
		return null;
	}

	
	@GetMapping("/user/email/{email}")
	@ApiOperation("This method is used to get profile as per the Email id ")
	public UserProfile findByEmail(@PathVariable("email") String email) {
		logger.trace("Get user by email id method accesed");
		
		try {
			UserProfile profile = profileServiceImpl.findByEmail(email).get(0);
			return profile;
		} catch (Exception e) {
			 
		}
		return null;
	}
	
	@GetMapping("/user/username/{username}")
	@ApiOperation("This method is used to get profile as per the username ")
	public UserProfile findByUsername(@PathVariable("username")String username) {
		logger.trace("Get user by username id method accesed");
		UserProfile profile=profileServiceImpl.getByUsername(username);
		return profile;
	}

	

}
