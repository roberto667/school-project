import { useState } from "react";
import { authFetch } from "../../Services/api";
import { formStyle } from "./formStyle";

export default function CreateTeacherForm({ onDone }) {
  const [form, setForm] = useState({
    matricula: "",
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
      const response = await authFetch("/users/teachers", {
        method: "POST",
        body: JSON.stringify({
          matricula: form.matricula,
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
        throw new Error(err?.message || "Erro ao criar professor");
      }

      onDone("Professor criado com sucesso!");
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
      <button type="submit">Criar Professor</button>
    </form>
  );
}
