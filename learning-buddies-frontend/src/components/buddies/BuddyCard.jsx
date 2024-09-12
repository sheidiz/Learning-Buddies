export const BuddyCard = ({ profile, contactable, onClick }) => {
  const {
    id,
    name,
    profilePicture,
    profilePictureBackground,
    gender,
    jobPosition,
    country,
    bio,
    skillsLearned,
    skillsToLearn,
  } = profile;

  const handleContactButton = () => {
    onClick(id, name);
  };

  return (
    <div className="rounded-3xl bg-white p-5">
      <div className="mb-2 flex gap-2 md:gap-4">
        <img
          src={profilePicture}
          alt={"Foto de perfil de " + name}
          className="h-20 rounded-full md:h-20"
          style={{ backgroundColor: profilePictureBackground }}
        />
        <div>
          <h4 className="text-xl font-semibold text-dark md:text-2xl">
            {name}
          </h4>
          <h5 className="text-lg font-medium text-dark md:text-xl">
            {jobPosition}
          </h5>
          <p className="text-sm font-medium text-dark md:text-base">
            {country}
          </p>
        </div>
      </div>
      <p>{bio}</p>
      <p className="mt-2 font-medium">CONOCIMIENTOS:</p>
      <div className="flex gap-3">
        {skillsLearned.length > 0 ? (
          skillsLearned.map((item, index) => (
            <div
              key={index}
              className="rounded-md bg-light-green px-2 py-1 text-sm text-white md:text-base dark:bg-dm-light-green"
            >
              {item}
            </div>
          ))
        ) : (
          <p>-</p>
        )}
      </div>
      <p className="mt-2 font-medium">APRENDIENDO:</p>
      <div className="flex gap-3">
        {skillsToLearn.length > 0 ? (
          skillsToLearn.map((item, index) => (
            <div
              key={index}
              className="rounded-md border-2 border-medium-green px-2 py-1 text-sm text-dark md:text-base dark:border-dm-medium-green"
            >
              {item}
            </div>
          ))
        ) : (
          <p>-</p>
        )}
      </div>
      <div className="mt-3 w-full text-center">
        {contactable && (
          <button
            onClick={handleContactButton}
            className="rounded-3xl bg-dark px-10 py-1 font-extrabold text-light hover:scale-110"
          >
            Contactar
          </button>
        )}
      </div>
    </div>
  );
};
