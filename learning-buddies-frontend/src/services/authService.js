import axios from "axios";

const API_URL = "http://localhost:8080/api";

const register = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/auth/register`, userData);
    return response.data;
  } catch (error) {
    if (error.response) {
      throw new Error("Email ya se encuentra registrado");
    } else {
      throw new Error("Ocurrio un error al registrarse.");
    }
  }
};

export const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_URL}/auth/login`, {
      email: email,
      password: password,
    });
    return response.data;
  } catch (error) {
    if (error.response) {
      if (error.response.data.includes("User not found")) {
        throw new Error("No se encontro un usuario con el email ingresado.");
      } else {
        throw new Error("Contraseña incorrecta.");
      }
    }
  }
};

export default {
  login,
  register,
};
