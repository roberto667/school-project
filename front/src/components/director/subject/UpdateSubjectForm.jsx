import { useState } from "react";

import {
  getSubjectByName,
  updateSubject,
} from "../../../Services/subjectService";

import { formStyle, inputStyle, labelStyle, buttonStyle } from "../formStyle";

export default function UpdateSubjectForm({ setFeedback }) {
  const [nomeBusca, setNomeBusca] = useState("");
  const [nome, setNome] = useState("");
  const [subject, setSubject] = useState(null);

  const [loadingBusca, setLoadingBusca] = useState(false);
  const [loadingUpdate, setLoadingUpdate] = useState(false);

  async function handleSearch() {
    const nomeLimpo = nomeBusca.trim();

    if (!nomeLimpo) {
      setFeedback("Informe o nome da matéria.");
      return;
    }

    setLoadingBusca(true);
    setFeedback("");
    setSubject(null);
    setNome("");

    try {
      const data = await getSubjectByName(nomeLimpo);

      setSubject(data);
      setNome(data.name);
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setLoadingBusca(false);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!subject) return;

    const nomeLimpo = nome.trim();

    if (!nomeLimpo) {
      setFeedback("Informe o novo nome da matéria.");
      return;
    }

    setLoadingUpdate(true);
    setFeedback("");

    try {
      const updated = await updateSubject(subject.id, nomeLimpo);

      setSubject(updated);
      setNome(updated.name);
      setNomeBusca(updated.name);

      setFeedback(`Matéria "${updated.name}" atualizada com sucesso!`);
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setLoadingUpdate(false);
    }
  }

  const loading = loadingBusca || loadingUpdate;

  return (
    <div style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Alterar matéria</h2>

      {/* Buscar pelo nome */}
      <div className="flex flex-col gap-2">
        <label htmlFor="subject-search" className={labelStyle}>
          Nome atual da matéria
        </label>

        <input
          id="subject-search"
          type="text"
          value={nomeBusca}
          onChange={(e) => {
            setNomeBusca(e.target.value);
            setSubject(null);
            setNome("");
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

      {/* Atualizar matéria */}
      {subject && (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="border-t border-gray-200 pt-4">
            <h3 className="text-lg font-semibold text-gray-800">
              Dados da matéria
            </h3>

            <p className="mt-1 text-sm text-gray-500">
              Altere o nome e salve as modificações.
            </p>
          </div>

          <div className="flex flex-col gap-2">
            <label htmlFor="subject-name" className={labelStyle}>
              Novo nome da matéria
            </label>

            <input
              id="subject-name"
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              placeholder="Ex.: Matemática Aplicada"
              required
              disabled={loadingUpdate}
              className={inputStyle}
            />
          </div>

          <button
            type="submit"
            disabled={loading || !nome.trim()}
            className={buttonStyle}
          >
            {loadingUpdate ? "Salvando..." : "Salvar alterações"}
          </button>
        </form>
      )}
    </div>
  );
}
