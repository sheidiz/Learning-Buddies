import Woman1 from "../assets/users/woman-1.png";
import Woman2 from "../assets/users/woman-2.png";
import Man1 from "../assets/users/man-1.png";
import Man2 from "../assets/users/man-2.png";

const front = [{ name: 'HTML' }, { name: 'CSS' }, { name: 'JavaScript' }];
const front2 = [{ name: 'React' }, { name: 'NodeJS' }];
const back = [{ name: 'C++' }, { name: 'Java' }, { name: 'Spring' }];

export const users = [
    { name: "Juan Carlos G", profilePic: Man1, job: "Desarrollador/a Front-end", country: "Argentina", description: "Soy Juan y me encuentro estudiando de manera autodidacta lenguajes orientados al front-end.", skillsLearned: front, skillsToLearn: front2 },
    { name: "Camila D.", profilePic: Woman1, job: "Desarrollador/a Back-end", country: "Argentina", description: "Soy Camila y me encuentro estudiando ingenieria en informatica y aprendiendo back end con cursos online.", skillsLearned: back, skillsToLearn: back },
    { name: "Zoe P.", profilePic: Woman2, job: "Desarrollador/a Web", country: "Uruguay", description: "Soy Zoe, vivo en Uruguay y estoy recien entrando al mundo de la programación, del desarrollo web en especifico.", skillsLearned: front, skillsToLearn: front2 },
];

export const choosenFilters = {
    skillsLearned: [{ name: 'HTML' }, { name: 'CSS' }],
    skillsToLearn: [{ name: 'React' }]
};