import React, { useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { TextLabel } from "../components/user/TextLabel";
import profilesService from "../services/profilesService";
import friendshipService from "../services/friendshipService";
import { FriendCard } from "../components/buddies/FriendCard";
import { FriendRequest } from "../components/buddies/FriendRequest";
import { getProfilePictureId } from "../utils/functions";
import FriendProfile from "./FriendProfile";

export default function Profile() {
  const { token } = useAuth();
  const [profile, setProfile] = useState();
  const [friends, setFriends] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const [openFriendModal, setOpenFriendModal] = useState(false);
  const [selectedProfile, setSelectedProfile] = useState();

  const fetchProfile = async () => {
    try {
      const [profileData, friendshipsData] = await Promise.all([
        profilesService.getProfile(token),
        friendshipService.getFriendships(token),
      ]);
      setProfile(profileData);
      setFriends(friendshipsData);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  if (isLoading) {
    return <p>Cargando...</p>;
  }

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
  const { friendships, sentRequests, receivedRequests } = friends;

  return (
    <main>
      <section
        className={`relative mb-5 w-full px-2 pt-2 font-raleway text-dark md:mx-auto md:flex md:max-w-7xl md:gap-x-4 dark:text-light ${openFriendModal && "blur-sm"}`}
      >
        <div className="px-3 md:w-2/3 md:rounded-md md:bg-white md:p-6 lg:mx-auto lg:w-1/2 lg:max-w-5xl lg:px-10 md:dark:bg-dm-dark-green">
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
          <h4 className="mb-1 text-lg font-semibold">Biografía</h4>
          <p className="mb-4 font-light">{bio}</p>
          <h2 className="mt-3 text-2xl font-bold">Datos de contacto</h2>
          <h3 className="text-sm font-semibold text-dark-green dark:text-dm-light-green">
            Estos datos se mostraran solo a tus conexiones
          </h3>
          <div className="mt-2 grid grid-cols-2 gap-4">
            <TextLabel label="Discord" inputPlaceholder={discordUrl} />
            <TextLabel label="GitHub" inputPlaceholder={githubUrl} />
            <TextLabel label="LinkedIn" inputPlaceholder={linkedinUrl} />
            <TextLabel label="Email" inputPlaceholder={contactEmail} />
          </div>
          <h2 className="mt-6 text-2xl font-bold">Tus habilidades</h2>
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
          <a
            href="/edicion-perfil"
            className="text-decoration-none mx-auto mt-5 block w-fit rounded-3xl border-2 border-transparent bg-dark-green px-6 py-1 font-bold text-white md:hover:scale-105 dark:bg-dm-medium-green md:dark:bg-dark"
          >
            Editar perfil
          </a>
        </div>
        <div className="mt-10 px-3 md:mt-0 md:h-fit md:w-1/3 md:rounded-md md:bg-white md:p-6 lg:mx-auto lg:w-1/2 lg:max-w-5xl lg:px-10 md:dark:bg-dm-dark-green">
          <h2 className="text-2xl font-bold">Tus conexiones</h2>
          <div className="my-4 flex flex-col gap-4">
            {friendships.length > 0 ? (
              friendships.map((profile, index) => (
                <FriendCard
                  key={index}
                  profile={profile}
                  openModal={setOpenFriendModal}
                  selectProfile={setSelectedProfile}
                />
              ))
            ) : (
              <p>Sin amistades.</p>
            )}
          </div>
          <h2 className="pt-2 text-2xl font-bold">
            Tus solicitudes pendientes
          </h2>
          <div className="my-4 flex flex-col gap-4">
            {receivedRequests.length > 0 ? (
              receivedRequests.map((profile, index) => (
                <FriendRequest
                  key={index}
                  profile={profile}
                  type="Received"
                  onUpdate={fetchProfile}
                />
              ))
            ) : (
              <p>Sin solicitudes pendientes.</p>
            )}
          </div>
          <h2 className="pt-2 text-2xl font-bold">Tus solicitudes enviadas</h2>
          <div className="my-4 flex flex-col gap-4">
            {sentRequests.length > 0 ? (
              sentRequests.map((profile, index) => (
                <FriendRequest
                  key={index}
                  profile={profile}
                  type="Sent"
                  onUpdate={fetchProfile}
                />
              ))
            ) : (
              <p>Sin solicitudes pendientes de respuesta.</p>
            )}
          </div>
        </div>
      </section>
      {openFriendModal && (
        <FriendProfile
          profile={selectedProfile}
          openModal={setOpenFriendModal}
        />
      )}
    </main>
  );
}
