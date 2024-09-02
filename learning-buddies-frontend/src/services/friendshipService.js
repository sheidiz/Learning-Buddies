import axios from "axios";

const API_URL = 'http://localhost:8080/api';


export const getFriendships = async (token) => {
    try {
        const response = await axios.get(`${API_URL}/friendships/me`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error al obtener amistades y solicitudes de amistad.');
        } else {
            throw new Error("Ocurrio un error obteniendo los amistades.");
        }
    }
};

export default {
    getFriendships
};