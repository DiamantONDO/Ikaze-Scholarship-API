import { defineStore } from "pinia";
import { ref } from "vue";

export const useApplicationStore = defineStore("applications", () => {
  const applications = ref<any[]>(JSON.parse(localStorage.getItem("applications") || "[]"));

  const save = () => localStorage.setItem("applications", JSON.stringify(applications.value));

  const add = (app: any) => { applications.value.push(app); save(); };

  const byStudent = (studentId: number) =>
    applications.value.filter(a => Number(a.studentId) === Number(studentId));

  const updateStatus = (id: number, status: string) => {
    const app = applications.value.find(a => a.id === id);
    if (app) { app.status = status; save(); }
  };

  return { applications, add, byStudent, updateStatus };
});