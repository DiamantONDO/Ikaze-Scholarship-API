export const getStudentSession = () => JSON.parse(sessionStorage.getItem("student_session") || "null");
export const getSponsorSession = () => JSON.parse(sessionStorage.getItem("sponsor_session") || "null");
export const getAdminSession = () => JSON.parse(sessionStorage.getItem("admin_session") || "null");