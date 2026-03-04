<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

const sponsor = JSON.parse(sessionStorage.getItem("sponsor_session") || "null");

interface Application {
  id: number;
  studentId: number;
  scholarshipId: number;
  date: string;
  status: string;
}

interface Scholarship {
  id: number;
  sponsorId: number;
  title: string;
  amount: number;
}

interface Student {
  id: number;
  fullName: string;
  email: string;
}

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

const applications = ref<Application[]>([]);
const scholarships = ref<Scholarship[]>([]);
const students = ref<Student[]>([]);
const payments = ref<Payment[]>([]);

const selectedStudentId = ref<number | null>(null);
const selectedScholarshipId = ref<number | null>(null);
const selectedMonth = ref("");

const selectedDate = ref("");

const fetchData = () => {
  const allScholarships = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarships.value = allScholarships.filter((s: Scholarship) => s.sponsorId === sponsor.id);

  const allApplications: Application[] = JSON.parse(localStorage.getItem("applications") || "[]");
  const sponsorScholarshipIds = scholarships.value.map(s => s.id);

  // Only approved applications for this sponsor's scholarships
  applications.value = allApplications.filter(
    app => sponsorScholarshipIds.includes(app.scholarshipId) && app.status === "Approved"
  );

  const allUsers = JSON.parse(localStorage.getItem("users") || "[]");
  students.value = allUsers.filter((u: any) => u.role === "student");

  payments.value = JSON.parse(localStorage.getItem("payments") || "[]");
};

onMounted(fetchData);

// Get student by id
const getStudent = (id: number) => students.value.find(s => s.id === id);
const getScholarship = (id: number) => scholarships.value.find(s => s.id === id);

// Approved students with their scholarship info
const approvedEntries = computed(() =>
  applications.value
    .filter(app => app.scholarshipId === selectedScholarshipId.value)
    .map(app => ({
      app,
      student: getStudent(app.studentId),
      scholarship: getScholarship(app.scholarshipId)
    }))
);

// Selected scholarship amount
const selectedAmount = computed(() => {
  if (!selectedScholarshipId.value) return 0;
  return getScholarship(selectedScholarshipId.value)?.amount || 0;
});

const processPayment = () => {
  if (!selectedStudentId.value || !selectedScholarshipId.value || !selectedDate.value) {
    alert("Please select a scholarship, student and date.");
    return;
  }

  const alreadyPaid = payments.value.find(
    p =>
      p.studentId === selectedStudentId.value &&
      p.scholarshipId === selectedScholarshipId.value &&
      p.month === selectedDate.value
  );

  if (alreadyPaid) {
    alert("This student has already been paid for this date.");
    return;
  }

  const newPayment: Payment = {
    id: Date.now(),
    sponsorId: sponsor.id,
    studentId: selectedStudentId.value,
    scholarshipId: selectedScholarshipId.value,
    month: selectedDate.value,  // reusing month field to store date
    amount: selectedAmount.value,
    status: "paid",
    date: new Date().toISOString()
  };

  payments.value.push(newPayment);
  localStorage.setItem("payments", JSON.stringify(payments.value));

  alert(`Payment of ${selectedAmount.value.toLocaleString()} RWF processed successfully!`);

  selectedStudentId.value = null;
  selectedDate.value = "";
};

// Payment history
const sponsorPayments = computed(() =>
  payments.value.filter(p => p.sponsorId === sponsor.id)
);
</script>

<template>
  <div class="page">
    <h1>Payments</h1>

    <div class="form-card">
      <h2>Process New Payment</h2>

      <div class="form-grid">
        
        <div class="form-group">
          <label>Select Scholarship</label>
          <select v-model="selectedScholarshipId">
            <option disabled :value="null">-- Choose Scholarship --</option>
            <option v-for="sch in scholarships" :key="sch.id" :value="sch.id">
              {{ sch.title }}
            </option>
          </select>
        </div>


        <div class="form-group">
          <label>Select Student</label>
          <select v-model="selectedStudentId" :disabled="!selectedScholarshipId">
            <option disabled :value="null">
              {{ selectedScholarshipId ? "Choose Student" :"-- Choose Scholarship first --" }} 
            </option>
            <option
              v-for="entry in approvedEntries"
              :key="entry.app.id"
              :value="entry.student?.id"
            >
              {{ entry.student?.fullName }} — {{ entry.scholarship?.title }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>Payment Date</label>
          <input style="height: 58%;" type="date" v-model="selectedDate"/>
        </div>

        <div class="form-group">
          <label>Amount to Pay</label>
          <div class="amount-box">
            {{ selectedAmount.toLocaleString() }} RWF
          </div>
        </div>
      </div>

      <button class="pay-btn" @click="processPayment">
        Process Payment
      </button>
    </div>

    <div class="table-card">
      <h2>Payment History</h2>
      <table>
        <thead>
          <tr>
            <th>Student</th>
            <th>Scholarship</th>
            <th>Month</th>
            <th>Amount (RWF)</th>
            <th>Date</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="payment in sponsorPayments" :key="payment.id">
            <tr>
              <td>{{ getStudent(payment.studentId)?.fullName || "Unknown" }}</td>
              <td>{{ getScholarship(payment.scholarshipId)?.title || "Unknown" }}</td>
              <td>{{ payment.month }}</td>
              <td>{{ payment.amount.toLocaleString() }}</td>
              <td>{{ new Date(payment.date).toLocaleDateString() }}</td>
              <td class="paid">{{ payment.status.toUpperCase() }}</td>
            </tr>
          </template>
          <tr v-if="sponsorPayments.length === 0">
            <td colspan="6" style="text-align:center; color:gray;">No payments yet.</td>
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
  padding-top: 0px;
}

.form-card, .table-card {
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  margin-bottom: 30px;
  
}

.form-card h2, .table-card h2 {
  margin-bottom: 20px;
  color: #1e3a8a;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 21px;
}

.form-group label {
  font-weight: bold;
  font-size: 21px;
}

.form-group select {
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.amount-box {
  padding: 10px;
  background: #f0f4ff;
  border-radius: 8px;
  border: 1px solid #c7d2fe;
  font-size: 18px;
  font-weight: bold;
  color: #1e3a8a;
}

.pay-btn {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: bold;
  font-size: 15px;
  cursor: pointer;
}

.pay-btn:hover {
  background: #2d4fa3;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 10px;
  border: 1px solid #eee;
  text-align: left;
  font-size: 21px;
}

th {
  background: #1e3a8a;
  color: white;
}

td.paid {
  color: green;
  font-weight: bold;
}
</style>