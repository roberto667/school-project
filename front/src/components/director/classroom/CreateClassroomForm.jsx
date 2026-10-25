import { useEffect, useState } from "react";
import { getSchoolGrades } from "../../../Services/schoolGradeService.js";
import { createClassroom } from "../../../Services/classroomService.js";
import { formStyle, inputStyle, labelStyle, buttonStyle } from "../formStyle";

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
    return "";
  }

  return `${formatGrau(schoolGrade.grau)} - ${schoolGrade.ano}`;
}

export default function CreateClassroomForm({ setFeedback }) {
  const [schoolGrades, setSchoolGrades] = useState([]);

  const [name, setName] = useState("");
  const [schoolGradeId, setSchoolGradeId] = useState("");
  const [academicYear, setAcademicYear] = useState(new Date().getFullYear());

  const [loading, setLoading] = useState(false);
  const [loadingGrades, setLoadingGrades] = useState(true);

  const [error, setError] = useState("");

  useEffect(() => {
    async function loadSchoolGrades() {
      try {
        const data = await getSchoolGrades();

        if (!Array.isArray(data)) {
          throw new Error("A API não retornou uma lista de anos escolares.");
        }

        setSchoolGrades(data);
      } catch (err) {
        setError(err.message || "Erro ao carregar os anos escolares.");
      } finally {
        setLoadingGrades(false);
      }
    }

    loadSchoolGrades();
  }, []);

  async function handleSubmit(e) {
    e.preventDefault();

    const nomeLimpo = name.trim();

    if (!nomeLimpo) {
      setError("Informe o nome da turma.");
      return;
    }

    if (!schoolGradeId) {
      setError("Selecione o ano escolar.");
      return;
    }

    const ano = Number(academicYear);

    if (!Number.isInteger(ano) || ano <= 0) {
      setError("Informe um ano acadêmico válido.");
      return;
    }

    setLoading(true);
    setError("");
    setFeedback("");

    try {
      const classroom = await createClassroom(nomeLimpo, schoolGradeId, ano);

      setFeedback(`Turma "${classroom.name}" cadastrada com sucesso!`);

      setName("");
      setSchoolGradeId("");
      setAcademicYear(new Date().getFullYear());
    } catch (err) {
      setError(err.message || "Erro ao cadastrar a turma.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Adicionar turma</h2>

      {/* NOME */}
      <div className="flex flex-col gap-2">
        <label className={labelStyle}>Nome da turma</label>

        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Ex.: 7A"
          required
          className={inputStyle}
        />
      </div>

      {/* ANO ESCOLAR */}
      <div className="flex flex-col gap-2">
        <label className={labelStyle}>Ano escolar</label>

        <select
          value={schoolGradeId}
          onChange={(e) => setSchoolGradeId(e.target.value)}
          disabled={loadingGrades}
          required
          className={inputStyle}
        >
          <option value="">
            {loadingGrades ? "Carregando..." : "Selecione o ano escolar"}
          </option>

          {schoolGrades.map((schoolGrade) => (
            <option key={schoolGrade.id} value={schoolGrade.id}>
              {formatSchoolGrade(schoolGrade)}
            </option>
          ))}
        </select>
      </div>

      {/* ANO ACADÊMICO */}
      <div className="flex flex-col gap-2">
        <label className={labelStyle}>Ano acadêmico</label>

        <input
          type="number"
          value={academicYear}
          onChange={(e) => setAcademicYear(e.target.value)}
          min="1"
          required
          className={inputStyle}
        />
      </div>

      {error && <p className="text-sm text-red-600">{error}</p>}

      <button
        type="submit"
        disabled={loading || loadingGrades}
        className={buttonStyle}
      >
        {loading ? "Cadastrando..." : "Cadastrar turma"}
      </button>
    </form>
  );
}
