import { useState } from "react";
import { authFetch } from "../../Services/api";
import { formStyle } from "./formStyle";

export default function UpdateStudentForm({ onDone }) {
  const [matricula, setMatricula] = useState("");
  const [form, setForm] = useState({
    matricula: "",
    grau: "FUNDAMENTAL_1",
    ano: 1,
  });

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await authFetch(
        `/users/students/matricula/${matricula}`,
        {
          method: "PUT",
          body: JSON.stringify({
            matricula: form.matricula,
            grau: form.grau,
            ano: Number(form.ano),
          }),
        },
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);
        throw new Error(err?.message || "Erro ao atualizar aluno");
      }

      onDone("Aluno atualizado com sucesso!");
    } catch (err) {
      onDone(err.message);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <input
        placeholder="Matrícula do Aluno"
        value={matricula}
        onChange={(e) => setMatricula(e.target.value)}
        required
      />
      <input
        name="matricula"
        placeholder="Nova Matrícula"
        value={form.matricula}
        onChange={handleChange}
        required
      />
      <select name="grau" value={form.grau} onChange={handleChange}>
        <option value="FUNDAMENTAL_1">Fundamental 1</option>
        <option value="FUNDAMENTAL_2">Fundamental 2</option>
        <option value="MEDIO">Médio</option>
        <option value="GRADUACAO">Graduação</option>
      </select>
      <input
        name="ano"
        type="number"
        placeholder="Ano"
        value={form.ano}
        onChange={handleChange}
        required
      />
      <button type="submit">Atualizar Aluno</button>
    </form>
  );
}
