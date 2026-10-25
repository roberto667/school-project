import { useEffect, useRef } from "react";

const stickFigures = [
  {
    top: "0%",
    src: "https://cdn.21st.dev/assets/mirror/54/54f366bdbf75b7a2d3b9f2264c3ada12aefcaf6e6a467bcecc856ffcd686e52e.svg",
    transform: "rotateZ(-90deg)",
    speedX: 1500,
  },
  {
    top: "10%",
    src: "https://cdn.21st.dev/assets/mirror/7e/7e48603d6fd3fac9720b25b4b6a06d107feea2d21ef8fa0720921808b9808514.svg",
    speedX: 3000,
    speedRotation: 2000,
  },
  {
    top: "20%",
    src: "https://cdn.21st.dev/assets/mirror/4f/4fd3a604a36cc8811c341ef3221010ed11e2563d4add29901922d7464c28c186.svg",
    speedX: 5000,
    speedRotation: 1000,
  },
  {
    top: "25%",
    src: "https://cdn.21st.dev/assets/mirror/54/54f366bdbf75b7a2d3b9f2264c3ada12aefcaf6e6a467bcecc856ffcd686e52e.svg",
    speedX: 2500,
    speedRotation: 1500,
  },
  {
    top: "35%",
    src: "https://cdn.21st.dev/assets/mirror/54/54f366bdbf75b7a2d3b9f2264c3ada12aefcaf6e6a467bcecc856ffcd686e52e.svg",
    speedX: 2000,
    speedRotation: 300,
  },
  {
    bottom: "5%",
    src: "https://cdn.21st.dev/assets/mirror/66/668d66f4c4d1dbc5c421692b4e5ad644c0f11f0327da214bcae21f78816c6b2f.svg",
    speedX: 0,
  },
];

export default function CharactersAnimation() {
  const charactersRef = useRef(null);

  useEffect(() => {
    const container = charactersRef.current;

    if (!container) return;

    let animations = [];

    function createCharacters() {
      animations.forEach((animation) => animation.cancel());
      animations = [];

      container.replaceChildren();

      stickFigures.forEach((figure, index) => {
        const stick = document.createElement("img");

        stick.className = "characters";

        stick.alt = "";
        stick.setAttribute("aria-hidden", "true");
        stick.draggable = false;

        stick.style.position = "absolute";
        stick.style.width = "18%";
        stick.style.height = "18%";
        stick.style.objectFit = "contain";
        stick.style.pointerEvents = "none";

        if (figure.top) {
          stick.style.top = figure.top;
        }

        if (figure.bottom) {
          stick.style.bottom = figure.bottom;
        }

        if (figure.transform) {
          stick.style.transform = figure.transform;
        }

        stick.src = figure.src;

        container.appendChild(stick);

        // O último personagem permanece parado.
        if (index === 5) {
          return;
        }

        // Movimento horizontal.
        const horizontalAnimation = stick.animate(
          [{ left: "100%" }, { left: "-20%" }],
          {
            duration: figure.speedX,
            easing: "linear",
            fill: "forwards",
          },
        );

        animations.push(horizontalAnimation);

        // O primeiro personagem não gira.
        if (index === 0) {
          return;
        }

        if (figure.speedRotation) {
          const rotationAnimation = stick.animate(
            [{ transform: "rotate(0deg)" }, { transform: "rotate(-360deg)" }],
            {
              duration: figure.speedRotation,
              iterations: Infinity,
              easing: "linear",
            },
          );

          animations.push(rotationAnimation);
        }
      });
    }

    createCharacters();

    function handleResize() {
      createCharacters();
    }

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);

      animations.forEach((animation) => animation.cancel());

      container.replaceChildren();
    };
  }, []);

  return (
    <div
      ref={charactersRef}
      aria-hidden="true"
      className="
        pointer-events-none
        absolute
        inset-0
        z-10
        h-full
        w-full
        overflow-hidden
      "
    />
  );
}
