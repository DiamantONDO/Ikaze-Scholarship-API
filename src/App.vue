<script setup lang="ts">
import { ref, onMounted } from "vue";
import { supabase } from "@/utils/supabase";

const todos = ref<any[]>([]);

async function getTodos() {
  const { data, error } = await supabase.from("todos").select();
  if (error) {
    console.error("Error fetching todos:", error.message);
    return;
  }
  todos.value = data || [];
}

onMounted(() => {
  getTodos();
});
</script>

<template>
  <ul>
    <li v-for="todo in todos" :key="todo.id">{{ todo.name }}</li>
  </ul>
</template>

<style scoped></style>
