<template>
  <div class="heading-wrapper">
    <h1
      style="margin-top: 50px; margin-bottom: 50px; padding-bottom: 10px; height: 50px; font-size: 40px; font-weight: 700;">
      Please Answer the following 10 questions
    </h1>
  </div>

  <div class="container">

    <!-- Display first batch of questions -->
    <SingleQuestion v-if="step == 1" :question="`Does your child look at you when you call his/her name?`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" :value="answers[0]" :index="0"
      @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 1" :question="`How easy is it for you to get eye contact with your child?`"
      :options="['Very easy', 'Quite easy', 'Quite difficult', 'Very difficult', 'Impossible']" :value="answers[1]"
      :index="1" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 1"
      :question="`Does your child point to indicate that s/he wants something? (e.g. a toy that is out of reach)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[2]" :index="2" @update-answer="updateAnswer" />

    <!-- Display second batch of questions -->
    <SingleQuestion v-if="step == 2"
      :question="`Does your child point to share interest with you? (e.g. pointing at an interesting sight)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[3]" :index="3" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 2" :question="`Does your child pretend? (e.g. care for dolls, talk on a toy phone)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[4]" :index="4" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 2" :question="`Does your child follow where you're looking?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[5]" :index="5" @update-answer="updateAnswer" />


    <!-- Display 3rd batch of questions -->
    <SingleQuestion v-if="step == 3"
      :question="`When someone else in the family is visibly upset, does your child show signs of wanting to comfort them? (e.g. stroking hair, hugging them)`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" :value="answers[6]" :index="6"
      @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 3" :question="`Would you describe your child's first words as:`"
      :options="['Very typical', 'Quite typical', 'Slightly unusual', 'Very unusual', 'My child doesn\'t speak']"
      :value="answers[7]" :index="7" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 3" :question="`Does your child use simple gestures? (e.g. wave goodbye)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[8]" :index="8" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 4" :question="`Does your child stare at nothing with no apparent purpose?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[9]" :index="9" @update-answer="updateAnswer" />
  </div>

  <br><br>

  <div class="button-wrapper">

    <button class="button6" @click="prevSection" style="margin-right: 100px;">
      <div class="create-account2">
        < Previous Section</div>
    </button>

    <button v-if="step < 4" class="button6" @click="nextSection">
      <div class="create-account2">Next Section ></div>
    </button>


    <button v-else class="button6" @click="viewTestResult">
      <div class="create-account2">View Test Result</div>
    </button>
  </div>
</template>

<!-- <script>
import SingleQuestion from './SingleQuestion.vue';

export default {
  components: {
    SingleQuestion, // Register the component here
  },
  data() {
    return {
      step: 1, // Track the step to know which questions to show
      answers: Array(10).fill(null), // To store answers for all 10 questions
    };
  },
  methods: {
    nextSection() {
      if (this.step < 4) {
        this.step++;
      }
    },
    prevSection() {
      if (this.step > 1) {
        this.step--;
      }
    },
    viewTestResult() {
      console.log('Selected answers:', this.answers);
    },
  },
};
</script> -->

<script>
import SingleQuestion from './SingleQuestion.vue';

export default {
  components: {
    SingleQuestion,
  },
  data() {
    return {
      step: 1,
      answers: Array(10).fill(null), // Answer array for storing the responses
    };
  },
  methods: {
    updateAnswer({ answer, index }) {
      // Directly update the answers array at the specified index
      this.answers[index] = answer;
    },
    nextSection() {
      if (this.step < 4) {
        this.step++;
      }
    },
    prevSection() {
      if (this.step > 1) {
        this.step--;
      }
    },
    viewTestResult() {
      console.log('Selected answers:', this.answers);
    },
  },
};
</script>

<style scoped>
.button-wrapper {
  width: 100%;
}
</style>
