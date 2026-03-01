<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const student = JSON.parse(sessionStorage.getItem("student_session") || "{}");

const scholarships = ref<any[]>([]);
const applications = ref<any[]>([]);

onMounted(() => {
  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");
});

const hasApplied = (scholarshipId: number) => {
  return applications.value.some(
    (app) => app.studentId === student.id && app.scholarshipId === scholarshipId
  );
};

// Redirect to apply page instead of direct apply
const goToApply = (scholarshipId: number) => {
  router.push(`/student/scholarships/${scholarshipId}`);
};
</script>

<template>
  <div class="page">
    <h1>Posted Scholarships</h1>
    <div v-if="scholarships.length === 0" class="no-sch">
      There is no posted scholarships yet.
    </div>

    <div class="scholarships-grid">
      <div class="card" v-for="sch in scholarships" :key="sch.id">
        <div class="card-header">
          <h4>{{ sch.name }}</h4>
          <h3>{{ sch.title }}</h3>
          <span :class="['status-badge', sch.status]">{{ sch.status.toUpperCase() }}</span>
        </div>
        <p><strong>Field:</strong> {{ sch.field }}</p>
        <p><strong>Description:</strong> {{ sch.description }}</p>
        <p><strong>Amount:</strong> {{ sch.amount?.toLocaleString() }} RWF</p>
        <p><strong>Deadline:</strong> {{ new Date(sch.deadline).toLocaleDateString("en-GB") }}</p>

        <button
          :disabled="hasApplied(sch.id)"
          @click="goToApply(sch.id)"
          :class="hasApplied(sch.id) ? 'applied-btn' : 'apply-btn'"
        >
          {{ hasApplied(sch.id) ? "Already Applied" : "Apply Now →" }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
}

.scholarships-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-top: 20px;
}

.no-sch{
  margin-top: 20px;
  font-style: italic;
  color: gray;
}

.card {
  padding: 24px;
  border-radius: 12px;
  background: white;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.card-header h3 { margin: 0; }

.status-badge {
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 11px;
  font-weight: bold;
}

.status-badge.active { background: #dcfce7; color: #16a34a; }
.status-badge.inactive { background: #fee2e2; color: #dc2626; }

.apply-btn {
  margin-top: 12px;
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
}

.applied-btn {
  margin-top: 12px;
  background: #e5e7eb;
  color: #6b7280;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: not-allowed;
  font-weight: bold;
}
</style>