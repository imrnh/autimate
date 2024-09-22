<script>
import SingleQuestion from './subcomps/SingleQuestion.vue';
export default {
  components: {
    SingleQuestion, // Register the component here
  },
};

</script>



<template>
  <div class="heading-wrapper">
    <h1 style="margin-top: 20px; margin-bottom: 20px; padding-bottom: 10px;" class="heading">Please Answer the following
      questions</h1>
  </div>


  <div class="container">
    <SingleQuestion :question="`Does your child look at you when you call his/her name?`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" />

    <SingleQuestion :question="`How easy is it for you to get eye contact with your child?`"
      :options="['Very easy', 'Quite easy', 'Quite difficult', 'Very difficult', 'Impossible']" />

    <SingleQuestion
      :question="`Does your child point to indicate that s/he wants something? (e.g. a toy that is out of reach)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

    <SingleQuestion
      :question="`Does your child point to share interest with you? (e.g. pointing at an interesting sight)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

    <SingleQuestion :question="`Does your child pretend? (e.g. care for dolls, talk on a toy phone)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

    <SingleQuestion :question="`Does your child follow where you're looking?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

    <SingleQuestion
      :question="`If you or someone else in the family is visibly upset, does your child show signs of wanting to comfort them? (e.g. stroking hair, hugging them)`"
      :options="['Always', 'Usually', 'Sometimes', 'Rarely', 'Never']" />

    <SingleQuestion :question="`Would you describe your child's first words as:`"
      :options="['Very typical', 'Quite typical', 'Slightly unusual', 'Very unusual', 'My child doesn\'t speak']" />

    <SingleQuestion :question="`Does your child use simple gestures? (e.g. wave goodbye)`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

    <SingleQuestion :question="`Does your child stare at nothing with no apparent purpose?`"
      :options="['Many times a day', 'A few times a day', 'A few times a week', 'Less than once a week', 'Never']" />

  </div>

  <br><br>

  <div class="button-wrapper">
    <button class="button6">
      <div class="create-account2">Next Section</div>
    </button>
  </div>

</template>