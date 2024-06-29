import React from 'react'

export const UserCardExample = ({user}) => {

    const { name, job, country, description, skillsLearned, skillsToLearn} = user;

    return (
        <div className='p-5 bg-white rounded-3xl shadow-md'>
            <div>
                <h4 className='font-semibold text-dark text-2xl'>{name}</h4>
                <h5 className='font-medium text-dark text-xl'>{job}</h5>
                <p className='font-medium text-dark'>{country}</p>
            </div>
            <p>{description}</p>
            <p className='mt-2 font-medium'>CONOCIMIENTOS:</p>
            <div className="flex gap-3">
                {skillsLearned.map((item, index)=>(
                    <div key={index} className='px-2 py-1 bg-light-green dark:bg-dm-light-green text-light rounded-md'>{item.name}</div>
                ))}
            </div>
            <p className='mt-2 font-medium'>APRENDIENDO:</p>
            <div className="flex gap-3">
                {skillsToLearn.map((item, index)=>(
                    <div key={index} className='px-2 py-1 border-2 border-medium-green dark:border-dm-medium-green text-dark rounded-md'>{item.name}</div>
                ))}
            </div>
        </div>
    )
}
