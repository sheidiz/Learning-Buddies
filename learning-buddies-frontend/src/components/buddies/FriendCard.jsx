import React from 'react'

export const FriendCard = ({ index, profile }) => {
    const { id, name, profilePicture, profilePictureBackground, gender, jobPosition, country, bio, skillsLearned, skillsToLearn, discordUrl, githubUrl, linkedinUrl, contactEmail } = profile;

    return (
        <div key={index} className='flex gap-4'>
            <div className="rounded-full overflow-hidden">
                <img src={profilePicture} alt="Avatar" className="h-24 rounded-full bg-zinc-400 dark:bg-zinc-700" style={{ backgroundColor: profilePictureBackground }} />
            </div>
            <div className='flex flex-col justify-evenly'>
                <p className='text-lg font-semibold'>{name}</p>
                <p className='mb-1 font-semibold'>{jobPosition}</p>
                <a href={`/perfil/${id}`} className="block w-fit py-1 px-10 rounded-3xl text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dm-medium-green md:dark:bg-dm-light-green/40 text-sm font-semibold text-white md:hover:scale-105 select-none">Ver info</a>
            </div>
        </div>
    )
}
