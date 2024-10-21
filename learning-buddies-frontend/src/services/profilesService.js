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
    handleError(error, "Error al obtener el perfil");
  }
};

export const getAllProfiles = async (
  token,
  skillsLearned = [],
  skillsToLearn = [],
) => {
  try {
    const params = new URLSearchParams();
    if (skillsLearned.length > 0) {
      console.log(skillsLearned);
      params.append("skillsLearned", skillsLearned.join(","));
    }
    if (skillsToLearn.length > 0) {
      console.log(skillsToLearn);
      params.append("skillsToLearn", skillsToLearn.join(","));
    }

    const response = await axios.get(`${API_URL}/profiles`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: params,
    });
    return response.data;
  } catch (error) {
    handleError(error, "Error al obtener los perfiles");
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
    handleError(error, "Error en la creación del perfil");
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
    handleError(error, "Error en la edición del perfil");
  }
};

const handleError = (error, defaultMessage) => {
  if (error.response) {
    throw new Error(error.response.data.message || defaultMessage);
  } else if (error.request) {
    throw new Error("No se recibió respuesta del servidor.");
  } else {
    throw new Error(defaultMessage);
  }
};

export default {
  saveProfile,
  getProfile,
  getAllProfiles,
  editProfile,
};
