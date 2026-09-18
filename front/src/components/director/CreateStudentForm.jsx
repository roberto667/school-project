import { useState } from "react";
import { authFetch } from "../../Services/api";
import { formStyle } from "./formStyle";

export default function CreateStudentForm({ onDone }) {
  const [form, setForm] = useState({
    matricula: "",
    grau: "FUNDAMENTAL_1",
    ano: 1,
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await authFetch("/users/students", {
        method: "POST",
        body: JSON.stringify({
          matricula: form.matricula,
          grau: form.grau,
          ano: Number(form.ano),
          user: {
            firstName: form.firstName,
            lastName: form.lastName,
            email: form.email,
            password: form.password,
          },
        }),
      });

      if (!response.ok) {
        const err = await response.json().catch(() => null);
        throw new Error(err?.message || "Erro ao criar aluno");
      }

      onDone("Aluno criado com sucesso!");
    } catch (err) {
      onDone(err.message);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <input
        name="matricula"
        placeholder="Matrícula"
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
      <input
        name="firstName"
        placeholder="Nome"
        value={form.firstName}
        onChange={handleChange}
        required
      />
      <input
        name="lastName"
        placeholder="Sobrenome"
        value={form.lastName}
        onChange={handleChange}
        required
      />
      <input
        name="email"
        type="email"
        placeholder="Email"
        value={form.email}
        onChange={handleChange}
        required
      />
      <input
        name="password"
        type="password"
        placeholder="Senha"
        value={form.password}
        onChange={handleChange}
        required
      />
      <button type="submit">Criar Aluno</button>
    </form>
  );
}
