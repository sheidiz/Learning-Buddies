import React from "react";

export const FriendCard = ({ index, profile, openModal, selectProfile }) => {
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
        <button
          onClick={() => {
            openModal(true);
            selectProfile(profile);
          }}
          className="text-decoration-none block w-fit select-none rounded-3xl border-2 border-transparent bg-dark-green px-10 py-1 text-sm font-semibold text-white md:hover:scale-105 dark:bg-dm-medium-green md:dark:bg-dm-light-green/40"
        >
          Ver info
        </button>
      </div>
    </div>
  );
};
