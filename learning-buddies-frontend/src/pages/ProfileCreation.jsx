import { useCallback, useEffect, useState } from "react";
import profilesService from "../services/profilesService";
import ImageSelector from "../components/user/ImageSelector";
import { TextInput } from "../components/user/TextInput";
import { TextareaInput } from "../components/user/TextareaInput";
import skillsService from "../services/skillsService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { CountrySelector } from "../components/user/CountrySelector";
import { GenderSelect } from "../components/user/GenderSelect";
import { validateProfileData } from "../utils/validationUtils";

export default function ProfileCreation() {
  const [profileData, setProfileData] = useState({
    name: "",
    profilePicture: "/src/assets/users/1.png",
    profilePictureBackground: "",
    gender: "-",
    pronouns: "",
    country: "-",
    jobPosition: "",
    bio: "",
    discordUrl: "",
    githubUrl: "",
    linkedinUrl: "",
    contactEmail: "",
    skillsLearned: [],
    skillsToLearn: [],
  });
  const [skills, setSkills] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedImage, setSelectedImage] = useState("/src/assets/users/1.png");
  const [bgColor, setBgColor] = useState("#f2f2f2");
  const [errorMessage, setErrorMessage] = useState(null);
  const { token, updateProfile } = useAuth();
  const navigate = useNavigate();

  const handleInputChange = useCallback((e) => {
    setProfileData((prevData) => ({
      ...prevData,
      [e.target.name]: e.target.value,
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

    const validationError = validateProfileData(profileData);
    if (validationError) {
      setErrorMessage(validationError);
      return;
    }

    const updatedProfileData = {
      ...profileData,
      profilePicture: selectedImage,
      profilePictureBackground: bgColor,
    };
    console.log("FormData:", updatedProfileData);
    try {
      const savedProfile = await profilesService.saveProfile(
        updatedProfileData,
        token,
      );
      updateProfile(savedProfile);
      navigate("/mi-perfil");
    } catch (error) {
      setErrorMessage(error.message);
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
  }, [isLoading]);

  if (isLoading) {
    return <p>Cargando habilidades...</p>;
  }

  return (
    <main className="mb-5 w-full px-2 pt-2 font-raleway text-dark md:mx-auto md:flex md:max-w-7xl md:gap-x-4 dark:text-light">
      <section className="px-3 md:w-2/3 md:rounded-md md:bg-white md:p-6 lg:mx-auto lg:w-1/2 lg:max-w-5xl lg:px-10 md:dark:bg-dm-dark-green">
        <form onSubmit={handleSubmit}>
          <h1 className="mb-2 text-2xl font-bold">Crea tu perfil</h1>
          <div className="grid grid-cols-2 gap-4">
            <div className="col-span-2 mb-3 flex items-center justify-center">
              <ImageSelector
                currentIndex="1"
                onImageSelect={handleImageSelect}
                bgColor={bgColor}
              />
              <input
                type="color"
                value={bgColor}
                onChange={(e) => setBgColor(e.target.value)}
              />
            </div>
            <TextInput
              label="Nombre Completo"
              inputName="name"
              inputPlaceholder="María Lopez"
              className="col-span-1"
              required={true}
              onChangeAction={handleInputChange}
            />
            <CountrySelector onChangeAction={handleInputChange} />
            <GenderSelect onChangeAction={handleInputChange} />
            <TextInput
              label="Pronombres"
              inputName="pronouns"
              inputPlaceholder="ella"
              className="col-span-1"
              onChangeAction={handleInputChange}
            />
            <TextInput
              label="Rol / Puesto Laboral"
              inputName="jobPosition"
              inputPlaceholder="Estudiante"
              required={true}
              className="col-span-2"
              onChangeAction={handleInputChange}
            />
            <TextareaInput
              label="Biografia"
              inputName="bio"
              inputPlaceholder="Tus intereses"
              className="col-span-2"
              required={true}
              onChangeAction={handleInputChange}
            />
          </div>
          <h3 className="mt-3 text-xl font-bold">Datos de contacto</h3>
          <h4 className="font-semibold text-dark-green dark:text-dm-light-green">
            Estos datos se mostraran solo a tus conexiones
          </h4>
          <div className="mt-2 grid grid-cols-2 gap-4">
            <TextInput
              label="Discord"
              inputName="discordUrl"
              inputPlaceholder="ejemplo"
              onChangeAction={handleInputChange}
            />
            <TextInput
              label="GitHub"
              inputName="githubUrl"
              inputPlaceholder="/ejemplo"
              onChangeAction={handleInputChange}
            />
            <TextInput
              label="LinkedIn"
              inputName="linkedinUrl"
              inputPlaceholder="/ejemplo"
              onChangeAction={handleInputChange}
            />
            <TextInput
              label="Email"
              inputName="contactEmail"
              inputPlaceholder="ejemplo@gmail.com"
              onChangeAction={handleInputChange}
            />
          </div>
          <h2 className="mt-4 text-2xl font-bold lg:mb-4">
            Marcá tus habilidades
          </h2>
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
          {errorMessage && (
            <div
              className="mt-3 rounded-lg px-6 py-1 text-sm font-bold text-red-600 dark:bg-light"
              dangerouslySetInnerHTML={{ __html: errorMessage }}
            />
          )}
          <input
            type="submit"
            value="Guardar Mi perfil"
            className="text-decoration-none mt-5 w-full cursor-pointer rounded-3xl border-2 border-transparent bg-dark-green px-6 py-1 font-bold text-white md:hover:scale-105 dark:bg-dm-medium-green md:dark:bg-dark"
          />
        </form>
      </section>
    </main>
  );
}
