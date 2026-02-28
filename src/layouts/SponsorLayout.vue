<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const isActive = (name: string) => route.name === name;

const logout = () => {
  localStorage.removeItem("sponsor_session");
  router.push("/login");
};
</script>

<template>
  <div class="layout">

    <aside class="sidebar">
      <h2 class="logo">Sponsor Panel</h2>

      <ul class="menu">
        <li :class="{active: isActive('sponsor-dashboard')}"
            @click="router.push('/sponsor/dashboard')">
          Dashboard
        </li>

        <li :class="{active: isActive('post-scholarship')}"
            @click="router.push('/sponsor/post')">
          Post Scholarship
        </li>

        <li :class="{active: isActive('applications')}"
            @click="router.push('/sponsor/applications')">
          Applications
        </li>

        <li :class="{active: isActive('scholarships')}"
            @click="router.push('/sponsor/scholarships')">
          My Scholarships
        </li>

        <li :class="{active: isActive('payments')}"
            @click="router.push('/sponsor/payments')">
          Payments
        </li>

        <!--<li :class="{active: isActive('payments')}"
            @click="router.push('/sponsor/payments')">
          Payments
        </li>-->

        <li @click="logout">Sign Out</li>
        <li id="sponsorName" @click="logout">Sign Out</li>
      </ul>
    </aside>

    <main class="content">
      <router-view />
    </main>

  </div>
</template>

<style scoped>
.layout {
  display: flex;
  min-height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
}

.sidebar {
  width: 220px;
  background: #1e3a8a;
  color: white;
  padding: 20px;
}

.logo {
  margin-bottom: 30px;
}

.menu {
  list-style: none;
  padding: 0;
}

.menu li {
  padding: 12px;
  cursor: pointer;
  border-radius: 6px;
  margin-bottom: 10px;
}

.menu li:hover {
  background: rgba(255,255,255,0.2);
}

.menu li.active {
  background: white;
  color: #1e3a8a;
  font-weight: bold;
}

.content {
  flex: 1;
  padding: 30px;
  background: #f9fafb;
}

#sponsorName{
  margin-top: 60px;
  background-color: #f9fafb;
  color:#1e3a8a;
  border-radius: 50%;
  text-align: center;
}
</style>