import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("users", () => {
  const users = ref<any[]>(JSON.parse(localStorage.getItem("users") || "[]"));

  const save = () => localStorage.setItem("users", JSON.stringify(users.value));

  const addUser = (user: any) => { users.value.push(user); save(); };

  const findByEmail = (email: string) => users.value.find(u => u.email === email);

  return { users, addUser, findByEmail };
}
);