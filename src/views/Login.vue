<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");
const password = ref("");

const login = () => {
  const users = JSON.parse(localStorage.getItem("users") || "[]");

  const user = users.find(
    (u: any) => u.email === email.value && u.password === password.value
  );

  if (!user) {
    alert("Invalid email or password!");
    return;
  }

  //Save to role-specific session key instead of overwriting "user"
  if (user.role === "student") {
    sessionStorage.setItem("student_session", JSON.stringify(user));
    router.push("/student/dashboard");
  } else if (user.role === "sponsor") {
    sessionStorage.setItem("sponsor_session", JSON.stringify(user));
    router.push("/sponsor/dashboard");
  } else if (user.role === "admin") {
    sessionStorage.setItem("admin_session", JSON.stringify(user));
    router.push("/admin/dashboard");
  }
};
</script>

<template>
  <div class="auth-container">
    <h2>Login</h2>

    <form @submit.prevent="login">
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
    </form>

    <RouterLink to="/register">Don't have an account? Register</RouterLink>
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

input {
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
  font-weight: bold;
  cursor: pointer;
}
</style>