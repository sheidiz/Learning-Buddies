import axios from "axios";

const API_URL = 'http://localhost:8080/api';

export const getProfile = async () => {

    try {
        const response = await axios.get(`${API_URL}/profiles/me`);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error al obtener el perfil');
        } else {
            throw new Error("Ocurrio un error obteniendo el perfil.");
        }
    }
};

export const saveProfile = async (profileData, token) => {
    try {
        const response = await axios.post(`${API_URL}/profiles/me`, profileData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error en la creación del perfil.');
        } else {
            throw new Error("Ocurrio un error al crear el perfil.");
        }
    }
};

export default {
    saveProfile,
};