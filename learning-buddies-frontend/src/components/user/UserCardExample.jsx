export const UserCardExample = ({ user, contactable }) => {

    const { name, profilePic, job, country, description, skillsLearned, skillsToLearn } = user;

    return (
        <div className='p-5 bg-white rounded-3xl shadow-md'>
            <div className="mb-2 flex gap-2 md:gap-4">
                <img src={profilePic} alt={"Foto de perfil de " + name} className="h-20 md:h-20 bg-light-green dark:bg-dm-light-green rounded-full" />
                <div>
                    <h4 className='font-semibold text-dark text-xl md:text-2xl'>{name}</h4>
                    <h5 className='font-medium text-dark text-lg md:text-xl'>{job}</h5>
                    <p className='font-medium text-dark text-sm md:text-base'>{country}</p>
                </div>
            </div>
            <p>{description}</p>
            <p className='mt-2 font-medium'>CONOCIMIENTOS:</p>
            <div className="flex gap-3">
                {skillsLearned.map((item, index) => (
                    <div key={index} className='px-2 py-1 bg-light-green dark:bg-dm-light-green text-white text-sm md:text-base rounded-md'>{item.name}</div>
                ))}
            </div>
            <p className='mt-2 font-medium'>APRENDIENDO:</p>
            <div className="flex gap-3">
                {skillsToLearn.map((item, index) => (
                    <div key={index} className='px-2 py-1 border-2 border-medium-green dark:border-dm-medium-green text-dark text-sm md:text-base rounded-md'>{item.name}</div>
                ))}
            </div>
            <div className="mt-3 w-full text-center">
                {contactable && (
                    <button className="px-10 py-1 bg-dark text-light font-extrabold rounded-3xl hover:scale-110">Contactar</button>
                )}
            </div>
        </div>
    )
}
