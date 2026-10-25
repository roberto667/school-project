import { useState } from "react";
import { authFetch } from "../../../../Services/api";

import {
  formStyle,
  inputStyle,
  labelStyle,
  buttonStyle,
} from "../../formStyle";

export default function UpdateTeacherForm({ onDone }) {
  const [matriculaBusca, setMatriculaBusca] = useState("");
  const [form, setForm] = useState(null);

  const [loadingBusca, setLoadingBusca] = useState(false);
  const [loadingUpdate, setLoadingUpdate] = useState(false);

  async function buscarProfessor() {
    const matricula = matriculaBusca.trim();

    if (!matricula) {
      onDone("Informe a matrícula do professor.");
      return;
    }

    if (loadingBusca || loadingUpdate) return;

    setLoadingBusca(true);
    setForm(null);
    onDone("");

    try {
      const response = await authFetch(
        `/teachers/matricula/${encodeURIComponent(matricula)}`,
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);

        throw new Error(err?.message || "Professor não encontrado");
      }

      const professor = await response.json();

      setForm({
        matricula: professor.matricula,
        firstName: professor.user.firstName,
        lastName: professor.user.lastName,
        email: professor.user.email,
        password: "",
      });

      onDone("");
    } catch (err) {
      setForm(null);
      onDone(err.message);
    } finally {
      setLoadingBusca(false);
    }
  }

  function handleChange(e) {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!form || loadingUpdate) return;

    setLoadingUpdate(true);
    onDone("");

    try {
      const response = await authFetch(
        `/teachers/matricula/${encodeURIComponent(matriculaBusca.trim())}`,
        {
          method: "PUT",
          body: JSON.stringify({
            matricula: form.matricula,
            user: {
              firstName: form.firstName,
              lastName: form.lastName,
              email: form.email,

              // Senha vazia: mantém a atual
              // se o backend tratar null dessa forma.
              password: form.password.trim() ? form.password : null,
            },
          }),
        },
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);

        throw new Error(err?.message || "Erro ao atualizar professor");
      }

      const updated = await response.json();

      setMatriculaBusca(updated.matricula);

      setForm({
        matricula: updated.matricula,
        firstName: updated.user.firstName,
        lastName: updated.user.lastName,
        email: updated.user.email,
        password: "",
      });

      onDone("Professor atualizado com sucesso!");
    } catch (err) {
      onDone(err.message);
    } finally {
      setLoadingUpdate(false);
    }
  }

  const loading = loadingBusca || loadingUpdate;

  return (
    <div style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">
        Atualizar professor
      </h2>

      {/* Busca por matrícula */}
      <div className="flex flex-col gap-2">
        <label htmlFor="teacher-search" className={labelStyle}>
          Matrícula do professor
        </label>

        <input
          id="teacher-search"
          type="text"
          placeholder="Ex.: 20260001"
          value={matriculaBusca}
          onChange={(e) => {
            setMatriculaBusca(e.target.value);
            setForm(null);
          }}
          disabled={loading}
          className={inputStyle}
        />
      </div>

      <button
        type="button"
        onClick={buscarProfessor}
        disabled={loading}
        className={buttonStyle}
      >
        {loadingBusca ? "Buscando..." : "Buscar professor"}
      </button>

      {/* Formulário de atualização */}
      {form && (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="border-t border-gray-200 pt-4">
            <h3 className="text-lg font-semibold text-gray-800">
              Dados do professor
            </h3>

            <p className="mt-1 text-sm text-gray-500">
              Altere os campos que deseja atualizar.
            </p>
          </div>

          {/* Matrícula */}
          <div className="flex flex-col gap-2">
            <label htmlFor="teacher-matricula" className={labelStyle}>
              Matrícula
            </label>

            <input
              id="teacher-matricula"
              name="matricula"
              type="text"
              placeholder="Matrícula"
              value={form.matricula}
              onChange={handleChange}
              required
              disabled={loadingUpdate}
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
              placeholder="Nome"
              value={form.firstName}
              onChange={handleChange}
              required
              disabled={loadingUpdate}
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
              placeholder="Sobrenome"
              value={form.lastName}
              onChange={handleChange}
              required
              disabled={loadingUpdate}
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
              disabled={loadingUpdate}
              className={inputStyle}
            />
          </div>

          {/* Nova senha */}
          <div className="flex flex-col gap-2">
            <label htmlFor="teacher-password" className={labelStyle}>
              Nova senha (opcional)
            </label>

            <input
              id="teacher-password"
              name="password"
              type="password"
              placeholder="Deixe vazio para manter a senha"
              value={form.password}
              onChange={handleChange}
              minLength={8}
              disabled={loadingUpdate}
              className={inputStyle}
            />

            <p className="text-xs text-gray-500">
              Preencha somente se desejar alterar a senha.
            </p>
          </div>

          {/* Botão de atualização */}
          <button
            type="submit"
            disabled={loadingUpdate}
            className={buttonStyle}
          >
            {loadingUpdate ? "Atualizando..." : "Atualizar professor"}
          </button>
        </form>
      )}
    </div>
  );
}
