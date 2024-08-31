import React, { useCallback, useEffect, useState } from 'react'
import { useAuth } from '../contexts/AuthContext';
import skillsService from '../services/skillsService';
import profilesService from '../services/profilesService';
import { useNavigate } from 'react-router-dom';
import ImageSelector from '../components/user/ImageSelector';
import { TextInput } from '../components/user/TextInput';
import { TextareaInput } from '../components/user/TextareaInput';
import { CountrySelector } from '../components/user/CountrySelector';
import { GenderSelect } from '../components/user/GenderSelect';
import { validateProfileData } from '../utils/validationUtils';

export const ProfileEdition = () => {
    const navigate = useNavigate();
    const { token, user, updateProfile } = useAuth();
    const [profileData, setProfileData] = useState(user.profile);
    const [skills, setSkills] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState(null);
    const [selectedImage, setSelectedImage] = useState();
    const [bgColor, setBgColor] = useState();

    useEffect(() => {
        if (user && user.profile) {
            setProfileData(user.profile);
            setSelectedImage(user.profile.profilePicture || "/src/assets/users/1.png");
            setBgColor(user.profile.profilePictureBackground || "#f2f2f2");
        }
    }, [user]);

    const handleInputChange = useCallback((e) => {
        console.log(e.target.value)
        setProfileData(prevData => ({
            ...prevData,
            [e.target.name]: e.target.value
        }));
    }, []);

    const handleImageSelect = useCallback((image) => {
        setSelectedImage(image);
    }, []);

    const handleSkillToggle = useCallback((skill, isLearned) => {
        if (isLearned) {
            setProfileData(prev => ({
                ...prev,
                skillsLearned: prev.skillsLearned.includes(skill)
                    ? prev.skillsLearned.filter(s => s !== skill)
                    : [...prev.skillsLearned, skill]
            }));
        } else {
            setProfileData(prev => ({
                ...prev,
                skillsToLearn: prev.skillsToLearn.includes(skill)
                    ? prev.skillsToLearn.filter(s => s !== skill)
                    : [...prev.skillsToLearn, skill]
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
            profilePictureBackground: bgColor
        };
        console.log('FormData:', updatedProfileData);
        try {
            const savedProfile = await profilesService.editProfile(updatedProfileData, token);
            updateProfile(savedProfile);
            navigate("/mi-perfil");
        } catch (error) {
            console.log(error)
        }
    };

    useEffect(() => {
        const fetchSkills = async () => {
            try {
                const skillsData = await skillsService.getSkills();
                setSkills(skillsData);
            } catch (error) {
                console.error('Error fetching skills:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchSkills();
    }, [user, isLoading]);

    if (isLoading) {
        return (<p>Cargando habilidades...</p>)
    }

    return (
        <main className="w-full md:max-w-7xl mb-5 md:mx-auto px-2 pt-2 font-raleway text-dark dark:text-light md:flex md:gap-x-4">
            <section className="md:w-2/3 lg:w-1/2 px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <form onSubmit={handleSubmit}>
                    <h1 className="mb-2 font-bold text-2xl">Edita tu perfil</h1>
                    <div className="grid grid-cols-2 gap-4">
                        <div className="col-span-2 mb-3 flex justify-center items-center">
                            <ImageSelector currentIndex={profileData.profilePicture.slice(-5, -4)} onImageSelect={handleImageSelect} bgColor={bgColor} />
                            <input type="color" value={bgColor} onChange={(e) => setBgColor(e.target.value)} />
                        </div>
                        <TextInput label="Nombre Completo" inputName="name" value={profileData.name} className="col-span-1" required={true} onChangeAction={handleInputChange} />
                        <CountrySelector value={profileData.country} onChangeAction={handleInputChange} />
                        <GenderSelect value={profileData.gender} onChangeAction={handleInputChange} />
                        <TextInput label="Pronombres" inputName="pronouns" value={profileData.pronouns} className="col-span-1" onChangeAction={handleInputChange} />
                        <TextInput label="Rol / Puesto Laboral" inputName="jobPosition" value={profileData.jobPosition} required={true} className="col-span-2" onChangeAction={handleInputChange} />
                        <TextareaInput label="Biografia" inputName="bio" value={profileData.bio} className="col-span-2" required={true} onChangeAction={handleInputChange} />
                    </div>
                    <h3 className="mt-3 font-bold text-xl">Datos de contacto</h3>
                    <h4 className="text-dark-green dark:text-dm-light-green font-semibold">Estos datos se mostraran solo a tus conexiones</h4>
                    <div className="mt-2 grid grid-cols-2 gap-4">
                        <TextInput label="Discord" inputName="discordUrl" value={profileData.discordUrl} onChangeAction={handleInputChange} />
                        <TextInput label="GitHub" inputName="githubUrl" value={profileData.githubUrl} onChangeAction={handleInputChange} />
                        <TextInput label="LinkedIn" inputName="linkedinUrl" value={profileData.linkedinUrl} onChangeAction={handleInputChange} />
                        <TextInput label="Email" inputName="contactEmail" value={profileData.contactEmail} onChangeAction={handleInputChange} />
                    </div>
                    <h2 className="mt-4 lg:mb-4 font-bold text-2xl">Marcá tus habilidades</h2>
                    <p className='my-2 font-medium md:text-xl'>CONOCIMIENTOS:</p>
                    <div className="pb-3 flex flex-wrap gap-2 md:gap-3 text-white text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index} type="button" onClick={() => handleSkillToggle(item.name, true)}
                                className={`md:text-sm lg:text-base px-2 py-1 md:py-2 bg-light-green dark:bg-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom ${profileData.skillsLearned.includes(item.name) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                                {item.name}
                            </button>
                        ))}
                    </div>
                    <p className='my-2 font-medium md:text-xl'>APRENDIENDO:</p>
                    <div className="flex flex-wrap gap-2 md:gap-3 text-dark text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index} type="button" onClick={() => handleSkillToggle(item.name, false)}
                                className={`md:text-sm lg:text-base px-2 py-1 border-2 bg-light border-light-green dark:border-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom ${profileData.skillsToLearn.includes(item.name) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                                {item.name}
                            </button>
                        ))}
                    </div>
                    {errorMessage && <div className="mt-3 px-6 py-1 text-red-600 font-bold text-sm dark:bg-light rounded-lg" dangerouslySetInnerHTML={{ __html: errorMessage }} />}
                    <input type="submit" value="Guardar Mi perfil" className="cursor-pointer w-full mt-5 py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dm-medium-green md:dark:bg-dark font-bold text-white md:hover:scale-105" />
                </form>
            </section>
        </main>)
}
