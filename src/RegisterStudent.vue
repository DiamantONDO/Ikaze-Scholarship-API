<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const fullName = ref("");
const email = ref("");
const password = ref("");
const phone = ref("");
const high_school = ref("");
const field = ref("");
const year = ref("");
const fatherName = ref("");
const fatherPhone = ref("");
const motherName = ref("");
const motherPhone = ref("");

const register = () => {
  const users = JSON.parse(localStorage.getItem("users") || "[]");

  const existingUser = users.find((u: any) => u.email === email.value);
  if (existingUser) {
    alert("Email already registered!");
    return;
  }

  const newUser = {
    id: Date.now(),
    role: "student",
    fullName: fullName.value,
    email: email.value,
    password: password.value,
    phone: phone.value,
    high_school: high_school.value,
    field: field.value,
    year: year.value,
    fatherName: fatherName.value,
    fatherPhone: fatherPhone.value,
    motherName: motherName.value,
    motherPhone: motherPhone.value
  };

  users.push(newUser);
  localStorage.setItem("users", JSON.stringify(users));

  alert("Registration successful!");
  router.push("/login");
};
</script>

<template>
  <div class="auth-container">
    <h2>Student Registration</h2>

    <form @submit.prevent="register">
      <fieldset>
        <legend>Personal Information</legend>
        <input v-model="fullName" placeholder="Full Name" required />
        <input v-model="email" type="email" placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <input v-model="phone" placeholder="Phone Number" required />
        <input v-model="high_school" placeholder="Graduated High School" required />
        <input v-model="field" placeholder="Field of Study" required />
        <input v-model="year" placeholder="Year (e.g. Year 2)" required />
      </fieldset>

      <fieldset>
        <legend>Parents Information</legend>
        <input v-model="fatherName" placeholder="Father's Full Name" required />
        <input v-model="fatherPhone" placeholder="Father's Phone" required />
        <input v-model="motherName" placeholder="Mother's Full Name" required />
        <input v-model="motherPhone" placeholder="Mother's Phone" required />
      </fieldset>

      <button type="submit">Register</button>
    </form>

    <RouterLink to="/login">Already have an account? Login</RouterLink>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 500px;
  margin: 60px auto;
  display: flex;
  flex-direction: column;
  gap: 15px;
  font-family: Arial, Helvetica, sans-serif;
}

fieldset {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

legend {
  font-weight: bold;
  color: #1e3a8a;
  padding: 0 6px;
}

input {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 14px;
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