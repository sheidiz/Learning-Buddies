import { useEffect, useState } from "react";
import { MdClose } from "react-icons/md";
import { saveToLocalStorage } from "../../utils/storageUtils";

const skills = ["HTML", "CSS", "JavaScript", "SQL", "C / C++", ".NET", "Java", "Spring", "Python", "NodeJs", "NextJs", "React", "Angular", "Vue", "TypeScript", "PHP", "MongoDB", "Go", "Swift", "Kotlin", "Rust", "Ruby", "COBOL"];

export const Filters = ({ setOpenModal, skills1, skills2, setSkills1, setSkills2 }) => {
    const [localSkills1, setLocalSkills1] = useState(skills1);
    const [localSkills2, setLocalSkills2] = useState(skills2);

    useEffect(() => {
        setLocalSkills1(skills1);
        setLocalSkills2(skills2);
    }, [skills1, skills2]);

    const closeModal = () => {
        setOpenModal(false);
    };

    const handleSkill1 = (skill) => {
        setLocalSkills1((prev) => prev.includes(skill) ? prev.filter((item) => item !== skill) : [...prev, skill]);
    };

    const handleSkill2 = (skill) => {
        setLocalSkills2((prev) => prev.includes(skill) ? prev.filter((item) => item !== skill) : [...prev, skill]);
    };

    const saveFilters = () => {
        setSkills1(localSkills1);
        setSkills2(localSkills2);
        saveToLocalStorage("skills1", localSkills1);
        saveToLocalStorage("skills2", localSkills2);
        closeModal();
    };

    const removeFilters = () => {
        setSkills1([]);
        setSkills2([]);
        saveToLocalStorage("skills1", []);
        saveToLocalStorage("skills2", []);
        closeModal();
    };

    return (
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-5/6 md:w-3/4 lg:w-2/4 max-w-3xl p-3 lg:p-6 bg-light dark:bg-dark border border-dark-green dark:border-dm-light-green rounded-lg shadow-lg flex flex-col gap-3">
            <button onClick={closeModal} className="self-end absolute top-5 right-5 hover:scale-125">
                <MdClose className="text-2xl text-dark dark:text-light" />
            </button>
            <form className="w-full">
                <p className='my-2 font-medium md:text-xl'>CONOCIMIENTOS:</p>
                <div className="pb-3 flex flex-wrap gap-2 md:gap-3 text-white text-sm md:text-base">
                    {skills.map((item, index) => (
                        <div key={index} onClick={() => handleSkill1(item)}
                            className={`px-2 py-1 md:py-2 bg-light-green dark:bg-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom ${localSkills1.includes(item) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                            {item}
                        </div>
                    ))}
                </div>
                <p className='my-2 font-medium md:text-xl'>APRENDIENDO:</p>
                <div className="flex flex-wrap gap-2 md:gap-3 text-dark text-sm md:text-base">
                    {skills.map((item, index) => (
                        <div key={index} onClick={() => handleSkill2(item)}
                            className={`px-2 py-1 border-2 border-light-green dark:border-dm-light-green rounded-md shadow-lg hover:shadow-inner-custom dark:text-light ${localSkills2.includes(item) && "py-1 font-bold border-2 border-medium-green dark:border-dm-medium-green"}`}>
                            {item}
                        </div>
                    ))}
                </div>
                <div className="flex justify-evenly">
                    <button type="button" onClick={removeFilters} className="mt-4 lg:mt-6 py-1 px-4 md:px-6 border-2 border-brown dark:border-dm-brown text-brown dark:text-dm-brown rounded-full font-bold hover:scale-110">Quitar filtros</button>
                    <button type="button" onClick={saveFilters} className="mt-4 lg:mt-6 py-1 px-4 md:px-6 bg-brown dark:bg-dm-brown text-light rounded-full font-bold hover:scale-110">Ver resultados</button>
                </div>
            </form>
        </div>
    )
}
