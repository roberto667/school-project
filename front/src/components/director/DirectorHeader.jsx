import { AnimatePresence, motion } from "framer-motion";
import { ArrowLeft } from "lucide-react";

export default function DirectorHeader({ activeAction, firstName, goBack }) {
  return (
    <header className="p-6">
      <AnimatePresence mode="wait">
        {!activeAction ? (
          <motion.div
            key="welcome"
            initial={{
              opacity: 0,
              x: -20,
            }}
            animate={{
              opacity: 1,
              x: 0,
            }}
            exit={{
              opacity: 0,
              x: -20,
            }}
          >
            <h1 className="text-2xl font-bold">Área do Diretor</h1>

            <p className="text-gray-500">Bem-vindo, {firstName}!</p>
          </motion.div>
        ) : (
          <motion.button
            key="back"
            onClick={goBack}
            initial={{
              opacity: 0,
              x: -20,
            }}
            animate={{
              opacity: 1,
              x: 0,
            }}
            exit={{
              opacity: 0,
              x: -20,
            }}
            whileHover={{
              x: -4,
            }}
            whileTap={{
              scale: 0.95,
            }}
            className="
              flex
              cursor-pointer
              items-center
              gap-2
              rounded-lg
              bg-gray-100
              px-4
              py-2
              hover:bg-gray-200
            "
          >
            <ArrowLeft size={18} />
            Voltar
          </motion.button>
        )}
      </AnimatePresence>
    </header>
  );
}
