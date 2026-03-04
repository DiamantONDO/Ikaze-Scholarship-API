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

const totalScholarships = computed(() =>
  scholarships.value.filter(s => s.status === "active").length
);

const myApplications = computed(() =>
  applications.value.filter(a => Number(a.studentId) === Number(student?.id))
);

const totalApplications = computed(() => myApplications.value.length);

const approvedApplications = computed(() =>
  myApplications.value.filter(a => a.status === "Approved").length
);

const pendingApplications = computed(() =>
  myApplications.value.filter(a => a.status === "Pending").length
);

const myPayments = computed(() =>
  payments.value.filter(p => Number(p.studentId) === Number(student?.id))
);

const totalPayments = computed(() =>
  myPayments.value
    .filter(p => p.status === "paid")//in lower case
    .reduce((sum, p) => sum + p.amount, 0)
);

const paymentsCount = computed(() =>
  myPayments.value.filter(p => p.status === "paid").length
);

const lastPayment = computed(() => {
  const paid = myPayments.value.filter(p => p.status === "paid");
  if (paid.length === 0) return null;
  return paid.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())[0];
});

const getScholarshipTitle = (id: number) => {
  const sch = scholarships.value.find(s => Number(s.id) === Number(id));
  return sch ? sch.title : "Unknown";
};
</script>

<template>
  <div class="page">
    <h1 class="page-title">Student Dashboard</h1>

    <div class="welcome-banner">
      <div>
        <h2>Welcome back, {{ student?.fullName }}</h2>
        <p>{{ student?.high_school }} — {{ student?.field }}, {{ student?.year }}</p>
      </div>
    </div>

    <div class="cards">
      <div class="card blue">
        <div>
          <h3>Active Scholarships</h3>
          <p>{{ totalScholarships }}</p>
        </div>
      </div>

      <div class="card navy">
        <div>
          <h3>My Applications</h3>
          <p>{{ totalApplications }}</p>
        </div>
      </div>

      <div class="card green">
        <div>
          <h3>Total Received (RWF)</h3>
          <p>{{ totalPayments.toLocaleString() }}</p>
        </div>
      </div>
    </div>

    <div class="cards secondary">
      <div class="mini-card">
        <span class="mini-label">Pending Applications</span>
        <span class="mini-value orange">{{ pendingApplications }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Approved Applications</span>
        <span class="mini-value green">{{ approvedApplications }}</span>
      </div>
      <div class="mini-card">
        <span class="mini-label">Payments Received</span>
        <span class="mini-value blue">{{ paymentsCount }}</span>
      </div>
    </div>

    <div class="last-payment" v-if="lastPayment">
      <h3>Last Payment</h3>
      <div class="last-payment-row">
        <div>
          <p class="lp-label">Scholarship</p>
          <p class="lp-value">{{ getScholarshipTitle(lastPayment.scholarshipId) }}</p>
        </div>
        <div>
          <p class="lp-label">Amount</p>
          <p class="lp-value">{{ lastPayment.amount.toLocaleString() }} RWF</p>
        </div>
        <div>
          <p class="lp-label">Date</p>
          <p class="lp-value">{{ new Date(lastPayment.date).toLocaleDateString("en-GB") }}</p>
        </div>
        <div>
          <p class="lp-label">Status</p>
          <span class="badge paid">Paid</span>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  padding-top: 0;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
}

.page-title {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #1e3a8a;
}

.welcome-banner {
  background: #3b82f6;
  color: white;
  padding: 20px 24px;
  border-radius: 12px;
  margin-bottom: 24px;
}

.welcome-banner h2 {
  margin: 0 0 6px;
  font-size: 20px;
  font-weight: bold;
}

.welcome-banner p {
  margin: 0;
  opacity: 0.85;
  font-size: 14px;
}

.cards {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  height: 140px;
  font-size: 21px;
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
.card.navy  { background: #16a34a; }
.card.green { background: #1e3a8a; }

.cards.secondary { margin-bottom: 24px; }

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

.mini-label { color: #374151; font-size: 16px;}
.mini-value { font-size: 22px; font-weight: bold; }
.mini-value.orange { color: #f97316; }
.mini-value.green  { color: #16a34a; }
.mini-value.blue   { color: #3b82f6; }

.last-payment {
  background: white;
  padding: 20px 55px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
  font-size: 26px;
}

.last-payment h3 {
  margin: 0 0 16px;
  font-size: 16px;
  color: #1e3a8a;
  font-size: 21px;
}

.last-payment-row {
  display: flex;
  gap: 180px;
  align-items: center;
}

.lp-label {
  margin: 0;
  font-size: 12px;
  color: #6b7280;
  font-size: 26px;
}

.lp-value {
  margin: 4px 0 0;
  font-weight: bold;
  font-size: 15px;
  color: #1e3a8a;
  font-size: 19px;
}

.badge.paid {
  background: #dcfce7;
  color: #16a34a;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 21px;
  font-weight: bold;
}
</style>