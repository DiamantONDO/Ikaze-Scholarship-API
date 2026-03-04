<script setup lang="ts">
import { RouterView, RouterLink } from "vue-router";
</script>

<template>
  <div class="layout">

    <aside class="sidebar">
      <h2 class="logo">Student Panel</h2>

      <nav>
        <RouterLink to="/student/dashboard">Dashboard</RouterLink>
        <RouterLink to="/student/scholarships">Scholarships</RouterLink>
        <RouterLink to="/student/applications">My Applications</RouterLink>
        <RouterLink to="/student/profile">My Profile</RouterLink>
        <RouterLink to="/student/education">University Education</RouterLink>
        <RouterLink to="/student/payments">My Payments</RouterLink>
        <RouterLink id="logout" to="/login">Logout</RouterLink>
      </nav>
    </aside>

    <!-- Main Content -->
    <main class="content">
      <RouterView />
    </main>

  </div>
</template>

<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
  overflow: hidden;
}

.sidebar {
  width: 240px;
  background: #1f2937;
  color: white;
  padding: 20px;
  font-size: x-large;
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  overflow-y: auto;
}

.logo {
  margin-bottom: 30px;
  font-size: 38px;
}

nav {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

a {
  color: #d1d5db;
  text-decoration: none;
}

a.router-link-active {
  color: white;
  font-weight: bold;
  background-color: #727374;
}

.content {
  flex: 1;
  background: #f3f4f6;
  padding: 30px;
  overflow-y: auto;
  margin-left: 220px;
  margin-top: 0px;
}

#logout{
  margin-top: 30px;
  background: #dc2626;
  text-align: left;
}
</style>