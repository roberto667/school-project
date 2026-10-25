// Renderiza o conteúdo CRUD selecionado na área de gestão do diretor.
import { AnimatePresence, motion } from "framer-motion";
import AnimatedForm from "./AnimatedForm";

const pageVariants = {
  initial: { opacity: 0, x: 40, scale: 0.98 },
  animate: { opacity: 1, x: 0, scale: 1 },
  exit: { opacity: 0, x: -40, scale: 0.98 },
};

export default function ManagementArea({
  title,
  selectedAction,
  CreateForm,
  UpdateForm,
  DeleteForm,
  ListComponent,
  deleteLabel,
  deleteEndpoint,
  setFeedback,
}) {
  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="animate"
      exit="exit"
      transition={{
        duration: 0.3,
        ease: "easeInOut",
      }}
      className="flex w-full flex-col items-center justify-center gap-8"
    >
      <h2 className="text-3xl font-bold">{title}</h2>

      <AnimatePresence mode="wait">
        {selectedAction === "list" && ListComponent && (
          <AnimatedForm key="list">
            <ListComponent />
          </AnimatedForm>
        )}

        {selectedAction === "create" && CreateForm && (
          <AnimatedForm key="create">
            <CreateForm onDone={setFeedback} setFeedback={setFeedback} />
          </AnimatedForm>
        )}

        {selectedAction === "update" && UpdateForm && (
          <AnimatedForm key="update">
            <UpdateForm onDone={setFeedback} setFeedback={setFeedback} />
          </AnimatedForm>
        )}

        {selectedAction === "delete" && DeleteForm && (
          <AnimatedForm key="delete">
            <DeleteForm
              label={deleteLabel}
              endpoint={deleteEndpoint}
              onDone={setFeedback}
              setFeedback={setFeedback}
            />
          </AnimatedForm>
        )}
      </AnimatePresence>
    </motion.div>
  );
}
