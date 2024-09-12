import Woman1 from "../assets/users/2.png";
import Woman2 from "../assets/users/4.png";
import Man1 from "../assets/users/1.png";
import Man2 from "../assets/users/3.png";

const front = [{ name: "HTML" }, { name: "CSS" }, { name: "JavaScript" }];
const front2 = [{ name: "React" }, { name: "NodeJS" }];
const back = [{ name: "C++" }, { name: "Java" }, { name: "Spring" }];

export const users = [
  {
    name: "Juan Carlos G",
    profilePic: "/src/assets/users/1.png",
    job: "Desarrollador/a Front-end",
    country: "Argentina",
    description:
      "Soy Juan y me encuentro estudiando de manera autodidacta lenguajes orientados al front-end.",
    skillsLearned: front,
    skillsToLearn: front2,
  },
  {
    name: "Camila D.",
    profilePic: "/src/assets/users/2.png",
    job: "Desarrollador/a Back-end",
    country: "Argentina",
    description:
      "Soy Camila y me encuentro estudiando ingenieria en informatica y aprendiendo back end con cursos online.",
    skillsLearned: back,
    skillsToLearn: back,
  },
  {
    name: "Zoe P.",
    profilePic: "/src/assets/users/4.png",
    job: "Desarrollador/a Web",
    country: "Uruguay",
    description:
      "Soy Zoe, vivo en Uruguay y estoy recien entrando al mundo de la programación, del desarrollo web en especifico.",
    skillsLearned: front,
    skillsToLearn: front2,
  },
];

export const choosenFilters = {
  skillsLearned: [{ name: "HTML" }, { name: "CSS" }],
  skillsToLearn: [{ name: "React" }],
};

export const skills = [
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
];

export const images = [
  "/src/assets/users/1.png",
  "/src/assets/users/2.png",
  "/src/assets/users/3.png",
  "/src/assets/users/4.png",
  "/src/assets/users/5.png",
  "/src/assets/users/6.png",
  "/src/assets/users/7.png",
  "/src/assets/users/8.png",
  "/src/assets/users/9.png",
  "/src/assets/users/10.png",
];
