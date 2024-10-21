import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import HeroImg from "../assets/images/users.png";
import { MdEmail, MdLock } from "react-icons/md";
import authService from "../services/authService";
import AuthContext from "../contexts/AuthContext";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(null);
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogIn = async (e) => {
    e.preventDefault();
    try {
      const { token, user } = await authService.login(email, password);
      login(token, user);
      if (user.profileId) {
        navigate("/mi-perfil");
      } else {
        navigate("/creacion-perfil");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <main className="mb-2 flex h-[80vh] w-full font-raleway md:bg-light-green dark:bg-dm-light-green">
      <div className="hidden items-center px-5 md:flex md:w-1/2">
        <img src={HeroImg} alt="Usuarios registrados" className="m-auto" />
      </div>
      <div className="flex w-full flex-col items-start justify-center bg-light px-6 md:w-1/2 md:items-center md:rounded-s-2xl md:px-0 md:drop-shadow-[-8px_4px_10px_rgba(0,0,0,0.25)] dark:bg-dark">
        <div>
          <h1 className="text-4xl font-semibold text-dark dark:text-light">
            Iniciar sesión
          </h1>
          <div className="ms-[2px] h-2 w-36 self-start border-b-2 border-b-medium-green drop-shadow-[0_4px_4px_rgba(0,0,0,0.25)] dark:border-b-light-green dark:drop-shadow-[0_4px_4px_rgba(255,255,255,255.25)]"></div>
        </div>
        <form
          onSubmit={handleLogIn}
          className="mt-5 w-full text-dark md:w-fit dark:text-light"
        >
          <label htmlFor="email" className="font-semibold">
            Email
          </label>
          <div className="mb-5 flex gap-2 border-b-2 border-b-dark-grey pb-2 pt-1 md:w-96 dark:border-b-light">
            <MdEmail className="text-2xl" />
            <div className="w-2 border-l-2 border-l-dark-grey dark:border-l-light"></div>
            <input
              type="email"
              name="email"
              id="email"
              placeholder="juan@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full bg-transparent px-1 focus-visible:outline-light-green/50 active:outline-light-green/50"
            />
          </div>
          <label htmlFor="password" className="font-semibold">
            Contraseña
          </label>
          <div className="mb-5 flex gap-2 border-b-2 border-b-dark-grey pb-2 pt-1 md:w-96 dark:border-b-light">
            <MdLock className="text-2xl" />
            <div className="w-2 border-l-2 border-l-dark-grey dark:border-l-light"></div>
            <input
              type="password"
              name="password"
              id="password"
              placeholder="********"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full bg-transparent px-1 focus-visible:outline-light-green/50 active:outline-light-green/50"
            />
          </div>
          <input
            type="submit"
            value="Iniciar sesión"
            className="text-decoration-none w-full cursor-pointer rounded-3xl border-2 border-transparent bg-light-brown px-6 py-1 font-bold text-white hover:scale-105"
          />
        </form>
        {errorMessage && (
          <p className="mt-3 rounded-lg px-6 py-1 text-sm font-bold text-red-600 dark:bg-light">
            {errorMessage}
          </p>
        )}
        <p className="mt-5 w-full text-center font-semibold text-dark md:w-fit dark:text-light">
          ¿No tenés una cuenta?{" "}
          <a
            href="/registro"
            className="text-medium-green dark:text-light-green"
          >
            ¡Registrate hoy!
          </a>
        </p>
      </div>
    </main>
  );
}
