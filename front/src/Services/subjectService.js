import { authFetch } from "./api";

async function handleResponse(response) {
  if (!response.ok) {
    const error = await response.json().catch(() => null);

    console.error("Erro na API de matérias:", {
      status: response.status,
      error,
    });

    throw new Error(
      error?.message ||
        error?.detail ||
        `Erro na operação (${response.status}).`,
    );
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

// CADASTRAR MATÉRIA
export async function createSubject(nome) {
  const response = await authFetch("/subjects", {
    method: "POST",
    body: JSON.stringify({
      nome: nome.trim(),
    }),
  });

  return handleResponse(response);
}

// LISTAR MATÉRIAS
export async function getSubjects() {
  const response = await authFetch("/subjects");

  return handleResponse(response);
}

// BUSCAR POR ID
export async function getSubject(id) {
  const response = await authFetch(`/subjects/${id}`);

  return handleResponse(response);
}

// BUSCAR POR NOME
export async function getSubjectByName(nome) {
  const response = await authFetch(
    `/subjects/name/${encodeURIComponent(nome.trim())}`,
  );

  return handleResponse(response);
}

// ATUALIZAR MATÉRIA
export async function updateSubject(id, nome) {
  const response = await authFetch(`/subjects/${id}`, {
    method: "PUT",
    body: JSON.stringify({
      nome: nome.trim(),
    }),
  });

  return handleResponse(response);
}

// EXCLUIR MATÉRIA
export async function deleteSubject(id) {
  const response = await authFetch(`/subjects/${id}`, {
    method: "DELETE",
  });

  return handleResponse(response);
}
