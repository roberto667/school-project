import { useState } from "react";
import { authFetch } from "../../../../Services/api";

import {
  formStyle,
  inputStyle,
  labelStyle,
  buttonStyle,
} from "../../formStyle";

const initialForm = {
  matricula: "",
  firstName: "",
  lastName: "",
  email: "",
  password: "",
};

export default function CreateTeacherForm({ onDone }) {
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(false);

  function handleChange(e) {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (loading) return;

    setLoading(true);
    onDone("");

    try {
      const response = await authFetch("/teachers", {
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

      setForm({ ...initialForm });

      onDone("Professor criado com sucesso!");
    } catch (err) {
      onDone(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">
        Adicionar professor
      </h2>

      {/* Matrícula */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-matricula" className={labelStyle}>
          Matrícula
        </label>

        <input
          id="teacher-matricula"
          name="matricula"
          type="text"
          placeholder="Ex.: 20260001"
          value={form.matricula}
          onChange={handleChange}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Nome */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-firstName" className={labelStyle}>
          Nome
        </label>

        <input
          id="teacher-firstName"
          name="firstName"
          type="text"
          placeholder="Ex.: João"
          value={form.firstName}
          onChange={handleChange}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Sobrenome */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-lastName" className={labelStyle}>
          Sobrenome
        </label>

        <input
          id="teacher-lastName"
          name="lastName"
          type="text"
          placeholder="Ex.: Silva"
          value={form.lastName}
          onChange={handleChange}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Email */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-email" className={labelStyle}>
          Email
        </label>

        <input
          id="teacher-email"
          name="email"
          type="email"
          placeholder="Ex.: professor@email.com"
          value={form.email}
          onChange={handleChange}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Senha */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-password" className={labelStyle}>
          Senha
        </label>

        <input
          id="teacher-password"
          name="password"
          type="password"
          placeholder="Mínimo de 8 caracteres"
          value={form.password}
          onChange={handleChange}
          minLength={8}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Botão */}
      <button type="submit" disabled={loading} className={buttonStyle}>
        {loading ? "Cadastrando..." : "Cadastrar professor"}
      </button>
    </form>
  );
}
