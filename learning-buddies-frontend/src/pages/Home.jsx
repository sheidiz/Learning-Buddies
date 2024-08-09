import HomeImg from "../assets/images/home.png";
import { SkillsSlider } from "../components/home/SkillsSlider";
import { UserCardExample } from "../components/user/UserCardExample";
import { users } from "../utils/examples";

export default function Home() {
    return (
        <main className="my-2 px-1 md:px-0 font-raleway">
            <section className="my-5 py-2 md:py-6 lg:mx-auto px-3 lg:px-5 lg:max-w-6xl flex items-center">
                <div className="md:w-2/3 xl:mt-3 flex flex-col justify-center md:justify-start gap-2 text-center md:text-start">
                    <h2 className="font-medium text-brown dark:text-dm-light-brown">Encontrá tu próximo compañerx de estudios</h2>
                    <h1 className="font-bold text-5xl text-brown dark:text-dm-light-brown">Learning Buddies</h1>
                    <p className="mt-2 font-medium text-dark-grey dark:text-light">
                        Aprender y crecer no tiene por qué ser un viaje solitario.
                    </p>
                    <p className="font-medium text-dark-grey dark:text-light">
                        Learning Buddies es la plataforma que conecta a entusiastas y profesionales de IT, ofreciéndote la oportunidad de unirte a compañeros apasionados que comparten tus intereses y metas.
                    </p>
                    <div className="my-5 flex justify-center md:justify-start gap-4">
                        <a href="/signup" className="py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-light-brown font-semibold text-light hover:scale-105">
                            Registrarme
                        </a>
                        <a href="/login" className="py-1 px-6 rounded-3xl text-decoration-none border-2 border-light-brown font-semibold text-light-brown dark:text-light hover:scale-105">
                            Iniciar sesión
                        </a>
                    </div>
                </div>
                <div className="hidden md:block">
                    <img src={HomeImg} alt="Personas compartiendo conocimiento" className="xl:me-20" />
                </div>
            </section>
            <section className="px-4 md:px-0 py-10 md:py-16 bg-medium-green dark:bg-dm-dark-green flex flex-col items-center gap-3">
                <h2 className="font-semibold text-light text-4xl">Comunidad</h2>
                <p className="md:max-w-4xl font-medium text-light text-center md:text-xl">Learning Buddies es tu puerta de entrada a una experiencia colaborativa y enriquecedora de aprendizaje en TI. Ya sea que estés dando tus primeros pasos o buscando perfeccionar tus habilidades en distintas áreas.</p>
                <SkillsSlider />
            </section>
            <section className="mx-2 my-10 py-10 rounded-3xl bg-light-green dark:bg-dm-light-green">
                <div className="p-5 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 justify-center">
                    <UserCardExample user={users[0]} contactable={false} />
                    <UserCardExample user={users[1]} contactable={false} />
                    <UserCardExample user={users[2]} contactable={false} />
                </div>
            </section>
        </main>
    )
}