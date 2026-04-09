<script setup lang="ts">
import { ref, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const students = ref<any[]>([]);

onMounted(async () => {
  const { data: userData } = await supabase
    .from("users")
    .select("id, full_name, email")
    .eq("role", "student");

  const { data: profileData } = await supabase
    .from("student_profiles")
    .select("id, phone, high_school, field, year");

  const profiles = profileData || [];

  students.value = (userData || []).map(u => ({
    ...u,
    ...profiles.find(p => p.id === u.id),
    status: "Active",
  }));
});

const toggleStatus = async (student: any) => {
  student.status = student.status === "Active" ? "Inactive" : "Active";
};
</script>

<template>
  <div class="page">
    <h1 class="page-title">Students Management</h1>

    <div class="table-card">
      <div class="summary-row">
        <div class="summary-item">
          <span class="summary-label">Total Students</span>
          <span class="summary-value">{{ students.length }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">Active</span>
          <span class="summary-value green">{{ students.filter(s => s.status !== "Inactive").length }}</span>
        </div>
        <div class="summary-item">
          <span class="summary-label">Inactive</span>
          <span class="summary-value red">{{ students.filter(s => s.status === "Inactive").length }}</span>
        </div>
      </div>

      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>University</th>
            <th>Field</th>
            <th>Year</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(student, index) in students" :key="student.id">
            <td>{{ index + 1 }}</td>
            <td>{{ student.full_name }}</td>
            <td>{{ student.email }}</td>
            <td>{{ student.phone }}</td>
            <td>{{ student.high_school }}</td>
            <td>{{ student.field }}</td>
            <td>{{ student.year }}</td>
            <td>
              <span :class="student.status === 'Inactive' ? 'badge inactive' : 'badge active'">
                {{ student.status }}
              </span>
            </td>
            <td>
              <button
                :class="student.status === 'Inactive' ? 'activate-btn' : 'deactivate-btn'"
                @click="toggleStatus(student)"
              >
                {{ student.status === "Inactive" ? "Activate" : "Deactivate" }}
              </button>
            </td>
          </tr>
          <tr v-if="students.length === 0">
            <td colspan="9" class="empty">No students registered yet.</td>
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
  margin-bottom: 20px;
}

.table-card {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
}

.summary-row {
  display: flex;
  gap: 94px;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.summary-item {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.summary-label {
  font-size: larger;
  color: #6b7280;
  gap: 14px;
}

.summary-value {
  font-size: 22px;
  font-weight: bold;
  color: #1e3a8a;
}

.summary-value.green { color: #16a34a; }
.summary-value.red   { color: #dc2626; }

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

.badge.active   { background: #dcfce7; color: #16a34a; }
.badge.inactive { background: #fee2e2; color: #dc2626; }

.activate-btn {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  font-size: 13px;
}

.deactivate-btn {
  background: #e24960;
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