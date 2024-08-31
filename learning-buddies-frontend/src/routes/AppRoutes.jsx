import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Header } from '../components/layout/Header'
import { Footer } from '../components/layout/Footer'
import Home from '../pages/Home'
import Login from '../pages/Login'
import Registration from '../pages/Registration'
import AuthGuard from './AuthGuard'
import Buddies from '../pages/Buddies'
import ProfileCreation from '../pages/ProfileCreation'
import Profile from '../pages/Profile'
import { ProfileEdition } from '../pages/ProfileEdition'

export default function AppRoutes() {
    return (
        <div className="bg-light dark:bg-dark">
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route path="*" element={<Home />} />
                    <Route path="/" element={<Home />} />
                    <Route path="/iniciar-sesion" element={<Login />} />
                    <Route path="/registro" element={<Registration />} />
                    <Route element={<AuthGuard />}> {/*FALTA*/}
                        <Route path="/buddies" element={<Buddies />} />
                        <Route path="/creacion-perfil" element={<ProfileCreation />} />
                        <Route path="/edicion-perfil" element={<ProfileEdition />} />
                        <Route path="/mi-perfil" element={<Profile />} />
                    </Route>
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    )
}
