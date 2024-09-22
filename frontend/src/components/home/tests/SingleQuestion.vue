<template>
  <div class="question_card">
    <div class="ces">
      <div class="">
        <p style="font-size: 20px; margin-left: 20px; margin-top: 5px; font-weight: 500;">
          {{ question }}
        </p>
      </div>
      <div class="choices" style="margin-top: -13px;">
        <div v-for="(option, index) in options" :key="index" class="ans-button" style="width: 230px;">
          <label>
            <input
              class="form-check-input"
              type="radio"
              :value="(index+1).toString()"
              v-model="selectedOption"
              @change="emitAnswer"
              style="margin-right: 10px; font-size: 20px; margin-top: 2px;"
            />
            {{ option }}
          </label>
        </div>
      </div>
      <div style="width: 1px; height: 0px;"></div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    question: {
      type: String,
      required: true,
    },
    options: {
      type: Array,
      required: true,
    },
    value: {
      type: String,
      default: null,
    },
    index: {
      type: Number,
      required: true, // Ensure index of the question is passed from the parent
    },
  },
  data() {
    return {
      selectedOption: this.value, // Local state to bind the selected value
    };
  },
  methods: {
    emitAnswer() {
      this.$emit('update-answer', { answer: this.selectedOption, index: this.index });
    },
  },
  watch: {
    value(newValue) {
      this.selectedOption = newValue;
    },
  },
};
</script>
