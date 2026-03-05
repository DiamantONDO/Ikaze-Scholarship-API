<script setup lang="ts">
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const student = JSON.parse(sessionStorage.getItem("student_session") || "{}");
const users = ref<any[]>([]);

const scholarships = ref<any[]>([]);
const applications = ref<any[]>([]);

onMounted(() => {
  scholarships.value = JSON.parse(localStorage.getItem("scholarships") || "[]");
  applications.value = JSON.parse(localStorage.getItem("applications") || "[]");
    users.value = JSON.parse(localStorage.getItem("users") || "[]");

});

const getSponsorName = (sponsorId: number) => {
  const sponsor = users.value.find((u) => u.id === sponsorId);
  return sponsor ? sponsor.fullName : "Unknown Sponsor";
}

const hasApplied = (scholarshipId: number) => {
  return applications.value.some(
    (app) => app.studentId === student.id && app.scholarshipId === scholarshipId
  );
};

// Go to /student/scholarships/:id
const goToApply = (scholarshipId: number) => {
  router.push(`/student/scholarships/${scholarshipId}`);
};

const availableFields = computed(() => {
  const fields = scholarships.value.map(s => s.field).filter(Boolean);
  return [...new Set(fields)];
});

const searchQuery = ref("");
const selectedField = ref("");

const filteredScholarships = computed(() => {
  return scholarships.value.filter(sch => {
    const query = searchQuery.value.toLowerCase();

    const matchesSearch = !query || [
      sch.title,
      sch.field,
      sch.description,
      getSponsorName(sch.sponsorId)
    ].some(val => val?.toLowerCase().includes(query));

    const matchesField = !selectedField.value || sch.field === selectedField.value;

    return matchesSearch && matchesField;
  });
});

const clearFilters = () => {
  searchQuery.value = "";
  selectedField.value = "";
};

</script>

<template>
  <div class="page">
    <h1>Posted Scholarships</h1>

    <!--Search and Filter Bar-->
    <div class="search-bar">
      <div class="search-input-wrapper">
        <span class="search-icon"></span>
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search by title, field, sponsor, description..."
          class="search-input"
        />
        <button v-if="searchQuery" class="clear-input" @click="searchQuery = ''">✕</button>
      </div>

      <select v-model="selectedField" class="filter-select">
        <option value="">All Fields</option>
        <option v-for="field in availableFields" :key="field" :value="field">
          {{ field }}
        </option>
      </select>

      <button
        v-if="searchQuery || selectedField"
        class="clear-btn"
        @click="clearFilters"
      >
        Clear All
      </button>
    </div>
    
    <!--Filter active but no matches -->
    <div v-if="filteredScholarships.length === 0" class="no-sch">
      <span v-if="scholarships.length === 0">There are no posted scholarships yet.</span>
      <span v-else>No scholarships match your search.</span>
    </div>

    <!--Filter active with matches -->
    <template v-else>
      <p class="results-count" v-if="searchQuery || selectedField">
        {{ filteredScholarships.length }} result(s) found
      </p>

      <div class="scholarships-grid">
        <div class="card" v-for="sch in filteredScholarships" :key="sch.id">
          <div class="card-header">
            <h3>{{ getSponsorName(sch.sponsorId) }}</h3>
            <span :class="['status-badge', sch.status]">{{ sch.status.toUpperCase() }}</span>
          </div>
          <p><strong>Scholarship:</strong> {{ sch.title }}</p>
          <p><strong>Field:</strong> {{ sch.field }}</p>
          <p><strong>Description:</strong> {{ sch.description }}</p>
          <p><strong>Amount:</strong> {{ sch.amount?.toLocaleString() }} RWF</p>
          <p><strong>Deadline:</strong> {{ new Date(sch.deadline).toLocaleDateString("en-GB") }}</p>

          <div v-if="sch.requirements?.length > 0">
            <strong>Requirements:</strong>
            <ul class="req-list">
              <li v-for="(req, i) in sch.requirements" :key="i">{{ req }}</li>
            </ul>
          </div>

          <button
            :disabled="hasApplied(sch.id)"
            @click="goToApply(sch.id)"
            :class="hasApplied(sch.id) ? 'applied-btn' : 'apply-btn'"
          >
            {{ hasApplied(sch.id) ? "Already Applied" : "Apply Now →" }}
          </button>
        </div>
      </div>
    </template>

  </div>
</template>

<style scoped>
.page {
  padding: 30px;
  font-family: Arial, Helvetica, sans-serif;
  color: #1e3a8a;
  padding-top: 0px;
}

.scholarships-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-top: 20px;
}

.no-sch{
  margin-top: 20px;
  font-style: italic;
  color: gray;
}

.card {
  padding: 24px;
  border-radius: 12px;
  background: white;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  font-size: 21px;
}

.card-header h3 { margin: 0; }

.status-badge {
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 11px;
  font-weight: bold;
}

.status-badge.active { background: #dcfce7; color: #16a34a; }
.status-badge.inactive { background: #fee2e2; color: #dc2626; }

.apply-btn {
  margin-top: 12px;
  background: #1e3a8a;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
}

.applied-btn {
  margin-top: 12px;
  background: #e5e7eb;
  color: #6b7280;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: not-allowed;
  font-weight: bold;
}

.search-bar {
  display: flex;
  gap: 12px;
  align-items: center;
  margin: 20px 0 10px;
  flex-wrap: wrap;
}

.search-input-wrapper {
  flex: 1;
  display: flex;
  align-items: center;
  background: white;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  padding: 0 12px;
  gap: 8px;
  min-width: 200px;
}

.search-icon { font-size: 16px; color: #9ca3af; }

.search-input {
  flex: 1;
  border: none;
  outline: none;
  padding: 10px 0;
  font-size: 14px;
  color: #374151;
}

.clear-input {
  background: none;
  border: none;
  cursor: pointer;
  color: #9ca3af;
  font-size: 14px;
  padding: 0;
}

.filter-select {
  padding: 10px 14px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
  background: white;
  color: #374151;
  cursor: pointer;
  min-width: 160px;
}

.clear-btn {
  padding: 10px 16px;
  background: #fee2e2;
  color: #dc2626;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  font-size: 13px;
}

.results-count {
  font-size: 19px;
  color: #6b7280;
  margin-bottom: 10px;
}
</style>