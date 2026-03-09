<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const applicationId = Number(route.params.applicationId);

const application = ref<any>(null);
const student = ref<any>(null);
const scholarship = ref<any>(null);

onMounted(() => {
  const apps = JSON.parse(localStorage.getItem("applications") || "[]");
  application.value = apps.find((a: any) => a.id === applicationId);

  if (!application.value) return;

  const users = JSON.parse(localStorage.getItem("users") || "[]");
  student.value = users.find((u: any) => u.id === application.value.studentId);

  const scholarships = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarship.value = scholarships.find((s: any) => s.id === application.value.scholarshipId);
});

const updateStatus = (status: "Approved" | "Rejected") => {
  const apps = JSON.parse(localStorage.getItem("applications") || "[]");
  const app = apps.find((a: any) => a.id === applicationId);
  if (!app) return;
  app.status = status;
  localStorage.setItem("applications", JSON.stringify(apps));
  application.value.status = status;
};
</script>

<template>
  <div class="page" v-if="application">

    <div class="page-header">
      <button class="back-btn" @click="router.back()">← Back</button>
      <h1>Application Detail</h1>
    </div>

    <div :class="['status-banner', application.status.toLowerCase()]">
      Status: <strong>{{ application.status }}</strong>
    </div>

    <div class="grid-layout">

      <div class="detail-card">
        <h2>Student Information</h2>
        <div class="info-row"><label>Full Name</label><span>{{ application.details?.fullName }}</span></div>
        <div class="info-row"><label>Age</label><span>{{ application.details?.age }}</span></div>
        <div class="info-row"><label>Sex</label><span>{{ application.details?.sex }}</span></div>
        <div class="info-row"><label>Email</label><span>{{ student?.email }}</span></div>
        <div class="info-row"><label>Phone</label><span>{{ student?.phone }}</span></div>
        <div class="info-row"><label>University/College</label><span>{{ student?.high_school }}</span></div>
        <div class="info-row"><label>Field</label><span>{{ student?.field }}</span></div>
        <div class="info-row"><label>Year</label><span>{{ student?.year }}</span></div>
      </div>

      <div class="detail-card">
        <h2>Scholarship Information</h2>
        <div class="info-row"><label>Title</label><span>{{ scholarship?.title }}</span></div>
        <div class="info-row"><label>Field</label><span>{{ scholarship?.field }}</span></div>
        <div class="info-row"><label>Amount</label><span>{{ scholarship?.amount?.toLocaleString() }} RWF</span></div>
        <div class="info-row"><label>Deadline</label><span>{{ new Date(scholarship?.deadline).toLocaleDateString("en-GB") }}</span></div>
        <div class="info-row"><label>Applied On</label><span>{{ new Date(application.date).toLocaleDateString("en-GB") }}</span></div>
      </div>

      <div class="detail-card full-width">
        <h2>Submitted Documents</h2>
        <div class="docs-grid">

    <div class="doc-item">
      <div>
        <p class="doc-label">ID Card / Passport</p>
        <p class="doc-name">{{ application.details?.idCard?.name || "Not provided" }}</p>

        <a
        
          v-if="application.details?.idCard?.data"
          :href="application.details.idCard.data"
          target="_blank"
          class="view-link"
        >
          👁 View Document
        </a>
        <span v-else class="no-doc">Old Version</span>
      </div>
    </div>

    <div class="doc-item">
      <div>
        <p class="doc-label">Equivalence Document</p>
        <p class="doc-name">{{ application.details?.equivalence?.name || "Not provided" }}</p>
        <a
        
          v-if="application.details?.equivalence?.data"
          :href="application.details.equivalence.data"
          target="_blank"
          class="view-link"
        >
          👁 View Document
        </a>
        <span v-else class="no-doc">Old Version</span>
      </div>
    </div>

    <div class="doc-item">
      <div>
        <p class="doc-label">Transcript</p>
        <p class="doc-name">{{ application.details?.transcript?.name || "Not provided" }}</p>

        <a
        
          v-if="application.details?.transcript?.data"
          :href="application.details.transcript.data"
          target="_blank"
          class="view-link"
        >
          👁 View Document
        </a>
        <span v-else class="no-doc">Old Version</span>
      </div>
    </div>

  </div>
</div>

    </div>

    <div class="actions" v-if="application.status === 'Pending'">
      <button class="approve-btn" @click="updateStatus('Approved')">Approve</button>
      <button class="reject-btn" @click="updateStatus('Rejected')">Reject</button>
    </div>

  </div>

  <div v-else class="not-found">Application not found.</div>
</template>

<style scoped>
.page {
  padding: 36px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  max-width: 1200px;
  padding-top: 0px;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 20px;
}

.page-header h1 { margin: 0; }

.back-btn {
  background: none;
  border: 1px solid #d1d5db;
  padding: 8px 14px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  color: #374151;
}

.status-banner {
  padding: 12px 20px;
  border-radius: 10px;
  margin-bottom: 24px;
  font-size: 15px;
}

.status-banner.pending  { background: #fff7ed; color: #c2410c; }
.status-banner.approved { background: #dcfce7; color: #15803d; }
.status-banner.rejected { background: #fee2e2; color: #b91c1c; }

.grid-layout {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 24px;
}

.detail-card {
  background: white;
  border-radius: 12px;
  padding: 22px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.07);
  font-size: 21px;
}

.detail-card h2 {
  font-size: 21px;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #f0f0f0;
}

.full-width { grid-column: 1 / -1; }

.info-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #f9fafb;
  font-size: 21px;
}

.info-row label { color: #6b7280; }
.info-row span  { font-weight: 600; }

.docs-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.doc-item {
  display: flex;
  align-items: center;
  gap: 12px;
  background: #f9fafb;
  padding: 14px;
  border-radius: 10px;
  border: 1px solid #e5e7eb;
}

.doc-icon { font-size: 28px; }
.doc-label { margin: 0; font-size: 21px; color: #6b7280; }
.doc-name  { margin: 4px 0 0; font-weight: bold; font-size: 21px; color: #1e3a8a; }

.actions {
  display: flex;
  gap: 12px;
}

.approve-btn {
  padding: 12px 28px;
  background: #16a34a;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.reject-btn {
  padding: 12px 28px;
  background: #dc2626;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.not-found {
  padding: 60px;
  text-align: center;
  color: gray;
}

.view-link {
  display: inline-block;
  margin-top: 8px;
  font-size: 12px;
  color: #1e3a8a;
  font-weight: bold;
  text-decoration: none;
  background: #e0e7ff;
  padding: 5px 12px;
  border-radius: 6px;
}

.view-link:hover { background: #c7d2fe; }

.no-doc {
  font-size: 12px;
  color: #9ca3af;
  font-style: italic;
  margin-top: 6px;
  display: block;
}
</style>