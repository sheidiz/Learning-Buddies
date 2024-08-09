import { useState } from "react";
import ImageSelector from "../components/user/ImageSelector";
import { TextInput } from "../components/user/TextInput";
import { TextareaInput } from "../components/user/TextareaInput";
import { skills, users } from "../utils/examples"
import { MdChevronLeft, MdChevronRight } from "react-icons/md";


export default function ProfileCreation() {

    const user = users[0];
    const [selectedImage, setSelectedImage] = useState('');

    const handleImageSelect = (image) => {
        setSelectedImage(image);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Selected image:', selectedImage); 
        //add validation if user doesnt complete contact info
    };

    return (
        <main className="w-full md:max-w-7xl mb-5 md:mx-auto px-2 pt-2 font-raleway text-dark dark:text-light md:flex md:gap-x-4">
            <section className="md:w-2/3 lg:w-1/2 px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <form onSubmit={handleSubmit} >
                    <h1 className="mb-2 font-bold text-2xl">Crea tu perfil</h1>
                    <div className="grid grid-cols-3 gap-4">
                        <div className="col-span-3 mb-3 flex justify-center items-center">
                            <ImageSelector onImageSelect={handleImageSelect} />
                        </div>
                        <TextInput label="Nombre Completo" inputName="inputName" inputPlaceholder={user.name} className="col-span-2" required={true} />
                        <TextInput label="País" inputName="inputCountry" inputPlaceholder={user.country} className="col-span-1" required={true} />
                        <TextInput label="Género" inputName="inputGender" inputPlaceholder="Hombre" className="col-span-2" />
                        <TextInput label="Pronombres" inputName="inputPronouns" inputPlaceholder="él" className="col-span-1" />
                        <TextInput label="Rol / Puesto Laboral" inputName="inputJob" inputPlaceholder={user.job} className="col-span-3" />
                        <TextareaInput label="Biografia" inputName="inputBio" inputPlaceholder={user.description} className="col-span-3" required={true} />
                    </div>
                    <h2 className="mt-3 font-bold text-2xl">Datos de contacto</h2>
                    <h3 className="text-dark-green dark:text-dm-light-green font-semibold">Estos datos se mostraran solo a tus conexiones</h3>
                    <div className="mt-2 grid grid-cols-2 gap-4">
                        <TextInput label="Discord" inputName="inputDiscord" inputId="inputDiscord" inputPlaceholder="juancito" />
                        <TextInput label="GitHub" inputName="inputGitHub" inputId="inputGitHub" inputPlaceholder="/juancitodev" />
                        <TextInput label="LinkedIn" inputName="inputLinkedIn" inputId="inputLinkedIn" inputPlaceholder="/juancito-g" />
                        <TextInput label="Email" inputName="inputEmail" inputId="inputEmail" inputPlaceholder="juancitodev@gmail.com" />
                    </div>
                    <input type="submit" value="Guardar Mi perfil" className="w-full mt-5 py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dm-medium-green md:dark:bg-dark font-bold text-white md:hover:scale-105" />
                </form>
            </section>
            <section className="md:w-1/3 lg:w-1/2 mt-10 md:mt-0 md:h-fit px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <form className="w-full">
                    <h2 className="lg:mb-4 font-bold text-2xl">Marcá tus habilidades</h2>
                    <p className='my-2 font-medium md:text-xl'>CONOCIMIENTOS:</p>
                    <div className="pb-3 flex flex-wrap gap-2 md:gap-3 text-white text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index}
                                className={`md:text-sm lg:text-base px-2 py-1 md:py-2 bg-light-green dark:bg-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom`}>
                                {item}
                            </button>
                        ))}
                    </div>
                    <p className='my-2 font-medium md:text-xl'>APRENDIENDO:</p>
                    <div className="flex flex-wrap gap-2 md:gap-3 text-dark text-sm md:text-base">
                        {skills.map((item, index) => (
                            <button key={index}
                                className={`md:text-sm lg:text-base px-2 py-1 border-2 bg-light border-light-green dark:border-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom`}>
                                {item}
                            </button>
                        ))}
                    </div>
                    <input type="submit" value="Guardar Mis habilidades" className="w-full mt-5 p-1 rounded-3xl md:text-sm lg:text-base text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dark font-bold text-white md:hover:scale-105" />
                </form>
            </section>
        </main>
    )
}
