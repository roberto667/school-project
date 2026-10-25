// Hook para acessar o estado e as ações de autenticação do aplicativo.
import { useContext } from "react";
import { authContext } from "./auth-context";

export function useAuth() {
  const context = useContext(authContext);

  if (!context) {
    throw new Error("useAuth deve ser usado dentro de AuthProvider");
  }

  return context;
}
