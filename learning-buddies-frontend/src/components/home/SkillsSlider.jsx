import { useEffect, useState } from "react";

export const SkillsSlider = () => {
  const texts = ["Diseño UX/UI", "Java", "JavaScript", "React", "Python"];
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
    <div className="my-3 flex w-64 items-center justify-center overflow-hidden rounded-3xl border-2 border-light py-1">
      <p
        className={`text-center text-lg font-semibold text-light transition-opacity duration-500 ${isFading ? "fade-out" : "fade-in"}`}
      >
        {texts[currentIndex]}
      </p>
    </div>
  );
};
