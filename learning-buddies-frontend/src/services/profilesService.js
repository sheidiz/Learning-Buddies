import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const getProfile = async (token) => {
  try {
    const response = await axios.get(`${API_URL}/profiles/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data || "Error al obtener el perfil");
    } else {
      throw new Error("Ocurrio un error obteniendo el perfil.");
    }
  }
};

export const getAllProfiles = async (token) => {
  try {
    const response = await axios.get(`${API_URL}/profiles`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data || "Error al obtener los perfiles");
    } else {
      throw new Error("Ocurrio un error obteniendo los perfiles.");
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
      throw new Error(
        error.response.data || "Error en la creación del perfil.",
      );
    } else {
      throw new Error("Ocurrio un error al crear el perfil.");
    }
  }
};

export const editProfile = async (profileData, token) => {
  try {
    const response = await axios.put(`${API_URL}/profiles/me`, profileData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data || "Error en la edición del perfil.");
    } else {
      throw new Error("Ocurrio un error al crear el perfil.");
    }
  }
};

export default {
  saveProfile,
  getProfile,
  getAllProfiles,
  editProfile,
};
