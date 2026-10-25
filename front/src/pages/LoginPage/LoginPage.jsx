// Autentica o usuário, persiste a sessão e redireciona conforme o papel.
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";

import { login } from "../../Services/api";
import { useAuth } from "../../context/useAuth";

const colors = ["#facc15", "#22c55e", "#3b82f6", "#f472b6", "#f97316"];

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const [success, setSuccess] = useState(false);
  const [particles, setParticles] = useState([]);

  const { signIn } = useAuth();
  const navigate = useNavigate();

  function createConfetti() {
    const newParticles = Array.from({ length: 30 }).map((_, i) => ({
      id: Date.now() + i,

      x: (Math.random() - 0.5) * 300,
      y: -(Math.random() * 250 + 50),

      rotate: Math.random() * 360,
      exitRotate: Math.random() * 360,

      color: colors[Math.floor(Math.random() * colors.length)],
    }));

    setParticles(newParticles);

    setTimeout(() => {
      setParticles([]);
    }, 1000);
  }

  async function handleSubmit(e) {
    e.preventDefault();

    setError("");
    setLoading(true);

    try {
      const data = await login(email, password);

      const validRoles = ["DIRECTOR", "TEACHER", "STUDENT"];

      if (!validRoles.includes(data.role)) {
        throw new Error("Usuário sem papel definido.");
      }

      signIn(data);

      // Login realizado
      setSuccess(true);

      // Confete
      createConfetti();

      /*
       * Pequeno delay para que a animação de
       * sucesso apareça antes do redirecionamento.
       */
      setTimeout(() => {
        switch (data.role) {
          case "DIRECTOR":
            navigate("/director", {
              replace: true,
            });
            break;

          case "TEACHER":
            navigate("/teacher", {
              replace: true,
            });
            break;

          case "STUDENT":
            navigate("/student", {
              replace: true,
            });
            break;

          default:
            break;
        }
      }, 900);
    } catch (err) {
      setError(err.message || "Erro ao realizar login.");

      setSuccess(false);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div
      className="
        relative
        flex
        min-h-screen
        items-center
        justify-center
        overflow-hidden
        black
        p-4
      "
    >
      {/* CONFETE */}

      <AnimatePresence>
        {particles.map((particle) => (
          <motion.div
            key={particle.id}
            className="
              pointer-events-none
              absolute
              z-50
              h-3
              w-3
              rounded-full
            "
            style={{
              backgroundColor: particle.color,
            }}
            initial={{
              x: 0,
              y: 0,
              scale: 1,
              opacity: 1,
              rotate: particle.rotate,
            }}
            animate={{
              x: particle.x,
              y: particle.y,

              scale: 0,
              opacity: 0,

              rotate: particle.rotate + particle.exitRotate,
            }}
            transition={{
              duration: 1,
              ease: "easeOut",
            }}
          />
        ))}
      </AnimatePresence>

      {/* CARD */}

      <motion.form
        onSubmit={handleSubmit}
        initial={{
          scale: 0.95,
          opacity: 0,
          y: 20,
        }}
        animate={{
          scale: 1,
          opacity: 1,
          y: 0,
        }}
        transition={{
          duration: 0.4,
        }}
        className="
          relative
          z-10
          flex
          w-full
          max-w-md
          flex-col
          gap-6
          rounded-2xl
          bg-white
          p-8
          shadow-2xl
        "
      >
        {/* TÍTULO */}

        <motion.h1
          key={success ? "success" : "login"}
          initial={{
            opacity: 0,
            y: -10,
          }}
          animate={{
            opacity: 1,
            y: 0,
          }}
          className="
            text-center
            text-3xl
            font-bold
            text-black
          "
        >
          {success ? "Bem-vindo!" : "Entrar"}
        </motion.h1>

        {/* ERRO */}

        <AnimatePresence>
          {error && (
            <motion.div
              initial={{
                opacity: 0,
                y: -10,
              }}
              animate={{
                opacity: 1,
                y: 0,
              }}
              exit={{
                opacity: 0,
                y: -10,
              }}
              className="
                rounded-lg
                border
                border-red-200
                bg-red-50
                px-4
                py-3
                text-sm
                text-red-600
              "
            >
              {error}
            </motion.div>
          )}
        </AnimatePresence>

        {/* CAMPOS */}

        <div className="flex flex-col gap-5">
          {/* EMAIL */}

          <div className="flex flex-col gap-2">
            <label
              htmlFor="email"
              className="
                text-sm
                font-medium
                text-gray-700
              "
            >
              Email
            </label>

            <input
              id="email"
              type="email"
              placeholder="voce@exemplo.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              disabled={loading || success}
              className="
                h-11
                w-full
                rounded-lg
                border
                border-gray-300
                bg-white
                px-3
                text-sm
                outline-none
                transition-all
                duration-200

                placeholder:text-gray-400

                hover:scale-[1.02]
                hover:border-gray-400

                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/20

                disabled:cursor-not-allowed
                disabled:opacity-60
              "
            />
          </div>

          {/* SENHA */}

          <div className="flex flex-col gap-2">
            <label
              htmlFor="password"
              className="
                text-sm
                font-medium
                text-gray-700
              "
            >
              Senha
            </label>

            <input
              id="password"
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              disabled={loading || success}
              className="
                h-11
                w-full
                rounded-lg
                border
                border-gray-300
                bg-white
                px-3
                text-sm
                outline-none
                transition-all
                duration-200

                placeholder:text-gray-400

                hover:scale-[1.02]
                hover:border-gray-400

                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/20

                disabled:cursor-not-allowed
                disabled:opacity-60
              "
            />
          </div>
        </div>

        {/* BOTÃO */}

        <motion.button
          type="submit"
          disabled={loading || success}
          whileHover={
            loading || success
              ? {}
              : {
                  scale: 1.05,
                }
          }
          whileTap={
            loading || success
              ? {}
              : {
                  scale: 0.97,
                }
          }
          className="
            mt-2
            h-11
            w-full
            rounded-lg
            bg-gray-900
            font-medium
            text-white
            transition-colors

            hover:bg-gray-800

            disabled:cursor-not-allowed
            disabled:opacity-60
          "
        >
          {success ? "Login realizado!" : loading ? "Entrando..." : "Entrar"}
        </motion.button>
      </motion.form>
    </div>
  );
}
