import { useState } from "react";
import { authFetch } from "../../../Services/api";

import { formStyle, inputStyle, labelStyle } from "../formStyle";

export default function DeleteForm({ label, endpoint, onDone }) {
  const [matricula, setMatricula] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();

    const matriculaLimpa = matricula.trim();

    if (!matriculaLimpa) {
      setError("Informe a matrícula.");
      return;
    }

    const confirmed = window.confirm(
      `Deseja realmente desativar ${label.toLowerCase()} de matrícula ${matriculaLimpa}?`,
    );

    if (!confirmed) return;

    setLoading(true);
    setError("");
    onDone?.("");

    try {
      const response = await authFetch(endpoint(matriculaLimpa), {
        method: "PATCH",
      });

      if (!response.ok) {
        const data = await response.json().catch(() => null);

        throw new Error(data?.message || "Erro ao desativar.");
      }

      setMatricula("");
      onDone?.("Conta desativada com sucesso.");
    } catch (err) {
      setError(err.message || "Erro inesperado.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">{label}</h2>

      <p className="text-sm text-gray-500">
        Informe a matrícula da conta que deseja desativar.
      </p>

      {/* Matrícula */}
      <div className="flex flex-col gap-2">
        <label htmlFor="delete-matricula" className={labelStyle}>
          Matrícula
        </label>

        <input
          id="delete-matricula"
          type="text"
          placeholder="Ex.: 20260001"
          value={matricula}
          onChange={(e) => {
            setMatricula(e.target.value);
            setError("");
          }}
          required
          disabled={loading}
          className={inputStyle}
        />
      </div>

      {/* Mensagem de erro */}
      {error && (
        <div
          role="alert"
          className="rounded-lg border border-red-200 bg-red-50 p-3"
        >
          <p className="text-sm font-medium text-red-600">{error}</p>
        </div>
      )}

      {/* Aviso */}
      <div className="rounded-lg border border-amber-200 bg-amber-50 p-4">
        <div className="flex items-start gap-3">
          <div>
            <p className="text-sm font-semibold text-amber-800">Atenção</p>

            <p className="mt-1 text-sm text-amber-700">
              A conta será desativada e poderá perder acesso ao sistema.
              Confirme a matrícula antes de continuar.
            </p>
          </div>
        </div>
      </div>

      {/* Botão de desativação */}
      <button
        type="submit"
        disabled={loading || !matricula.trim()}
        className="
          w-full cursor-pointer rounded-lg
          bg-red-600 p-3 font-medium text-white
          transition hover:bg-red-700
          disabled:cursor-not-allowed
          disabled:opacity-50
        "
      >
        {loading ? "Desativando..." : label}
      </button>
    </form>
  );
}
