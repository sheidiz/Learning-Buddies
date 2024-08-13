import { useEffect, useState } from "react";
import { useUser } from "../contexts/UserContext";
import { createProfile } from "../services/profilesService";
import ImageSelector from "../components/user/ImageSelector";
import { TextInput } from "../components/user/TextInput";
import { TextareaInput } from "../components/user/TextareaInput";
import { getSkills } from "../services/skillsService";
import { useNavigate } from "react-router-dom";


export default function ProfileCreation() {
    const navigate = useNavigate();
    const [errorMessage, setErrorMessage] = useState(null);
    const [skills, setSkills] = useState([]);
    const { user, profile, setProfile } = useUser();
    const [formData, setFormData] = useState({
        name: '',
        profilePicture: '/src/assets/users/2.png',
        gender: '',
        pronouns: '',
        country: '',
        jobPosition: '',
        bio: '',
        discordUrl: '',
        githubUrl: '',
        linkedinUrl: '',
        contactEmail: '',
        skillsLearned: [],
        skillsToLearn: []
    });
    const [selectedImage, setSelectedImage] = useState('/src/assets/users/2.png');
    const [skillsLearned, setSkillsLearned] = useState(formData.skillsLearned || []);
    const [skillsToLearn, setSkillsToLearn] = useState(formData.skillsToLearn || []);

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleImageSelect = (image) => {
        setSelectedImage(image);
    };

    const handleSkillToggle = (skill, isLearned) => {
        if (isLearned) {
            setSkillsLearned(prev => {
                if (prev.includes(skill)) {
                    return prev.filter(s => s !== skill);
                } else {
                    return [...prev, skill];
                }
            });
        } else {
            setSkillsToLearn(prev => {
                if (prev.includes(skill)) {
                    return prev.filter(s => s !== skill);
                } else {
                    return [...prev, skill];
                }
            });
        }
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        formData.profilePicture = selectedImage;
        formData.skillsLearned = skillsLearned;
        formData.skillsToLearn = skillsToLearn;
        console.log('FormData:', formData);
        try {
            const savedProfile = await createProfile(user.id, formData);
            setProfile(savedProfile);
            navigate("/mi-perfil");
        } catch (error) {
            setErrorMessage(error.message);
            console.log(error)
        }
        //console.log('Selected image:', formData); 
        //add validation if user doesnt complete contact info
    };

    useEffect(() => {
        const fetchSkills = async () => {
            try {
                const skillsData = await getSkills();
                setSkills(skillsData);
            } catch (error) {
                console.error('Error fetching skills:', error);
            }
        };

        fetchSkills();
    }, [profile]);

    return (
        <main className="w-full md:max-w-7xl mb-5 md:mx-auto px-2 pt-2 font-raleway text-dark dark:text-light md:flex md:gap-x-4">
            <section className="md:w-2/3 lg:w-1/2 px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <form onSubmit={handleSubmit}>
                    <h1 className="mb-2 font-bold text-2xl">Crea tu perfil</h1>
                    <div className="grid grid-cols-3 gap-4">
                        <div className="col-span-3 mb-3 flex justify-center items-center">
                            <ImageSelector onImageSelect={handleImageSelect} />
                        </div>
                        <TextInput label="Nombre Completo" inputName="name" inputPlaceholder="María Lopez" className="col-span-2" required={true} onChangeAction={handleInputChange} />
                        <TextInput label="País" inputName="country" inputPlaceholder="Argentina" className="col-span-1" required={true} onChangeAction={handleInputChange} />
                        <TextInput label="Género" inputName="gender" inputPlaceholder="Mujer" className="col-span-2" onChangeAction={handleInputChange} />
                        <TextInput label="Pronombres" inputName="pronouns" inputPlaceholder="ella" className="col-span-1" onChangeAction={handleInputChange} />
                        <TextInput label="Rol / Puesto Laboral" inputName="jobPosition" inputPlaceholder="Estudiante" className="col-span-3" onChangeAction={handleInputChange} />
                        <TextareaInput label="Biografia" inputName="bio" inputPlaceholder="" className="col-span-3" required={true} onChangeAction={handleInputChange} />
                    </div>
                    <h3 className="mt-3 font-bold text-xl">Datos de contacto</h3>
                    <h4 className="text-dark-green dark:text-dm-light-green font-semibold">Estos datos se mostraran solo a tus conexiones</h4>
                    <div className="mt-2 grid grid-cols-2 gap-4">
                        <TextInput label="Discord" inputName="discordUrl" inputPlaceholder="ejemplo" onChangeAction={handleInputChange} />
                        <TextInput label="GitHub" inputName="githubUrl" inputPlaceholder="/ejemplo" onChangeAction={handleInputChange} />
                        <TextInput label="LinkedIn" inputName="linkedinUrl" inputPlaceholder="/ejemplo" onChangeAction={handleInputChange} />
                        <TextInput label="Email" inputName="contactEmail" inputPlaceholder="ejemplo@gmail.com" onChangeAction={handleInputChange} />
                    </div>
                    <h2 className="mt-4 lg:mb-4 font-bold text-2xl">Marcá tus habilidades</h2>
                    <p className='my-2 font-medium md:text-xl'>CONOCIMIENTOS:</p>
                    <div className="pb-3 flex flex-wrap gap-2 md:gap-3 text-white text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index} type="button" onClick={() => handleSkillToggle(item.name, true)}
                                className={`md:text-sm lg:text-base px-2 py-1 md:py-2 bg-light-green dark:bg-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom ${skillsLearned.includes(item.name) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                                {item.name}
                            </button>
                        ))}
                    </div>
                    <p className='my-2 font-medium md:text-xl'>APRENDIENDO:</p>
                    <div className="flex flex-wrap gap-2 md:gap-3 text-dark text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index} type="button" onClick={() => handleSkillToggle(item.name, false)}
                                className={`md:text-sm lg:text-base px-2 py-1 border-2 bg-light border-light-green dark:border-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom ${skillsToLearn.includes(item.name) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                                {item.name}
                            </button>
                        ))}
                    </div>
                    <input type="submit" value="Guardar Mi perfil" className="cursor-pointer w-full mt-5 py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dm-medium-green md:dark:bg-dark font-bold text-white md:hover:scale-105" />
                </form>
            </section>
        </main>
    )
}
