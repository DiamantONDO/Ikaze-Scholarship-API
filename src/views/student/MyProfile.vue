<script setup lang="ts">
import { ref, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const student = ref<any>({});

onMounted(async () => {
  const session = JSON.parse(sessionStorage.getItem("student_session") || "null");
  if (!session) return;

  const { data: userData } = await supabase
    .from("users")
    .select("*")
    .eq("id", session.id)
    .single();

  const { data: profileData } = await supabase
    .from("student_profiles")
    .select("*")
    .eq("id", session.id)
    .single();

  student.value = { ...userData, ...profileData };
});
</script>

<template>
  <div>
    <h1 class="page-title">My Profile</h1>

    <div class="card">
      <h2>Personal Information</h2>
      <div class="grid">
        <div><label>Full Name</label><p>{{ student.full_name }}</p></div>
        <div><label>Sex</label><p>{{ student.sex }}</p></div>
        <div><label>Age</label><p>{{ student.age }}</p></div>
        <div><label>Email</label><p>{{ student.email }}</p></div>
        <div><label>Phone</label><p>{{ student.phone }}</p></div>
        <div><label>High School</label><p>{{ student.high_school }}</p></div>
        <div><label>Field</label><p>{{ student.field }}</p></div>
        <div><label>Year</label><p>{{ student.year }}</p></div>
      </div>
    </div>

    <div class="card">
      <h2>Parents Information</h2>
      <div class="grid">
        <div><label>Father's Name</label><p>{{ student.father_name }}</p></div>
        <div><label>Father's Phone</label><p>{{ student.father_phone }}</p></div>
        <div><label>Mother's Name</label><p>{{ student.mother_name }}</p></div>
        <div><label>Mother's Phone</label><p>{{ student.mother_phone }}</p></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  padding: 30px;
  color: #1e3a8a;
  font-family: Arial, Helvetica, sans-serif;
  padding-top: 0px;
  padding-bottom: 0px;
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
  font-size: x-large;
  color: gray;
}

label {
  font-size: 12px;
  color: #1e3a8a;
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
  font-size: large;
}

p {
  font-weight: 500;
  color: rgb(69, 69, 69);
  margin: 0;
}

.edit-btn {
  padding: 10px 20px;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
</style>