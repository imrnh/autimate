<template>
  <div class="main-menu" style="width: calc(100vw - 300px); height: 80vh; display: flex; flex-wrap: wrap; align-items: center; justify-content: center;">
    <div>
      <h1>Fun with Alphabet</h1>
      <br>
      <button @click="startLearn">Learn</button>
      <button @click="startFind">Find</button>
      
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    startLearn() {
      this.$router.push({ name: '/dashboard/therapy/funalphabet/learn' });
    },
    startFind() {
      this.$router.push({ name: '/dashboard/therapy/funalphabet/find' });
    }
  }
};
</script>

<style scoped>
.main-menu {
  text-align: center;
  padding: 50px;
}
button {
  margin: 20px;
  padding: 10px 20px;
  font-size: 18px;
  cursor: pointer;
}
</style>
