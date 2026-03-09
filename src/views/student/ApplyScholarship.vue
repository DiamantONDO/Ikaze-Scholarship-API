<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getScholarships } from "@/utils/storage";  

const route = useRoute();
const router = useRouter();

const student = JSON.parse(sessionStorage.getItem("student_session") || "{}");
const scholarshipId = Number(route.params.scholarshipId);

const scholarship = ref<any>(null);

const fullName = ref(student.fullName);
const age = ref(student.age);
const sex = ref(student.sex);
const idCardRef = ref<HTMLInputElement | null>(null);
const equivalenceRef = ref<HTMLInputElement | null>(null);
const transcriptRef = ref<HTMLInputElement | null>(null);

onMounted(() => {
  const all = JSON.parse(localStorage.getItem("scholarships") || "[]");
  scholarship.value = all.find((s: any) => s.id === scholarshipId);

  // Redirect if already applied
  const apps = JSON.parse(localStorage.getItem("applications") || "[]");
  const alreadyApplied = apps.some(
    (a: any) => a.studentId === student.id && a.scholarshipId === scholarshipId
  );
  if (alreadyApplied) {
    alert("You have already applied for this scholarship.");
    router.push("/student/scholarships");
  }
});

const toBase64 = (file: File): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
};

const submitApplication = async() => {
  const idCard = idCardRef.value?.files?.[0];
  const equivalence = equivalenceRef.value?.files?.[0];
  const transcript = transcriptRef.value?.files?.[0];

  if (!fullName.value || !age.value || !sex.value || !idCard || !equivalence || !transcript) {
    alert("Please fill all fields and upload all required documents.");
    return;
  }

  const idCardBase64 = await toBase64(idCard);
  const equivalenceBase64 = await toBase64(equivalence);
  const transcriptBase64 = await toBase64(transcript);

  const applications = JSON.parse(localStorage.getItem("applications") || "[]");

  const newApp = {
    id: Date.now(),
    studentId: student.id,
    scholarshipId,
    status: "Pending",
    date: new Date().toISOString(),
    details: {
      fullName: fullName.value,
      age: age.value,
      sex: sex.value,
      idCard: {name: idCard.name, data: idCardBase64},
      equivalence: {name: equivalence.name, data: equivalenceBase64},
      transcript: {name: transcript.name, data: transcriptBase64},
    }
  };

  applications.push(newApp);
  localStorage.setItem("applications", JSON.stringify(applications));

  alert("Application submitted successfully!");
  router.push("/student/scholarships");
  
};
</script>

<template>
  <div class="page">

    <div class="banner" v-if="scholarship">
      <div>
        <h2>{{ scholarship.title }}</h2>
        <p>{{ scholarship.field }} — {{ scholarship.amount?.toLocaleString() }} RWF</p>
      </div>
      <span class="deadline">Deadline: {{ new Date(scholarship.deadline).toLocaleDateString("en-GB") }}</span>
    </div>

    <h1>Application Form</h1>

    <div class="form-card">

      <div class="section">
        <h3>Personal Information</h3>
        <div class="form-grid">
          <div class="form-group">
            <label>Full Name</label>
            <input v-model="fullName" type="text" placeholder="Full Name" readonly />
          </div>

          <div class="form-group">
            <label>Age</label>
            <input v-model="age" type="number" placeholder="Your Age" min="16" max="24" readonly />
          </div>

          <div class="form-group">
            <label>Sex</label>
            <input v-model="sex" type="text" placeholder="Sex" readonly />
          </div>
        </div>
      </div>

      <div class="section">
        <h3>Required Documents</h3>
        <div class="form-grid">

          <div class="form-group">
            <label>ID Card / Passport</label>
            <div class="file-box">
              <input type="file" ref="idCardRef" accept=".pdf,.jpg,.png" />
              <span class="file-hint">PDF, JPG or PNG</span>
            </div>
          </div>

          <div class="form-group">
            <label>Equivalence Document</label>
            <div class="file-box">
              <input type="file" ref="equivalenceRef" accept=".pdf,.jpg,.png" />
              <span class="file-hint">PDF, JPG or PNG</span>
            </div>
          </div>

          <div class="form-group">
            <label>Transcript</label>
            <div class="file-box">
              <input type="file" ref="transcriptRef" accept=".pdf,.jpg,.png" />
              <span class="file-hint">PDF, JPG or PNG</span>
            </div>
          </div>

        </div>
      </div>

      <div class="form-actions">
        <button class="cancel-btn" @click="router.push('/student/scholarships')">Cancel</button>
        <button class="submit-btn" @click="submitApplication">Submit Application →</button>
      </div>

    </div>
  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  max-width: 800px;
}

.banner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #1e3a8a;
  color: white;
  padding: 20px 24px;
  border-radius: 12px;
  margin-bottom: 24px;
  font-size: 21px;
}

.banner h2 { margin: 0 0 4px; }
.banner p  { margin: 0; opacity: 0.85; font-size: 14px; }

.deadline {
  background: rgba(255,255,255,0.15);
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 21px;
}

h1 { margin-bottom: 20px; }

.form-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.08);
  padding: 28px;
}

.section {
  margin-bottom: 28px;
}

.section h3 {
  font-size: 15px;
  color: #374151;
  border-bottom: 2px solid #e5e7eb;
  padding-bottom: 8px;
  margin-bottom: 16px;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group label {
  font-weight: bold;
  font-size: 13px;
  color: #374151;
}

.form-group input,
.form-group select {
  padding: 10px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
}

.file-box {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.file-hint {
  font-size: 11px;
  color: #9ca3af;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 10px;
}

.cancel-btn {
  padding: 10px 20px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  font-weight: bold;
  color: #374151;
}

.submit-btn {
  padding: 10px 24px;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  font-size: 15px;
}
</style>