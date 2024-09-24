<template>
  <div class="heading-wrapper">
    <h1
      style="margin-top: 60px; margin-bottom: 50px; padding-bottom: 10px; height: 50px; font-size: 40px; font-weight: 700;">
      Please Answer the following 10 questions
    </h1>
  </div>

  <div class="container">

    <br>
    <!-- Display first batch of questions -->
    <SingleQuestion v-if="step == 1" :question="`Does your child look at you when you call his/her name?`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" :value="answers[0]" :index="0"
      @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 1" :question="`How easy is it for you to get eye contact with your child?`"
      :options="['Very easy', 'Quite easy', 'Quite difficult', 'Very difficult', 'Impossible']" :value="answers[1]"
      :index="1" @update-answer="updateAnswer" />

    <!-- Display second batch of questions -->

    <SingleQuestion v-if="step == 2"
      :question="`Does your child point to indicate that s/he wants something? (e.g. a toy that is out of reach)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[2]" :index="2" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 2"
      :question="`Does your child point to share interest with you? (e.g. pointing at an interesting sight)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[3]" :index="3" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 3" :question="`Does your child pretend? (e.g. care for dolls, talk on a toy phone)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[4]" :index="4" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 3" :question="`Does your child follow where you're looking?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[5]" :index="5" @update-answer="updateAnswer" />


    <!-- Display 3rd batch of questions -->
    <SingleQuestion v-if="step == 4"
      :question="`When someone else in the family is visibly upset, does your child show signs of wanting to comfort them? (e.g. stroking hair, hugging them)`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" :value="answers[6]" :index="6"
      @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 4" :question="`Would you describe your child's first words as:`"
      :options="['Very typical', 'Quite typical', 'Slightly unusual', 'Very unusual', 'My child doesn\'t speak']"
      :value="answers[7]" :index="7" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 5" style="margin-top: -50px;"
      :question="`Does your child use simple gestures? (e.g. wave goodbye)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[8]" :index="8" @update-answer="updateAnswer" />

    <SingleQuestion v-if="step == 5" :question="`Does your child stare at nothing with no apparent purpose?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']"
      :value="answers[9]" :index="9" @update-answer="updateAnswer" />

    <div v-if="step == 5" class="personal_questions">
      <div class="row">

        <div class="col-5">
          <div class="form-group">
            <select class="form-control" style="margin-bottom: 10px;width: 100%; height: 50px;" id="autismHistory"
              v-model="answers[10]">
              <option value="" disabled>Does any family member of this child have a history of autism?</option>
              <option value="1">Yes</option>
              <option value="0">No</option>
            </select>
          </div>
        </div>

        <div class="col-4">
          <div class="form-group">
            <input type="number" min="0" style="width: 100%; height: 50px;" class="form-control" name="child_age"
              id="childAge" placeholder="Age of the child (in months)" v-model="answers[12]">
          </div>
        </div>

        <div class="col-3">
          <div class="form-group">
            <select class="form-control" style=" height: 50px; width: 300px;" id="genderSelect" v-model="answers[13]">
              <option value="" disabled>Gender</option>
              <option value="0">Female</option>
              <option value="1">Male</option>
            </select>
          </div>
        </div>

      </div>
    </div>


    <br><br>
  </div>

  <div class="button-wrapper" style="margin-top: 0px;">
    <button class="button6" @click="prevSection" style="margin-right: 100px;">
      <div class="create-account2">
        < Previous Section</div>
    </button>

    <button v-if="step < 5" class="button6" @click="nextSection">
      <div class="create-account2">Next Section ></div>
    </button>


    <button v-else class="button6" @click="viewTestResult">
      <div class="create-account2">View Test Result</div>
    </button>
  </div>
</template>


<script>
import SingleQuestion from './SingleQuestion.vue';
import axios from 'axios';


export default {
  components: {
    SingleQuestion,
  },
  data() {
    return {
      step: 1,
      answers: Array(14).fill(null),
    };
  },
  methods: {
    updateAnswer({ answer, index }) {
      this.answers[index] = answer;
    },
    nextSection() {
      if (this.step < 5) {
        this.step++;
      }
    },
    prevSection() {
      if (this.step > 1) {
        this.step--;
      }
    },
    async viewTestResult() {
      //calculate score fild.
      this.answers[11] = this.answers.slice(0, 10).reduce((sum, answer) => {
        const value = parseInt(answer) || 0; // Convert answer to an integer, default to 0 if NaN
        return sum + value;
      }, 0);

      //put values within 0-1 range.
      this.answers = this.answers.map((value, index) => {
        if (index >= 0 && index <= 9) {
          return (value < 3) ? 1 : 0; // Convert to 1 if value is below 3, else 0
        }
        return value;
      });


      const response = await axios.post("http://localhost:8080/api/ex/q10", {
        questionAnswers: this.answers.filter((_, index) => index < 14)
      });

      console.log(response)

    },
  },
};
</script>

<style scoped>
.button-wrapper {
  width: 100%;
}

.personal_questions {
  height: 100px;
  padding-left: 15px;
  padding-right: 15px;
  margin-top: 10px;
  margin-bottom: -25px;
  background-color: rgb(239, 237, 234);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 15px;
  margin-right: 15px;
  border-radius: 15px;
}
</style>
