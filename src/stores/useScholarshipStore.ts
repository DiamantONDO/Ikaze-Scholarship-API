import { defineStore } from "pinia";
import { ref } from "vue";

export const useScholarshipStore = defineStore("scholarships", () => {
  const scholarships = ref<any[]>(JSON.parse(localStorage.getItem("scholarships") || "[]"));

  const save = () => localStorage.setItem("scholarships", JSON.stringify(scholarships.value));

  const add = (s: any) => { scholarships.value.push(s); save(); };

  const bySponsor = (sponsorId: number) =>
    scholarships.value.filter(s => Number(s.sponsorId) === Number(sponsorId));

  return { scholarships, add, bySponsor };
});