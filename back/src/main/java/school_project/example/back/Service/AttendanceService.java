package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.Attendance.AttendanceRequestDTO;
import school_project.example.back.DTO.school.Attendance.AttendanceResponseDTO;
import school_project.example.back.Entity.enums.AttendanceStatus;

public interface AttendanceService {

    AttendanceResponseDTO createAttendance(
            AttendanceRequestDTO dto);

    AttendanceResponseDTO getAttendance(Long attendanceId);

    List<AttendanceResponseDTO> getAllAttendances();

    List<AttendanceResponseDTO> getAttendancesByEnrollment(
            Long enrollmentId);

    List<AttendanceResponseDTO> getAttendancesByTeachingAssignment(
            Long teachingAssignmentId);

    List<AttendanceResponseDTO> getAttendancesByEnrollmentAndTeachingAssignment(
            Long enrollmentId,
            Long teachingAssignmentId);

    long countByEnrollmentAndStatus(
            Long enrollmentId,
            AttendanceStatus status);

    long countByEnrollmentAndTeachingAssignmentAndStatus(
            Long enrollmentId,
            Long teachingAssignmentId,
            AttendanceStatus status);

    AttendanceResponseDTO updateAttendance(
            Long attendanceId,
            AttendanceRequestDTO dto);

    void deleteAttendance(Long attendanceId);
}