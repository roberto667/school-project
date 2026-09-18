import { useAuth } from "../../context/AuthContext";

export default function StudentPage() {
  const { auth, signOut } = useAuth();

  return (
    <div style={{ padding: 32 }}>
      <h1>Área do Aluno</h1>
      <p>Bem-vindo, {auth.user.firstName}!</p>
      <button onClick={signOut}>Sair</button>
    </div>
  );
}
