package com.sheiladiz.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.exceptions.friendship.FriendshipNotFoundException;
import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.repositories.FriendshipRepository;
//import com.sheiladiz.repositories.ProfileRepository;
//import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.FriendshipService;

@Service
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	// @Autowired
	// private ProfileRepository profileRepository;

	// @Autowired
	// private UserRepository userRepository;

	public Friendship findFriendship(Profile profile, Profile friendProfile) {
		return friendshipRepository.findByProfileAndFriendProfile(profile, friendProfile)
				.orElseThrow(() -> new FriendshipNotFoundException("No se encontro la solicitud de amistad"));
	}

	public boolean friendshipExists(Profile profile, Profile friendProfile) {
		return friendshipRepository.existsByProfileAndFriendProfile(profile, friendProfile)
				|| friendshipRepository.existsByProfileAndFriendProfile(friendProfile, profile);
	}

	public String sendFriendRequest(Profile profile, Profile friendProfile) {
		if (friendshipExists(profile, friendProfile)) {
			return "Ya existe una solicitud de amistad / ya son amigos";
		}

		Friendship friendship = new Friendship(profile, friendProfile, FriendshipStatus.PENDING);
		friendshipRepository.save(friendship);

		return "Solicitud de amistad enviada!";

	}

	public String acceptFriendRequest(Profile profile, Profile friendProfile) {
		Friendship friendship = findFriendship(friendProfile, profile);
		if (friendship.getStatus() == FriendshipStatus.PENDING) {
			Friendship existingFriendship = friendship;
			existingFriendship.setStatus(FriendshipStatus.ACCEPTED);
			friendshipRepository.save(existingFriendship);
			return "Solicitud de amistad aceptada.";
		}

		return "No se encontró una solicitud de amistad pendiente.";
	}

	public String rejectFriendRequest(Profile profile, Profile friendProfile) {
		Friendship friendship = findFriendship(friendProfile, profile);
		if (friendship.getStatus() == FriendshipStatus.PENDING) {
			friendshipRepository.delete(friendship);
			return "Solicitud de amistad rechazada y eliminada.";
		}

		return "No se encontró una solicitud de amistad pendiente.";
	}

	public String removeFriend(Profile profile, Profile friendProfile) {
		Friendship friendship = findFriendship(friendProfile, profile);
		if (friendship != null) {
			friendshipRepository.delete(friendship);
			return "Amistad eliminada.";
		}

		return "No se encontró una amistad existente.";
	}
}
