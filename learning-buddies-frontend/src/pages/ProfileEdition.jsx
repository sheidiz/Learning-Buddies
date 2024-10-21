import React, { useCallback, useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import skillsService from "../services/skillsService";
import profilesService from "../services/profilesService";
import { useNavigate } from "react-router-dom";
import AvatarSelector from "../components/user/AvatarSelector";
import { TextInput } from "../components/user/TextInput";
import { TextareaInput } from "../components/user/TextareaInput";
import { CountrySelector } from "../components/user/CountrySelector";
import { GenderSelect } from "../components/user/GenderSelect";
import { validateProfileData } from "../utils/validationUtils";
import { getProfilePictureId } from "../utils/functions";

export const ProfileEdition = () => {
  const navigate = useNavigate();
  const { token, user, updateProfile } = useAuth();
  const [profileData, setProfileData] = useState(user.profile);
  const [skills, setSkills] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState({
    name: null,
    jobPosition: null,
    bio: null,
    country: null,
    gender: null,
    contact: null,
  });
  const [selectedImage, setSelectedImage] = useState();
  const [bgColor, setBgColor] = useState();

  useEffect(() => {
    if (user && user.profile) {
      setProfileData(user.profile);
      setSelectedImage(user.profile.profilePicture);
      setBgColor(user.profile.profilePictureBackground);
    }
  }, [user]);

  const handleInputChange = useCallback((e) => {
    setProfileData((prevData) => ({
      ...prevData,
      [e.target.name]: e.target.value.trim(),
    }));
  }, []);

  const handleImageSelect = useCallback((image) => {
    setSelectedImage(image);
  }, []);

  const handleSkillToggle = useCallback((skill, isLearned) => {
    if (isLearned) {
      setProfileData((prev) => ({
        ...prev,
        skillsLearned: prev.skillsLearned.includes(skill)
          ? prev.skillsLearned.filter((s) => s !== skill)
          : [...prev.skillsLearned, skill],
      }));
    } else {
      setProfileData((prev) => ({
        ...prev,
        skillsToLearn: prev.skillsToLearn.includes(skill)
          ? prev.skillsToLearn.filter((s) => s !== skill)
          : [...prev.skillsToLearn, skill],
      }));
    }
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const validationErrors = validateProfileData(profileData);
    setErrorMessage(validationErrors);

    const hasErrors = Object.values(validationErrors).some(
      (error) => error !== null && error.trim() !== "",
    );

    if (hasErrors) {
      return;
    }

    const updatedProfileData = {
      ...profileData,
      profilePicture: selectedImage,
      profilePictureBackground: bgColor,
    };
    console.log("FormData:", updatedProfileData);
    try {
      const savedProfile = await profilesService.editProfile(
        updatedProfileData,
        token,
      );
      updateProfile(savedProfile);
      navigate("/mi-perfil");
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    const fetchSkills = async () => {
      try {
        const skillsData = await skillsService.getSkills();
        setSkills(skillsData);
      } catch (error) {
        console.error("Error fetching skills:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchSkills();
  }, [user, isLoading]);

  if (isLoading) {
    return <p>Cargando habilidades...</p>;
  }

  return (
    <main className="mb-5 w-full px-2 pt-2 font-raleway text-dark md:mx-auto md:flex md:max-w-7xl md:gap-x-4 dark:text-light">
      <form
        onSubmit={handleSubmit}
        className="px-3 md:rounded-md md:bg-white md:p-6 lg:mx-auto lg:max-w-6xl lg:px-10 md:dark:bg-dm-dark-green"
      >
        <section className="flex flex-col md:flex-row md:gap-10">
          <div className="md:w-1/2">
            <h1 className="mb-2 text-2xl font-bold">Edita tu perfil</h1>
            <div className="grid grid-cols-2 gap-4">
              <div className="col-span-2 mb-3 flex items-center justify-center">
                <AvatarSelector
                  currentIndex={
                    getProfilePictureId(profileData.profilePicture) - 1
                  }
                  onImageSelect={handleImageSelect}
                  bgColor={bgColor}
                  setBgColor={setBgColor}
                />
              </div>
              <TextInput
                label="Nombre Completo"
                name="name"
                value={profileData.name}
                error={errorMessage?.name}
                onChange={handleInputChange}
              />
              <CountrySelector
                value={profileData.country}
                onChange={handleInputChange}
                error={errorMessage?.country}
              />
              <GenderSelect
                value={profileData.gender}
                onChange={handleInputChange}
                error={errorMessage?.gender}
              />
              <TextInput
                label="Pronombres"
                name="pronouns"
                value={profileData.pronouns}
                error={null}
                onChange={handleInputChange}
              />
              <div className="col-span-2">
                <TextInput
                  label="Rol / Puesto Laboral"
                  name="jobPosition"
                  value={profileData.jobPosition}
                  error={errorMessage?.jobPosition}
                  onChange={handleInputChange}
                />
              </div>
              <div className="col-span-2">
                <TextareaInput
                  label="Biografia"
                  name="bio"
                  value={profileData.bio}
                  className="col-span-2"
                  error={errorMessage?.bio}
                  onChange={handleInputChange}
                />
              </div>
            </div>
            <h3 className="mt-3 text-xl font-bold">Datos de contacto</h3>
            <h4 className="font-semibold text-dark-green dark:text-dm-light-green">
              Estos datos se mostraran solo a tus conexiones
            </h4>
            <div className="mt-2 grid grid-cols-2 gap-4">
              <TextInput
                label="Discord"
                name="discordUrl"
                value={profileData.discordUrl}
                onChange={handleInputChange}
              />
              <TextInput
                label="GitHub"
                name="githubUrl"
                value={profileData.githubUrl}
                onChange={handleInputChange}
              />
              <TextInput
                label="LinkedIn"
                name="linkedinUrl"
                value={profileData.linkedinUrl}
                onChange={handleInputChange}
              />
              <TextInput
                label="Email"
                name="contactEmail"
                value={profileData.contactEmail}
                onChange={handleInputChange}
              />
            </div>
            {errorMessage.contact && (
              <p className="mt-1 text-red-600">{errorMessage.contact}</p>
            )}
          </div>
          <div className="mt-4 md:mt-0 md:w-1/2">
            <h2 className="mb-4 text-2xl font-bold">Marcá tus habilidades</h2>
            <p className="my-2 font-medium md:text-xl">CONOCIMIENTOS:</p>
            <div className="flex flex-wrap gap-2 pb-3 text-sm text-white md:gap-3 md:text-base">
              {skills.map((item, index) => (
                <button
                  key={index}
                  type="button"
                  onClick={() => handleSkillToggle(item.name, true)}
                  className={`rounded-md bg-light-green px-2 py-1 shadow-lg hover:shadow-inner-custom md:py-2 md:text-sm lg:text-base dark:bg-dm-light-green ${profileData.skillsLearned.includes(item.name) && "border-2 border-medium-green py-1 font-bold dark:border-dm-medium-green"}`}
                >
                  {item.name}
                </button>
              ))}
            </div>
            <p className="my-2 font-medium md:text-xl">APRENDIENDO:</p>
            <div className="flex flex-wrap gap-2 text-sm text-dark md:gap-3 md:text-base">
              {skills.map((item, index) => (
                <button
                  key={index}
                  type="button"
                  onClick={() => handleSkillToggle(item.name, false)}
                  className={`rounded-md border-2 border-light-green bg-light px-2 py-1 shadow-lg hover:shadow-inner-custom md:text-sm lg:text-base dark:border-dm-light-green ${profileData.skillsToLearn.includes(item.name) && "border-2 border-medium-green py-1 font-bold dark:border-dm-medium-green"}`}
                >
                  {item.name}
                </button>
              ))}
            </div>
          </div>
        </section>
        <div className="text-center">
          <input
            type="submit"
            value="Guardar Mi perfil"
            className="text-decoration-none mx-auto mt-5 w-fit cursor-pointer rounded-3xl border-2 border-transparent bg-dark-green px-6 py-1 font-bold text-white md:hover:scale-105 dark:bg-dm-medium-green md:dark:bg-dark"
          />
        </div>
      </form>
    </main>
  );
};
