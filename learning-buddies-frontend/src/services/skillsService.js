import axios from "axios";

const API_URL = 'http://localhost:8080';

export const getSkills = async () => {
    try {
        const response = await axios.get(`${API_URL}/api/v1/skills`);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw error.response.data;
        } else {
            throw new Error("Ocurrio un error obteniendo las habilidades.");
        }
    }
};
