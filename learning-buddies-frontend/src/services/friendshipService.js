import axios from "axios";

const API_URL = "http://localhost:8080/api/friendships";

export const getFriendships = async (token) => {
  try {
    const response = await axios.get(`${API_URL}/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error("Error al obtener amistades y solicitudes de amistad.");
    }
  }
};

export const sendFriendshipRequest = async (id, token) => {
  try {
    console.log("el token: " + token);
    const response = await axios.post(`${API_URL}/send-request/${id}`, null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error("Error al enviar solicitud de amistad.");
    }
  }
};

export const acceptFriendshipRequest = async (id, token) => {
  try {
    const response = await axios.put(
      `${API_URL}/me/accept-request/${id}`,
      null,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    );
    return response.data;
  } catch (error) {
    if (error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error("Error al enviar solicitud de amistad.");
    }
  }
};

export const removeFriendshipRequest = async (id, token) => {
  try {
    const response = await axios.delete(`${API_URL}/me/reject-request/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    if (error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error("Error al borrar solicitud de amistad.");
    }
  }
};

export default {
  getFriendships,
  sendFriendshipRequest,
  acceptFriendshipRequest,
  removeFriendshipRequest,
};
