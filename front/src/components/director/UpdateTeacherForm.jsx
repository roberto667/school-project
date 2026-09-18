import { useState } from "react";
import { authFetch } from "../../Services/api";
import { formStyle } from "./formStyle";

export default function UpdateTeacherForm({ onDone }) {
  const [matricula, setMatricula] = useState("");
  const [novaMatricula, setNovaMatricula] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await authFetch(
        `/users/teachers/matricula/${matricula}`,
        {
          method: "PUT",
          body: JSON.stringify({ matricula: novaMatricula }),
        },
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);
        throw new Error(err?.message || "Erro ao atualizar professor");
      }

      onDone("Professor atualizado com sucesso!");
    } catch (err) {
      onDone(err.message);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <input
        placeholder="Matrícula do Professor"
        value={matricula}
        onChange={(e) => setMatricula(e.target.value)}
        required
      />
      <input
        placeholder="Nova Matrícula"
        value={novaMatricula}
        onChange={(e) => setNovaMatricula(e.target.value)}
        required
      />
      <button type="submit">Atualizar Professor</button>
    </form>
  );
}
