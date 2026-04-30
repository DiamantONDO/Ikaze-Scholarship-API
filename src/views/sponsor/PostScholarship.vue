<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { supabase } from "@/utils/supabase";

const router = useRouter();

const sponsor = JSON.parse(sessionStorage.getItem("sponsor_session") || "{}");

const title = ref("");
const field = ref("");
const amount = ref("");
const deadline = ref("");
const description = ref("");
const requirements = ref<string[]>([]);
const newRequirement = ref("");
const status = ref("active");
const errorMessage = ref("");

const fieldList = [
  "Science", "Business", "Marketing", "Economy",
  "MBC", "Science (MBC)", "Engineering", "Medicine",
  "Law", "Education", "Technology", "Agriculture", "Other"
];

const addRequirement = () => {
  const trimmed = newRequirement.value.trim();
  if (!trimmed) return;
  requirements.value.push(trimmed);
  newRequirement.value = "";
};

const removeRequirement = (index: number) => {
  requirements.value.splice(index, 1);
};

const submitScholarship = async () => {
  errorMessage.value = "";

  if (!title.value || !field.value || !amount.value || !deadline.value || !description.value) {
    errorMessage.value = "Please fill in all fields.";
    return;
  }

  if (requirements.value.length === 0) {
    errorMessage.value = "Please add at least one requirement.";
    return;
  }

  const newScholarship = {
    id: Date.now(),
    sponsor_id: sponsor.id,
    title: title.value,
    field: field.value,
    amount: Number(amount.value),
    deadline: deadline.value,
    description: description.value,
    requirements: requirements.value,
    status: status.value,
  };

  const { error } = await supabase.from("scholarships").insert(newScholarship);

  if (error) {
    errorMessage.value = "Failed to post scholarship. Please try again.";
    console.error(error.message);
    return;
  }

  alert("Scholarship posted successfully!");
  router.push("/sponsor/scholarships");
};
</script>

<template>
  <div class="page">
    <h1>Post New Scholarship</h1>

    <div class="error-box" v-if="errorMessage">{{ errorMessage }}</div>

    <div class="form-card">

      <div class="form-group">
        <label>Title</label>
        <input type="text" v-model="title" placeholder="Scholarship Title" />
      </div>

      <div class="form-group">
        <label>Field of Study</label>
        <select v-model="field">
          <option disabled value="">Select field of study</option>
          <option v-for="option in fieldList" :key="option" :value="option">
            {{ option }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Amount (RWF)</label>
        <input type="number" v-model="amount" placeholder="Scholarship Amount" />
      </div>

      <div class="form-group">
        <label>Deadline</label>
        <input type="date" v-model="deadline" />
      </div>

      <div class="form-group">
        <label>Description</label>
        <textarea
          v-model="description"
          placeholder="Describe the scholarship, its purpose, and who it targets..."
          rows="4"
        ></textarea>
      </div>

      <div class="form-group">
        <label>Requirements</label>
        <div class="requirement-input-row">
          <input
            type="text"
            v-model="newRequirement"
            placeholder="e.g. Minimum GPA of 3.5"
            @keydown.enter.prevent="addRequirement"
          />
          <button class="add-btn" @click="addRequirement">+ Add</button>
        </div>

        <ul class="requirements-list" v-if="requirements.length > 0">
          <li v-for="(req, index) in requirements" :key="index" class="req-item">
            <span>{{ req }}</span>
            <button class="remove-btn" @click="removeRequirement(index)">✕</button>
          </li>
        </ul>
        <p class="req-hint" v-else>No requirements added yet. Press Enter or click Add.</p>
      </div>

      <div class="form-group">
        <label>Status</label>
        <select v-model="status">
          <option value="active">Active</option>
          <option value="inactive">Inactive</option>
        </select>
      </div>

      <button class="submit-btn" @click="submitScholarship">Post Scholarship</button>

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

.form-card {
  background: #fff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  max-width: 940px;
  margin-top: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 18px;
}

.form-group label {
  margin-bottom: 6px;
  font-weight: bold;
  font-size: 21px;
}

.form-group input,
.form-group select,
.form-group textarea {
  padding: 10px 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 21px;
  font-family: inherit;
  resize: vertical;
}

.requirement-input-row {
  display: flex;
  gap: 8px;
}

.requirement-input-row input {
  flex: 1;
  padding: 10px 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 21px;
}

.add-btn {
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  white-space: nowrap;
}

.requirements-list {
  list-style: none;
  padding: 0;
  margin: 10px 0 0;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.req-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f0f4ff;
  border: 1px solid #c7d2fe;
  padding: 8px 12px;
  border-radius: 8px;
  font-size: 21px;
  color: #1e3a8a;
}

.remove-btn {
  background: none;
  border: none;
  color: #dc2626;
  font-size: 21px;
  cursor: pointer;
  font-weight: bold;
  padding: 0 4px;
}

.req-hint {
  margin-top: 8px;
  font-size: 19px;
  color: #9ca3af;
}

.submit-btn {
  background-color: #1e3a8a;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 10px;
  font-size: 21px;
}

.submit-btn:hover {
  background-color: #2d4fa3;
}
</style>