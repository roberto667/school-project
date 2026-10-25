import React from "react";
import { motion } from "framer-motion";

const floatingAnimation = {
  initial: { y: 0 },

  animate: {
    y: [-2, 2, -2],

    transition: {
      duration: 4,
      repeat: Infinity,
      ease: "easeInOut",
    },
  },
};

const DockIconButton = React.forwardRef(
  ({ icon: Icon, label, onClick, className = "" }, ref) => {
    return (
      <motion.button
        ref={ref}
        whileHover={{ scale: 1.1, y: -2 }}
        whileTap={{ scale: 0.95 }}
        onClick={onClick}
        className={`
          relative group p-3 rounded-lg
          hover:bg-gray-200 transition-colors
          ${className}
        `}
      >
        <Icon className="w-5 h-5 text-gray-900" />

        <span
          className="
            absolute -top-8 left-1/2 -translate-x-1/2
            px-2 py-1 rounded text-xs
            bg-gray-900 text-white
            opacity-0 group-hover:opacity-100
            transition-opacity whitespace-nowrap
            pointer-events-none
          "
        >
          {label}
        </span>
      </motion.button>
    );
  },
);

DockIconButton.displayName = "DockIconButton";

const Dock = React.forwardRef(({ items, className = "" }, ref) => {
  return (
    <div
      ref={ref}
      className={`w-full flex items-center justify-center p-2 ${className}`}
    >
      <motion.div
        initial="initial"
        animate="animate"
        variants={floatingAnimation}
        className="
            flex items-center gap-1 p-2
            rounded-2xl
            backdrop-blur-lg
            border border-gray-200
            shadow-lg
            bg-white/90
            hover:shadow-xl
            transition-shadow
            duration-300
          "
      >
        {items.map((item) => (
          <DockIconButton key={item.label} {...item} />
        ))}
      </motion.div>
    </div>
  );
});

Dock.displayName = "Dock";

export { Dock };
