import HomeImg from "../assets/images/home.png";
import { SkillsSlider } from "../components/home/SkillsSlider";
import { UserCardExample } from "../components/home/UserCardExample";
import { useAuth } from "../contexts/AuthContext";
import { users } from "../utils/examples";

export default function Home() {
  const { user } = useAuth();

  return (
    <main className="my-2 px-1 font-raleway md:px-0">
      <section className="my-5 flex items-center px-3 py-2 md:py-6 lg:mx-auto lg:max-w-6xl lg:px-5">
        <div className="flex flex-col justify-center gap-2 text-center md:w-2/3 md:justify-start md:text-start xl:mt-3">
          <h2 className="font-medium text-brown dark:text-dm-light-brown">
            Encontrá tu próximo compañerx de estudios
          </h2>
          <h1 className="text-5xl font-bold text-brown dark:text-dm-light-brown">
            Learning Buddies
          </h1>
          <p className="mt-2 font-medium text-dark-grey dark:text-light">
            Aprender y crecer no tiene por qué ser un viaje solitario.
          </p>
          <p className="font-medium text-dark-grey dark:text-light">
            Learning Buddies es la plataforma que conecta a entusiastas y
            profesionales de IT, ofreciéndote la oportunidad de unirte a
            compañeros apasionados que comparten tus intereses y metas.
          </p>
          <div className="my-5 flex justify-center gap-4 md:justify-start">
            {user == null ? (
              <>
                <a
                  href="/registro"
                  className="text-decoration-none rounded-3xl border-2 border-transparent bg-light-brown px-6 py-1 font-semibold text-light hover:scale-105"
                >
                  Registrarme
                </a>
                <a
                  href="/iniciar-sesion"
                  className="text-decoration-none rounded-3xl border-2 border-light-brown px-6 py-1 font-semibold text-light-brown hover:scale-105 dark:text-light"
                >
                  Iniciar sesión
                </a>
              </>
            ) : (
              <a
                href="/buddies"
                className="text-decoration-none rounded-3xl border-2 border-transparent bg-light-brown px-6 py-1 font-semibold text-light hover:scale-105"
              >
                Encontrar compañeros
              </a>
            )}
          </div>
        </div>
        <div className="hidden md:block">
          <img
            src={HomeImg}
            alt="Personas compartiendo conocimiento"
            className="xl:me-20"
          />
        </div>
      </section>
      <section className="flex flex-col items-center gap-3 bg-medium-green px-4 py-10 md:px-0 md:py-16 dark:bg-dm-dark-green">
        <h2 className="text-4xl font-semibold text-light">Comunidad</h2>
        <p className="text-center font-medium text-light md:max-w-4xl md:text-xl">
          Learning Buddies es tu puerta de entrada a una experiencia
          colaborativa y enriquecedora de aprendizaje en TI. Ya sea que estés
          dando tus primeros pasos o buscando perfeccionar tus habilidades en
          distintas áreas.
        </p>
        <SkillsSlider />
      </section>
      <section className="mx-2 my-10 rounded-3xl bg-light-green py-10 dark:bg-dm-light-green">
        <div className="grid grid-cols-1 justify-center gap-6 p-5 md:grid-cols-2 lg:grid-cols-3">
          <UserCardExample user={users[0]} contactable={false} />
          <UserCardExample user={users[1]} contactable={false} />
          <UserCardExample user={users[2]} contactable={false} />
        </div>
      </section>
    </main>
  );
}
