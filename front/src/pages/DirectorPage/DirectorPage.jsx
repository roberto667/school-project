import { useState } from "react";
import { AnimatePresence, motion } from "framer-motion";

import {
  UserPlus,
  UserPen,
  UserMinus,
  GraduationCap,
  BookOpen,
  LogOut,
  User2,
  List,
  School,
  Plus,
  Pen,
  Delete,
} from "lucide-react";

// Coordena as telas e operações administrativas disponíveis ao diretor.
import { useAuth } from "../../context/useAuth";

import SiriAssistant from "../../components/director/SiriAssistant";
import { Dock } from "../../components/ui/docktwo/DockTwo";

import DirectorHeader from "../../components/director/DirectorHeader";
import ManagementArea from "../../components/director/ManagementArea";

import CreateStudentForm from "../../components/director/user/student/CreateStudentForm";
import UpdateStudentForm from "../../components/director/user/student/UpdateStudentForm";

import CreateTeacherForm from "../../components/director/user/teacher/CreateTeacherForm";
import UpdateTeacherForm from "../../components/director/user/teacher/UpdateTeacherForm";

import DeleteForm from "../../components/director/user/DeleteForm";

import CreateSubjectForm from "../../components/director/subject/CreateSubjectForm";
import UpdateSubjectForm from "../../components/director/subject/UpdateSubjectForm";
import DeleteSubjectForm from "../../components/director/subject/DeleteSubjectForm";
import SubjectTable from "../../components/director/subject/SubjectTable";
import StudentTable from "../../components/director/user/student/StudentTable";
import TeacherTable from "../../components/director/user/teacher/TeacherTable";
import SchoolGradeTable from "../../components/director/school_grade/SchoolGradeTable.jsx";
import CreateSchoolGradeForm from "../../components/director/school_grade/CreateSchoolGradeForm.jsx";
import ClassroomTable from "../../components/director/classroom/ClassroomTable.jsx";
import CreateClassroomForm from "../../components/director/classroom/CreateClassroomForm.jsx";

