import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Header } from '../components/layout/Header'
import { Footer } from '../components/layout/Footer'
import Home from '../pages/Home'
import Login from '../pages/Login'
import Registration from '../pages/Registration'

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
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    )
}
