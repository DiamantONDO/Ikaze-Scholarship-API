<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const student = JSON.parse(sessionStorage.getItem("student_session") || "null");

const payments = ref<any[]>([]);
const scholarships = ref<any[]>([]);
const sponsors = ref<any[]>([]);

onMounted(async () => {
  const { data: paymentData } = await supabase
    .from("payments")
    .select("*")
    .eq("student_id", student?.id);

  payments.value = paymentData || [];

  const scholarshipIds = [...new Set(payments.value.map(p => p.scholarship_id))];
  if (scholarshipIds.length > 0) {
    const { data: scholarshipData } = await supabase
      .from("scholarships")
      .select("id, title, field, amount")
      .in("id", scholarshipIds);
    scholarships.value = scholarshipData || [];
  }

  const sponsorIds = [...new Set(payments.value.map(p => p.sponsor_id))];
  if (sponsorIds.length > 0) {
    const { data: sponsorData } = await supabase
      .from("users")
      .select("id, full_name")
      .in("id", sponsorIds);
    sponsors.value = sponsorData || [];
  }
});

const getScholarship = (id: number) => scholarships.value.find(s => s.id === id);
const getSponsor = (id: number) => sponsors.value.find(s => s.id === id);

const totalReceived = computed(() =>
  payments.value.filter(p => p.status === "paid").reduce((sum, p) => sum + p.amount, 0)
);
const totalPaid = computed(() => payments.value.filter(p => p.status === "paid").length);
const totalPending = computed(() => payments.value.filter(p => p.status === "pending").length);

const groupedPayments = computed(() => {
  const groups: Record<number, { scholarship: any; payments: any[] }> = {};
  payments.value.forEach(p => {
    if (!groups[p.scholarship_id]) {
      groups[p.scholarship_id] = {
        scholarship: getScholarship(p.scholarship_id),
        payments: [],
      };
    }
    groups[p.scholarship_id]!.payments.push(p);
  });
  return Object.values(groups);
});
</script>

<template>
  <div class="page">
    <h1 class="page-title">My Payments</h1>

    <div class="stats-row">
      <div class="stat-card green">
        <div>
          <p class="stat-label">Total Received</p>
          <p class="stat-value">{{ totalReceived.toLocaleString() }} RWF</p>
        </div>
      </div>
      <div class="stat-card blue">
        <div>
          <p class="stat-label">Payments Done</p>
          <p class="stat-value">{{ totalPaid }}</p>
        </div>
      </div>
      <div class="stat-card orange">
        <div>
          <p class="stat-label">Pending Payments</p>
          <p class="stat-value">{{ totalPending }}</p>
        </div>
      </div>
    </div>

    <div v-if="payments.length === 0" class="empty-state">
      <p>No payments recorded yet.</p>
    </div>

    <div v-for="group in groupedPayments" :key="group.scholarship?.id" class="group-card">
      <div class="group-header">
        <div>
          <h2>{{ group.scholarship?.title || "Unknown Scholarship" }}</h2>
          <span class="field-badge">{{ group.scholarship?.field }}</span>
        </div>
        <div class="group-meta">
          <span>{{ group.scholarship?.amount?.toLocaleString() }} RWF / month</span>
        </div>
      </div>

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
            <td>{{ getSponsor(payment.sponsor_id)?.full_name || "Unknown" }}</td>
            <td>{{ new Date(payment.month).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" }) }}</td>
            <td>{{ payment.amount.toLocaleString() }}</td>
            <td>
              <span :class="['badge', payment.status]">
                {{ payment.status === "paid" ? "Paid" : "Pending" }}
              </span>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr class="subtotal-row">
            <td colspan="3">Subtotal Received</td>
            <td colspan="2">
              {{ group.payments.filter(p => p.status === "paid").reduce((sum, p) => sum + p.amount, 0).toLocaleString() }} RWF
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
  color: #1e3a8a;
  font-family: Arial, Helvetica, sans-serif;
  padding-top: 0px;
  padding-bottom: 0px;
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
  font-size: 21px;
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
  font-size: 19px;
  opacity: 0.9;
}

.field-badge {
  background: rgba(255,255,255,0.2);
  padding: 2px 10px;
  border-radius: 20px;
  font-size: 12px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  font-size: 24px;
}

th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
  font-size: 21px;
}

.subtotal-row td {
  background: #f0f4ff;
  font-weight: bold;
  color: #1e3a8a;
  border-top: 2px solid #c7d2fe;
}

.badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 21px;
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