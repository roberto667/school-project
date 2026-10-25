import { useState } from "react";
import { authFetch } from "../../../../Services/api";

import {
  formStyle,
  inputStyle,
  labelStyle,
  buttonStyle,
} from "../../formStyle";

export default function UpdateStudentForm({ onDone }) {
  const [matriculaBusca, setMatriculaBusca] = useState("");
  const [form, setForm] = useState(null);

  const [loadingBusca, setLoadingBusca] = useState(false);
  const [loadingUpdate, setLoadingUpdate] = useState(false);

  async function buscarAluno() {
    const matricula = matriculaBusca.trim();

    if (!matricula) {
      onDone("Informe a matrícula do aluno.");
      return;
    }

    setLoadingBusca(true);
    setForm(null);
    onDone("");

    try {
      const response = await authFetch(
        `/students/matricula/${encodeURIComponent(matricula)}`,
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);

        throw new Error(err?.message || "Aluno não encontrado");
      }

      const aluno = await response.json();

      setForm({
        matricula: aluno.matricula,
        firstName: aluno.user.firstName,
        lastName: aluno.user.lastName,
        email: aluno.user.email,
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
    setForm((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!form) return;

    setLoadingUpdate(true);
    onDone("");

    try {
      const response = await authFetch(
        `/students/matricula/${encodeURIComponent(matriculaBusca.trim())}`,
        {
          method: "PUT",
          body: JSON.stringify({
            matricula: form.matricula,
            user: {
              firstName: form.firstName,
              lastName: form.lastName,
              email: form.email,

              // Campo vazio mantém a senha atual,
              // desde que o backend trate null assim.
              password: form.password.trim() ? form.password : null,
            },
          }),
        },
      );

      if (!response.ok) {
        const err = await response.json().catch(() => null);

        throw new Error(err?.message || "Erro ao atualizar aluno");
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

      onDone("Aluno atualizado com sucesso!");
    } catch (err) {
      onDone(err.message);
    } finally {
      setLoadingUpdate(false);
    }
  }

  return (
    <div style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Atualizar aluno</h2>

      {/* Buscar aluno */}
      <div className="flex flex-col gap-2">
        <label htmlFor="matriculaBusca" className={labelStyle}>
          Matrícula do aluno
        </label>

        <input
          id="matriculaBusca"
          type="text"
          placeholder="Ex.: 20260001"
          value={matriculaBusca}
          onChange={(e) => {
            setMatriculaBusca(e.target.value);
            setForm(null);
          }}
          className={inputStyle}
          disabled={loadingBusca || loadingUpdate}
        />
      </div>

      <button
        type="button"
        onClick={buscarAluno}
        disabled={loadingBusca || loadingUpdate}
        className={buttonStyle}
      >
        {loadingBusca ? "Buscando..." : "Buscar aluno"}
      </button>

      {/* Formulário de atualização */}
      {form && (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="border-t border-gray-200 pt-4">
            <h3 className="text-lg font-semibold text-gray-800">
              Dados do aluno
            </h3>
            <p className="mt-1 text-sm text-gray-500">
              Altere os campos que deseja atualizar.
            </p>
          </div>

          {/* Matrícula */}
          <div className="flex flex-col gap-2">
            <label htmlFor="matricula" className={labelStyle}>
              Matrícula
            </label>

            <input
              id="matricula"
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
            <label htmlFor="firstName" className={labelStyle}>
              Nome
            </label>

            <input
              id="firstName"
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
            <label htmlFor="lastName" className={labelStyle}>
              Sobrenome
            </label>

            <input
              id="lastName"
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
            <label htmlFor="email" className={labelStyle}>
              Email
            </label>

            <input
              id="email"
              name="email"
              type="email"
              placeholder="Ex.: aluno@email.com"
              value={form.email}
              onChange={handleChange}
              required
              disabled={loadingUpdate}
              className={inputStyle}
            />
          </div>

          {/* Nova senha */}
          <div className="flex flex-col gap-2">
            <label htmlFor="password" className={labelStyle}>
              Nova senha (opcional)
            </label>

            <input
              id="password"
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
            {loadingUpdate ? "Atualizando..." : "Atualizar aluno"}
          </button>
        </form>
      )}
    </div>
  );
}
