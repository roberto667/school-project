// Lista, edita e remove matérias por meio dos serviços da API.
import { useEffect, useState } from "react";
import { Pencil, Trash2, Check, X, RefreshCw } from "lucide-react";
import {
  getSubjects,
  updateSubject,
  deleteSubject,
} from "../../../Services/subjectService";

export default function SubjectTable() {
  const [subjects, setSubjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [feedback, setFeedback] = useState("");

  const [editingId, setEditingId] = useState(null);
  const [editNome, setEditNome] = useState("");

  const [savingId, setSavingId] = useState(null);
  const [deletingId, setDeletingId] = useState(null);

  async function fetchSubjects() {
    const data = await getSubjects();

    if (!Array.isArray(data)) {
      throw new Error("A API não retornou uma lista de matérias.");
    }

    return data;
  }

  async function loadSubjects() {
    setLoading(true);
    setError("");
    setFeedback("");

    try {
      setSubjects(await fetchSubjects());
    } catch (err) {
      setError(err.message || "Erro ao carregar as matérias.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    let active = true;

    fetchSubjects()
      .then((data) => {
        if (active) setSubjects(data);
      })
      .catch((err) => {
        if (active) {
          setError(err.message || "Erro ao carregar as matérias.");
        }
      })
      .finally(() => {
        if (active) setLoading(false);
      });

    return () => {
      active = false;
    };
  }, []);

  function startEditing(subject) {
    setError("");
    setFeedback("");

    setEditingId(subject.id);
    setEditNome(subject.nome);
  }

  function cancelEditing() {
    setEditingId(null);
    setEditNome("");
  }

  async function handleSave(id) {
    const nome = editNome.trim();

    if (!nome) {
      setError("Informe o nome da matéria.");
      return;
    }

    setSavingId(id);
    setError("");
    setFeedback("");

    try {
      const updated = await updateSubject(id, nome);

      setSubjects((prev) =>
        prev.map((subject) => (subject.id === id ? updated : subject)),
      );

      setEditingId(null);
      setEditNome("");

      setFeedback("Matéria atualizada com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao atualizar a matéria.");
    } finally {
      setSavingId(null);
    }
  }

  async function handleDelete(subject) {
    const confirmed = window.confirm(
      `Deseja realmente excluir a matéria "${subject.nome}"?`,
    );

    if (!confirmed) return;

    setDeletingId(subject.id);
    setError("");
    setFeedback("");

    try {
      await deleteSubject(subject.id);

      setSubjects((prev) => prev.filter((item) => item.id !== subject.id));

      if (editingId === subject.id) {
        cancelEditing();
      }

      setFeedback("Matéria excluída com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao excluir a matéria.");
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
            Matérias cadastradas
          </h2>

          <p className="text-sm text-gray-500">{subjects.length} matéria(s)</p>
        </div>

        <button
          type="button"
          onClick={loadSubjects}
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
                Nome da matéria
              </th>

              <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">
                Ações
              </th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={3} className="px-4 py-8 text-center text-gray-500">
                  Carregando matérias...
                </td>
              </tr>
            ) : subjects.length === 0 ? (
              <tr>
                <td colSpan={3} className="px-4 py-8 text-center text-gray-500">
                  Nenhuma matéria cadastrada.
                </td>
              </tr>
            ) : (
              subjects.map((subject) => {
                const editing = editingId === subject.id;
                const saving = savingId === subject.id;
                const deleting = deletingId === subject.id;

                return (
                  <tr
                    key={subject.id}
                    className="
                      border-b border-gray-100
                      transition hover:bg-gray-50
                    "
                  >
                    {/* ID */}
                    <td className="px-4 py-3 text-sm text-gray-700">
                      {subject.id}
                    </td>

                    {/* NOME */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <input
                          type="text"
                          value={editNome}
                          onChange={(e) => setEditNome(e.target.value)}
                          disabled={saving}
                          autoFocus
                          className="
                            w-full max-w-md rounded-lg
                            border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        />
                      ) : (
                        <span className="text-sm text-gray-700">
                          {subject.nome}
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
                              onClick={() => handleSave(subject.id)}
                              disabled={saving}
                              title="Salvar"
                              className="
                                cursor-pointer
                                rounded-lg bg-green-600
                                p-2 text-white transition
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
                                p-2 text-white transition
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
                              onClick={() => startEditing(subject)}
                              disabled={deletingId !== null}
                              title="Alterar"
                              className="
                                cursor-pointer
                                rounded-lg bg-blue-600
                                p-2 text-white transition
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
                              onClick={() => handleDelete(subject)}
                              disabled={deleting || savingId !== null}
                              title="Excluir"
                              className="
                                cursor-pointer
                                rounded-lg bg-red-600
                                p-2 text-white transition
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
