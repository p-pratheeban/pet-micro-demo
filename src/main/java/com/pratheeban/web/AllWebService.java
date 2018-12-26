package com.pratheeban.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.pratheeban.exception.UserNotFoundException;
import com.pratheeban.pet.Pet;
import com.pratheeban.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllWebService {

	private static Logger logger = LoggerFactory.getLogger(AllWebService.class);
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	private String petsServiceUrl;
	private String usersServiceUrl;

	public AllWebService(String usersServiceUrl, String petsServiceUrl) {
		this.usersServiceUrl = usersServiceUrl;
		this.petsServiceUrl = petsServiceUrl;
	}

	public UserSkeleton findUserById(Long id) {
		logger.info("findUserById({}) called", id);
		User user = null;
		try {
			user = restTemplate.getForObject(usersServiceUrl + "/users/id/{id}", User.class, id);
		} catch (HttpClientErrorException e) {
			// no user
			throw new UserNotFoundException(id);
		}
		return new UserSkeleton(user.getId(), user.getUsername());
	}

	public List<PetSkeleton> findByOwnerId(Long ownerId) {
		logger.info("findByOwnerId({}) called", ownerId);
		Pet[] pets = null;
		try {
			pets = restTemplate.getForObject(petsServiceUrl + "/pets/owner/{id}", com.pratheeban.pet.Pet[].class,
					ownerId);
		} catch (HttpClientErrorException e) {
			// no pets
		}

		if (pets == null || pets.length == 0) {
			return null;
		}
		List<PetSkeleton> petsList = new ArrayList<>();
		for (Pet pet : pets) {
			petsList.add(new PetSkeleton(pet.getName(), pet.getAge(), pet.getPetType().toString()));
		}
		return petsList;
	}

	public List<PetSkeleton> findByType(String type) {
		logger.info("findBytype({}) called", type);
		Pet[] pets = null;
	
		List<PetSkeleton> petsList = new ArrayList<>();
		for (Pet pet : pets) {
			petsList.add(new PetSkeleton(pet.getName(), pet.getAge(), pet.getPetType().toString()));
		}
		return petsList;
	}

}
