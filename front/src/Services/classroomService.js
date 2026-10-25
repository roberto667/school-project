import { authFetch } from "./api";

async function handleResponse(response) {
  if (!response.ok) {
    const error = await response.json().catch(() => null);

    throw new Error(error?.message || `Erro na operação (${response.status}).`);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export async function createClassroom(name, schoolGradeId, academicYear) {
  const response = await authFetch("/classrooms", {
    method: "POST",
    body: JSON.stringify({
      name: name.trim(),
      schoolGradeId: Number(schoolGradeId),
      academicYear: Number(academicYear),
    }),
  });

  return handleResponse(response);
}

export async function getClassrooms() {
  const response = await authFetch("/classrooms");

  return handleResponse(response);
}

export async function getClassroom(id) {
  const response = await authFetch(`/classrooms/${id}`);

  return handleResponse(response);
}

export async function getClassroomsByYear(academicYear) {
  const response = await authFetch(`/classrooms/year/${academicYear}`);

  return handleResponse(response);
}

export async function getClassroomsBySchoolGrade(schoolGradeId) {
  const response = await authFetch(`/classrooms/school-grade/${schoolGradeId}`);

  return handleResponse(response);
}

export async function searchClassrooms(schoolGradeId, academicYear) {
  const params = new URLSearchParams({
    schoolGradeId: String(schoolGradeId),
    academicYear: String(academicYear),
  });

  const response = await authFetch(`/classrooms/search?${params.toString()}`);

  return handleResponse(response);
}

export async function updateClassroom(id, name, schoolGradeId, academicYear) {
  const response = await authFetch(`/classrooms/${id}`, {
    method: "PUT",
    body: JSON.stringify({
      name: name.trim(),
      schoolGradeId: Number(schoolGradeId),
      academicYear: Number(academicYear),
    }),
  });

  return handleResponse(response);
}

export async function deleteClassroom(id) {
  const response = await authFetch(`/classrooms/${id}`, {
    method: "DELETE",
  });

  return handleResponse(response);
}
