<script setup lang="ts">
import { ref, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const student = JSON.parse(sessionStorage.getItem("student_session") || "null");
const applications = ref<any[]>([]);

onMounted(async () => {
  if (!student) return;

  const { data: appData } = await supabase
    .from("applications")
    .select("*")
    .eq("student_id", student.id);

  if (!appData) return;

  const scholarshipIds = appData.map(a => a.scholarship_id);

  const { data: scholarshipData } = await supabase
    .from("scholarships")
    .select("id, title, sponsor_id")
    .in("id", scholarshipIds);

  const sponsorIds = [...new Set((scholarshipData || []).map(s => s.sponsor_id))];

  const { data: sponsorData } = await supabase
    .from("users")
    .select("id, full_name")
    .in("id", sponsorIds);

  applications.value = appData.map(app => {
    const scholarship = (scholarshipData || []).find(s => s.id === app.scholarship_id);
    const sponsor = (sponsorData || []).find(s => s.id === scholarship?.sponsor_id);
    return {
      id: app.id,
      title: scholarship?.title || "Unknown",
      sponsor: sponsor?.full_name || "Unknown",
      status: app.status,
    };
  });
});

const statusColor = (status: string) => {
  if (status === "Approved") return "green";
  if (status === "Rejected") return "red";
  return "orange";
};
</script>

<template>
  <div class="page">
    <h1>My Applications</h1>
    <div v-if="applications.length === 0" class="no-apps">
      You have not applied to any scholarships yet.
    </div>
    <table v-else class="applications-table">
      <thead>
        <tr>
          <th>Scholarship</th>
          <th>Sponsor</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="app in applications" :key="app.id">
          <td>{{ app.title }}</td>
          <td>{{ app.sponsor }}</td>
          <td>
            <span class="status-badge" :style="{ backgroundColor: statusColor(app.status) }">
              {{ app.status }}
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  color: #1e3a8a;
  font-family: Arial, Helvetica, sans-serif;
  padding-top: 0px;
  font-size: 21px;
}

.applications-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.applications-table th, .applications-table td {
  padding: 12px 15px;
  border-bottom: 1px solid #ddd;
  text-align: left;
}

.applications-table th {
  background-color: #f3f4f6;
  font-weight: bold;
}

.status-badge {
  padding: 4px 10px;
  border-radius: 12px;
  color: white;
  font-weight: bold;
  font-size: 14px;
}

.no-apps {
  margin-top: 20px;
  font-style: italic;
  color: gray;
}
</style>
