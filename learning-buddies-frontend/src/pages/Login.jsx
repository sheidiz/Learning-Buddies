import { useState } from "react";
import { MdEmail, MdLock } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../services/authService";
import HeroImg from "../assets/images/users.png";
import { normalizeError } from "../utils/functions";
import { loadFromLocalStorage, saveToLocalStorage } from "../utils/storageUtils";
import { useUser } from "../contexts/UserContext";
import { getProfile } from "../services/profilesService";

export default function Login() {
    const { setUser, setProfile } = useUser();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [errorMessage, setErrorMessage] = useState(null);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));
    };

    const handleLogIn = async (e) => {
        e.preventDefault();

        try {
            const user = await loginUser(formData);
            setUser(user);
            localStorage.setItem('user', JSON.stringify(user));

            try {
                const profile = await getProfile(user.email);
                setProfile(profile);
                if (profile) {
                    localStorage.setItem('profile', JSON.stringify(profile));
                    navigate('/mi-perfil');
                } else {
                    navigate('/creacion-perfil');
                }
            } catch (err) {
                console.error("Error al obtener el perfil:", err);
                navigate('/creacion-perfil');
            }
            setErrorMessage(null)
        } catch (error) {
            setErrorMessage(error.message);
        }
    }

    return (
        <main className='mb-2 w-full h-[80vh] md:bg-light-green dark:bg-dm-light-green font-raleway flex'>
            <div className='md:w-1/2 px-5 hidden md:flex items-center'>
                <img src={HeroImg} alt="Usuarios registrados" className="m-auto" />
            </div>
            <div className="w-full md:w-1/2 px-6 md:px-0 flex flex-col justify-center items-start md:items-center bg-light dark:bg-dark md:rounded-s-2xl md:drop-shadow-[-8px_4px_10px_rgba(0,0,0,0.25)]">
                <div>
                    <h1 className='text-4xl font-semibold text-dark dark:text-light'>Iniciar sesión</h1>
                    <div className='self-start ms-[2px] w-36 h-2 border-b-2 border-b-medium-green dark:border-b-light-green drop-shadow-[0_4px_4px_rgba(0,0,0,0.25)] dark:drop-shadow-[0_4px_4px_rgba(255,255,255,255.25)]'></div>
                </div>
                <form onSubmit={handleLogIn} className="w-full md:w-fit mt-5 text-dark dark:text-light">
                    <label htmlFor="email" className="font-semibold">Email</label>
                    <div className="md:w-96 mb-5 pt-1 pb-2 border-b-2 border-b-dark-grey dark:border-b-light flex gap-2">
                        <MdEmail className="text-2xl" />
                        <div className='w-2 border-l-2 border-l-dark-grey dark:border-l-light'></div>
                        <input type="email" name="email" id="email" placeholder="juan@email.com" onChange={handleInputChange} className="px-1 w-full bg-transparent active:outline-light-green/50 focus-visible:outline-light-green/50" />
                    </div>
                    <label htmlFor="password" className="font-semibold">Contraseña</label>
                    <div className="md:w-96 mb-5 pt-1 pb-2 border-b-2 border-b-dark-grey dark:border-b-light flex gap-2">
                        <MdLock className="text-2xl" />
                        <div className='w-2 border-l-2 border-l-dark-grey dark:border-l-light'></div>
                        <input type="password" name="password" id="password" placeholder="********" onChange={handleInputChange} className="px-1 w-full bg-transparent active:outline-light-green/50 focus-visible:outline-light-green/50" />
                    </div>
                    <input type="submit" value="Iniciar sesión" className="w-full py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-light-brown font-bold text-white hover:scale-105" />
                </form>
                {errorMessage &&
                    <p className="mt-3 px-6 py-1 text-red-600 font-bold text-sm dark:bg-light rounded-lg">{errorMessage}</p>
                }
                <p className="w-full md:w-fit mt-5 font-semibold text-dark dark:text-light text-center">¿No tenés una cuenta? <a href="/registro" className="text-medium-green dark:text-light-green">¡Registrate hoy!</a></p>
            </div>
        </main>
    )
}
