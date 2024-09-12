import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export function AuthGuard() {
  const { user, loading } = useAuth();

  if (loading) {
    return <p>Loading...</p>;
  }
  console.log(user);
  return user != null && user.email ? (
    <Outlet />
  ) : (
    <Navigate replace to={"/"} />
  );
}

export default AuthGuard;
