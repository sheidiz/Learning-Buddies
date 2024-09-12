export const users = [
  {
    id: "1",
    email: "juan.carlos@gmail.com",
    password: "123456",
    authProvider: "local",
  },
  {
    id: "2",
    email: "camilad@gmail.com",
    password: "123456",
    authProvider: "local",
  },
  {
    id: "3",
    email: "zoe.p@gmail.com",
    password: "123456",
    authProvider: "local",
  },
];

const front = [{ name: "HTML" }, { name: "CSS" }, { name: "JavaScript" }];
const front2 = [{ name: "React" }, { name: "NodeJS" }];
const back = [{ name: "C++" }, { name: "Java" }, { name: "Spring" }];

export const profiles = [
  {
    id: "1",
    userId: "1",
    name: "Juan Carlos G",
    profilePic: "/src/assets/users/6.png",
    gender: "Hombre",
    jobPosition: "Desarrollador/a Front-end",
    country: "Argentina",
    bio: "Soy Juan y me encuentro estudiando de manera autodidacta lenguajes orientados al front-end.",
    skillsLearned: front,
    skillsToLearn: front2,
  },
  {
    id: "2",
    userId: "2",
    name: "Camila D.",
    profilePic: "/src/assets/users/2.png",
    gender: "Mujer",
    jobPosition: "Desarrollador/a Back-end",
    country: "Argentina",
    bio: "Soy Camila y me encuentro estudiando ingenieria en informatica y aprendiendo back end con cursos online.",
    discord: "camd",
    github: "cam.d",
    linkedIn: "/in/camila-diaz",
    skillsLearned: front,
    skillsToLearn: back,
  },
  {
    id: "3",
    userId: "3",
    name: "Zoe P.",
    profilePic: "/src/assets/users/4.png",
    gender: "Mujer",
    jobPosition: "Desarrollador/a Web",
    country: "Uruguay",
    bio: "Soy Zoe, vivo en Uruguay y estoy recien entrando al mundo de la programación, del desarrollo web en especifico.",
    skillsLearned: front,
    skillsToLearn: front2,
  },
];
