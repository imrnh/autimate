<template>
  <div class="q">
    <div class="ces">
      <div class="eesha-works-for-container">
          <p style="font-size: 17px;">
            {{ question }}
          </p>
      </div>
      <div class="choices">
        <div v-for="(option, index) in options" :key="index" class="ans-button">
          <label>
            <input class="form-check-input" type="radio" :value="option" v-model="selectedOptions" />
            {{ option }}
          </label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    question: {
      type: String,
      required: true
    },
    options: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      selectedOptions: []
    };
  },
  watch: {
    selectedOptions(newVal) {
      console.log("Selected options:", newVal);
    }
  }
};
</script>

<style scoped>
.q {
  width: 100%;
  padding: 20px;
  margin-bottom: 15px;
  border-radius: 15px;
}

.ans-button {
  margin-top: 10px;
}

label {
  cursor: pointer;
  width: auto;
}

.choices {
  display: flex;
  margin-top: -30px;
  margin-left: 20px;
}
</style>