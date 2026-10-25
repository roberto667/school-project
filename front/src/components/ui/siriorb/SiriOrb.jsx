import "./siri-orb.css";
import { TextEffect } from "../texteffect/TextEffect";
export default function SiriOrb({
  size = "192px",
  className = "",
  colors = {},
  animationDuration = 20,
  text,
}) {
  const defaultColors = {
    bg: "transparent",
    c1: "oklch(75% 0.15 350)",
    c2: "oklch(80% 0.12 200)",
    c3: "oklch(78% 0.14 280)",
  };

  const finalColors = {
    ...defaultColors,
    ...colors,
  };

  const sizeValue = parseInt(size.replace("px", ""), 10);

  const blurAmount = Math.max(sizeValue * 0.08, 8);
  const contrastAmount = Math.max(sizeValue * 0.003, 1.8);

  return (
    <div className="flex flex-col items-center gap-5">
      <div
        className={`siri-orb ${className}`}
        style={{
          width: size,
          height: size,

          "--bg": finalColors.bg,
          "--c1": finalColors.c1,
          "--c2": finalColors.c2,
          "--c3": finalColors.c3,

          "--animation-duration": `${animationDuration}s`,
          "--blur-amount": `${blurAmount}px`,
          "--contrast-amount": contrastAmount,
        }}
      />

      {text && (
        <TextEffect
          per="char"
          preset="blur"
          as="p"
          className="
    max-w-sm
    text-center
    text-xl
    font-medium
    text-gray-800
  "
        >
          {text}
        </TextEffect>
      )}
    </div>
  );
}
