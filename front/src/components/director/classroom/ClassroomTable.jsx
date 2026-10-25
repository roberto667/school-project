// Exibe, edita e exclui turmas, mantendo séries e dados sincronizados com a API.
import { useEffect, useState } from "react";
import { Pencil, Trash2, Check, X, RefreshCw } from "lucide-react";

import {
  getClassrooms,
  updateClassroom,
  deleteClassroom,
} from "../../../Services/classroomService";

import { getSchoolGrades } from "../../../Services/schoolGradeService.js";

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

function formatSchoolGrade(schoolGrade) {
  if (!schoolGrade) {
    return "Não informado";
  }

  return `${formatGrau(schoolGrade.grau)} - ${schoolGrade.ano}`;
}

export default function ClassroomTable() {
  const [classrooms, setClassrooms] = useState([]);
  const [schoolGrades, setSchoolGrades] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [feedback, setFeedback] = useState("");

  const [editingId, setEditingId] = useState(null);

  const [editForm, setEditForm] = useState({
    name: "",
    schoolGradeId: "",
    academicYear: "",
  });

  const [savingId, setSavingId] = useState(null);
  const [deletingId, setDeletingId] = useState(null);

  async function fetchData() {
    const [classroomsData, schoolGradesData] = await Promise.all([
      getClassrooms(),
      getSchoolGrades(),
    ]);

    if (!Array.isArray(classroomsData)) {
      throw new Error("A API não retornou uma lista de turmas.");
    }

    if (!Array.isArray(schoolGradesData)) {
      throw new Error("A API não retornou uma lista de anos escolares.");
    }

    return { classroomsData, schoolGradesData };
  }

  async function loadData() {
    setLoading(true);
    setError("");
    setFeedback("");

    try {
      const { classroomsData, schoolGradesData } = await fetchData();

      setClassrooms(classroomsData);
      setSchoolGrades(schoolGradesData);
    } catch (err) {
      setError(err.message || "Erro ao carregar as turmas.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    let active = true;

    fetchData()
      .then(({ classroomsData, schoolGradesData }) => {
        if (active) {
          setClassrooms(classroomsData);
          setSchoolGrades(schoolGradesData);
        }
      })
      .catch((err) => {
        if (active) {
          setError(err.message || "Erro ao carregar as turmas.");
        }
      })
      .finally(() => {
        if (active) setLoading(false);
      });

    return () => {
      active = false;
    };
  }, []);

  function startEditing(classroom) {
    setError("");
    setFeedback("");

    setEditingId(classroom.id);

    setEditForm({
      name: classroom.name,
      schoolGradeId: classroom.schoolGrade?.id?.toString() || "",
      academicYear: classroom.academicYear,
    });
  }

  function cancelEditing() {
    setEditingId(null);

    setEditForm({
      name: "",
      schoolGradeId: "",
      academicYear: "",
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
    const nome = editForm.name.trim();

    if (!nome) {
      setError("Informe o nome da turma.");
      return;
    }

    if (!editForm.schoolGradeId) {
      setError("Selecione o ano escolar.");
      return;
    }

    const ano = Number(editForm.academicYear);

    if (!Number.isInteger(ano) || ano <= 0) {
      setError("Informe um ano acadêmico válido.");
      return;
    }

    setSavingId(id);
    setError("");
    setFeedback("");

    try {
      const updated = await updateClassroom(
        id,
        nome,
        editForm.schoolGradeId,
        ano,
      );

      setClassrooms((prev) =>
        prev.map((item) => (item.id === id ? updated : item)),
      );

      setEditingId(null);

      setFeedback("Turma atualizada com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao atualizar a turma.");
    } finally {
      setSavingId(null);
    }
  }

  async function handleDelete(classroom) {
    const confirmed = window.confirm(
      `Deseja realmente excluir a turma "${classroom.name}"?`,
    );

    if (!confirmed) {
      return;
    }

    setDeletingId(classroom.id);
    setError("");
    setFeedback("");

    try {
      await deleteClassroom(classroom.id);

      setClassrooms((prev) => prev.filter((item) => item.id !== classroom.id));

      if (editingId === classroom.id) {
        cancelEditing();
      }

      setFeedback("Turma excluída com sucesso!");
    } catch (err) {
      setError(err.message || "Erro ao excluir a turma.");
    } finally {
      setDeletingId(null);
    }
  }

  return (
    <div className="w-full max-w-6xl rounded-2xl bg-white p-6 shadow-lg">
      {/* CABEÇALHO */}
      <div className="mb-6 flex flex-wrap items-center justify-between gap-4">
        <div>
          <h2 className="text-xl font-semibold text-gray-800">
            Turmas cadastradas
          </h2>

          <p className="text-sm text-gray-500">{classrooms.length} turma(s)</p>
        </div>

        <button
          type="button"
          onClick={loadData}
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
                Turma
              </th>

              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                Ano escolar
              </th>

              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                Ano acadêmico
              </th>

              <th className="px-4 py-3 text-right text-sm font-semibold text-gray-700">
                Ações
              </th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={5} className="px-4 py-8 text-center text-gray-500">
                  Carregando turmas...
                </td>
              </tr>
            ) : classrooms.length === 0 ? (
              <tr>
                <td colSpan={5} className="px-4 py-8 text-center text-gray-500">
                  Nenhuma turma cadastrada.
                </td>
              </tr>
            ) : (
              classrooms.map((classroom) => {
                const editing = editingId === classroom.id;

                const saving = savingId === classroom.id;

                const deleting = deletingId === classroom.id;

                return (
                  <tr
                    key={classroom.id}
                    className="
                      border-b border-gray-100
                      transition hover:bg-gray-50
                    "
                  >
                    {/* ID */}
                    <td className="px-4 py-3 text-sm text-gray-700">
                      {classroom.id}
                    </td>

                    {/* NOME */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <input
                          type="text"
                          name="name"
                          value={editForm.name}
                          onChange={handleEditChange}
                          disabled={saving}
                          className="
                            w-full max-w-xs rounded-lg
                            border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        />
                      ) : (
                        <span className="text-sm font-medium text-gray-700">
                          {classroom.name}
                        </span>
                      )}
                    </td>

                    {/* ANO ESCOLAR */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <select
                          name="schoolGradeId"
                          value={editForm.schoolGradeId}
                          onChange={handleEditChange}
                          disabled={saving}
                          className="
                            rounded-lg border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        >
                          <option value="">Selecione</option>

                          {schoolGrades.map((schoolGrade) => (
                            <option key={schoolGrade.id} value={schoolGrade.id}>
                              {formatSchoolGrade(schoolGrade)}
                            </option>
                          ))}
                        </select>
                      ) : (
                        <span className="text-sm text-gray-700">
                          {formatSchoolGrade(classroom.schoolGrade)}
                        </span>
                      )}
                    </td>

                    {/* ANO ACADÊMICO */}
                    <td className="px-4 py-3">
                      {editing ? (
                        <input
                          type="number"
                          name="academicYear"
                          value={editForm.academicYear}
                          onChange={handleEditChange}
                          disabled={saving}
                          min="1"
                          className="
                            w-28 rounded-lg
                            border border-gray-300
                            p-2 text-sm outline-none
                            focus:border-blue-500
                          "
                        />
                      ) : (
                        <span className="text-sm text-gray-700">
                          {classroom.academicYear}
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
                              onClick={() => handleSave(classroom.id)}
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
                              onClick={() => startEditing(classroom)}
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
                              onClick={() => handleDelete(classroom)}
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
