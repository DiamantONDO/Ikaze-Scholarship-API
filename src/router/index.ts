import { createRouter, createWebHistory } from "vue-router";

// Import your pages
import HomePage from "@/views/HomePage.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/home" }, // Redirect root to home
    { path: "/home", name: "homepage", component: HomePage },
    { path: "/login", name: "login", component: Login },
    { path: "/register", name: "register", component: Register },

    {
      path: "/sponsor",
      component: () => import("@/layouts/SponsorLayout.vue"),
      children: [
        { path: "dashboard", name: "sponsor-dashboard", component: () => import("@/views/sponsor/SponsorDashboard.vue") },
        { path: "post", name: "post-scholarship", component: () => import("@/views/sponsor/PostScholarship.vue") },
        { path: "applications", name: "applications", component: () => import("@/views/sponsor/Applications.vue") },
        { path: "scholarships", name: "scholarships", component: () => import("@/views/sponsor/MyScholarships.vue") },
      ]
    },

    {
      path: "/admin",
      component: () => import("@/layouts/AdminLayout.vue"),
      children: [
        { path: "dashboard", name: "admin-dashboard", component: () => import("@/views/admin/AdminDashboard.vue") },
        { path: "students", name: "admin-students", component: () => import("@/views/admin/Students.vue") },
        { path: "applications", name: "admin-applications", component: () => import("@/views/admin/Applications.vue") },
        { path: "scholarships", name: "admin-scholarships", component: () => import("@/views/admin/Scholarships.vue") },
        { path: "payments", name: "admin-payments", component: () => import("@/views/admin/Payments.vue") }
      ]
    },

    {
      path: "/student",
      component: () => import("@/views/student/StudentLayout.vue"),
      children: [
        { path: "dashboard", name: "student-dashboard", component: () => import("@/views/student/Dashboard.vue") },
        { path: "scholarships", name: "student-scholarships", component: () => import("@/views/student/Scholarships.vue") },
        { path: "applications", name: "student-applications", component: () => import("@/views/student/MyApplications.vue") },
        { path: "profile", name: "student-profile", component: () => import("@/views/student/MyProfile.vue") },
        { path: "education", name: "student-education", component: () => import("@/views/student/UniversityEducation.vue") },
        { path: "payments", name: "student-payments", component: () => import("@/views/student/MyPayments.vue") }
      ]
    }
  ]
});

export default router;
