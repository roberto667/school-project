import { useState } from "react";
import { authFetch } from "../../Services/api";
import { formStyle } from "./formStyle";

export default function DeleteForm({ label, endpoint, onDone }) {
  const [matricula, setMatricula] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await authFetch(endpoint(matricula), {
        method: "DELETE",
      });

      if (!response.ok) {
        const err = await response.json().catch(() => null);
        throw new Error(err?.message || "Erro ao deletar");
      }

      onDone(`${label} realizado com sucesso!`);
    } catch (err) {
      onDone(err.message);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <input
        placeholder="Matrícula"
        value={matricula}
        onChange={(e) => setMatricula(e.target.value)}
        required
      />
      <button type="submit" style={{ background: "#c0392b", color: "white" }}>
        {label}
      </button>
    </form>
  );
}