export default function DirectorPage() {
  const { auth, signOut } = useAuth();

  const [activeAction, setActiveAction] = useState(null);
  const [selectedAction, setSelectedAction] = useState("list");
  const [feedback, setFeedback] = useState("");
  const [assistantOpen, setAssistantOpen] = useState(false);

  function openAction(action) {
    setFeedback("");
    setSelectedAction("list");
    setActiveAction(action);
  }

  function goBack() {
    setFeedback("");
    setSelectedAction("list");
    setActiveAction(null);
  }

  function selectAction(action) {
    setFeedback("");
    setSelectedAction(action);
  }

  // =========================
  // DOCK CRUD
  // =========================

  const crudDockItems = [
    {
      icon: List,
      label: "Listar",
      onClick: () => selectAction("list"),
    },
    {
      icon: UserPlus,
      label: "Adicionar",
      onClick: () => selectAction("create"),
    },
    {
      icon: UserPen,
      label: "Alterar",
      onClick: () => selectAction("update"),
    },
    {
      icon: UserMinus,
      label: "Desativar",
      onClick: () => selectAction("delete"),
    },
  ];

  const subjectDockItems = [
    {
      icon: List,
      label: "Listar",
      onClick: () => selectAction("list"),
    },
    {
      icon: Plus,
      label: "Adicionar",
      onClick: () => selectAction("create"),
    },
    {
      icon: Pen,
      label: "Alterar",
      onClick: () => selectAction("update"),
    },
    {
      icon: Delete,
      label: "Excluir",
      onClick: () => selectAction("delete"),
    },
  ];
  const schoolGradeDockItems = [
    {
      title: "Listar",
      icon: List,
      onClick: () => setSelectedAction("list"),
    },
    {
      title: "Adicionar",
      icon: Plus,
      onClick: () => setSelectedAction("create"),
    },
  ];
  const classroomDockItems = [
    {
      title: "Listar",
      icon: List,
      onClick: () => setSelectedAction("list"),
    },
    {
      title: "Adicionar",
      icon: Plus,
      onClick: () => setSelectedAction("create"),
    },
  ];

  // =========================
  // DOCK PRINCIPAL
  // =========================

  const mainDockItems = [
    {
      icon: User2,
      label: "Aluno",
      onClick: () => openAction("student"),
    },
    {
      icon: GraduationCap,
      label: "Professor",
      onClick: () => openAction("teacher"),
    },
    {
      icon: BookOpen,
      label: "Matérias",
      onClick: () => openAction("subject"),
    },
    {
      icon: School,
      label: "Grau escolar",
      onClick: () => openAction("schoolGrade"),
    },
    {
      label: "Turmas",
      icon: School,
      onClick: () => openAction("classroom"),
    },
    {
      icon: LogOut,
      label: "Sair",
      onClick: signOut,
    },
  ];

  // =========================
  // DOCK DO FOOTER
  // =========================

  const footerDockItems =
    activeAction === "subject"
      ? subjectDockItems
      : activeAction === "schoolGrade"
        ? schoolGradeDockItems
        : activeAction === "classroom"
          ? classroomDockItems
          : activeAction
            ? crudDockItems
            : mainDockItems;

  return (
    <div className="flex min-h-screen flex-col">
      {/* HEADER */}
      <AnimatePresence>
        {!assistantOpen && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0, y: -20 }}
          >
            <DirectorHeader
              activeAction={activeAction}
              firstName={auth?.user?.firstName}
              goBack={goBack}
            />
          </motion.div>
        )}
      </AnimatePresence>

      {/* CONTEÚDO PRINCIPAL */}
      <main
        className="
          relative
          flex
          w-full
          flex-1
          flex-col
          items-center
          justify-center
          px-4
          py-8
        "
      >
        <AnimatePresence mode="wait">
          {!activeAction && (
            <motion.div
              key="home"
              initial={{ opacity: 0, x: 40 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -40 }}
            >
              <SiriAssistant
                firstName={auth?.user?.firstName}
                onOpenChange={setAssistantOpen}
              />
            </motion.div>
          )}

          {/* ALUNOS */}
          {activeAction === "student" && (
            <ManagementArea
              key="student"
              title="Alunos"
              dockItems={[]}
              selectedAction={selectedAction}
              ListComponent={StudentTable}
              CreateForm={CreateStudentForm}
              UpdateForm={UpdateStudentForm}
              DeleteForm={DeleteForm}
              deleteLabel="Desativar Aluno"
              deleteEndpoint={(matricula) =>
                `/students/matricula/${encodeURIComponent(matricula)}/deactivate`
              }
              setFeedback={setFeedback}
            />
          )}

          {/* PROFESSORES */}
          {activeAction === "teacher" && (
            <ManagementArea
              key="teacher"
              title="Professores"
              dockItems={[]}
              selectedAction={selectedAction}
              ListComponent={TeacherTable}
              CreateForm={CreateTeacherForm}
              UpdateForm={UpdateTeacherForm}
              DeleteForm={DeleteForm}
              deleteLabel="Desativar Professor"
              deleteEndpoint={(matricula) =>
                `/teachers/matricula/${encodeURIComponent(matricula)}/deactivate`
              }
              setFeedback={setFeedback}
            />
          )}

          {/* MATÉRIAS */}
          {activeAction === "subject" && (
            <ManagementArea
              key="subject"
              title="Matérias"
              dockItems={[]}
              selectedAction={selectedAction}
              CreateForm={CreateSubjectForm}
              UpdateForm={UpdateSubjectForm}
              DeleteForm={DeleteSubjectForm}
              ListComponent={SubjectTable}
              setFeedback={setFeedback}
            />
          )}
        </AnimatePresence>
        {/* ANO ESCOLAR */}
        {activeAction === "schoolGrade" && (
          <ManagementArea
            key="schoolGrade"
            title="Graus escolares"
            dockItems={[]}
            selectedAction={selectedAction}
            ListComponent={SchoolGradeTable}
            CreateForm={CreateSchoolGradeForm}
            setFeedback={setFeedback}
          />
        )}
        {/* TURMAS */}
        {activeAction === "classroom" && (
          <ManagementArea
            key="classroom"
            title="Turmas"
            dockItems={[]}
            selectedAction={selectedAction}
            ListComponent={ClassroomTable}
            CreateForm={CreateClassroomForm}
            setFeedback={setFeedback}
          />
        )}

        {feedback && (
          <p className="mt-4 text-center font-semibold">{feedback}</p>
        )}
      </main>

      {/* FOOTER FIXADO NA BASE DO LAYOUT */}
      <AnimatePresence>
        {!assistantOpen && (
          <motion.footer
            key={activeAction ?? "home"}
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 30 }}
            transition={{ duration: 0.25 }}
            className="
              mt-auto
              flex
              w-full
              shrink-0
              justify-center
              px-4
              pb-6
              pt-3
            "
          >
            <Dock items={footerDockItems} />
          </motion.footer>
        )}
      </AnimatePresence>
    </div>
  );
}
