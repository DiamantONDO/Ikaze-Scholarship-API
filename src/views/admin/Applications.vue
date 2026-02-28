<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();


// Load data from localStorage
const applications = ref<any[]>([]);
const scholarships = ref<any[]>([]);
const students = ref<any[]>([]);

// Fetch all data on mounted
onMounted(() => {
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");
  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
  students.value = JSON.parse(localStorage.getItem("users") || "[]"); // all users
});

// Get scholarship title by id
const getScholarshipTitle = (id: number) => {
  const sch = scholarships.value.find(s => s.id === id);
  return sch ? sch.title : "Unknown";
};

// Get student name by id
const getStudentName = (id: number) => {
  const student = students.value.find(s => s.id === id);
  return student ? student.fullName : "Unknown";
};

// Approve or Reject an application
const updateStatus = (appId: number, status: "Approved" | "Rejected") => {
  const app = applications.value.find(a => a.id === appId);
  if (!app) return;

  app.status = status;
  localStorage.setItem("applications", JSON.stringify(applications.value));
};
</script>

<template>
  <div class="page">
    <h1>Student Applications</h1>

    <table>
      <thead>
  <tr>
    <th>Student</th>
    <th>Scholarship</th>
    <th>Date</th>
    <th>Status</th>
    <th>Details</th>
    <th>Action</th>
  </tr>
</thead>
<tbody>
  <tr v-for="app in applications" :key="app.id">
    <td>{{ getStudentName(app.studentId) }}</td>
    <td>{{ getScholarshipTitle(app.scholarshipId) }}</td>
    <td>{{ new Date(app.date).toLocaleDateString("en-GB") }}</td>
    <td>{{ app.status }}</td>
    <td>
      <!--  Details button -->
      <button class="detail-btn" @click="router.push(`/admin/applications/${app.id}`)">
        View Details
      </button>
    </td>
    <td>
      <button v-if="app.status === 'Pending'" @click="updateStatus(app.id, 'Approved')">Approve</button>
      <button style="background-color: #e24960;" v-if="app.status === 'Pending'" @click="updateStatus(app.id, 'Rejected')">Reject</button>
    </td>
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
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

th {
  background: #f0f0f0;
}

button {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  margin-right: 6px;
}

button:hover {
  opacity: 0.9;
}
</style>