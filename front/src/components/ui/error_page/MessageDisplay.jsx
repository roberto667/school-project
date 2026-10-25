import { useEffect, useState } from "react";
import { ArrowLeft, House } from "lucide-react";

export default function MessageDisplay({
  status,
  title,
  description,
  onBack,
  onHome,
}) {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => setVisible(true), 1200);
    return () => clearTimeout(timer);
  }, []);

  return (
    <div className="pointer-events-none absolute inset-0 z-20 flex items-center justify-center">
      <div
        className={`
          pointer-events-auto flex w-[90%] max-w-xl
          flex-col items-center text-center text-black
          transition-opacity duration-500
          ${visible ? "opacity-100" : "opacity-0"}
        `}
      >
        <h1 className="text-3xl font-semibold">{title}</h1>

        <span className="my-3 text-7xl font-bold">{status}</span>

        <p className="max-w-md text-sm">{description}</p>

        <div className="mt-8 flex flex-wrap justify-center gap-4">
          <button
            onClick={onBack}
            className="flex cursor-pointer items-center gap-2 border-2 border-black px-5 py-2 transition hover:bg-black hover:text-white"
          >
            <ArrowLeft size={18} />
            Voltar
          </button>

          <button
            onClick={onHome}
            className="flex cursor-pointer items-center gap-2 bg-black px-5 py-2 text-white transition hover:bg-gray-800"
          >
            <House size={18} />
            Início
          </button>
        </div>
      </div>
    </div>
  );
}
