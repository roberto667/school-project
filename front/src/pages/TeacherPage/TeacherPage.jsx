// Apresenta a área inicial do professor e oferece o encerramento da sessão.
import { useAuth } from "../../context/useAuth";

export default function TeacherPage() {
  const { auth, signOut } = useAuth();

  return (
    <div style={{ padding: 32 }}>
      <h1>Área do Professor</h1>
      <p>Bem-vindo, {auth.user.firstName}!</p>
      <button onClick={signOut}>Sair</button>
    </div>
  );
}
