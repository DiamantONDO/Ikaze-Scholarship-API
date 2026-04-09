<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { supabase } from "@/utils/supabase";

const router = useRouter();

const applications = ref<any[]>([]);
const scholarships = ref<any[]>([]);
const students = ref<any[]>([]);

onMounted(async () => {
  const { data: appData } = await supabase
    .from("applications")
    .select("*")
    .order("date", { ascending: false });
  applications.value = appData || [];

  const scholarshipIds = [...new Set(applications.value.map(a => a.scholarship_id))];
  const studentIds = [...new Set(applications.value.map(a => a.student_id))];

  if (scholarshipIds.length > 0) {
    const { data: schData } = await supabase
      .from("scholarships")
      .select("id, title")
      .in("id", scholarshipIds);
    scholarships.value = schData || [];
  }

  if (studentIds.length > 0) {
    const { data: studentData } = await supabase
      .from("users")
      .select("id, full_name")
      .in("id", studentIds);
    students.value = studentData || [];
  }
});

const getScholarshipTitle = (id: number) => {
  const sch = scholarships.value.find(s => s.id === id);
  return sch ? sch.title : "Scholarship Closed";
};

const getStudentName = (id: number) => {
  const student = students.value.find(s => s.id === id);
  return student ? student.full_name : "Unknown";
};

const updateStatus = async (appId: number, status: "Approved" | "Rejected") => {
  const { error } = await supabase
    .from("applications")
    .update({ status, processed: true })
    .eq("id", appId);

  if (error) {
    console.error(error.message);
    return;
  }

  const app = applications.value.find(a => a.id === appId);
  if (app) {
    app.status = status;
    app.processed = true;
  }
};
</script>

<template>
  <div class="page">
    <h1 class="page-title">Applications Management</h1>
    <table>
      <thead>
        <tr>
          <th>Student</th>
          <th>Scholarship</th>
          <th>Application Date</th>
          <th>Status</th>
          <th>Details</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="app in applications" :key="app.id">
          <td>{{ getStudentName(app.student_id) }}</td>
          <td>{{ getScholarshipTitle(app.scholarship_id) }}</td>
          <td>{{ new Date(app.date).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" }) }}</td>
          <td>
            <span :class="['badge', app.status.toLowerCase()]">{{ app.status }}</span>
          </td>
          <td>
            <button class="detail-btn" @click="router.push(`/admin/applications/${app.id}`)">
              View Details
            </button>
          </td>
          <td>
            <span v-if="app.processed" class="already-processed">Already Processed</span>
            <template v-else-if="app.status === 'Pending'">
              <button class="approve-btn" @click="updateStatus(app.id, 'Approved')">Approve</button>
              <button class="reject-btn" @click="updateStatus(app.id, 'Rejected')">Reject</button>
            </template>
            <span v-else class="already-processed">Already Processed</span>
          </td>
        </tr>
        <tr v-if="applications.length === 0">
          <td colspan="6" class="empty">No applications yet.</td>
        </tr>
      </tbody>
    </table>
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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  text-align: left;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  font-size: 16px;
}

th {
  background: #1e3a8a;
  background: #f0f0f0;
}

.empty {
  text-align: center;
  padding: 30px;
  color: #9ca3af;
}

.badge{
  display: inline-block;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 16px;
  font-weight: 700;
}

.badge.approved{
  color: #16a34a;
}

.badge.rejected{
  color: #dc2626;
}

.badge.pending{
  color: #f0a835;  
}


button {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  margin-right: 6px;
}

button:hover {
  opacity: 0.9;
}

.already-processed{
  color: green;
  font-weight: bold;
}

.approve-btn{
  background: #16a34a;
  color: white;
}

.reject-btn{
  background: #dc2626;
  color: white;
}
</style>