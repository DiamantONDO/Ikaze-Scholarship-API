<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const router = useRouter();
const route = useRoute();
const adminName = ref("admin");

onMounted(() => {
  const session = sessionStorage.getItem("admin_session");
  if (session) {
    try {
      const data = JSON.parse(session);
      adminName.value = data.fullName || "";
    } catch (e) {
      console.error("Could not parse admin session");
    }
  }
}
);


const isActive = (name: string) => route.name === name;

const logout = () => {
  sessionStorage.removeItem("admin_session");
  router.push("/login");
};
</script>

<template>
  <div class="layout">

    <aside class="sidebar">

      <h2 class="logo">Admin Panel</h2>

      <ul class="menu">
        <li :class="{active: isActive('admin-dashboard')}"
            @click="router.push('/admin/dashboard')">
          Dashboard
        </li>

        <li :class="{active: isActive('admin-students')}"
            @click="router.push('/admin/students')">
          Students
        </li>

        <li :class="{active: isActive('admin-applications')}"
            @click="router.push('/admin/applications')">
          Applications
        </li>

        <li :class="{active: isActive('admin-scholarships')}"
            @click="router.push('/admin/scholarships')">
          Scholarships
        </li>

        <li :class="{active: isActive('admin-payments')}"
            @click="router.push('/admin/payments')">
          Payments
        </li>

        <div class="admin-profile">
          <!--<span class="avatar">{{ adminName.charAt(0).toUpperCase() }}</span>-->
          <span class="name">{{ adminName }}</span>
        </div>

        <li class="logout" @click="logout">
          Sign Out
        </li>
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
  width: 240px;
  background: #111827;
  color: white;
  padding: 20px;
}

.logo {
  font-size: 20px;
  margin-bottom: 30px;
  font-weight: bold;
}

.menu {
  list-style: none;
  padding: 0;
}

.menu li {
  padding: 12px;
  margin-bottom: 8px;
  border-radius: 6px;
  cursor: pointer;
  transition: 0.2s;
}

.menu li:hover {
  background: #1f2937;
}

.menu li.active {
  background: white;
  color: #111827;
  font-weight: bold;
}

.logout {
  margin-top: 30px;
  background: #dc2626;
  text-align: center;
}

.logout:hover {
  background: #b91c1c;
}

.content {
  flex: 1;
  padding: 30px;
  background: #f3f4f6;
}

.admin-profile {
  margin-top: 40px;
  padding: 12px;
  border-top: 1px solid #ffffff;
  display: flex;
  align-items: center;
  gap: 10px;
}

.name{
  font-weight: bold;
  font-size: 16px;
}
</style>