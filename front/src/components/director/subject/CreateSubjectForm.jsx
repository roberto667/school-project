import { useState } from "react";
import { createSubject } from "../../../Services/subjectService";
import { formStyle, inputStyle, labelStyle, buttonStyle } from "../formStyle";

export default function CreateSubjectForm({ setFeedback }) {
  const [nome, setNome] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    const nomeLimpo = nome.trim();

    if (!nomeLimpo) {
      setFeedback("Informe o nome da matéria.");
      return;
    }

    setLoading(true);
    setFeedback("");

    try {
      const subject = await createSubject(nomeLimpo);

      setFeedback(`Matéria "${subject.nome}" cadastrada com sucesso!`);

      setNome("");
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} style={formStyle}>
      <h2 className="text-xl font-semibold text-gray-800">Adicionar matéria</h2>

      <div className="flex flex-col gap-2">
        <label className={labelStyle}>Nome da matéria</label>

        <input
          type="text"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          placeholder="Ex.: Matemática"
          required
          className={inputStyle}
        />
      </div>

      <button type="submit" disabled={loading} className={buttonStyle}>
        {loading ? "Cadastrando..." : "Cadastrar matéria"}
      </button>
    </form>
  );
}
