<script setup lang="ts">
import { ref } from "vue";

interface Education {
  id: number;
  academicYear: string;
  level: string;
  gpa: number;
  status: string;
}

const educationRecords = ref<Education[]>([
  {
    id: 1,
    academicYear: "2021-2022",
    level: "Year 1",
    gpa: 3.5,
    status: "Passed"
  },
  {
    id: 2,
    academicYear: "2023-2024",
    level: "Year 2",
    gpa: 3.7,
    status: "Passed"
  },
  {
    id: 3,
    academicYear: "2025-2026",
    level: "Year 3",
    gpa: 0,
    status: "In Progress"
  }
]);
</script>

<template>
  <div>
    <h1 class="page-title">University Education</h1>

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th>Academic Year</th>
            <th>Level</th>
            <th>GPA</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="record in educationRecords" :key="record.id">
            <td>{{ record.academicYear }}</td>
            <td>{{ record.level }}</td>
            <td>
              {{ record.gpa === 0 ? "-" : record.gpa }}
            </td>
            <td>
              <span :class="record.status.toLowerCase().replace(' ', '-')">
                {{ record.status }}
              </span>
            </td>
          </tr>

          <tr v-if="educationRecords.length === 0">
            <td colspan="4" class="empty">
              No education records available.
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.table-card {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  border-bottom: 1px solid #e5e7eb;
}

th {
  background: #f9fafb;
  text-align: left;
}

.passed {
  color: green;
  font-weight: bold;
}

.in-progress {
  color: blue;
  font-weight: bold;
}

.empty {
  text-align: center;
  padding: 15px;
  color: gray;
}
</style>