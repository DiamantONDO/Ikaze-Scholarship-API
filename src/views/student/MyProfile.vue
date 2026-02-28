<script setup lang="ts">
import { ref, onMounted } from "vue";

const student = ref({
  fullName: "",
  email: "",
  phone: "",
  university: "",
  field: "",
  year: "",
  fatherName: "",
  fatherPhone: "",
  motherName: "",
  motherPhone: ""
});

onMounted(() => {
  //Read the logged-in user from localStorage
  const loggedIn = JSON.parse(localStorage.getItem("student_session") || "null");
  if (loggedIn) {
    student.value = { ...student.value, ...loggedIn };
  }
});
</script>

<template>
  <div>
    <h1 class="page-title">My Profile</h1>

    <!-- Personal Info -->
    <div class="card">
      <h2>Personal Information</h2>
      <div class="grid">
        <div><label>Full Name</label><p>{{ student.fullName }}</p></div>
        <div><label>Email</label><p>{{ student.email }}</p></div>
        <div><label>Phone</label><p>{{ student.phone }}</p></div>
        <div><label>University</label><p>{{ student.university }}</p></div>
        <div><label>Field</label><p>{{ student.field }}</p></div>
        <div><label>Year</label><p>{{ student.year }}</p></div>
      </div>
    </div>

    <!-- Parents Info -->
    <div class="card">
      <h2>Parents Information</h2>
      <div class="grid">
        <div><label>Father's Name</label><p>{{ student.fatherName }}</p></div>
        <div><label>Father's Phone</label><p>{{ student.fatherPhone }}</p></div>
        <div><label>Mother's Name</label><p>{{ student.motherName }}</p></div>
        <div><label>Mother's Phone</label><p>{{ student.motherPhone }}</p></div>
      </div>
    </div>

    <button class="edit-btn">Edit Profile</button>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 26px;
  color: #1e3a8a;
  margin-bottom: 20px;
}

.card {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
  margin-bottom: 20px;
}

.card h2 {
  color: #1e3a8a;
  margin-bottom: 15px;
}

.grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

label {
  font-size: 12px;
  color: gray;
  display: block;
  margin-bottom: 4px;
}

p {
  font-weight: bold;
  color: #1e3a8a;
  margin: 0;
}

.edit-btn {
  padding: 10px 20px;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}
</style>