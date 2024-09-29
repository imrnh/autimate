<template>
    <div class="story-container" style="width: calc(100vw - 300px); display: flex; height: 98vh; align-items: center; justify-content: center;">
      <h2>{{ storyTitle }}</h2>
      <div class="image-container">
        <!-- Load images dynamically -->
        <img :src="`/story/${this.assetName}/${this.images[this.currentIndex]}`" alt="Story Image" />
      </div>
  
      <div class="navigation-buttons">
        <button @click="prevImage" :disabled="currentIndex === 0">
          Previous
        </button>
        <button @click="nextImage" :disabled="currentIndex === images.length - 1">
          Next
        </button>
      </div>
  
      <button class="end-button" @click="goBack">End</button>
    </div>
  </template>
  
  <script>
  export default {
    props: ['assetName'],
    data() {
      return {
        images: [], // Array to store image paths
        currentIndex: 0,
        storyTitle: '',
      };
    },
    computed: {
      // Dynamically calculate the image source based on the current asset and index
      imageSrc() {
        return new URL(`@/assets/${this.assetName}/${this.images[this.currentIndex]}`, import.meta.url).href;
      },
    },
    created() {
      this.loadStory();
    },
    methods: {
      loadStory() {
        this.images = this.getImageArray(this.assetName); // Load image file names
        this.storyTitle = this.getStoryTitle(this.assetName); // Get story title
      },
      getImageArray(assetName) {
        // Define the number of images for each asset
        const imageCount = {
          asset1: 5, // Number of images for asset1
          asset2: 4,
          asset3: 8,
          asset4: 5,
          asset5: 4,
          asset6: 6,
          asset7: 5,
        };
        const images = [];
        const count = imageCount[assetName] || 0;
        for (let i = 0; i < count; i++) {
          images.push(`${i}.png`); // File names are like 0.png, 1.png, etc.
        }
        return images;
      },
      getStoryTitle(assetName) {
        const storyMap = {
          asset1: "Let's Make Friends",
          asset2: 'I Can Calm Myself',
          asset3: 'Classroom Manners',
          asset4: 'Good Listeners',
          asset5: 'Respectful Behaviors',
          asset6: 'Good Manners',
          asset7: "What's My Hobby?",
        };
        return storyMap[assetName];
      },
      prevImage() {
        if (this.currentIndex > 0) {
          this.currentIndex--;
        }
      },
      nextImage() {
        if (this.currentIndex < this.images.length - 1) {
          this.currentIndex++;
        }
      },
      goBack() {
        this.$router.push('/dashboard/therapy/socialskill'); // Adjust the path to wherever you want to navigate
      },
    },
  };
  </script>
  
  <style>
  .story-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    gap: 20px;
  }
  
  .image-container img {
    max-width: 100%;
    max-height: 400px;
  }
  
  .navigation-buttons {
    display: flex;
    gap: 20px;
  }
  
  button {
    padding: 10px 20px;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:disabled {
    background-color: gray;
  }
  
  .end-button {
    margin-top: 20px;
    background-color: red;
  }
  </style>
  