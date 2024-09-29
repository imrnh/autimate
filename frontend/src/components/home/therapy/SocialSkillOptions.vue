<template>


  <div class="options-container" style="width: calc(100vw - 300px); display: flex; height: 98vh; align-items: center; justify-content: center;">

    <h1>Build Social Skills</h1>
    <br><br>

    <button v-for="story in stories" :key="story.assetName" @click="navigateToStory(story.assetName)"
      class="story-button" >
      {{ story.title }}
    </button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      stories: [
        { title: "Let's Make Friends", assetName: 'asset1' },
        { title: 'I Can Calm Myself', assetName: 'asset2' },
        { title: 'Classroom Manners', assetName: 'asset3' },
        { title: 'Good Listeners', assetName: 'asset4' },
        { title: 'Respectful Behaviors', assetName: 'asset5' },
        { title: 'Good Manners', assetName: 'asset6' },
        { title: "What's My Hobby?", assetName: 'asset7' },
      ],
    };
  },
  methods: {
    navigateToStory(assetName) {
      this.$router.push({ name: 'SocialSkillStoryView', params: { assetName } });
    },
  },
};
</script>

<style>
.options-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.story-button {
  padding: 10px 20px;
  font-size: 1.2rem;
  border: 2px solid #85c987;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 350px;
  height: 55px;
  background-color: transparent;
  color: black;
}

.story-button:hover {
  background-color: #45a049;
  border: 0;
}
</style>