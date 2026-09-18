const API_URL = import.meta.env.VITE_API_URL;

export async function login(email, password) {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    const error = await response.json().catch(() => null);
    throw new Error(error?.message || "Email ou senha inválidos");
  }

  return response.json();
}

// NOVO: fetch autenticado, usado em qualquer chamada depois do login
export async function authFetch(path, options = {}) {
  const stored = localStorage.getItem("auth");
  const token = stored ? JSON.parse(stored).token : null;

  const response = await fetch(`${API_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...options.headers,
    },
  });

  if (response.status === 401 || response.status === 403) {
    localStorage.removeItem("auth");
    window.location.href = "/login";
    throw new Error("Sessão expirada, faça login novamente");
  }

  return response;
}
