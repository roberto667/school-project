
const API_URL = import.meta.env.VITE_API_URL;

// Trata erros HTTP que exigem redirecionamento.
function handleHttpError(response) {
  const status = response.status;

  // 401 — Sessão expirada ou token inválido
  if (status === 401) {
    localStorage.removeItem("auth");

    window.location.replace("/login");

    throw new Error(
      "Sessão expirada. Faça login novamente."
    );
  }

  // 403 — Acesso negado
  if (status === 403) {
    window.location.replace("/403");

    throw new Error(
      "Você não possui permissão para realizar esta operação."
    );
  }

  // 500 — Erro interno do servidor
  if (status === 500) {
    window.location.replace("/500");

    throw new Error(
      "Ocorreu um erro interno no servidor."
    );
  }

  // 503 — Serviço indisponível
  if (status === 503) {
    window.location.replace("/503");

    throw new Error(
      "O serviço está temporariamente indisponível."
    );
  }
}

// LOGIN
export async function login(email, password) {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",

    headers: {
      "Content-Type": "application/json",
    },

    body: JSON.stringify({
      email,
      password,
    }),
  });

  if (!response.ok) {
    const error = await response.json().catch(() => null);

    // No login, 401 normalmente significa credenciais inválidas.
    // Não redirecionamos para evitar perder o formulário.
    if (response.status !== 401) {
      handleHttpError(response);
    }

    throw new Error(
      error?.message || "Email ou senha inválidos"
    );
  }

  return response.json();
}

// REQUISIÇÕES AUTENTICADAS
export async function authFetch(path, options = {}) {
  const stored = localStorage.getItem("auth");

  let token = null;

  if (stored) {
    try {
      token = JSON.parse(stored).token;
    } catch {
      localStorage.removeItem("auth");
    }
  }

  const response = await fetch(`${API_URL}${path}`, {
    ...options,

    headers: {
      "Content-Type": "application/json",

      ...(token
        ? {
            Authorization: `Bearer ${token}`,
          }
        : {}),

      ...options.headers,
    },
  });

  // Trata erros de autenticação, permissão e servidor.
  handleHttpError(response);

  // Outros erros continuam sendo tratados
  // pelos componentes que fizeram a requisição.
  return response;
}
