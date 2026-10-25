// Mantém o estado de autenticação compartilhado e persistido no navegador.
import { useState } from "react";
import { authContext } from "./auth-context";

export function AuthProvider({ children }) {
  const [auth, setAuth] = useState(() => {
    const stored = localStorage.getItem("auth");

    if (!stored) {
      return null;
    }

    try {
      return JSON.parse(stored);
    } catch {
      localStorage.removeItem("auth");
      return null;
    }
  });

  function signIn(data) {
    localStorage.setItem("auth", JSON.stringify(data));

    setAuth(data);
  }

  function signOut() {
    localStorage.removeItem("auth");
    setAuth(null);
  }

  return (
    <authContext.Provider
      value={{
        auth,
        signIn,
        signOut,
      }}
    >
      {children}
    </authContext.Provider>
  );
}
