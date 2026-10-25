// Busca e apresenta os alunos cadastrados para a gestão do diretor.
import { useEffect, useMemo, useState } from "react";
import { RefreshCw } from "lucide-react";

import { authFetch } from "../../../../Services/api";
import DataTable from "../../DataTable";

export default function StudentTable() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  async function fetchStudents(signal) {
    const response = await authFetch("/students", {
      method: "GET",
      signal,
    });

    if (!response.ok) {
      const data = await response.json().catch(() => null);
      throw new Error(
        data?.message || `Erro ao carregar alunos (${response.status}).`,
      );
    }

    const data = await response.json();

    if (!Array.isArray(data)) {
      throw new Error("A API não retornou uma lista de alunos.");
    }

    return data;
  }

  async function loadStudents(signal) {
    setLoading(true);
    setError("");

    try {
      setStudents(await fetchStudents(signal));
    } catch (err) {
      if (!signal?.aborted) {
        setError(err.message || "Erro ao carregar alunos.");
      }
    } finally {
      if (!signal?.aborted) setLoading(false);
    }
  }

  useEffect(() => {
    const controller = new AbortController();

    fetchStudents(controller.signal)
      .then((data) => {
        if (!controller.signal.aborted) setStudents(data);
      })
      .catch((err) => {
        if (!controller.signal.aborted) {
          setError(err.message || "Erro ao carregar alunos.");
        }
      })
      .finally(() => {
        if (!controller.signal.aborted) setLoading(false);
      });

    return () => controller.abort();
  }, []);

  const columns = useMemo(
    () => [
      {
        key: "matricula",
        header: "Matrícula",
        accessor: (student) => student.matricula,
      },
      {
        key: "firstName",
        header: "Nome",
        accessor: (student) => student.user?.firstName,
      },
      {
        key: "lastName",
        header: "Sobrenome",
        accessor: (student) => student.user?.lastName,
      },
      {
        key: "email",
        header: "E-mail",
        accessor: (student) => student.user?.email,
      },
    ],
    [],
  );

  return (
    <div className="flex w-full max-w-5xl flex-col gap-4">
      <div className="flex justify-end">
        <button
          type="button"
          onClick={() => loadStudents()}
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
          <RefreshCw size={18} className={loading ? "animate-spin" : ""} />
          Atualizar lista
        </button>
      </div>

      {error && (
        <div
          role="alert"
          className="rounded-lg border border-red-200 bg-red-50 p-3"
        >
          <p className="text-sm text-red-600">{error}</p>
        </div>
      )}

      <DataTable
        title="Alunos cadastrados"
        columns={columns}
        data={students}
        loading={loading}
      />
    </div>
  );
}
