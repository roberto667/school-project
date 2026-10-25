// Define as rotas públicas e as áreas protegidas do sistema escolar.
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./components/PrivateRoute";
import ErrorPage from "./components/ui/error_page/ErrorPage";
import LoginPage from "./pages/LoginPage/LoginPage";
import TeacherPage from "./pages/TeacherPage/TeacherPage";
import StudentPage from "./pages/StudentPage/StudentPage";
import DirectorPage from "./pages/DirectorPage/DirectorPage";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/403" element={<ErrorPage status={403} />} />

          <Route path="/500" element={<ErrorPage status={500} />} />

          <Route path="/503" element={<ErrorPage status={503} />} />

          <Route path="/login" element={<LoginPage />} />

          <Route
            path="/teacher"
            element={
              <PrivateRoute allowedRole="TEACHER">
                <TeacherPage />
              </PrivateRoute>
            }
          />

          <Route
            path="/student"
            element={
              <PrivateRoute allowedRole="STUDENT">
                <StudentPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/director"
            element={
              <PrivateRoute allowedRole="DIRECTOR">
                <DirectorPage />
              </PrivateRoute>
            }
          />

          <Route path="*" element={<ErrorPage status={404} />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
