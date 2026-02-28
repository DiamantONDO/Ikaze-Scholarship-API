<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

interface Scholarship {
  id: number;
  sponsorId: number;
  title: string;
  field: string;
  amount: number;
  deadline: string;
  status: "active" | "inactive";
}

interface Payment {
  id: number;
  sponsorId: number;
  amount: number;
  status: "paid" | "pending";
}

interface Application {
  id: number;
  scholarshipId: number;
  status: string;
}

const sponsor = JSON.parse(localStorage.getItem("sponsor_session") || "null");

const scholarships = ref<Scholarship[]>([]);
const payments = ref<Payment[]>([]);
const applications = ref<Application[]>([]);

const fetchScholarships = () => {
  const all = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarships.value = all.filter((s: Scholarship) => s.sponsorId === sponsor.id);

  const allPayments: Payment[] = JSON.parse(localStorage.getItem("payments") || "[]");
  payments.value = allPayments.filter(p => p.sponsorId === sponsor.id);

  const allApps: Application[] = JSON.parse(localStorage.getItem("applications") || "[]");
  const sponsorScholarshipIds = scholarships.value.map(s => s.id);
  applications.value = allApps.filter(a => sponsorScholarshipIds.includes(a.scholarshipId));
};

onMounted(fetchScholarships);


const totalScholarships = computed(() => scholarships.value.length);

const activeScholarships = computed(() =>
  scholarships.value.filter(s => s.status === "active").length
);

// Total funds = sum of all scholarship amounts
const availableFunds = computed(() =>
  scholarships.value.reduce((sum, s) => sum + s.amount, 0)
);

// Payments done = count of paid payments by this sponsor
const paymentsDone = computed(() => payments.value.filter(p => p.status === "paid").length);

// Pending = accepted applications that have no payment yet
const pendingPayments = computed(() =>
  applications.value.filter(app => {
    if (app.status !== "accepted") return false;
    return !payments.value.find(p => p.sponsorId === sponsor.id);
  }).length
);

const getStatus = (deadline: string) =>
  new Date(deadline) >= new Date() ? "Active" : "Closed";



const closedScholarships = computed(() =>
  scholarships.value.filter(s => s.status === "inactive").length
);

const deleteScholarship = (id: number) => {
  let all = JSON.parse(localStorage.getItem("scholarships") || "[]");
  all = all.filter((s: Scholarship) => s.id !== id);
  localStorage.setItem("scholarships", JSON.stringify(all));
  fetchScholarships();
};
</script>

<template>
  <div class="dashboard">

    <h1 class="page-title">Sponsor Dashboard</h1>

    <div class="grid">

      <div class="card">
        <h3>Total Scholarships</h3>
        <p>{{ totalScholarships }}</p>
      </div>

      <div class="card">
        <h3>Active</h3>
        <p>{{ activeScholarships }}</p>
      </div>
      
      <div class="card">
        <h3>Available Funds</h3>
        <p>15,000,995 RWF</p>
      </div>

      <div class="card">
        <h3>Total Scholarship Amount</h3>
        <p>{{ availableFunds.toLocaleString() }} RWF</p>
      </div>

      <div class="card">
        <h3>Payments Done</h3>
        <p>{{ paymentsDone }}</p>
      </div>

      <div class="card">
        <h3>Pending Payments</h3>
        <p>{{ pendingPayments }}</p>
      </div>

    </div>

    <!--Scholarships Table-->
    <div class="table-container">
      <div class="table-header">
        <h2>My Scholarships</h2>
        <RouterLink to="/sponsor/post" class="add-btn">
          + Post New
        </RouterLink>
      </div>

      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Field</th>
            <th>Amount (RWF)</th>
            <th>Deadline</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="scholarship in scholarships" :key="scholarship.id">
            <td>{{ scholarship.title }}</td>
            <td>{{ scholarship.field }}</td>
            <td>{{ scholarship.amount.toLocaleString() }}</td>
            <td>{{ scholarship.deadline }}</td>
            <td>
              <span
                :class="getStatus(scholarship.deadline) === 'Active'
                  ? 'active'
                  : 'closed'"
              >
                {{ getStatus(scholarship.deadline) }}
              </span>
            </td>
          </tr>

          <tr v-if="scholarships.length === 0">
            <td colspan="6" class="empty">
              No scholarships posted yet.
            </td>
          </tr>
        </tbody>
      </table>

    </div>

  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
}

.page-title {
  font-size: 26px;
  margin-bottom: 20px;
  color: #1e3a8a;
}

/* Stats Section */
.grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  
}

.card {
  flex: 1 1 calc(33.333% - 20px); 
  box-sizing: border-box;
  background-color: rgb(169, 236, 226);
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
  text-align: center;
}

.card h3 {
  font-size: 16px;
  margin-bottom: 10px;
}

.card p {
  font-size: 24px;
  font-weight: bold;
  color: #1e3a8a;
}

/* Table */
.table-container {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.add-btn {
  background: #1e3a8a;
  color: white;
  padding: 8px 15px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: bold;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background: #f3f4f6;
}

.active {
  color: green;
  font-weight: bold;
}

.closed {
  color: red;
  font-weight: bold;
}

.delete-btn {
  background: red;
  color: white;
  border: none;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}

.empty {
  text-align: center;
  padding: 20px;
  color: gray;
}
</style>