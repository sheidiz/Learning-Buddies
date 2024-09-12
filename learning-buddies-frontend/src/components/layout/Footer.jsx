import { FaGithub, FaLinkedin } from "react-icons/fa6";

let navigation = [
  { name: "Inicio", href: "/", current: true, type: "none" },
  { name: "Preguntas Frecuentes", href: "#", current: false, type: "none" },
  { name: "Recursos", href: "#", current: false, type: "none" },
];

// function classNames(...classes) {
//     return classes.filter(Boolean).join(' ')
// };

export const Footer = () => {
  return (
    <div className="bg-light-brown p-8 text-center text-brown dark:bg-dm-brown/50 dark:text-light">
      <h5 className="mb-5 text-2xl font-semibold md:text-3xl">
        Learning Buddies
      </h5>
      <div className="mb-5 flex flex-col justify-center gap-2 md:flex-row md:gap-5">
        {navigation.map((item, index) => (
          <a key={index} href={item.href} className="text-lg">
            {item.name}
          </a>
        ))}
      </div>
      <div className="flex items-center justify-center gap-2 border-t border-t-brown pt-2 text-center dark:border-t-light">
        <span>Desarrollado por Sheila Diz</span>
        <a href="https://github.com/sheidiz" target="_blank">
          <FaGithub className="" />
        </a>
        <a href="https://www.linkedin.com/in/sheila-diz" target="_blank">
          <FaLinkedin className="" />
        </a>
      </div>
    </div>
  );
};
