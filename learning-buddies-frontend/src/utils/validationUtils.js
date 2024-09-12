export const validateProfileData = (profileData) => {
  let errors = [];

  if (!profileData.name || profileData.name.trim().length === 0) {
    errors.push("Ingresa tu nombre.");
  }

  if (!profileData.jobPosition || profileData.jobPosition.trim().length === 0) {
    errors.push("Ingresa un rol.");
  }

  if (!profileData.bio || profileData.bio.trim().length === 0) {
    errors.push("Ingresa una breve biografía.");
  }

  if (
    !profileData.country ||
    profileData.country.trim().length === 0 ||
    profileData.country === "-"
  ) {
    errors.push("Selecciona un país.");
  }

  if (
    !profileData.gender ||
    profileData.gender.trim().length === 0 ||
    profileData.gender === "-"
  ) {
    errors.push("Selecciona un género.");
  }

  if (
    (!profileData.discordUrl || profileData.discordUrl.trim().length === 0) &&
    (!profileData.githubUrl || profileData.githubUrl.trim().length === 0) &&
    (!profileData.linkedinUrl || profileData.linkedinUrl.trim().length === 0) &&
    (!profileData.contactEmail || profileData.contactEmail.trim().length === 0)
  ) {
    errors.push("Ingresa al menos una forma de contacto.");
  }

  return errors.length > 0 ? errors.join("<br />") : null;
};
