import { useState } from "react";
import { useAuth } from "../context/AuthContext";

import CreateStudentForm from "../components/director/CreateStudentForm";
import UpdateStudentForm from "../components/director/UpdateStudentForm";
import CreateTeacherForm from "../components/director/CreateTeacherForm";
import UpdateTeacherForm from "../components/director/UpdateTeacherForm";
import DeleteForm from "../components/director/DeleteForm";

export default function DirectorPage() {
  const { auth, signOut } = useAuth();

  const [activeAction, setActiveAction] = useState(null);
  const [feedback, setFeedback] = useState("");

  function openAction(action) {
    setFeedback("");
    setActiveAction(activeAction === action ? null : action);
  }

  return (
    <div style={{ padding: 32, maxWidth: 700 }}>
      <h1>Área do Diretor</h1>
      <p>Bem-vindo, {auth.user.firstName}!</p>
      <button onClick={signOut}>Sair</button>

      <hr style={{ margin: "24px 0" }} />

      <h2>Alunos</h2>
      <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
        <button onClick={() => openAction("create-student")}>Adicionar</button>
        <button onClick={() => openAction("update-student")}>Alterar</button>
        <button onClick={() => openAction("delete-student")}>Deletar</button>
      </div>

      {activeAction === "create-student" && (
        <CreateStudentForm onDone={setFeedback} />
      )}
      {activeAction === "update-student" && (
        <UpdateStudentForm onDone={setFeedback} />
      )}
      {activeAction === "delete-student" && (
        <DeleteForm
          label="Deletar Aluno"
          endpoint={(matricula) => `/users/students/matricula/${matricula}`}
          onDone={setFeedback}
        />
      )}

      <hr style={{ margin: "24px 0" }} />

      <h2>Professores</h2>
      <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
        <button onClick={() => openAction("create-teacher")}>Adicionar</button>
        <button onClick={() => openAction("update-teacher")}>Alterar</button>
        <button onClick={() => openAction("delete-teacher")}>Deletar</button>
      </div>

      {activeAction === "create-teacher" && (
        <CreateTeacherForm onDone={setFeedback} />
      )}
      {activeAction === "update-teacher" && (
        <UpdateTeacherForm onDone={setFeedback} />
      )}
      {activeAction === "delete-teacher" && (
        <DeleteForm
          label="Deletar Professor"
          endpoint={(matricula) => `/users/teachers/matricula/${matricula}`}
          onDone={setFeedback}
        />
      )}

      {feedback && (
        <p style={{ marginTop: 16, fontWeight: "bold" }}>{feedback}</p>
      )}
    </div>
  );
}
