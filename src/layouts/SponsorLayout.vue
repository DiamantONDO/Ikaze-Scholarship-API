<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const router = useRouter();
const route = useRoute();
const adminName = ref("sponsor");

onMounted(() => {
  const session = sessionStorage.getItem("sponsor_session");
  if (session) {
    try {
      const data = JSON.parse(session);
      adminName.value = data.fullName || "";
    } catch (e) {
      console.error("Could not parse admin session");
    }
  }
});

const isActive = (name: string) => route.name === name;

const logout = () => {
  sessionStorage.removeItem("sponsor_session");
  router.push("/login");
};
</script>

<template>
  <div class="layout">

    <aside class="sidebar">
      <RouterLink to="/home" class="logo-link">
        <h2>IKAZEScholarship</h2>
      </RouterLink>
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

        <div class="sponsor-profile">
          <!--<span class="avatar">{{ adminName.charAt(0).toUpperCase() }}</span>-->
          <span class="name">{{ adminName }}</span>
        </div>

        <li class="logout" @click="logout">Sign Out</li>
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
  overflow: hidden;
}

.sidebar {
  width: 220px;
  background: #111827;
  color: white;
  padding: 20px;
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  overflow-y: auto;
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
  overflow-y: auto;
  margin-left: 220px;
  margin-top: 0px;
}

#sponsorName{
  margin-top: 60px;
  background-color: #f9fafb;
  color:#1e3a8a;
  border-radius: 50%;
  text-align: center;
}

.sponsor-profile {
  margin-top: 40px;
  padding: 12px;
  border-top: 1px solid #ffffff;
  display: flex;
  align-items: center;
  gap: 10px;
}

.logout{
  margin-top: 30px;
  background: #dc2626;
  text-align: center;
}

.logout:hover{
  background: #b91c1c;
}

.name{
  font-weight: bold;
  font-size: 16px;
}

.logo-link {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  color: white;
  margin-bottom: 30px;
}

.logo-link h2 {
  margin: 0;
  font-size: 20px;
}
</style>