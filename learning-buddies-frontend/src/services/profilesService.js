import axios from "axios";

const API_URL = 'http://localhost:8080';

export const getProfile = async (email) => {

    try {
        const response = await axios.get(`${API_URL}/api/v1/profiles/${email}`);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error al obtener el perfil');
        } else {
            throw new Error("Ocurrio un error obteniendo el perfil.");
        }
    }
};

export const createProfile = async (id, profileData) => {
    try {
        const response = await axios.post(`${API_URL}/api/v1/profiles/${id}`, profileData);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error en la creación del perfil.');
        } else {
            throw new Error("Ocurrio un error al crear el perfil.");
        }
    }
};