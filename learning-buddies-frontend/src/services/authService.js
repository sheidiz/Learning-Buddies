import axios from "axios";

const API_URL = 'http://localhost:8080';

export const registerUser = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/register`, userData);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error en el registro');
        } else {
            throw new Error("Ocurrio un error al registrarse.");
        }
    }
};

export const loginUser = async (loginData) => {
    try {
        const response = await axios.post(`${API_URL}/login`, loginData);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data || 'Error en el inicio de sesión');
        } else {
            throw new Error("Ocurrio un error al iniciar sesión.");
        }
    }
};