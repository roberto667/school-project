import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";

import SiriOrb from "../ui/siriorb/SiriOrb";

export default function SiriAssistant({ firstName, onOpenChange }) {
  const [open, setOpen] = useState(false);
  const [answer, setAnswer] = useState("");

  function toggleAssistant() {
    setOpen((current) => {
      const newValue = !current;

      onOpenChange?.(newValue);

      return newValue;
    });

    setAnswer("");
  }

  function handleOption(option) {
    switch (option) {
      case "oi":
        setAnswer("Oi!");
        break;

      case "ajuda":
        setAnswer("Claro! Com o que você precisa de ajuda?");
        break;

      default:
        setAnswer("");
    }
  }

  return (
    <div className="flex flex-col items-center gap-6">
      {/* ORB */}
      <div onClick={toggleAssistant}>
        <SiriOrb
          size="100px"
          animationDuration={10}
          text={
            open ? "" : `Olá, ${firstName}!\nPrecisa de ajuda? Clique em mim!`
          }
          className="
            cursor-pointer
            transition-transform
            duration-300
            ease-out
            hover:scale-150
          "
          colors={{
            c1: "#ff0080",
            c2: "#00ffff",
            c3: "#8000ff",
          }}
        />
      </div>

      {/* ASSISTENTE */}
      <AnimatePresence>
        {open && (
          <motion.div
            initial={{
              opacity: 0,
              y: 30,
              scale: 0.95,
            }}
            animate={{
              opacity: 1,
              y: 0,
              scale: 1,
            }}
            exit={{
              opacity: 0,
              y: 30,
              scale: 0.95,
            }}
            transition={{
              duration: 0.3,
              ease: "easeOut",
            }}
            className="
              flex
              max-w-md
              flex-col
              items-center
              gap-5
              text-center
            "
          >
            <p className="text-lg font-medium text-gray-700">
              Sou uma IA não muito avançada, mas posso tirar essas dúvidas.
            </p>

            {/* BOTÕES */}
            <div className="flex flex-wrap justify-center gap-3">
              <button
                onClick={() => handleOption("oi")}
                className="
                  cursor-pointer
                  rounded-lg
                  bg-gray-100
                  px-5
                  py-2
                  transition
                  hover:scale-105
                  hover:bg-gray-200
                "
              >
                Oi
              </button>

              <button
                onClick={() => handleOption("ajuda")}
                className="
                  cursor-pointer
                  rounded-lg
                  bg-gray-100
                  px-5
                  py-2
                  transition
                  hover:scale-105
                  hover:bg-gray-200
                "
              >
                Ajuda
              </button>

              <button
                onClick={toggleAssistant}
                className="
                  cursor-pointer
                  rounded-lg
                  bg-gray-100
                  px-5
                  py-2
                  transition
                  hover:scale-105
                  hover:bg-gray-200
                "
              >
                Fechar
              </button>
            </div>

            {/* RESPOSTA */}
            <AnimatePresence mode="wait">
              {answer && (
                <motion.p
                  key={answer}
                  initial={{
                    opacity: 0,
                    y: 15,
                  }}
                  animate={{
                    opacity: 1,
                    y: 0,
                  }}
                  exit={{
                    opacity: 0,
                    y: -15,
                  }}
                  transition={{
                    duration: 0.25,
                  }}
                  className="
                    rounded-xl
                    bg-gray-100
                    px-5
                    py-3
                    font-medium
                    text-gray-800
                  "
                >
                  {answer}
                </motion.p>
              )}
            </AnimatePresence>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
