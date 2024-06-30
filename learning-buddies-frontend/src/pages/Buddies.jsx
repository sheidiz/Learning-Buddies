import { useEffect, useState } from "react";
import { MdClose, MdFilterAlt, MdFilterAltOff } from "react-icons/md";
import { users } from "../utils/examples";
import { UserCardExample } from "../components/user/UserCardExample";
import { Filters } from "../components/buddies/Filters";
import { loadFromLocalStorage, saveToLocalStorage } from "../utils/storageUtils";


export default function Buddies() {

    const [skills1, setSkills1] = useState(() => loadFromLocalStorage("skills1") || []);
    const [skills2, setSkills2] = useState(() => loadFromLocalStorage("skills2") || []);
    const [openFiltersModal, setOpenFiltersModal] = useState(false);

    useEffect(() => {
        saveToLocalStorage("skills1", skills1);
        saveToLocalStorage("skills2", skills2);
    }, [skills1, skills2]);

    const handleRemoveSkill1 = (skill) => {
        setSkills1(prev => prev.filter(item => item !== skill));
    };

    const handleRemoveSkill2 = (skill) => {
        setSkills2(prev => prev.filter(item => item !== skill));
    };

    const handleRemoveFilters = () =>{
        setSkills1([]);
        setSkills2([]);
    }

    const openModal = () => {
        setOpenFiltersModal(true);
    };


    return (
        <main className="my-3 p-4 md:p-2 lg:max-w-screen-xl lg:mx-auto font-raleway">
            <h2 className={`md:mb-1 text-medium-green dark:text-dm-light-green text-2xl font-semibold text-center ${openFiltersModal && 'blur-sm'}`}>Encontrá a tu próximo compañero de estudios</h2>
            <div className={`flex flex-col md:flex-row gap-2 ${openFiltersModal && 'blur-sm'}`}>
                <div>
                    <h3 className="hidden md:block text-dark dark:text-light font-semibold text-xl">Filtros</h3>
                    <form className="py-3 md:py-2 flex items-center gap-2">
                        <input type="text" name="inputSearch" className="w-full md:w-fit px-5 py-1 rounded-3xl text-center" placeholder="Buscar por rol" />
                        <button type="button" onClick={openModal}><MdFilterAlt className="mb-1 text-dark dark:text-light text-3xl" /> </button>
                    </form>
                    <p className='mt-2 font-medium text-brown dark:text-dm-light-brown'>CONOCIMIENTOS:</p>
                    <div className="py-1 flex flex-wrap gap-3">
                        {skills1.map((item, index) => (
                            <div key={index} className='px-2 flex gap-1 items-center border-2 bg-light-brown dark:bg-light border-brown dark:border-dm-brown text-dark rounded-md'>
                                {item}
                                <MdClose className="ml-1 text-sm cursor-pointer" onClick={() => handleRemoveSkill1(item)} />
                            </div>
                        ))}
                    </div>
                    <p className='mt-2 font-medium text-brown dark:text-dm-light-brown'>APRENDIENDO:</p>
                    <div className="py-1 flex flex-wrap gap-3">
                        {skills2.map((item, index) => (
                            <div key={index} className='px-2 flex gap-1 items-center border-2 bg-light-brown dark:bg-light border-brown dark:border-dm-brown text-dark rounded-md'>
                                {item}
                                <MdClose className="ml-1 text-sm cursor-pointer" onClick={() => handleRemoveSkill2(item)} />
                            </div>
                        ))}
                    </div>
                    <button onClick={handleRemoveFilters} className='mt-2 font-bold text-brown dark:text-dm-brown flex gap-1 items-center'>Quitar Filtros <MdFilterAltOff /> </button>
                </div>
                <div className="p-5 grid grid-cols-1 md:grid-cols-2 gap-6 justify-center">
                    <UserCardExample user={users[0]} contactable={true} />
                    <UserCardExample user={users[1]} contactable={true} />
                    <UserCardExample user={users[2]} contactable={true} />
                </div>
            </div>
            {
                openFiltersModal && <Filters setOpenModal={setOpenFiltersModal} skills1={skills1} skills2={skills2} setSkills1={setSkills1} setSkills2={setSkills2} />
            }
        </main>
    )
}
