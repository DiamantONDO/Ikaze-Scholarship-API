<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const fullName = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const role = ref("sponsor");
const showPassword = ref(false);

const register = () => {
  if (!fullName.value || !email.value || !password.value || !confirmPassword.value) {
    alert("Please fill all fields.");
    return;
  }

  if (password.value !== confirmPassword.value) {
    alert("Passwords do not match!");
    return;
  }

  const users = JSON.parse(localStorage.getItem("users") || "[]");

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
  <div class="page">
    <div class="card">

      <div class="card-header">
        <div class="logo"></div>
        <h1>Create An Account</h1>
        <p>Register as a sponsor or admin</p>
      </div>

      <form @submit.prevent="register" class="form">

        <div class="form-group">
          <label>Full Name</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input v-model="fullName" type="text" placeholder="Enter your full name" required />
          </div>
        </div>

        <div class="form-group">
          <label>Email Address</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input v-model="email" type="email" placeholder="Enter your email" required />
          </div>
        </div>

        <div class="form-group">
          <label>Password</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Create a password"
              required
            />
            <button type="button" class="toggle-pw" @click="showPassword = !showPassword">
              {{ showPassword ? 'Hide' : 'View' }}
            </button>
          </div>
        </div>

        <div class="form-group">
          <label>Confirm Password</label>
          <div class="input-wrapper">
            <span class="input-icon"></span>
            <input
              v-model="confirmPassword"
              :type="showPassword ? 'text' : 'password'"
              placeholder="Confirm your password"
              required
            />
          </div>
        </div>

        <div class="form-group">
          <label>Register As</label>
          <div class="role-selector">
            <div
              :class="['role-option', role === 'sponsor' ? 'selected' : '']"
              @click="role = 'sponsor'"
            >
              <span></span>
              <strong>Sponsor</strong>
              <small>Post scholarships</small>
            </div>
            <div
              :class="['role-option', role === 'admin' ? 'selected' : '']"
              @click="role = 'admin'"
            >
              <span></span>
              <strong>Admin</strong>
              <small>Manage platform</small>
            </div>
          </div>
        </div>

        <button type="submit" class="submit-btn">Create Account →</button>

      </form>

      <p class="login-link">
        Already have an account?
        <RouterLink to="/login">Sign In</RouterLink>
      </p>

    </div>
  </div>
</template>

<style scoped>
.page {
  min-height: 100vh;
  background-image: url("public/ex2.jpg");
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
  background: white;
  border-radius: 20px;
  padding: 40px;
  width: 100%;
  max-width: 460px;
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

.role-selector {
  display: flex;
  gap: 12px;
}

.role-option {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 14px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.2s;
  text-align: center;
}

.role-option span { font-size: 24px; }
.role-option strong { font-size: 14px; color: #1e3a8a; }
.role-option small { font-size: 11px; color: #6b7280; }

.role-option:hover {
  border-color: #08662b;
  background: #f0f7ff;
}

.role-option.selected {
  border-color: #16a34a;
  background: #c6fcda;
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

.login-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #6b7280;
}

.login-link a {
  color: #1e3a8a;
  font-weight: bold;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>