import { defineStore } from "pinia";
import { ref } from "vue";

export const usePaymentStore = defineStore("payments", () => {
  const payments = ref<any[]>(JSON.parse(localStorage.getItem("payments") || "[]"));

  const save = () => localStorage.setItem("payments", JSON.stringify(payments.value));

  const add = (p: any) => { payments.value.push(p); save(); };

  const byStudent = (studentId: number) =>
    payments.value.filter(p => Number(p.studentId) === Number(studentId));

  const bySponsor = (sponsorId: number) =>
    payments.value.filter(p => Number(p.sponsorId) === Number(sponsorId));

  return { payments, add, byStudent, bySponsor };
});