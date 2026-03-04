<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

const students = ref<any[]>([]);
const scholarships = ref<any[]>([]);
const applications = ref<any[]>([]);
const payments = ref<any[]>([]);

const fetchData = () => {
  const allUsers = JSON.parse(localStorage.getItem("users") || "[]");
  students.value = allUsers.filter((u: any) => u.role === "student");

  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");
  payments.value = JSON.parse(localStorage.getItem("payments") || "[]");
};

onMounted(fetchData);

const totalStudents = computed(() => students.value.length);
const totalScholarships = computed(() => scholarships.value.length);
const totalApplications = computed(() => applications.value.length);
const totalPayments = computed(() =>
  payments.value.reduce((sum: number, p: any) => sum + p.amount, 0)
);

const pendingApplications = computed(() =>
  applications.value.filter(a => a.status === "Pending").length
);

const approvedApplications = computed(() =>
  applications.value.filter(a => a.status === "Approved").length
);

const getStudentName = (studentId: number) => {
  const student = students.value.find(s => s.id === studentId);
  return student ? student.fullName : "Unknown";
};

const getScholarshipTitle = (scholarshipId: number) => {
  const sch = scholarships.value.find(s => s.id === scholarshipId);
  return sch ? sch.title : "Unknown";
};

// Show only last 5 applications
const recentApplications = computed(() =>
  [...applications.value].reverse().slice(0, 5)
);
</script>

<template>
  <div class="dashboard">
    <h1 class="page-title">Admin Dashboard</h1>

    <div class="stats">
      <div class="card blue">
        <div class="card-icon"></div>
        <div>
          <h3>Total Students</h3>
          <p>{{ totalStudents }}</p>
        </div>
      </div>
      
      <div class="card orange">
        <div class="card-icon"></div>
        <div>
          <h3>Total Applications</h3>
          <p>{{ totalApplications }}</p>
        </div>
      </div>

      <div class="card navy">
        <div class="card-icon"></div>
        <div>
          <h3>Total Scholarships</h3>
          <p>{{ totalScholarships }}</p>
        </div>
      </div>

      <div class="card green">
        <div class="card-icon"></div>
        <div>
          <h3>Total Payments (RWF)</h3>
          <p>{{ totalPayments.toLocaleString() }}</p>
        </div>
      </div>
    </div>

    <div class="stats secondary">
      <div class="mini-card">
        <span class="mini-label">Pending Applications</span>
        <span class="mini-value orange-text">{{ pendingApplications }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Approved Applications</span>
        <span class="mini-value green-text">{{ approvedApplications }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Rejected Applications</span>
        <span class="mini-value red-text">
          {{ applications.filter(a => a.status === "Rejected").length }}
        </span>
      </div>
    </div>

    <div class="table-section">
      <div class="table-header">
        <h2>Recent Applications</h2>
        <span class="table-sub">Showing last {{ recentApplications.length }} entries</span>
      </div>

      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Student</th>
            <th>Scholarship</th>
            <th>Date</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(app, index) in recentApplications" :key="app.id">
            <td>{{ index + 1 }}</td>
            <td>{{ getStudentName(app.studentId) }}</td>
            <td>{{ getScholarshipTitle(app.scholarshipId) }}</td>
            <td>{{ app.date ? new Date(app.date).toLocaleDateString("en-GB") : "—" }}</td>
            <td>
              <span :class="['badge', app.status.toLowerCase()]">
                {{ app.status }}
              </span>
            </td>
          </tr>

          <tr v-if="applications.length === 0">
            <td colspan="5" class="empty">No applications yet.</td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<style scoped>
.dashboard {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  padding-top: 0px;
}

.page-title {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 24px;
}

.stats {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.card {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 22px;
  border-radius: 12px;
  color: white;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.card-icon { font-size: 32px; }

.card h3 {
  margin: 0 0 6px;
  font-size: 13px;
  opacity: 0.85;
}

.card p {
  margin: 0;
  font-size: 28px;
  font-weight: bold;
}

.card.blue  { background: #3b82f6; }
.card.orange { background: #3b82f6; }
.card.navy  { background: #16a34a; }
.card.green { background: #16a34a; }

.stats.secondary {
  gap: 16px;
  margin-bottom: 30px;
}

.mini-card {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  padding: 16px 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  font-size: 14px;
}

.mini-label { color: #374151; height: 42px;}
.mini-value { font-size: 22px; font-weight: bold; height: 42px;}
.orange-text { color: #f97316; }
.green-text  { color: #16a34a; }
.red-text    { color: #dc2626; }

.table-section {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.table-header h2 { margin: 0; font-size: 17px; }
.table-sub { font-size: 13px; color: #9ca3af; }

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 10px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  font-size: 14px;
}

th {
  background: #1e3a8a;
  color: white;
}

.badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: bold;
}

.badge.pending  { background: #fff7ed; color: #c2410c; }
.badge.approved { background: #dcfce7; color: #15803d; }
.badge.rejected { background: #fee2e2; color: #b91c1c; }

.empty {
  text-align: center;
  padding: 30px;
  color: #9ca3af;
}
</style>