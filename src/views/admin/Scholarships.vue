<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const scholarships = ref<any[]>([]);
const sponsors = ref<any[]>([]);

onMounted(async () => {
  const { data: schData } = await supabase
    .from("scholarships")
    .select("*")
    .order("created_at", { ascending: false });
  scholarships.value = schData || [];

  const sponsorIds = [...new Set(scholarships.value.map(s => s.sponsor_id))];
  if (sponsorIds.length > 0) {
    const { data: sponsorData } = await supabase
      .from("users")
      .select("id, full_name")
      .in("id", sponsorIds);
    sponsors.value = sponsorData || [];
  }
});

const getSponsorName = (sponsorId: number) => {
  const sponsor = sponsors.value.find(u => u.id === sponsorId);
  return sponsor ? sponsor.full_name : "Unknown";
};

const getStatus = (deadline: string) =>
  new Date(deadline) >= new Date() ? "Active" : "Closed";

const totalActive = computed(() =>
  scholarships.value.filter(s => s.status === "active").length
);
const totalClosed = computed(() =>
  scholarships.value.filter(s => s.status === "inactive").length
);

const deleteScholarship = async (id: number) => {
  if (!confirm("Are you sure you want to delete this scholarship?")) return;
  const { error } = await supabase.from("scholarships").delete().eq("id", id);
  if (error) { console.error(error.message); return; }
  scholarships.value = scholarships.value.filter(s => s.id !== id);
};
</script>

<template>
  <div class="page">
    <h1 class="page-title">Scholarships Management</h1>

    <div class="stats-row">
      <div class="stat-card navy">
        <div><p class="stat-label">Total Scholarships</p><p class="stat-value">{{ scholarships.length }}</p></div>
      </div>
      <div class="stat-card green">
        <div><p class="stat-label">Active</p><p class="stat-value">{{ totalActive }}</p></div>
      </div>
      <div class="stat-card red">
        <div><p class="stat-label">Inactive</p><p class="stat-value">{{ totalClosed }}</p></div>
      </div>
    </div>

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Sponsor</th>
            <th>Field</th>
            <th>Amount (RWF)</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(sch, index) in scholarships" :key="sch.id">
            <td>{{ index + 1 }}</td>
            <td>{{ sch.title }}</td>
            <td>{{ getSponsorName(sch.sponsor_id) }}</td>
            <td>{{ sch.field }}</td>
            <td>{{ sch.amount.toLocaleString() }}</td>
            <td>{{ new Date(sch.deadline).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" }) }}</td>
            <td>
              <span :class="['badge', getStatus(sch.deadline).toLowerCase()]">
                {{ getStatus(sch.deadline) }}
              </span>
            </td>
            <td>
              <button class="delete-btn" @click="deleteScholarship(sch.id)">Delete</button>
            </td>
          </tr>
          <tr v-if="scholarships.length === 0">
            <td colspan="8" class="empty">No scholarships available.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.page {
  padding: 36px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  padding-top: 0px;
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

.stat-card.navy  { border-color: #1e3a8a; }
.stat-card.green { border-color: #16a34a; }
.stat-card.red   { border-color: #dc2626; }

.stat-icon { font-size: 28px; }
.stat-label { margin: 0; font-size: 22px; color: #6b7280; }
.stat-value { margin: 4px 0 0; font-size: 22px; font-weight: bold; color: #1e3a8a; }

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
  font-size: 16px;
  text-align: left;
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

.delete-btn {
  background: #dc2626;
  color: white;
  border: none;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  font-size: 13px;
}

.empty {
  text-align: center;
  padding: 30px;
  color: #9ca3af;
}
</style>