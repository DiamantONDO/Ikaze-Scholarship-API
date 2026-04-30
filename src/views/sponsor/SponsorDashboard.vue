<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const sponsor = JSON.parse(sessionStorage.getItem("sponsor_session") || "null");

const scholarships = ref<any[]>([]);
const payments = ref<any[]>([]);
const applications = ref<any[]>([]);

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
  }

  const { data: paymentData } = await supabase
    .from("payments")
    .select("*")
    .eq("sponsor_id", sponsor.id);
  payments.value = paymentData || [];
});

const totalScholarships = computed(() => scholarships.value.length);
const activeScholarships = computed(() => scholarships.value.filter(s => s.status === "active").length);
const totalApplications = computed(() => applications.value.length);
const availableFunds = computed(() => scholarships.value.reduce((sum, s) => sum + s.amount, 0));
const paymentsDone = computed(() => payments.value.filter(p => p.status === "paid").length);
const totalAmountPaid = computed(() =>
  payments.value.filter(p => p.status === "paid").reduce((sum, p) => sum + p.amount, 0)
);
const pendingApplications = computed(() => applications.value.filter(a => a.status === "Pending").length);
const approvedApplications = computed(() => applications.value.filter(a => a.status === "Approved").length);

const getStatus = (deadline: string) =>
  new Date(deadline) >= new Date() ? "Active" : "Closed";

const recentScholarships = computed(() =>
  [...scholarships.value].reverse().slice(0, 5)
);
</script>

<template>
  <div class="dashboard">
    <h1 class="page-title">Sponsor Dashboard</h1>

    <div class="stats">
      <div class="card blue">
        <div class="card-icon"></div>
        <div>
          <h3>Total Scholarships</h3>
          <p>{{ totalScholarships }}</p>
        </div>
      </div>
      <div class="card green">
        <div class="card-icon"></div>
        <div>
          <h3>Active</h3>
          <p>{{ activeScholarships }}</p>
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
          <h3>Total Amount Paid</h3>
          <p>{{ totalAmountPaid.toLocaleString() }} RWF</p>
        </div>
      </div>
    </div>

    <div class="stats secondary">
      <div class="mini-card">
        <span class="mini-label">Scholarship Funds</span>
        <span class="mini-value navy-text">{{ availableFunds.toLocaleString() }} RWF</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Payments Done</span>
        <span class="mini-value green-text">{{ paymentsDone }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Pending Applications</span>
        <span class="mini-value orange-text">{{ pendingApplications }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Approved Applications</span>
        <span class="mini-value blue-text">{{ approvedApplications }}</span>
      </div>
    </div>

    <div class="table-section">
      <div class="table-header">
        <h2>My Scholarships</h2>
        <RouterLink to="/sponsor/post" class="add-btn">+ Post New</RouterLink>
      </div>
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Field</th>
            <th>Amount (RWF)</th>
            <th>Deadline</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(scholarship, index) in recentScholarships" :key="scholarship.id">
            <td>{{ index + 1 }}</td>
            <td>{{ scholarship.title }}</td>
            <td>{{ scholarship.field }}</td>
            <td>{{ scholarship.amount.toLocaleString() }}</td>
            <td>{{ new Date(scholarship.deadline).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" }) }}</td>
            <td>
              <span :class="['badge', getStatus(scholarship.deadline).toLowerCase()]">
                {{ getStatus(scholarship.deadline) }}
              </span>
            </td>
          </tr>
          <tr v-if="scholarships.length === 0">
            <td colspan="6" class="empty">No scholarships posted yet.</td>
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
  font-size: 22px;
  opacity: 0.85;
}

.card p {
  margin: 0;
  font-size: 38px;
  font-weight: bold;
}

.card.blue  { background: #3b82f6; }
.card.green { background: #3b82f6; }
.card.orange { background: #16a34a; }
.card.navy  { background: #1e3a8a; }

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
  padding: 16px 15px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  font-size: 14px;
}

.mini-label { color: #374151; font-size: 18px;}
.mini-value { font-size: 20px; font-weight: bold; }
.navy-text   { color: #1e3a8a; font-size: 21px;}
.green-text  { color: #16a34a; }
.orange-text { color: #f97316; }
.blue-text   { color: #3b82f6; }

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

.add-btn {
  background: #1e3a8a;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: bold;
  font-size: 14px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 10px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  font-size: 21px;
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

.badge.active { background: #dcfce7; color: #16a34a; }
.badge.closed { background: #fee2e2; color: #dc2626; }

.empty {
  text-align: center;
  padding: 20px;
  color: gray;
}
</style>