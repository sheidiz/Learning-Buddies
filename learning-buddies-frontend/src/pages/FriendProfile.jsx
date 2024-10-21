import React, { useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { TextLabel } from "../components/user/TextLabel";
import profilesService from "../services/profilesService";
import friendshipService from "../services/friendshipService";
import { FriendCard } from "../components/buddies/FriendCard";
import { FriendRequest } from "../components/buddies/FriendRequest";
import { getProfilePictureId } from "../utils/functions";
import { MdClose } from "react-icons/md";

export default function FriendProfile({ profile, openModal }) {
  const {
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
    <section className="fixed left-1/2 top-[10vh] flex w-5/6 max-w-3xl -translate-x-1/2 flex-col rounded-md border border-dark-green bg-white p-4 dark:border-medium-green dark:bg-dm-dark-green dark:text-light">
      <button
        onClick={() => openModal(false)}
        className="absolute right-5 top-5 self-end hover:scale-125"
      >
        <MdClose className="text-2xl text-dark dark:text-light" />
      </button>
      <div className="mb-2 flex flex-col items-center justify-center">
        <div className="overflow-hidden rounded-full">
          <img
            src={profilePicture}
            alt="Avatar"
            className="h-28 rounded-full md:h-32"
            style={{ backgroundColor: profilePictureBackground }}
          />
        </div>
        <h1 className="mt-1 text-2xl font-bold">{name}</h1>
        <h3>
          {jobPosition} | {gender}{" "}
        </h3>
      </div>
      <h4 className="mb-1 text-xl font-bold">Biografía</h4>
      <p className="mb-2 font-light">{bio}</p>
      <h2 className="text-xl font-bold">Datos de contacto</h2>
      <div className="mt-2 grid grid-cols-2 gap-4">
        <TextLabel label="Discord" inputPlaceholder={discordUrl} />
        <TextLabel label="GitHub" inputPlaceholder={githubUrl} />
        <TextLabel label="LinkedIn" inputPlaceholder={linkedinUrl} />
        <TextLabel label="Email" inputPlaceholder={contactEmail} />
      </div>
      <h2 className="mt-6 text-xl font-bold">Habilidades</h2>
      {skillsLearned.length > 0 && (
        <>
          <p className="my-2 font-medium">CONOCIMIENTOS:</p>
          <div className="flex flex-wrap gap-2 pb-3 text-sm text-white md:gap-3 md:text-base">
            {skillsLearned.map((item, index) => (
              <p
                key={index}
                className={`rounded-md bg-light-green px-2 shadow-inner-custom md:text-sm lg:text-base dark:bg-dm-light-green`}
              >
                {item}
              </p>
            ))}
          </div>
        </>
      )}
      {skillsToLearn.length > 0 && (
        <>
          <p className="my-2 font-medium">APRENDIENDO:</p>
          <div className="flex flex-wrap gap-2 text-sm text-dark md:gap-3 md:text-base">
            {skillsToLearn.map((item, index) => (
              <p
                key={index}
                className={`rounded-md border-2 border-light-green bg-light px-2 shadow-inner-custom md:text-sm lg:text-base dark:border-dm-light-green`}
              >
                {item}
              </p>
            ))}
          </div>
        </>
      )}
    </section>
  );
}
