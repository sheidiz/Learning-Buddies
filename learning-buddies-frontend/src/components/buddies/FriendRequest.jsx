import React from 'react'
import { BiTrash } from 'react-icons/bi';
import { MdCheck } from 'react-icons/md';

export const FriendRequest = ({ index, profile, type }) => {
    const { id, name, profilePicture, profilePictureBackground, gender, jobPosition, country, bio, skillsLearned, skillsToLearn, discordUrl, githubUrl, linkedinUrl, contactEmail } = profile;

    return (
        <div key={index} className='flex gap-4'>
            <div className="rounded-full overflow-hidden">
                <img src={profilePicture} alt="Avatar" className="h-24 rounded-full bg-zinc-400 dark:bg-zinc-700" style={{ backgroundColor: profilePictureBackground }} />
            </div>
            <div className='flex flex-col justify-evenly'>
                <p className='text-lg font-semibold'>{name}</p>
                <p className='mb-1 font-semibold'>{jobPosition}</p>
                <div className='flex gap-2'>
                    <a href="/solicitud/id/borrar" className="block w-fit py-1 px-4 rounded-3xl text-decoration-none border-2 border-transparent bg-red-800 text-sm md:text-base font-semibold text-white md:hover:scale-105 select-none"><BiTrash /></a>
                    {
                        type == "Received" &&
                        <a href="/solicitud/id/aceptar" className="block w-fit py-1 px-4 rounded-3xl text-decoration-none border-2 border-transparent bg-green-800 text-sm md:text-base font-semibold text-white md:hover:scale-105 select-none"><MdCheck /></a>
                    }
                </div>
            </div>
        </div>
    )
}
