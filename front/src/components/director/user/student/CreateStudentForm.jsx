import { useState } from "react";
import { authFetch } from "../../../../Services/api";

import {
  formStyle,
  inputStyle,
  labelStyle,
  buttonStyle,
} from "../../formStyle";

export default function CreateStudentForm({ onDone }) {
  const [form, setForm] = useState({
    matricula: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);

  function handleChange(e) {
    setForm((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setLoading(true);

    try {
      const response = await authFetch("/students", {
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

        throw new Error(err?.message || "Erro ao criar aluno");
      }

      setForm({
        matricula: "",
        firstName: "",
        lastName: "",
        email: "",
        password: "",
      });

      onDone("Aluno criado com sucesso!");
    } catch (err) {
      onDone(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Adicionar aluno</h2>

      <div className="flex flex-col gap-2">
        <label htmlFor="matricula" className={labelStyle}>
          Matrícula
        </label>

        <input
          id="matricula"
          name="matricula"
          type="text"
          placeholder="Ex.: 20260001"
          value={form.matricula}
          onChange={handleChange}
          required
          className={inputStyle}
        />
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="firstName" className={labelStyle}>
          Nome
        </label>

        <input
          id="firstName"
          name="firstName"
          type="text"
          placeholder="Ex.: João"
          value={form.firstName}
          onChange={handleChange}
          required
          className={inputStyle}
        />
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="lastName" className={labelStyle}>
          Sobrenome
        </label>

        <input
          id="lastName"
          name="lastName"
          type="text"
          placeholder="Ex.: Silva"
          value={form.lastName}
          onChange={handleChange}
          required
          className={inputStyle}
        />
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="email" className={labelStyle}>
          Email
        </label>

        <input
          id="email"
          name="email"
          type="email"
          placeholder="Ex.: joao@email.com"
          value={form.email}
          onChange={handleChange}
          required
          className={inputStyle}
        />
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="password" className={labelStyle}>
          Senha
        </label>

        <input
          id="password"
          name="password"
          type="password"
          placeholder="Mínimo de 8 caracteres"
          value={form.password}
          onChange={handleChange}
          minLength={8}
          required
          className={inputStyle}
        />
      </div>

      <button type="submit" disabled={loading} className={buttonStyle}>
        {loading ? "Cadastrando..." : "Cadastrar aluno"}
      </button>
    </form>
  );
}
