<script setup lang="ts">
import { ref, nextTick } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");
const password = ref("");
const showPassword = ref(false);
const errorMessage = ref("");

const login = async () => {
  errorMessage.value = "";

  const users = JSON.parse(localStorage.getItem("users") || "[]");

  const user = users.find(
    (u: any) => u.email === email.value && u.password === password.value
  );

  if (!user) {
    errorMessage.value = "Invalid email or password. Please try again.";
    return;
  }

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
  <div class="page">
    <div class="card">

      <div class="card-header">
        <div class="logo"></div>
        <h1>Welcome Back</h1>
        <p>Sign in to your account</p>
      </div>

      <div class="error-box" v-if="errorMessage">
         {{ errorMessage }}
      </div>

      <form @submit.prevent="login" class="form">

        <div class="form-group">
          <label>Email Address</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input
              v-model="email"
              type="email"
              placeholder="Enter your email"
              required
            />
          </div>
        </div>

        <div class="form-group">
          <label>Password</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Enter your password"
              required
            />
            <button type="button" class="toggle-pw" @click="showPassword = !showPassword">
              {{ showPassword ? 'Hide' : 'View' }}
            </button>
          </div>
        </div>

        <button type="submit" class="submit-btn">Sign In →</button>

      </form>

      <div class="links">
        <p class="register-link">
          Don't have an account?
          <RouterLink to="/register">Register here</RouterLink>
        </p>
        <p class="register-link">
          Are you a student?
          <RouterLink to="/register/student">Register as Student</RouterLink>
        </p>
      </div>

    </div>
  </div>
</template>

<style scoped>
.page {
  min-height: 100vh;
  background-image: url("/Riviera.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  font-family: Arial, Helvetica, sans-serif;
}

.card {
  background: rgba(255, 255, 255, 0.881);
  border-radius: 20px;
  padding: 40px;
  width: 100%;
  max-width: 440px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.2);
}

.card-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo {
  font-size: 48px;
  margin-bottom: 12px;
}

.card-header h1 {
  font-size: 24px;
  color: #1e3a8a;
  margin: 0 0 6px;
}

.card-header p {
  color: #6b7280;
  font-size: 14px;
  margin: 0;
}

.error-box {
  background: #fee2e2;
  color: #b91c1c;
  padding: 12px 16px;
  border-radius: 10px;
  font-size: 14px;
  margin-bottom: 20px;
  text-align: center;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group label {
  font-size: 13px;
  font-weight: bold;
  color: #374151;
}

.input-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid #d1d5db;
  border-radius: 10px;
  padding: 0 12px;
  gap: 8px;
  background: #f9fafb;
  transition: border 0.2s;
}

.input-wrapper:focus-within {
  border-color: #1e3a8a;
  background: white;
}

.input-icon { font-size: 16px; }

.input-wrapper input {
  flex: 1;
  border: none;
  outline: none;
  padding: 12px 0;
  font-size: 14px;
  background: transparent;
  color: #111827;
}

.toggle-pw {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  padding: 0;
}

.submit-btn {
  background: #16a34a;
  color: white;
  border: none;
  padding: 14px;
  border-radius: 10px;
  font-size: 15px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 4px;
  transition: background 0.2s;
}

.submit-btn:hover {
  background: #08662b;
}

.links {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.register-link {
  text-align: center;
  font-size: 14px;
  color: #6b7280;
  margin: 0;
}

.register-link a {
  color: #1e3a8a;
  font-weight: bold;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>