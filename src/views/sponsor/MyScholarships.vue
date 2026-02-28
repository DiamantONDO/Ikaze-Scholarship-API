<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const sponsor = JSON.parse(localStorage.getItem("sponsor_session") || "null");

interface Scholarship {
  id: number;
  sponsorId: number;
  title: string;
  field: string;
  amount: number;
  deadline: string;
  requirements: { passport: string; equivalence: string };
  status: "active" | "inactive";
}

const scholarships = ref<Scholarship[]>([]);

const fetchScholarships = () => {
  const allScholarships = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarships.value = allScholarships.filter((s: Scholarship) => s.sponsorId === sponsor.id);
};

onMounted(fetchScholarships);

const deleteScholarship = (id: number) => {
  if (!confirm("Are you sure you want to delete this scholarship?")) return;

  let allScholarships = JSON.parse(localStorage.getItem("scholarships") || "[]");
  allScholarships = allScholarships.filter((s: Scholarship) => s.id !== id);
  localStorage.setItem("scholarships", JSON.stringify(allScholarships));

  fetchScholarships();
};

//Implement real edit(All 7 fields) later
const editScholarship = (id: number) => {
  router.push({ path: "/sponsor/scholarships", query: { id } });
};

const viewApplications = (id: number) => {
  router.push({ path: "/sponsor/applications", query: { scholarshipId: id } });
};
</script>

<template>
  <div class="page">
    <h1>My Scholarships</h1>

    <table class="scholarship-table">
      <thead>
        <tr>
          <th>Title</th>
          <th>Field</th>
          <th>Amount (RWF)</th>
          <th>Deadline</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="sch in scholarships" :key="sch.id">
          <td>{{ sch.title }}</td>
          <td>{{ sch.field }}</td>
          <td>{{ sch.amount.toLocaleString() }}</td>
          <td>{{ new Date(sch.deadline).toLocaleDateString() }}</td>
          <td :class="sch.status">{{ sch.status.toUpperCase() }}</td>
          <td class="actions">
            <button @click="editScholarship(sch.id)">Edit</button>
            <button @click="deleteScholarship(sch.id)">Delete</button>
            <button @click="viewApplications(sch.id)">Applications</button>
          </td>
        </tr>
        <tr v-if="scholarships.length === 0">
          <td colspan="6" style="text-align:center;">No scholarships posted yet.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>

tr{
  text-align: center;
}
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
}

.scholarship-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  text-align: center;
}

.scholarship-table th,
.scholarship-table td {
  border: 1px solid #ccc;
  padding: 12px 10px;
  text-align: left;
}

.scholarship-table th {
  background-color: #1e3a8a;
  color: white;
}

.scholarship-table td.active {
  color: green;
  font-weight: bold;
}

.scholarship-table td.inactive {
  color: red;
  font-weight: bold;
}

.actions button {
  margin-right: 6px;
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.actions button:nth-child(1) {
  background-color: #1e3a8a;
  color: white;
}

.actions button:nth-child(2) {
  background-color: red;
  color: white;
}

.actions button:nth-child(3) {
  background-color: rgb(8, 170, 127);
  color: white;
}
</style>