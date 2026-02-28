<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

const student = JSON.parse(localStorage.getItem("student_session") || "null");

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

interface Scholarship {
  id: number;
  title: string;
  amount: number;
  field: string;
}

interface Sponsor {
  id: number;
  fullName: string;
}

const payments = ref<Payment[]>([]);
const scholarships = ref<Scholarship[]>([]);
const sponsors = ref<Sponsor[]>([]);

onMounted(() => {
  const allPayments: Payment[] = JSON.parse(localStorage.getItem("payments") || "[]");
  payments.value = allPayments.filter(p => p.studentId === student?.id);

  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");

  const allUsers = JSON.parse(localStorage.getItem("users") || "[]");
  sponsors.value = allUsers.filter((u: any) => u.role === "sponsor");
});

const getScholarship = (id: number) => scholarships.value.find(s => s.id === id);
const getSponsor = (id: number) => sponsors.value.find(s => s.id === id);

// Summary stats
const totalReceived = computed(() =>
  payments.value
    .filter(p => p.status === "paid")
    .reduce((sum, p) => sum + p.amount, 0)
);

const totalPaid = computed(() => payments.value.filter(p => p.status === "paid").length);
const totalPending = computed(() => payments.value.filter(p => p.status === "pending").length);

// Group payments by scholarship
const groupedPayments = computed(() => {
  const groups: Record<number, { scholarship: Scholarship | undefined; payments: Payment[] }> = {};

  payments.value.forEach(p => {
    if (!groups[p.scholarshipId]) {
      groups[p.scholarshipId] = {
        scholarship: getScholarship(p.scholarshipId),
        payments: []
      };
    }
    groups[p.scholarshipId]!.payments.push(p);
  });

  return Object.values(groups);
});
</script>

<template>
  <div class="page">
    <h1 class="page-title">My Payments</h1>

    <!-- Summary Cards -->
    <div class="stats-row">
      <div class="stat-card green">
        <div class="stat-icon"></div>
        <div>
          <p class="stat-label">Total Received</p>
          <p class="stat-value">{{ totalReceived.toLocaleString() }} RWF</p>
        </div>
      </div>

      <div class="stat-card blue">
        <div class="stat-icon"></div>
        <div>
          <p class="stat-label">Payments Done</p>
          <p class="stat-value">{{ totalPaid }}</p>
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

    <!-- No payments state -->
    <div v-if="payments.length === 0" class="empty-state">
      <p>No payments recorded yet.</p>
    </div>

    <!-- Grouped by Scholarship -->
    <div
      v-for="group in groupedPayments"
      :key="group.scholarship?.id"
      class="group-card"
    >
      <!-- Scholarship Header -->
      <div class="group-header">
        <div>
          <h2>{{ group.scholarship?.title || "Unknown Scholarship" }}</h2>
          <span class="field-badge">{{ group.scholarship?.field }}</span>
        </div>
        <div class="group-meta">
          <span> {{ group.scholarship?.amount.toLocaleString() }} RWF / month</span>
        </div>
      </div>

      <!-- Payments Table -->
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Sponsor</th>
            <th>Payment Date</th>
            <th>Amount (RWF)</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(payment, index) in group.payments" :key="payment.id">
            <td>{{ index + 1 }}</td>
            <td>{{ getSponsor(payment.sponsorId)?.fullName || "Unknown" }}</td>
            <td>{{ new Date(payment.month).toLocaleDateString("en-GB", {
              day: "2-digit", month: "long", year: "numeric"
            }) }}</td>
            <td>{{ payment.amount.toLocaleString() }}</td>
            <td>
              <span :class="['badge', payment.status]">
                {{ payment.status === "paid" ? "Paid" : "Pending" }}
              </span>
            </td>
          </tr>
        </tbody>

        <!-- Per-scholarship subtotal -->
        <tfoot>
          <tr class="subtotal-row">
            <td colspan="3">Subtotal Received</td>
            <td colspan="2">
              {{
                group.payments
                  .filter(p => p.status === "paid")
                  .reduce((sum, p) => sum + p.amount, 0)
                  .toLocaleString()
              }} RWF
            </td>
          </tr>
        </tfoot>
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
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 24px;
}

/* Stats Row */
.stats-row {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
  background: white;
  border-left: 5px solid;
}

.stat-card.green { border-color: #22c55e; }
.stat-card.blue  { border-color: #3b82f6; }
.stat-card.orange { border-color: #f97316; }

.stat-icon {
  font-size: 28px;
}

.stat-label {
  font-size: 12px;
  color: #6b7280;
  margin: 0;
  font-size: larger;
}

.stat-value {
  font-size: 22px;
  font-weight: bold;
  color: #1e3a8a;
  margin: 4px 0 0;
}

/* Group Card */
.group-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
  margin-bottom: 28px;
  overflow: hidden;
}

.group-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px 24px;
  background: #1e3a8a;
  color: white;
}

.group-header h2 {
  margin: 0 0 6px;
  font-size: 17px;
}

.group-meta {
  font-size: 14px;
  opacity: 0.9;
}

.field-badge {
  background: rgba(255,255,255,0.2);
  padding: 2px 10px;
  border-radius: 20px;
  font-size: 12px;
}

/* Table */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  font-size: 14px;
}

th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
}

/* Subtotal Row */
.subtotal-row td {
  background: #f0f4ff;
  font-weight: bold;
  color: #1e3a8a;
  border-top: 2px solid #c7d2fe;
}

/* Badge */
.badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: bold;
}

.badge.paid {
  background: #dcfce7;
  color: #16a34a;
}

.badge.pending {
  background: #fff7ed;
  color: #ea580c;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 60px;
  background: white;
  border-radius: 12px;
  color: #9ca3af;
  font-size: 16px;
}
</style>