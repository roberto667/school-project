import { useState } from "react";

import { createSchoolGrade } from "../../../Services/schoolGradeService.js";

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

export default function CreateSchoolGradeForm({ setFeedback }) {
  const [grau, setGrau] = useState("");
  const [ano, setAno] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    if (!grau) {
      setFeedback("Selecione o grau.");
      return;
    }

    if (!ano) {
      setFeedback("Informe o ano.");
      return;
    }

    const anoNumero = Number(ano);

    if (!Number.isInteger(anoNumero) || anoNumero <= 0) {
      setFeedback("Informe um ano válido.");
      return;
    }

    setLoading(true);
    setFeedback("");

    try {
      await createSchoolGrade(grau, anoNumero);

      setFeedback("Grau escolar cadastrado com sucesso!");

      setGrau("");
      setAno("");
    } catch (error) {
      setFeedback(error.message || "Erro ao cadastrar o grau escolar.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">
        Adicionar grau escolar
      </h2>

      <div className="flex flex-col gap-2">
        <label htmlFor="school-grade-grau" className={labelStyle}>
          Grau
        </label>

        <select
          id="school-grade-grau"
          value={grau}
          onChange={(e) => setGrau(e.target.value)}
          disabled={loading}
          className={inputStyle}
        >
          <option value="">Selecione o grau</option>

          {graus.map((item) => (
            <option key={item.value} value={item.value}>
              {item.label}
            </option>
          ))}
        </select>
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="school-grade-ano" className={labelStyle}>
          Ano
        </label>

        <input
          id="school-grade-ano"
          type="number"
          min="1"
          value={ano}
          onChange={(e) => setAno(e.target.value)}
          placeholder="Ex.: 1"
          disabled={loading}
          className={inputStyle}
        />
      </div>

      <button type="submit" disabled={loading} className={buttonStyle}>
        {loading ? "Cadastrando..." : "Cadastrar grau escolar"}
      </button>
    </form>
  );
}
