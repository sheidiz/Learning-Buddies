import { useEffect, useState } from "react";
import { MdClose } from "react-icons/md";
import { saveToLocalStorage } from "../../utils/storageUtils";

const skills = [
  "HTML",
  "CSS",
  "JavaScript",
  "SQL",
  "C / C++",
  ".NET",
  "Java",
  "Spring",
  "Python",
  "NodeJs",
  "NextJs",
  "React",
  "Angular",
  "Vue",
  "TypeScript",
  "PHP",
  "MongoDB",
  "Go",
  "Swift",
  "Kotlin",
  "Rust",
  "Ruby",
  "COBOL",
];

export const Filters = ({
  setOpenModal,
  skills1,
  skills2,
  setSkills1,
  setSkills2,
}) => {
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
    setLocalSkills1((prev) =>
      prev.includes(skill)
        ? prev.filter((item) => item !== skill)
        : [...prev, skill],
    );
  };

  const handleSkill2 = (skill) => {
    setLocalSkills2((prev) =>
      prev.includes(skill)
        ? prev.filter((item) => item !== skill)
        : [...prev, skill],
    );
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
    <div className="absolute left-1/2 top-1/2 flex w-5/6 max-w-3xl -translate-x-1/2 -translate-y-1/2 flex-col gap-3 rounded-lg border border-dark-green bg-light p-3 shadow-lg md:w-3/4 lg:w-2/4 lg:p-6 dark:border-dm-light-green dark:bg-dark">
      <button
        onClick={closeModal}
        className="absolute right-5 top-5 self-end hover:scale-125"
      >
        <MdClose className="text-2xl text-dark dark:text-light" />
      </button>
      <form className="w-full">
        <p className="my-2 font-medium md:text-xl">CONOCIMIENTOS:</p>
        <div className="flex flex-wrap gap-2 pb-3 text-sm text-white md:gap-3 md:text-base">
          {skills.map((item, index) => (
            <div
              key={index}
              onClick={() => handleSkill1(item)}
              className={`rounded-md bg-light-green px-2 py-1 shadow-lg hover:shadow-inner-custom md:py-2 dark:bg-dm-light-green ${localSkills1.includes(item) && "border-2 border-medium-green py-1 font-bold dark:border-dm-medium-green"}`}
            >
              {item}
            </div>
          ))}
        </div>
        <p className="my-2 font-medium md:text-xl">APRENDIENDO:</p>
        <div className="flex flex-wrap gap-2 text-sm text-dark md:gap-3 md:text-base">
          {skills.map((item, index) => (
            <div
              key={index}
              onClick={() => handleSkill2(item)}
              className={`rounded-md border-2 border-light-green px-2 py-1 shadow-lg hover:shadow-inner-custom dark:border-dm-light-green dark:text-light ${localSkills2.includes(item) && "border-2 border-medium-green py-1 font-bold dark:border-dm-medium-green"}`}
            >
              {item}
            </div>
          ))}
        </div>
        <div className="flex justify-evenly">
          <button
            type="button"
            onClick={removeFilters}
            className="mt-4 rounded-full border-2 border-brown px-4 py-1 font-bold text-brown hover:scale-110 md:px-6 lg:mt-6 dark:border-dm-brown dark:text-dm-brown"
          >
            Quitar filtros
          </button>
          <button
            type="button"
            onClick={saveFilters}
            className="mt-4 rounded-full bg-brown px-4 py-1 font-bold text-light hover:scale-110 md:px-6 lg:mt-6 dark:bg-dm-brown"
          >
            Ver resultados
          </button>
        </div>
      </form>
    </div>
  );
};
