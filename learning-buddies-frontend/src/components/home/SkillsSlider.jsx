import { useEffect, useState } from "react";

export const SkillsSlider = () => {
    const texts = ['Diseño UX/UI', 'Java', 'JavaScript', 'React', 'Python'];
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isFading, setIsFading] = useState(false);

    useEffect(() => {
        const intervalId = setInterval(() => {
            setIsFading(true);
            setTimeout(() => {
                setCurrentIndex((prevIndex) => (prevIndex + 1) % texts.length);
                setIsFading(false);
            }, 500); // Duración de la animación
        }, 5000);

        return () => clearInterval(intervalId);
    }, [texts.length]);

    return (
        <div className="w-64 my-3 py-1 border-2 border-light rounded-3xl overflow-hidden flex items-center justify-center">
            <p className={`font-semibold text-lg text-center text-light transition-opacity duration-500 ${isFading ? 'fade-out' : 'fade-in'}`}>
                {texts[currentIndex]}
            </p>
        </div>
    )
}
