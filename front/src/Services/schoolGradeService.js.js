import { authFetch } from "./api";

async function handleResponse(response) {
  if (!response.ok) {
    const error = await response.json().catch(() => null);

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

export async function createSchoolGrade(grau, ano) {
  const response = await authFetch("/school-grades", {
    method: "POST",
    body: JSON.stringify({
      grau,
      ano,
    }),
  });

  return handleResponse(response);
}

export async function getSchoolGrades() {
  const response = await authFetch("/school-grades");

  return handleResponse(response);
}

export async function getSchoolGrade(id) {
  const response = await authFetch(`/school-grades/${id}`);

  return handleResponse(response);
}

export async function updateSchoolGrade(id, grau, ano) {
  const response = await authFetch(`/school-grades/${id}`, {
    method: "PUT",
    body: JSON.stringify({
      grau,
      ano,
    }),
  });

  return handleResponse(response);
}

export async function deleteSchoolGrade(id) {
  const response = await authFetch(`/school-grades/${id}`, {
    method: "DELETE",
  });

  return handleResponse(response);
}
