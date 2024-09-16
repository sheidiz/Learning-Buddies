export const validateProfileData = (profileData) => {
  let errors = {
    name: null,
    jobPosition: null,
    bio: null,
    country: null,
    gender: null,
    contact: null,
  };

  console.log(errors, profileData.name);
  if (!profileData.name || profileData.name.trim().length === 0) {
    errors.name = "Ingresa tu nombre.";
  }

  if (!profileData.jobPosition || profileData.jobPosition.trim().length === 0) {
    errors.jobPosition = "Ingresa un rol.";
  }

  if (!profileData.bio || profileData.bio.trim().length === 0) {
    errors.bio = "Ingresa una breve biografía.";
  }

  if (
    !profileData.country ||
    profileData.country.trim().length === 0 ||
    profileData.country === "-"
  ) {
    errors.country = "Selecciona un país.";
  }

  if (
    !profileData.gender ||
    profileData.gender.trim().length === 0 ||
    profileData.gender === "-"
  ) {
    errors.gender = "Selecciona un género.";
  }

  if (
    (!profileData.discordUrl || profileData.discordUrl.trim().length === 0) &&
    (!profileData.githubUrl || profileData.githubUrl.trim().length === 0) &&
    (!profileData.linkedinUrl || profileData.linkedinUrl.trim().length === 0) &&
    (!profileData.contactEmail || profileData.contactEmail.trim().length === 0)
  ) {
    errors.contact = "Ingresa al menos una forma de contacto.";
  }
  console.log(errors);
  return errors;
};
