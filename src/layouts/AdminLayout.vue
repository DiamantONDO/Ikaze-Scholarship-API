<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const isActive = (name: string) => route.name === name;

const logout = () => {
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
</style>