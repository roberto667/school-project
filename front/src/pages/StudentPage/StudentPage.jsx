// Apresenta a área inicial do aluno e oferece o encerramento da sessão.
import { useAuth } from "../../context/useAuth";

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
