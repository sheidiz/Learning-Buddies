import React from "react";
import { BiTrash } from "react-icons/bi";
import { MdCheck } from "react-icons/md";
import { useAuth } from "../../contexts/AuthContext";
import friendshipService from "../../services/friendshipService";
import { useNavigate } from "react-router-dom";

export const FriendRequest = ({ index, profile, type }) => {
  const { token } = useAuth();
  const {
    id,
    name,
    profilePicture,
    profilePictureBackground,
    gender,
    jobPosition,
    country,
    bio,
    skillsLearned,
    skillsToLearn,
    discordUrl,
    githubUrl,
    linkedinUrl,
    contactEmail,
  } = profile;

  const handleDeleteRequest = async () => {
    try {
      await friendshipService.removeFriendshipRequest(id, token);
      window.location.reload();
    } catch (error) {
      console.log(error);
    }
  };

  const handleAcceptRequest = async () => {
    try {
      await friendshipService.acceptFriendshipRequest(id, token);
      window.location.reload();
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div key={index} className="flex gap-4">
      <div className="overflow-hidden rounded-full">
        <img
          src={profilePicture}
          alt="Avatar"
          className="h-24 rounded-full bg-zinc-400 dark:bg-zinc-700"
          style={{ backgroundColor: profilePictureBackground }}
        />
      </div>
      <div className="flex flex-col justify-evenly">
        <p className="text-lg font-semibold">{name}</p>
        <p className="mb-1 font-semibold">{jobPosition}</p>
        <div className="flex gap-2">
          <button
            onClick={handleDeleteRequest}
            className="text-decoration-none block w-fit select-none rounded-3xl border-2 border-transparent bg-red-800 px-4 py-1 text-sm font-semibold text-white md:text-base md:hover:scale-105"
          >
            <BiTrash />
          </button>
          {type == "Received" && (
            <button
              onClick={handleAcceptRequest}
              className="text-decoration-none block w-fit select-none rounded-3xl border-2 border-transparent bg-green-800 px-4 py-1 text-sm font-semibold text-white md:text-base md:hover:scale-105"
            >
              <MdCheck />
            </button>
          )}
        </div>
      </div>
    </div>
  );
};
