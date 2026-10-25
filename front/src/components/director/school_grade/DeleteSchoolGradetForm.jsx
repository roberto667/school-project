import { useState } from "react";

import {
  getSubjectByName,
  deleteSubject,
} from "../../../Services/subjectService";

import { formStyle, inputStyle, labelStyle, buttonStyle } from "../formStyle";

export default function DeleteSubjectForm({ setFeedback }) {
  const [nomeBusca, setNomeBusca] = useState("");
  const [subject, setSubject] = useState(null);

  const [loadingBusca, setLoadingBusca] = useState(false);
  const [loadingDelete, setLoadingDelete] = useState(false);

  async function handleSearch() {
    const nomeLimpo = nomeBusca.trim();

    if (!nomeLimpo) {
      setFeedback("Informe o nome da matéria.");
      return;
    }

    setLoadingBusca(true);
    setFeedback("");
    setSubject(null);

    try {
      const data = await getSubjectByName(nomeLimpo);

      setSubject(data);
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setLoadingBusca(false);
    }
  }

  async function handleDelete() {
    if (!subject) return;

    const confirmed = window.confirm(
      `Deseja realmente excluir a matéria "${subject.name}"?`,
    );

    if (!confirmed) return;

    setLoadingDelete(true);
    setFeedback("");

    try {
      await deleteSubject(subject.id);

      setFeedback(`Matéria "${subject.name}" excluída com sucesso!`);

      setNomeBusca("");
      setSubject(null);
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setLoadingDelete(false);
    }
  }

  const loading = loadingBusca || loadingDelete;

  return (
    <div style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Excluir matéria</h2>

      {/* Buscar pelo nome */}
      <div className="flex flex-col gap-2">
        <label htmlFor="delete-subject-search" className={labelStyle}>
          Nome da matéria
        </label>

        <input
          id="delete-subject-search"
          type="text"
          value={nomeBusca}
          onChange={(e) => {
            setNomeBusca(e.target.value);
            setSubject(null);
          }}
          placeholder="Ex.: Matemática"
          disabled={loading}
          className={inputStyle}
        />
      </div>

      <button
        type="button"
        onClick={handleSearch}
        disabled={loading || !nomeBusca.trim()}
        className={buttonStyle}
      >
        {loadingBusca ? "Buscando..." : "Buscar matéria"}
      </button>

      {/* Confirmação da exclusão */}
      {subject && (
        <div className="flex flex-col gap-4 border-t border-gray-200 pt-4">
          <div>
            <h3 className="text-lg font-semibold text-gray-800">
              Matéria encontrada
            </h3>

            <p className="mt-1 text-sm text-gray-500">
              Confira os dados antes de excluir.
            </p>
          </div>

          <div className="rounded-lg border border-red-200 bg-red-50 p-4">
            <p className="font-semibold text-gray-800">{subject.name}</p>

            <p className="mt-1 text-sm text-gray-500">ID: {subject.id}</p>
          </div>

          <p className="text-sm text-red-600">
            Atenção: esta operação poderá ser irreversível.
          </p>

          <button
            type="button"
            onClick={handleDelete}
            disabled={loading}
            className="
              w-full cursor-pointer rounded-lg
              bg-red-600 p-3 font-medium text-white
              transition hover:bg-red-700
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >
            {loadingDelete ? "Excluindo..." : "Confirmar exclusão"}
          </button>
        </div>
      )}
    </div>
  );
}
