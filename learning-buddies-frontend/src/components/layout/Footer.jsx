import { FaGithub, FaLinkedin } from "react-icons/fa6";

let navigation = [
    { name: 'Inicio', href: '/', current: true, type: 'none' },
    { name: 'Preguntas Frecuentes', href: '#', current: false, type: 'none' },
    { name: 'Recursos', href: '#', current: false, type: 'none' },
];

// function classNames(...classes) {
//     return classes.filter(Boolean).join(' ')
// };

export const Footer = () => {
    return (
        <div className="p-8 bg-light-brown dark:bg-dm-brown/50 text-brown dark:text-light text-center">
            <h5 className="mb-5 text-2xl md:text-3xl font-semibold">Learning Buddies</h5>
            <div className="mb-5 flex flex-col md:flex-row gap-2 md:gap-5 justify-center">
                {
                    navigation.map((item,index)=>(
                        <a key={index} href={item.href} className="text-lg">{item.name}</a>
                    ))
                }
            </div>
            <div className="pt-2 border-t border-t-brown dark:border-t-light flex justify-center items-center gap-2 text-center">
                <span>Desarrollado por Sheila Diz</span>
                <a href="https://github.com/sheidiz" target="_blank"><FaGithub className="" /></a>
                <a href="https://www.linkedin.com/in/sheila-diz" target="_blank"><FaLinkedin className="" /></a>
            </div>
        </div>
    )
}
