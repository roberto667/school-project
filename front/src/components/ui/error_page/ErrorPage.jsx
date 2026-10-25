import { useNavigate } from "react-router-dom";

import MessageDisplay from "./MessageDisplay";
import CharactersAnimation from "./CharactersAnimation";
import CircleAnimation from "./CircleAnimation";

const errorMessages = {
  400: {
    title: "Requisição inválida",
    description: "Os dados enviados não puderam ser processados.",
  },
  401: {
    title: "Sessão expirada",
    description: "Entre novamente para continuar.",
  },
  403: {
    title: "Acesso negado",
    description: "Você não tem permissão para acessar esta página.",
  },
  404: {
    title: "Página não encontrada",
    description: "A página solicitada não existe ou foi movida.",
  },
  500: {
    title: "Erro interno",
    description: "Ocorreu um problema inesperado no servidor.",
  },
  503: {
    title: "Serviço indisponível",
    description: "O sistema está temporariamente indisponível.",
  },
};

export default function ErrorPage({ status = 404 }) {
  const navigate = useNavigate();

  const error = errorMessages[status] ?? errorMessages[500];

  return (
    <div className="relative flex min-h-screen w-full items-center justify-center overflow-hidden bg-black">
      <CircleAnimation />
      <CharactersAnimation />

      <MessageDisplay
        status={status}
        title={error.title}
        description={error.description}
        onBack={() => navigate(-1)}
        onHome={() => navigate("/login", { replace: true })}
      />
    </div>
  );
}
