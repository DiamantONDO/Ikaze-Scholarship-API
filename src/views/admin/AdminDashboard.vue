<script setup lang="ts">
import { ref, computed } from "vue";

/* Mock Data */
const students = ref([
  { id: 1, name: "Alice Uwimana" },
  { id: 2, name: "Eric Mugisha" },
  { id: 3, name: "Claudine Iradukunda" }
]);

const scholarships = ref([
  { id: 1, title: "STEM Excellence Award" },
  { id: 2, title: "Medical Leaders Scholarship" }
]);

const applications = ref([
  { id: 1, student: "Alice Uwimana", scholarship: "STEM Excellence Award", status: "Pending" },
  { id: 2, student: "Eric Mugisha", scholarship: "Medical Leaders Scholarship", status: "Approved" }
]);

const payments = ref([
  { id: 1, student: "Alice Uwimana", amount: 500000 },
  { id: 2, student: "Eric Mugisha", amount: 800000 }
]);

/* Computed Stats */
const totalStudents = computed(() => students.value.length);
const totalScholarships = computed(() => scholarships.value.length);
const totalApplications = computed(() => applications.value.length);
const totalPayments = computed(() =>
  payments.value.reduce((sum, p) => sum + p.amount, 0)
);
</script>

<template>
  <div class="dashboard">

    <h1 class="page-title">Admin Dashboard</h1>

    <!-- Summary Cards -->
    <div class="stats">

      <div class="card">
        <h3>Total Students</h3>
        <p>{{ totalStudents }}</p>
      </div>

      <div class="card">
        <h3>Total Scholarships</h3>
        <p>{{ totalScholarships }}</p>
      </div>

      <div class="card">
        <h3>Total Applications</h3>
        <p>{{ totalApplications }}</p>
      </div>

      <div class="card">
        <h3>Total Payments (RWF)</h3>
        <p>{{ totalPayments.toLocaleString() }}</p>
      </div>

    </div>

    <!-- Recent Applications -->
    <div class="table-section">

      <h2>Recent Applications</h2>

      <table>
        <thead>
          <tr>
            <th>Student</th>
            <th>Scholarship</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="app in applications" :key="app.id">
            <td>{{ app.student }}</td>
            <td>{{ app.scholarship }}</td>
            <td>
              <span :class="app.status === 'Approved' ? 'approved' : 'pending'">
                {{ app.status }}
              </span>
            </td>
          </tr>

          <tr v-if="applications.length === 0">
            <td colspan="3" class="empty">No applications available</td>
          </tr>
        </tbody>
      </table>

    </div>

  </div>
</template>

<style scoped>
.dashboard {
  padding: 10px;
}

.page-title {
  font-size: 26px;
  margin-bottom: 25px;
  color: #111827;
}

/* Stats Cards */
.stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.card {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  text-align: center;
}

.card h3 {
  font-size: 15px;
  margin-bottom: 10px;
  color: #6b7280;
}

.card p {
  font-size: 24px;
  font-weight: bold;
  color: #111827;
}

/* Table Section */
.table-section {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}

.table-section h2 {
  margin-bottom: 15px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  border-bottom: 1px solid #e5e7eb;
  text-align: left;
}

th {
  background: #f9fafb;
}

.approved {
  color: green;
  font-weight: bold;
}

.pending {
  color: orange;
  font-weight: bold;
}

.empty {
  text-align: center;
  padding: 15px;
  color: gray;
}
</style>