import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import PrivateRoute from "./components/PrivateRoute";
import LoginPage from "./pages/LoginPage/LoginPage";
import TeacherPage from "./pages/TeacherPage/TeacherPage";
import StudentPage from "./pages/StudentPage/StudentPage";
import DirectorPage from "./pages/DirectorPage/DirectorPage";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
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

          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
