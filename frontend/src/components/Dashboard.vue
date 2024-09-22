<script>
import Sidebar from './Sidebar.vue';

import Modal from './Modal.vue'; // Assuming you have a Modal component
import AutismTest from './AutismTest.vue';
import SingleQuestion from './subcomps/SingleQuestion.vue';

export default {
  components: {
    Sidebar,
    Modal,
    AutismTest,
    SingleQuestion,
  },
  data() {
    return {
      isModalVisible: false,
    };
  },
  methods: {
    showModal() {
      this.isModalVisible = true;
    },
  },
};

</script>

<template>

  <div class="wrapper">
    <Sidebar @openModal="showModal" />

    <div class="content_renderer">
      <AutismTest />

      <!-- Modal -->
      <Modal v-if="isModalVisible" @close="isModalVisible = false" />
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