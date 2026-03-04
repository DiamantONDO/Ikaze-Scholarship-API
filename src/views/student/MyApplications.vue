<script setup lang="ts">
import { ref, onMounted } from "vue";

const student = JSON.parse(sessionStorage.getItem("student_session") || "null");

const applications = ref<any[]>([]);

onMounted(() => {
  if (!student) {
    console.error("No logged-in student found.");
    return;
  }

  const storedApps = JSON.parse(localStorage.getItem("applications") || "[]");
  const storedScholarships = JSON.parse(localStorage.getItem("scholarships") || "[]");
  const storedSponsors = JSON.parse(localStorage.getItem("users") || "[]")
  .filter((user: any) => user.role === "sponsor");

  applications.value = storedApps
    .filter((app: any) => app.studentId === student.id)
    .map((app: any) => {
      const scholarship = storedScholarships.find(
        (s: any) => s.id === app.scholarshipId
      );

      const sponsor = storedSponsors.find(
        (sp: any) => sp.id === scholarship?.sponsorId
      );

      return {
        id: app.id,
        title: scholarship?.title || "Unknown",
        sponsor: sponsor?.fullName || "Unknown",
        status: app.status
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
        <tr v-for="app in applications" :key="app.scholarship_id">
          <td>{{ app.title }}</td>
          <td>{{ app.sponsor }}</td>
          <td>
            <span class="status-badge" :style="{ backgroundColor: statusColor(app.status) }" :title="app.status">
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
