import { useAuth } from "../context/AuthContext";

export default function DirectorPage() {
  const { auth, signOut } = useAuth();

  return (
    <div style={{ padding: 32 }}>
      <h1>Área do Diretor</h1>
      <p>Bem-vindo, {auth.user.firstName}!</p>
      <button onClick={signOut}>Sair</button>
    </div>
  );
}
