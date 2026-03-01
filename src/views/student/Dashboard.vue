<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

const student = JSON.parse(sessionStorage.getItem("student_session") || "null");

const scholarships = ref<any[]>([]);
const applications = ref<any[]>([]);
const payments = ref<any[]>([]);

onMounted(() => {
  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");
  payments.value = JSON.parse(localStorage.getItem("payments") || "[]");
});


const totalScholarships = computed(() => {
  return scholarships.value.filter(s => s.status === "active").length;
});


const totalApplications = computed(() => {
  return applications.value.filter(
    a => a.studentId === student.id
  ).length;
});


const totalPayments = computed(() => {
  return payments.value
    .filter(p => p.studentId === student.id && p.status === "Paid")
    .reduce((sum, p) => sum + p.amount, 0);
});
</script>

<template>
  <div>
    <h1 class="page-title">Student Dashboard</h1>

    <div class="cards">

      <div class="card">
        <h3>Available Scholarships</h3>
        <p>{{ totalScholarships }}</p>
      </div>

      <div class="card">
        <h3>My Applications</h3>
        <p>{{ totalApplications }}</p>
      </div>

      <div class="card">
        <h3>Total Payments (RWF)</h3>
        <p>{{ totalPayments.toLocaleString() }}</p>
      </div>

    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 24px;
  margin-bottom: 25px;
}

.cards {
  display: flex;
  gap: 20px;
}

.card {
  flex: 1;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
}

.card h3 {
  font-size: 14px;
  color: #6b7280;
}

.card p {
  font-size: 28px;
  font-weight: bold;
  margin-top: 10px;
}
</style>