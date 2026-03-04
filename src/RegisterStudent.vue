<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const fullName = ref("");
const age = ref("");
const sex = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const phone = ref("");
const high_school = ref("");
const field = ref("");
const year = ref("");
const fatherName = ref("");
const fatherPhone = ref("");
const motherName = ref("");
const motherPhone = ref("");
const showPassword = ref(false);
const errorMessage = ref("");

const register = () => {
  errorMessage.value = "";

  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Passwords do not match!";
    return;
  }

  const users = JSON.parse(localStorage.getItem("users") || "[]");

  const existingUser = users.find((u: any) => u.email === email.value);
  if (existingUser) {
    errorMessage.value = "Email already registered!";
    return;
  }

  const newUser = {
    id: Date.now(),
    role: "student",
    fullName: fullName.value,
    age: age.value,
    sex: sex.value,
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
  <div class="page">
    <div class="card">

      <div class="card-header">
        <div class="logo">🎓</div>
        <h1>Student Registration</h1>
        <p>Create your student account</p>
      </div>

      <div class="error-box" v-if="errorMessage">
        {{ errorMessage }}
      </div>

      <form @submit.prevent="register" class="form">

        <div class="two-columns">

          <div class="column">
            <h3 class="section-title">Personal Information</h3>

            <div class="form-group">
              <label>Full Name</label>
              <div class="input-wrapper">
                <input v-model="fullName" type="text" placeholder="Full Name" required />
              </div>
            </div>

            <div class="form-group">
              <label>Age</label>
              <div class="input-wrapper">
                <input v-model="age" type="number" placeholder="Your Age" required />
              </div>
            </div>

            <div class="form-group">
              <label>Sex</label>
              <div class="radio-group">
                <label class="radio-option">
                  <input type="radio" v-model="sex" value="Male" required />
                  Male
                </label>
                <label class="radio-option">
                  <input type="radio" v-model="sex" value="Female" />
                  Female
                </label>
              </div>
            </div>

            <div class="form-group">
              <label>Email Address</label>
              <div class="input-wrapper">
                <input v-model="email" type="email" placeholder="Email" required />
              </div>
            </div>

            <div class="form-group">
              <label>Password</label>
              <div class="input-wrapper">
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
                <input
                  v-model="confirmPassword"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="Confirm password"
                  required
                />
              </div>
            </div>

            <div class="form-group">
              <label>Phone Number</label>
              <div class="input-wrapper">
                <input v-model="phone" type="tel" placeholder="Phone Number" required />
              </div>
            </div>

            <div class="form-group">
              <label>Graduated High School</label>
              <div class="input-wrapper">
                <input v-model="high_school" type="text" placeholder="High School Name" required />
              </div>
            </div>

            <div class="form-group">
              <label>Field of Study</label>
              <div class="input-wrapper">
                <input v-model="field" type="text" placeholder="e.g. Computer Science" required />
              </div>
            </div>

            <div class="form-group">
              <label>Year</label>
              <div class="input-wrapper">
                <input v-model="year" type="text" placeholder="e.g. Year 2" required />
              </div>
            </div>
          </div>

          <div class="column">
            <h3 class="section-title">Your Tutors Information</h3>

            <div class="form-group">
              <label>Father's Full Name</label>
              <div class="input-wrapper">
                <input v-model="fatherName" type="text" placeholder="Father's Full Name" required />
              </div>
            </div>

            <div class="form-group">
              <label>Father's Phone</label>
              <div class="input-wrapper">
                <input v-model="fatherPhone" type="tel" placeholder="Father's Phone" required />
              </div>
            </div>

            <div class="form-group">
              <label>Mother's Full Name</label>
              <div class="input-wrapper">
                <input v-model="motherName" type="text" placeholder="Mother's Full Name" required />
              </div>
            </div>

            <div class="form-group">
              <label>Mother's Phone</label>
              <div class="input-wrapper">
                <input v-model="motherPhone" type="tel" placeholder="Mother's Phone" required />
              </div>
            </div>
          </div>

        </div>

        <button type="submit" class="submit-btn">Create Student Account →</button>

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
  background-image: url("public/ALU.jpg");
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
  background: #fffffff7;
  border: 2px solid #e5e7eb;
  border-radius: 20px;
  padding: 40px;
  width: 100%;
  max-width: 900px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.2);
}

.card-header {
  text-align: center;
  margin-bottom: 32px;
  font-size: 21px;
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
  font-size: 21px;
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

.two-columns {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
  margin-bottom: 24px;
}

.column {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.section-title {
  font-size: 15px;
  color: #1e3a8a;
  margin: 0 0 4px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e5e7eb;
}

.form {
  display: flex;
  flex-direction: column;
  font-size: 21px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
  font-size: 21px;
}

.form-group label {
  font-size: 13px;
  font-weight: bold;
  color: #374151;
  font-size: 19px;
}

.input-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid #9e9f9f;
  border-radius: 10px;
  padding: 0 12px;
  background: #f9fafb;
  transition: border 0.2s;
  font-size: 21px;
}

.input-wrapper:focus-within {
  border-color: #1e3a8a;
  background: white;
}

.input-wrapper input {
  flex: 1;
  border: none;
  outline: none;
  padding: 11px 0;
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

.radio-group {
  display: flex;
  gap: 20px;
  padding: 10px 0;
}

.radio-option {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  cursor: pointer;
  color: #374151;
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
  transition: background 0.2s;
  width: 100%;
}

.submit-btn:hover {
  background: #08662b;
}

/* Login link */
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