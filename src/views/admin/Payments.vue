<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

interface Payment {
  id: number;
  sponsorId: number;
  studentId: number;
  scholarshipId: number;
  month: string;
  amount: number;
  status: "paid" | "pending";
  date: string;
}

const payments = ref<Payment[]>([]);
const users = ref<any[]>([]);
const scholarships = ref<any[]>([]);

const fetchData = () => {
  payments.value = JSON.parse(localStorage.getItem("payments") || "[]");
  users.value = JSON.parse(localStorage.getItem("users") || "[]");
  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
};

onMounted(fetchData);

const getStudentName = (id: number) => {
  const user = users.value.find(u => u.id === id);
  return user ? user.fullName : "Unknown";
};

const getSponsorName = (id: number) => {
  const user = users.value.find(u => u.id === id);
  return user ? user.fullName : "Unknown";
};

const getScholarshipTitle = (id: number) => {
  const sch = scholarships.value.find(s => s.id === id);
  return sch ? sch.title : "Unknown";
};

const totalPaid = computed(() =>
  payments.value
    .filter(p => p.status === "paid")
    .reduce((sum, p) => sum + p.amount, 0)
);

const totalPending = computed(() =>
  payments.value.filter(p => p.status === "pending").length
);

const totalTransactions = computed(() => payments.value.length);
</script>

<template>
  <div class="page">
    <h1 class="page-title">Payments Management</h1>

    <div class="stats-row">
      <div class="stat-card green">
        <div class="stat-icon"></div>
        <div>
          <p class="stat-label">Total Paid</p>
          <p class="stat-value">{{ totalPaid.toLocaleString() }} RWF</p>
        </div>
      </div>
      <div class="stat-card blue">
        <div class="stat-icon"></div>
        <div>
          <p class="stat-label">Total Transactions</p>
          <p class="stat-value">{{ totalTransactions }}</p>
        </div>
      </div>
      <div class="stat-card orange">
        <div class="stat-icon"></div>
        <div>
          <p class="stat-label">Pending Payments</p>
          <p class="stat-value">{{ totalPending }}</p>
        </div>
      </div>
    </div>

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Student</th>
            <th>Sponsor</th>
            <th>Scholarship</th>
            <th>Amount (RWF)</th>
            <th>Payment Date</th>
            <th>Month</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(payment, index) in payments" :key="payment.id">
            <td>{{ index + 1 }}</td>
            <td>{{ getStudentName(payment.studentId) }}</td>
            <td>{{ getSponsorName(payment.sponsorId) }}</td>
            <td>{{ getScholarshipTitle(payment.scholarshipId) }}</td>
            <td>{{ payment.amount.toLocaleString() }}</td>
            <td>{{ new Date(payment.date).toLocaleDateString("en-GB") }}</td>
            <td>{{ new Date(payment.month).toLocaleDateString("en-GB") }}</td>
            <td>
              <span :class="['badge', payment.status]">
                {{ payment.status === "paid" ? "Paid" : "Pending" }}
              </span>
            </td>
          </tr>

          <tr v-if="payments.length === 0">
            <td colspan="8" class="empty">No payments recorded yet.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 24px;
}

.stats-row {
  display: flex;
  gap: 20px;
  margin-bottom: 28px;
}

.stat-card {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  border-radius: 12px;
  background: white;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
  border-left: 5px solid;
}

.stat-card.green  { border-color: #16a34a; }
.stat-card.blue   { border-color: #3b82f6; }
.stat-card.orange { border-color: #f97316; }

.stat-icon { font-size: 28px; }
.stat-label { margin: 0; font-size: 22px; color: #6b7280; }
.stat-value { margin: 4px 0 0; font-size: 20px; font-weight: bold; color: #1e3a8a; }

.table-card {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 10px;
  border-bottom: 1px solid #f0f0f0;
  font-size: 14px;
  text-align: left;
}

th {
  background: #1e3a8a;
  color: white;
}

.badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 22px;
  font-weight: bold;
}

.badge.paid    { background: #dcfce7; color: #16a34a; }
.badge.pending { background: #fff7ed; color: #ea580c; }

.empty {
  text-align: center;
  padding: 30px;
  color: #9ca3af;
}
</style>