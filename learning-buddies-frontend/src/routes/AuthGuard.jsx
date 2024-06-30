//import { useSelector } from "react-redux";
import { Navigate, Outlet } from "react-router-dom";

export function AuthGuard() {
    //mirar algo(redux) y si no la cumple navega a la home
    //const loginSlice = useSelector(store => store.users);
    // return loginSlice.email ? <Outlet/> : <Navigate replace to={'/'}/>
    return <Outlet />;
}

export default AuthGuard;