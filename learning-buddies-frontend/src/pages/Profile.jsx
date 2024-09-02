import React, { useEffect, useState } from 'react'
import { useAuth } from '../contexts/AuthContext';
import { TextLabel } from '../components/user/TextLabel';
import profilesService from '../services/profilesService';
import friendshipService from '../services/friendshipService';
import { FriendCard } from '../components/buddies/FriendCard';
import { FriendRequest } from '../components/buddies/FriendRequest';

export default function Profile() {
    const { token } = useAuth();
    const [profile, setProfile] = useState();
    const [friends, setFriends] = useState();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const [profileData, friendshipsData] = await Promise.all([
                    profilesService.getProfile(token),
                    friendshipService.getFriendships(token)
                ]);
                setProfile(profileData);
                setFriends(friendshipsData);
            } catch (error) {
                console.error('Error fetching data:', error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchProfile();
    }, []);

    if (isLoading) {
        return (<p>Cargando...</p>)
    }

    const { name, profilePicture, profilePictureBackground, gender, jobPosition, country, bio, skillsLearned, skillsToLearn, discordUrl, githubUrl, linkedinUrl, contactEmail } = profile;
    const { friendships, pendingRequests, receivedRequests } = friends;

    return (
        <main className="w-full md:max-w-7xl mb-5 md:mx-auto px-2 pt-2 font-raleway text-dark dark:text-light md:flex md:gap-x-4">
            <section className="md:w-2/3 lg:w-1/2 px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <div className="mb-2 flex flex-col justify-center items-center">
                    <div className="rounded-full overflow-hidden">
                        <img src={profilePicture} alt="Avatar" className="h-28 md:h-32 rounded-full" style={{ backgroundColor: profilePictureBackground }} />
                    </div>
                    <h1 className='mt-1 font-bold text-2xl'>{name}</h1>
                    <h3>{jobPosition} | {gender} </h3>
                </div>
                <h4 className='mb-1 text-lg font-semibold'>Biografía</h4>
                <p className='mb-4 font-light'>{bio}</p>
                <h2 className="mt-3 font-bold text-2xl">Datos de contacto</h2>
                <h3 className="text-dark-green dark:text-dm-light-green font-semibold text-sm">Estos datos se mostraran solo a tus conexiones</h3>
                <div className="mt-2 grid grid-cols-2 gap-4">
                    <TextLabel label="Discord" inputPlaceholder={discordUrl} />
                    <TextLabel label="GitHub" inputPlaceholder={githubUrl} />
                    <TextLabel label="LinkedIn" inputPlaceholder={linkedinUrl} />
                    <TextLabel label="Email" inputPlaceholder={contactEmail} />
                </div>
                <h2 className="mt-6 font-bold text-2xl">Tus habilidades</h2>
                <p className='my-2 font-medium'>CONOCIMIENTOS:</p>
                <div className="pb-3 flex flex-wrap gap-2 md:gap-3 text-white text-sm md:text-base">
                    {skillsLearned.map((item, index) => (
                        <p key={index}
                            className={`md:text-sm lg:text-base px-2 bg-light-green dark:bg-dm-light-green rounded-md shadow-inner-custom`}>
                            {item}
                        </p>
                    ))}
                </div>
                <p className='my-2 font-medium'>APRENDIENDO:</p>
                <div className="flex flex-wrap gap-2 md:gap-3 text-dark text-sm md:text-base">
                    {skillsToLearn.map((item, index) => (
                        <p key={index}
                            className={`md:text-sm lg:text-base px-2 border-2 bg-light border-light-green dark:border-dm-light-green rounded-md shadow-inner-custom`}>
                            {item}
                        </p>
                    ))}
                </div>
                <a href="/edicion-perfil" className="block w-fit mt-5 mx-auto py-1 px-6 rounded-3xl text-decoration-none border-2 border-transparent bg-dark-green dark:bg-dm-medium-green md:dark:bg-dark font-bold text-white md:hover:scale-105">Editar perfil</a>
            </section>
            <section className="md:w-1/3 lg:w-1/2 mt-10 md:mt-0 md:h-fit px-3 md:p-6 lg:px-10 lg:max-w-5xl lg:mx-auto md:bg-white md:dark:bg-dm-dark-green md:rounded-md">
                <h2 className='font-bold text-2xl'>Tus conexiones</h2>
                <div className='my-4 flex flex-col gap-4'>
                    {friendships.length > 0 ? friendships.map((profile, index) => (
                        <FriendCard key={index} profile={profile} />
                    ))
                        : <p>Sin amistades.</p>
                    }
                </div>
                <h2 className='pt-2 font-bold text-2xl'>Tus solicitudes pendientes</h2>
                <div className='my-4 flex flex-col gap-4'>
                    {receivedRequests.length > 0 ? receivedRequests.map((profile, index) => (
                        <FriendRequest key={index} profile={profile} type="Received" />
                    ))
                        : <p>Sin solicitudes pendientes.</p>}
                </div>
                <h2 className='pt-2 font-bold text-2xl'>Tus solicitudes enviadas</h2>
                <div className='my-4 flex flex-col gap-4'>
                    {pendingRequests.length > 0 ? pendingRequests.map((profile, index) => (
                        <FriendRequest key={index} profile={profile} type="Sent" />
                    ))
                        : <p>Sin solicitudes pendientes de respuesta.</p>}
                </div>
            </section>
        </main>
    )
}
