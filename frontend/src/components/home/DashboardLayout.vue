<script>
import Sidebar from './Sidebar.vue';

import Modal from './Modal.vue'; // Assuming you have a Modal component
import SingleQuestion from './tests/SingleQuestion.vue';

export default {
  components: {
    Sidebar,
    Modal,
    SingleQuestion,
  },
  data() {
    return {
      isModalVisible: false,
      isTestingModel: true,
    };
  },
  methods: {
    showModal() {
      this.isModalVisible = true;
      this.isTestingModel = false;
    },
    showAutismTestingModal() {
      this.isModalVisible = true;
      this.isTestingModel = true;
    }
  },
};

</script>

<template>

  <div class="wrapper">
    <Sidebar @openModal="showModal" @showAutismTestingModal="showAutismTestingModal" />

    <div class="content_renderer">
      <router-view></router-view>
      
      <Modal v-if="isModalVisible" @close="isModalVisible = false" :isTestingModel="isTestingModel" />
    </div>
  </div>
</template>

<style lang="css" scoped>
.wrapper {
  width: calc(100vw - 30px);
  min-height: calc(100vh-10px);
  padding-top: 5px;
  padding-left: 15px;
  display: flex;
  flex-direction: row;
}

.content_renderer {
  width: calc(100vw - 230px - 15px - 30px);
  /** sidebar is 230px + 25px padding from it. */
  height: calc(100vh-10px);
  margin-left: 15px;
  border-radius: var(--br-base);
  background-color: var(--color-whitesmoke-100);
}
</style>