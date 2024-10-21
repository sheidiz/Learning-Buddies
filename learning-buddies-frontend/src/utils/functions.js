export const normalizeError = (error) => {
  let message = error.message;

  message = message.replace('["', "").replace('"]', "");

  return message.toString();
};

export const getProfilePictureId = (path) => {
  const regex = /\/users\/(\d+)\.png/;
  const match = path.match(regex);
  return match ? match[1] : null;
};
