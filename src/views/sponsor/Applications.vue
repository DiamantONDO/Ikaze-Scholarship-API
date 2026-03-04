<script setup lang="ts">
import { ref, onMounted } from "vue";

const sponsor = JSON.parse(sessionStorage.getItem("sponsor_session") || "null");

interface Scholarship {
  id: number;
  sponsorId: number;
  title: string;
}

interface Application {
  id: number;
  studentId: number;
  scholarshipId: number;
  date: string;
  status: "pending" | "accepted" | "rejected";
}

interface Student {
  id: number;
  fullName: string;
  email: string;
}

const scholarships = ref<Scholarship[]>([]);
const applications = ref<Application[]>([]);
const students = ref<Student[]>([]);

const fetchData = () => {
  const allScholarships: Scholarship[] = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarships.value = allScholarships.filter(s => s.sponsorId === sponsor.id);
  
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");

  const allUSers = JSON.parse(localStorage.getItem("users") || "[]");
  students.value = allUSers.filter((u: any) => u.role === "student");
};

onMounted(fetchData);

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
    <tr v-if="getScholarship(app.scholarshipId)">
      <td>{{ getScholarship(app.scholarshipId)?.title }}</td>
      <td>{{ getStudent(app.studentId)?.fullName || "Unknown" }}</td>
      <td>{{ getStudent(app.studentId)?.email || "Unknown" }}</td>
      <td>{{ new Date(app.date).toLocaleDateString() }}</td>
      <td :class="app.status">{{ app.status.toUpperCase() }}</td>
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

.applications-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.applications-table th,
.applications-table td {
  border: 1px solid #ccc;
  padding: 12px 10px;
  text-align: left;
  font-size: 21px;
}

.applications-table th {
  background-color: #1e3a8a;
  color: white;
}

td.pending {
  color: orange;
  font-weight: bold;
}

td.accepted {
  color: green;
  font-weight: bold;
}

td.rejected {
  color: red;
  font-weight: bold;
}
</style>