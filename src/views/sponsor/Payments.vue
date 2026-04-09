<script setup lang="ts">
import { ref, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const sponsor = JSON.parse(sessionStorage.getItem("sponsor_session") || "null");

const scholarships = ref<any[]>([]);
const applications = ref<any[]>([]);
const students = ref<any[]>([]);

onMounted(async () => {
  const { data: scholarshipData } = await supabase
    .from("scholarships")
    .select("*")
    .eq("sponsor_id", sponsor.id);
  scholarships.value = scholarshipData || [];

  const scholarshipIds = scholarships.value.map(s => s.id);

  if (scholarshipIds.length > 0) {
    const { data: appData } = await supabase
      .from("applications")
      .select("*")
      .in("scholarship_id", scholarshipIds);
    applications.value = appData || [];

    const studentIds = [...new Set(applications.value.map(a => a.student_id))];
    if (studentIds.length > 0) {
      const { data: studentData } = await supabase
        .from("users")
        .select("id, full_name, email")
        .in("id", studentIds);
      students.value = studentData || [];
    }
  }
});

const getStudent = (id: number) => students.value.find(s => s.id === id);
const getScholarship = (id: number) => scholarships.value.find(s => s.id === id);
</script>

<template>
  <div class="page">
    <h1>Scholarship Applications</h1>
    <table class="applications-table">
      <thead>
        <tr>
          <th>Scholarship</th>
          <th>Student</th>
          <th>Email</th>
          <th>Date of Application</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <template v-for="app in applications" :key="app.id">
          <tr v-if="getScholarship(app.scholarship_id)">
            <td>{{ getScholarship(app.scholarship_id)?.title }}</td>
            <td>{{ getStudent(app.student_id)?.full_name || "Unknown" }}</td>
            <td>{{ getStudent(app.student_id)?.email || "Unknown" }}</td>
            <td>{{ new Date(app.date).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" }) }}</td>
            <td :class="app.status.toLowerCase()">{{ app.status.toUpperCase() }}</td>
          </tr>
        </template>
        <tr v-if="applications.length === 0">
          <td colspan="5" style="text-align:center;">No applications yet.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  padding-top: 0px;
}

.form-card, .table-card {
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  margin-bottom: 30px;
  
}

.form-card h2, .table-card h2 {
  margin-bottom: 20px;
  color: #1e3a8a;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 21px;
}

.form-group label {
  font-weight: bold;
  font-size: 21px;
}

.form-group select {
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.amount-box {
  padding: 10px;
  background: #f0f4ff;
  border-radius: 8px;
  border: 1px solid #c7d2fe;
  font-size: 18px;
  font-weight: bold;
  color: #1e3a8a;
}

.pay-btn {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: bold;
  font-size: 15px;
  cursor: pointer;
}

.pay-btn:hover {
  background: #2d4fa3;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 10px;
  border: 1px solid #eee;
  text-align: left;
  font-size: 21px;
}

th {
  background: #1e3a8a;
  color: white;
}

td.paid {
  color: green;
  font-weight: bold;
}
</style>