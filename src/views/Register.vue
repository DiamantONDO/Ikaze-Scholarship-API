<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const fullName = ref("");
const email = ref("");
const password = ref("");
const role = ref("admin");

const register = () => {
  const users = JSON.parse(localStorage.getItem("users") || "[]");

  // Check if email already exists
  const existingUser = users.find((u: any) => u.email === email.value);
  if (existingUser) {
    alert("Email already registered!");
    return;
  }

  const newUser = {
    id: Date.now(),
    fullName: fullName.value,
    email: email.value,
    password: password.value,
    role: role.value
  };

  users.push(newUser);
  localStorage.setItem("users", JSON.stringify(users));

  alert("Registration successful!");
  router.push("/login");
};
</script>

<template>
  <div class="auth-container">
    <h2>Register</h2>

    <form @submit.prevent="register">
      <input v-model="fullName" placeholder="Full Name" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />

      <select v-model="role">
        <option value="admin">admin</option>
        <option value="sponsor">sponsor</option>
      </select>

      <button type="submit">Register</button>
    </form>

    <RouterLink to="/login">Already have an account? Login</RouterLink>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 100px auto;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

input, select {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

button {
  padding: 10px;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 6px;
}
</style>