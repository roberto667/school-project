import { useEffect, useRef } from "react";

export default function CircleAnimation() {
  const canvasRef = useRef(null);
  const requestIdRef = useRef(null);
  const timerRef = useRef(0);
  const circulosRef = useRef([]);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const context = canvas.getContext("2d");
    if (!context) return;

    let active = true;

    // Inicializa os 300 círculos.
    function initArr() {
      circulosRef.current = [];

      for (let index = 0; index < 300; index++) {
        const randomX =
          Math.random() * (canvas.width * 1.8) + canvas.width * 1.2;

        const randomY =
          Math.random() * (canvas.height * 1.2) - canvas.height * 0.2;

        const size = canvas.width / 1000;

        circulosRef.current.push({
          x: randomX,
          y: randomY,
          size,
        });
      }
    }

    // Desenha e movimenta os círculos.
    function draw() {
      if (!active) return;

      timerRef.current++;

      context.setTransform(1, 0, 0, 1, 0, 0);
      context.clearRect(0, 0, canvas.width, canvas.height);

      const distanceX = canvas.width / 80;
      const growthRate = canvas.width / 1000;

      context.fillStyle = "white";

      circulosRef.current.forEach((circulo) => {
        context.beginPath();

        // Movimento inicial mais rápido.
        if (timerRef.current < 65) {
          circulo.x -= distanceX;
          circulo.size += growthRate;
        }

        // Movimento mais lento.
        if (timerRef.current > 65 && timerRef.current < 500) {
          circulo.x -= distanceX * 0.02;
          circulo.size += growthRate * 0.2;
        }

        context.arc(circulo.x, circulo.y, circulo.size, 0, Math.PI * 2);

        context.fill();
      });

      // Encerra após 500 quadros.
      if (timerRef.current >= 500) {
        requestIdRef.current = null;
        return;
      }

      requestIdRef.current = requestAnimationFrame(draw);
    }

    // Ajusta o canvas e reinicia a animação.
    function initializeAnimation() {
      if (requestIdRef.current !== null) {
        cancelAnimationFrame(requestIdRef.current);
        requestIdRef.current = null;
      }

      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;

      timerRef.current = 0;

      initArr();
      draw();
    }

    initializeAnimation();

    window.addEventListener("resize", initializeAnimation);

    return () => {
      active = false;

      window.removeEventListener("resize", initializeAnimation);

      if (requestIdRef.current !== null) {
        cancelAnimationFrame(requestIdRef.current);
        requestIdRef.current = null;
      }
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      aria-hidden="true"
      className="
        pointer-events-none
        absolute
        inset-0
        z-0
        h-full
        w-full
      "
    />
  );
}
