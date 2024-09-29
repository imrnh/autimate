<script>
import axios from "axios";

export default {
  name: "SnakeGame",
  data() {
    return {
      canvasWidth: 700,
      canvasHeight: 700,
      ctx: null,
      timer: null,
      interval: 140,
      score: 0,
      highestScore: 0,
      showHighScoreModal: false,
      // Game State
      inGame: false,
      dots: 3,
      DOT_SIZE: 10,
      ALL_DOTS: 900,
      RAND_POS: 29,
      x: Array(900).fill(0),
      y: Array(900).fill(0),
      apple_x: 0,
      apple_y: 0,
      leftDirection: false,
      rightDirection: true,
      upDirection: false,
      downDirection: false,
      images: {
        ball: null,
        apple: null,
        head: null,
      },
    };
  },
  methods: {
    startGame() {
      this.initBoard();
      this.initGame();
      this.inGame = true;
      this.timer = setInterval(this.gameLoop, this.interval);
      window.addEventListener("keydown", this.keyDownHandler);
    },
    initBoard() {
      const canvas = this.$refs.gameCanvas;
      this.ctx = canvas.getContext("2d");
      // Load Images
      this.images.ball = new Image();
      this.images.ball.src = "/snake/dot.png";
      this.images.apple = new Image();
      this.images.apple.src = "/snake/apple.png";
      this.images.head = new Image();
      this.images.head.src = "/snake/head.png";
    },
    initGame() {
      this.dots = 3;
      this.score = 0; // Reset the score
      for (let z = 0; z < this.dots; z++) {
        this.x[z] = 50 - z * this.DOT_SIZE;
        this.y[z] = 50;
      }
      this.locateApple();
    },
    gameLoop() {
      if (this.inGame) {
        this.checkApple();
        this.checkCollision();
        this.move();
        this.draw();
      } else {
        clearInterval(this.timer);
        window.removeEventListener("keydown", this.keyDownHandler);
        alert(`Game Over. Your score: ${this.score}! Let's Play Again!`);
        this.saveScore();
      }
    }
    ,
    draw() {
      this.ctx.fillStyle = "black";
      this.ctx.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
      this.ctx.drawImage(this.images.apple, this.apple_x, this.apple_y, this.DOT_SIZE, this.DOT_SIZE);
      for (let z = 0; z < this.dots; z++) {
        if (z === 0) {
          this.ctx.drawImage(this.images.head, this.x[z], this.y[z], this.DOT_SIZE, this.DOT_SIZE);
        } else {
          this.ctx.drawImage(this.images.ball, this.x[z], this.y[z], this.DOT_SIZE, this.DOT_SIZE);
        }
      }
    },
    move() {
      for (let z = this.dots; z > 0; z--) {
        this.x[z] = this.x[z - 1];
        this.y[z] = this.y[z - 1];
      }

      if (this.leftDirection) {
        this.x[0] -= this.DOT_SIZE;
      }
      if (this.rightDirection) {
        this.x[0] += this.DOT_SIZE;
      }
      if (this.upDirection) {
        this.y[0] -= this.DOT_SIZE;
      }
      if (this.downDirection) {
        this.y[0] += this.DOT_SIZE;
      }
    },
    checkApple() {
      if (this.x[0] === this.apple_x && this.y[0] === this.apple_y) {
        this.dots++;
        this.score += 10;
        this.locateApple();
      }
    },
    checkCollision() {
      for (let z = this.dots; z > 0; z--) {
        if (z > 4 && this.x[0] === this.x[z] && this.y[0] === this.y[z]) {
          this.inGame = false;
        }
      }

      if (this.y[0] >= this.canvasHeight || this.y[0] < 0 || this.x[0] >= this.canvasWidth || this.x[0] < 0) {
        this.inGame = false;
      }
    },
    locateApple() {
      let r = Math.floor(Math.random() * this.RAND_POS);
      this.apple_x = r * this.DOT_SIZE;

      r = Math.floor(Math.random() * this.RAND_POS);
      this.apple_y = r * this.DOT_SIZE;
    },
    keyDownHandler(e) {
      const key = e.keyCode;
      if (key === 37 && !this.rightDirection) {
        this.leftDirection = true;
        this.upDirection = false;
        this.downDirection = false;
      }
      if (key === 39 && !this.leftDirection) {
        this.rightDirection = true;
        this.upDirection = false;
        this.downDirection = false;
      }
      if (key === 38 && !this.downDirection) {
        this.upDirection = true;
        this.rightDirection = false;
        this.leftDirection = false;
      }
      if (key === 40 && !this.upDirection) {
        this.downDirection = true;
        this.rightDirection = false;
        this.leftDirection = false;
      }
    },
    async saveScore() {
      try {
        // Send the current score to the backend
        await axios.post("http://localhost:8080/api/snake/score", { score: this.score });

        // Fetch the highest score from the backend
        const response = await axios.get("http://localhost:8080/api/snake/highscore");
        this.highestScore = response.data.highScore;

        // Show the high score modal
        this.showHighScoreModal = true;

      } catch (error) {
        console.error("Error saving score or fetching high score:", error);
      }
    },
    closeHighScoreModal() {
      this.showHighScoreModal = false;
      this.initGame(); // Reinitialize the game for a new round
    },
  },
};
</script>

<template>
  <div class="game-container" style="width: calc(100vw - 300px); display: flex; height: 95vh; align-items: center; justify-content: center;">
    <canvas
        id="gameCanvas"
        ref="gameCanvas"
        :width="canvasWidth"
        :height="canvasHeight"
    ></canvas>
    <div class="controls">
      <button @click="startGame">Start Game</button>
      <p>Score: {{ score }}</p>
    </div>

    <!-- Modal to show high score -->
    <div v-if="showHighScoreModal" class="modal">
      <div class="modal-content">
        <p>Game Over! Highest Score: {{ highestScore }}</p>
        <button @click="closeHighScoreModal">Close</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.game-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.controls {
  margin-top: 10px;
}

button {
  padding: 10px 20px;
  font-size: 16px;
}

p {
  font-size: 18px;
  margin-top: 5px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.7);
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}

.modal-content button {
  margin-top: 10px;
  padding: 10px 20px;
  font-size: 16px;
}
</style>
