// Restringe páginas a usuários autenticados com o papel necessário.
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export default function PrivateRoute({ children, allowedRole }) {
  const { auth } = useAuth();

  // 1. Usuário não autenticado
  if (!auth) {
    return <Navigate to="/login" replace />;
  }

  // 2. Usuário autenticado, mas sem permissão
  if (allowedRole && auth.role !== allowedRole) {
    return <Navigate to="/403" replace />;
  }

  // 3. Usuário autenticado e autorizado
  return children;
}
