<template>
    <div :style="backgroundStyle" class="game-container" style="width: calc(100vw - 250px); display: flex; height: 98vh; align-items: center; justify-content: center;">
      <!-- Stylish Stopwatch in the top-left corner -->
      <div class="stopwatch">
        <h3>{{ timeLeft }}s</h3>
      </div>
  
      <!-- Scoreboard in the top-center -->
      <div class="scoreboard">
        <h3>Score: {{ score }}/{{ totalAppearances }}</h3>
      </div>
  
      <!-- Ball that appears and reappears -->
      <img
          v-if="ballVisible"
          :src="selectedBall"
          alt="Ball"
          class="ball"
          :style="ballPosition"
          @click="tapBall"
      />
  
      <!-- Attractive End Game button in the bottom-right corner -->
      <button @click="endGame" class="end-button" v-if="!gameOver">End Game</button>
  
      <!-- Game Over Screen -->
      <div v-if="gameOver" class="game-over">
        <h2>Great Job! Wanna try again?</h2>
        <p>Your Score: {{ score }}/{{ totalAppearances }}</p>
        <button @click="replayGame" class="replay-button">
          <img src="/tapgame/replay-icon.png" alt="Replay" class="replay-icon"/>
        </button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        // Store ball and background pairs
        pairs: [
          { ball: '/tapgame/ball1.png', background: '/tapgame/back1.png' },
          { ball: '/tapgame/ball2.png', background: '/tapgame/back2.png' },
          { ball: '/tapgame/ball3.png', background: '/tapgame/back3.png' },
          { ball: '/tapgame/ball4.png', background: '/tapgame/back4.png' },
        ],
        selectedBall: null,
        selectedBackground: null,
        ballPosition: { top: '50%', left: '50%' },
        ballVisible: true,
        score: 0,
        totalAppearances: 0,
        timeLeft: 4,
        gameOver: false,
        timerInterval: null,
      };
    },
    computed: {
      backgroundStyle() {
        return {
          backgroundImage: `url(${this.selectedBackground})`,
          backgroundSize: 'cover',
          width: '100vw',
          height: '100vh',
          position: 'relative'
        };
      }
    },
    methods: {
      // Select a random pair of ball and background at the start of the game
      selectRandomPair() {
        const randomPair = this.pairs[Math.floor(Math.random() * this.pairs.length)];
        this.selectedBall = randomPair.ball;
        this.selectedBackground = randomPair.background;
      },
  
      // Function to randomly move the ball on the screen
      moveBallRandomly() {
        const randomTop = Math.random() * 80 + 10;  // Random position from 10% to 90% of the screen height
        const randomLeft = Math.random() * 80 + 10; // Random position from 10% to 90% of the screen width
        this.ballPosition = { top: `${randomTop}%`, left: `${randomLeft}%` };
  
        // Count the ball appearance
        this.totalAppearances++;
  
        // Hide the ball and show it again after 300ms
        this.ballVisible = false;
        setTimeout(() => {
          this.ballVisible = true;
        }, 300);
      },
  
      // Ball tap event handler
      tapBall() {
        this.score++;
        this.moveBallRandomly();
        this.resetTimer();
      },
  
      // End the game and stop the timer
      endGame() {
        clearInterval(this.timerInterval);
        this.gameOver = true;
      },
  
      // Start the game timer and handle time expiration
      startTimer() {
        this.timerInterval = setInterval(() => {
          this.timeLeft--;
  
          if (this.timeLeft === 0) {
            this.moveBallRandomly();
            this.resetTimer();
          }
        }, 1000);
      },
  
      // Reset the timer back to 4 seconds
      resetTimer() {
        this.timeLeft = 4;
      },
  
      // Replay the game
      replayGame() {
        this.gameOver = false;
        this.score = 0;
        this.totalAppearances = 0;
        this.selectRandomPair();
        this.moveBallRandomly();
        this.startTimer();
      }
    },
    mounted() {
      this.selectRandomPair();
      this.moveBallRandomly(); // Move ball to a random position when the game starts
      this.startTimer(); // Start the timer
    },
    beforeUnmount() {
      clearInterval(this.timerInterval); // Cleanup timer when component is destroyed
    }
  };
  </script>
  
  <style scoped>
  /* Full screen container */
  .game-container {
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
  }
  
  /* Positioning for the ball */
  .ball {
    width: 50px; /* Adjust ball size */
    height: 50px;
    position: absolute;
    cursor: pointer;
    transition: transform 0.3s ease; /* Adding a small animation */
  }
  
  /* Style for the stopwatch in the top-left corner */
  .stopwatch {
    position: absolute;
    top: 10px;
    left: 10px;
    background-color: rgba(0, 0, 0, 0.5); /* Transparent background */
    padding: 10px;
    border-radius: 10px;
    color: #fff;
    font-size: 22px;
    font-family: 'Courier New', Courier, monospace;
  }
  
  /* Scoreboard in the top-right corner */
  .scoreboard {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: rgba(0, 0, 0, 0.5); /* Transparent background */
    padding: 10px;
    border-radius: 10px;
    color: #fff;
    font-size: 22px;
    font-family: 'Courier New', Courier, monospace;
  }
  
  /* Attractive End button in the bottom-right corner */
  .end-button {
    position: absolute;
    bottom: 10px;
    right: 10px;
    padding: 15px 25px;
    background-color: #ff6347;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .end-button:hover {
    background-color: #e55347;
  }
  
  /* Replay button with an image */
  .replay-button {
    background-color: transparent;
    border: none;
    cursor: pointer;
    margin-top: 40px;
  }
  
  .replay-icon {
    width: 200px;
    height:200px;
  }
  
  /* Game over text styling */
  .game-over {
    text-align: center;
    font-size: 24px;
    color: white;
    background-color: rgba(0, 0, 0, 0.6);
    padding: 20px;
    border-radius: 10px;
  }
  </style>
  