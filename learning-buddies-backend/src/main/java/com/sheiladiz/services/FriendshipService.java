package com.sheiladiz.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.repositories.FriendshipRepository;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.UserRepository;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<Friendship> findFriendship(Profile profile, Profile friendProfile) {
		return friendshipRepository.findByProfileAndFriendProfile(profile, friendProfile);
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
		Optional<Friendship> friendship = findFriendship(friendProfile, profile);
		if (friendship.isPresent() && friendship.get().getStatus() == FriendshipStatus.PENDING) {
			Friendship existingFriendship = friendship.get();
			existingFriendship.setStatus(FriendshipStatus.ACCEPTED);
			friendshipRepository.save(existingFriendship);
			return "Solicitud de amistad aceptada.";
		}

		return "No se encontró una solicitud de amistad pendiente.";
	}

	public String rejectFriendRequest(Profile profile, Profile friendProfile) {
		Optional<Friendship> friendship = findFriendship(friendProfile, profile);
		if (friendship.isPresent() && friendship.get().getStatus() == FriendshipStatus.PENDING) {
			friendshipRepository.delete(friendship.get());
			return "Solicitud de amistad rechazada y eliminada.";
		}

		return "No se encontró una solicitud de amistad pendiente.";
	}

	public String removeFriend(Profile profile, Profile friendProfile) {
		Optional<Friendship> friendship = findFriendship(profile, friendProfile);
		if (friendship.isPresent()) {
			friendshipRepository.delete(friendship.get());
			return "Amistad eliminada.";
		}

		return "No se encontró una amistad existente.";
	}
}
