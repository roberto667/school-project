// Lista, edita e exclui os graus escolares cadastrados na API.
import { useEffect, useState } from "react";
import { Pencil, Trash2, Check, X, RefreshCw } from "lucide-react";

import {
  getSchoolGrades,
  updateSchoolGrade,
  deleteSchoolGrade,
} from "../../../Services/schoolGradeService.js";

const graus = [
  {
    value: "FUNDAMENTAL_1",
    label: "Fundamental 1",
  },
  {
    value: "FUNDAMENTAL_2",
    label: "Fundamental 2",
  },
  {
    value: "MEDIO",
    label: "Ensino Médio",
  },
  {
    value: "GRADUACAO",
    label: "Graduação",
  },
];

function formatGrau(grau) {
  const encontrado = graus.find((item) => item.value === grau);

  return encontrado?.label || grau;
}

export default function SchoolGradeTable() {
  const [schoolGrades, setSchoolGrades] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [feedback, setFeedback] = useState("");

  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({
    grau: "",
    ano: "",
  });

  const [savingId, setSavingId] = useState(null);
  const [deletingId, setDeletingId] = useState(null);

  async function fetchSchoolGrades() {
    const data = await getSchoolGrades();

    if (!Array.isArray(data)) {
      throw new Error("A API não retornou uma lista de graus escolares.");
    }

    return data;
  }

  async function loadSchoolGrades() {
    setLoading(true);
    setError("");
    setFeedback("");

    try {
      setSchoolGrades(await fetchSchoolGrades());
    } catch (err) {
      setError(err.message || "Erro ao carregar os graus escolares.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    let active = true;

    fetchSchoolGrades()
      .then((data) => {
        if (active) setSchoolGrades(data);
      })
      .catch((err) => {
        if (active) {
          setError(err.message || "Erro ao carregar os graus escolares.");
        }
      })
      .finally(() => {
        if (active) setLoading(false);
      });

    return () => {
      active = false;
    };
  }, []);

  function startEditing(schoolGrade) {
    setFeedback("");
    setError("");

    setEditingId(schoolGrade.id);

    setEditForm({
      grau: schoolGrade.grau,
      ano: schoolGrade.ano,
    });
  }

  function cancelEditing() {
    setEditingId(null);

    setEditForm({
      grau: "",
      ano: "",
    });
  }

  function handleEditChange(e) {
    const { name, value } = e.target;

    setEditForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  async function handleSave(id) {
    if (!editForm.grau) {
      setError("Selecione o grau.");
      return;
    }

    if (!editForm.ano) {
      setError("Informe o ano.");
      return;
    }

    const ano = Number(editForm.ano);

    if (!Number.isInteger(ano) || ano <= 0) {
      setError("Informe um ano válido.");
      return;
    }

    setSavingId(id);
    setError("");
    setFeedback("");

    try {
      const updated = await updateSchoolGrade(id, editForm.grau, ano);

      setSchoolGrades((prev) =>
        prev.map((item) => (item.id === id ? updated : item)),
      );

      setEditingId(null);

      setFeedback("Grau escolar atualizado com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao atualizar o grau escolar.");
    } finally {
      setSavingId(null);
    }
  }

  async function handleDelete(schoolGrade) {
    const confirmed = window.confirm(
      `Deseja realmente excluir "${formatGrau(
        schoolGrade.grau,
      )} - ${schoolGrade.ano}"?`,
    );

    if (!confirmed) return;

    setDeletingId(schoolGrade.id);
    setError("");
    setFeedback("");

    try {
      await deleteSchoolGrade(schoolGrade.id);

      setSchoolGrades((prev) =>
        prev.filter((item) => item.id !== schoolGrade.id),
      );

      if (editingId === schoolGrade.id) {
        cancelEditing();
      }

      setFeedback("Grau escolar excluído com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao excluir o grau escolar.");
    } finally {
      setDeletingId(null);
    }
  }

  return (
    <div className="w-full max-w-5xl rounded-2xl bg-white p-6 shadow-lg">
      {/* CABEÇALHO */}
      <div className="mb-6 flex flex-wrap items-center justify-between gap-4">
        <div>
          <h2 className="text-xl font-semibold text-gray-800">
            Graus escolares
          </h2>

          <p className="text-sm text-gray-500">
            {schoolGrades.length} registro(s)
          </p>
        </div>

        <button
          type="button"
          onClick={loadSchoolGrades}
          disabled={loading}
          className="
            flex cursor-pointer items-center gap-2
            rounded-lg bg-blue-600 px-4 py-2
            font-medium text-white transition
            hover:bg-blue-700
            disabled:cursor-not-allowed
            disabled:opacity-50
          "
        >
          <RefreshCw size={17} className={loading ? "animate-spin" : ""} />
          Atualizar
        </button>
      </div>

      {/* ERRO */}
      {error && (
        <div className="mb-4 rounded-lg border border-red-200 bg-red-50 p-3">
          <p className="text-sm text-red-600">{error}</p>
        </div>
      )}

      {/* FEEDBACK */}
      {feedback && (
        <div className="mb-4 rounded-lg border border-green-200 bg-green-50 p-3">
          <p className="text-sm text-green-700">{feedback}</p>
        </div>
      )}

      {/* TABELA */}
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className="border-b border-gray-200 bg-gray-100">
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                ID
              </th>

              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                Grau
              </th>

              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                Ano
              </th>

              <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">
                Ações
              </th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={4} className="px-4 py-8 text-center text-gray-500">
                  Carregando graus escolares...
                </td>
              </tr>
            ) : schoolGrades.length === 0 ? (
              <tr>
                <td colSpan={4} className="px-4 py-8 text-center text-gray-500">
                  Nenhum grau escolar cadastrado.
                </td>
              </tr>
            ) : (
              schoolGrades.map((schoolGrade) => {
                const editing = editingId === schoolGrade.id;

                const saving = savingId === schoolGrade.id;

                const deleting = deletingId === schoolGrade.id;

                return (
                  <tr
                    key={schoolGrade.id}
                    className="
                      border-b border-gray-100
                      transition hover:bg-gray-50
                    "
                  >
                    {/* ID */}
                    <td className="px-4 py-3 text-sm text-gray-700">
                      {schoolGrade.id}
                    </td>

                    {/* GRAU */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <select
                          name="grau"
                          value={editForm.grau}
                          onChange={handleEditChange}
                          disabled={saving}
                          className="
                            rounded-lg border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        >
                          <option value="">Selecione</option>

                          {graus.map((grau) => (
                            <option key={grau.value} value={grau.value}>
                              {grau.label}
                            </option>
                          ))}
                        </select>
                      ) : (
                        <span className="text-sm text-gray-700">
                          {formatGrau(schoolGrade.grau)}
                        </span>
                      )}
                    </td>

                    {/* ANO */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <input
                          type="number"
                          name="ano"
                          value={editForm.ano}
                          onChange={handleEditChange}
                          disabled={saving}
                          min="1"
                          className="
                            w-24 rounded-lg
                            border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        />
                      ) : (
                        <span className="text-sm text-gray-700">
                          {schoolGrade.ano}
                        </span>
                      )}
                    </td>

                    {/* AÇÕES */}
                    <td className="px-4 py-3">
                      <div className="flex justify-end gap-2">
                        {editing ? (
                          <>
                            {/* SALVAR */}
                            <button
                              type="button"
                              onClick={() => handleSave(schoolGrade.id)}
                              disabled={saving}
                              title="Salvar"
                              className="
                                cursor-pointer
                                rounded-lg bg-green-600
                                p-2 text-white
                                transition
                                hover:bg-green-700
                                disabled:cursor-not-allowed
                                disabled:opacity-50
                              "
                            >
                              <Check size={18} />
                            </button>

                            {/* CANCELAR */}
                            <button
                              type="button"
                              onClick={cancelEditing}
                              disabled={saving}
                              title="Cancelar"
                              className="
                                cursor-pointer
                                rounded-lg bg-gray-500
                                p-2 text-white
                                transition
                                hover:bg-gray-600
                                disabled:cursor-not-allowed
                                disabled:opacity-50
                              "
                            >
                              <X size={18} />
                            </button>
                          </>
                        ) : (
                          <>
                            {/* ALTERAR */}
                            <button
                              type="button"
                              onClick={() => startEditing(schoolGrade)}
                              disabled={deletingId !== null}
                              title="Alterar"
                              className="
                                cursor-pointer
                                rounded-lg bg-blue-600
                                p-2 text-white
                                transition
                                hover:bg-blue-700
                                disabled:cursor-not-allowed
                                disabled:opacity-50
                              "
                            >
                              <Pencil size={18} />
                            </button>

                            {/* EXCLUIR */}
                            <button
                              type="button"
                              onClick={() => handleDelete(schoolGrade)}
                              disabled={deleting || savingId !== null}
                              title="Excluir"
                              className="
                                cursor-pointer
                                rounded-lg bg-red-600
                                p-2 text-white
                                transition
                                hover:bg-red-700
                                disabled:cursor-not-allowed
                                disabled:opacity-50
                              "
                            >
                              <Trash2 size={18} />
                            </button>
                          </>
                        )}
                      </div>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
