import React, { createContext, useContext, useEffect, useState } from 'react';
import { loadFromLocalStorage } from '../utils/storageUtils';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [profile, setProfile] = useState(null);

    useEffect(() => {
        const savedUser = loadFromLocalStorage('user');
        const savedProfile = loadFromLocalStorage('profile');
        if (savedUser) setUser(savedUser);
        if (savedProfile) setProfile(savedProfile);
    }, []);

    return (
        <UserContext.Provider value={{ user, profile, setUser, setProfile }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUser = () => useContext(UserContext);
