<template>
  <div class="drawing_system_wrapper">
    <!-- Reference Image -->
    <div class="reference_image_section">
      <h1>Draw It 😀</h1> <br>
      <img :src="`/drawing_images_references/${imageName}`" style="max-width: 350px; max-height: 350px;"> <br><br>
      <button @click="shuffleImage" :style="{ backgroundColor: 'blue' }">New Image</button>
    </div>

    <!-- Canvas and tools -->
    <div style="display: flex; align-items: center; width: calc(100vw - 300px); height: 95vh;
    flex-direction: column; justify-content: center;">
      <canvas ref="canvas" id="drawingCanvas" width="700" height="700" @mousedown="startDrawing" @mouseup="stopDrawing"
        @mousemove="draw" class="the_original_canvas"></canvas>
      <div class="tools">
        <button class="canvas_click_button" :class="{ active: activeTool === 'pen' }" @click="setPen">Pen</button>
        <button class="canvas_click_button" :class="{ active: activeTool === 'brush' }" @click="setBrush">Brush</button>
        <button class="canvas_click_button" :class="{ active: activeTool === 'eraser' }"
          @click="setEraser">Eraser</button>
        <button class="canvas_click_button" :class="{ active: activeTool === 'clear' }"
          @click="clearCanvas">Clear</button>
        <div style="margin-left: 50px; display: flex; align-items: center; justify-content: center; margin-top: 7px;">
          <p>Color</p>
          <input type="color" v-model="color" style="margin-left: 10px; width: 37px; height: 37px;">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie'; 


export default {
  name: 'DrawingCanvas',
  data() {
    return {
      isDrawing: false,
      context: null,
      lineWidth: 2,
      color: '#000000',
      activeTool: 'pen',
      imageName: '',
      imageList: [
        '8115ab50-6f99-409a-ae47-d730b9c68ced.jpeg',
      ],
      combinedImage: null,
    };
  },
  mounted() {
    const canvas = this.$refs.canvas;
    this.context = canvas.getContext('2d');
    this.shuffleImage();
  },
  methods: {
    shuffleImage() {
      if (this.imageList.length > 0) {
        const randomIndex = Math.floor(Math.random() * this.imageList.length);
        this.imageName = this.imageList[randomIndex];
      }
    },
    startDrawing(event) {
      this.isDrawing = true;
      this.context.beginPath();
      this.context.moveTo(event.offsetX, event.offsetY);
    },
    async stopDrawing() {
      this.isDrawing = false;
      this.context.closePath();

      // Create a temporary canvas to combine images
      const tempCanvas = document.createElement('canvas');
      const tempContext = tempCanvas.getContext('2d');

      // Load the reference image
      const refImage = new Image();
      refImage.crossOrigin = "Anonymous";
      refImage.src = `/drawing_images_references/${this.imageName}`;

      await new Promise((resolve) => {
        refImage.onload = async () => {
          // Set canvas size to accommodate both images vertically
          tempCanvas.width = Math.max(700, refImage.width);
          tempCanvas.height = refImage.height + 700; // 700 is the height of drawing canvas

          // Draw reference image at the top
          tempContext.drawImage(refImage, 0, 0);

          // Draw the canvas content below it
          tempContext.drawImage(this.$refs.canvas, 0, refImage.height);

          // Convert combined image to base64
          this.combinedImage = tempCanvas.toDataURL('image/png');

          const token = Cookies.get('token');
          try {
            // Send the combined image to OCR API
            const response = await axios.post('http://localhost:8080/api/v1/drawing/compare_drawn_and_reference',
              {
                image: this.combinedImage
              },
              {
                headers: {
                  'Authorization': `Bearer ${token}`
                }
              }
            );

            this.playPredefinedText(response.data);

          } catch (error) {
            console.error('Error calling OCR API:', error);
          }

          resolve();
        };
      });
    },
    draw(event) {
      if (!this.isDrawing) return;
      this.context.lineWidth = this.lineWidth;
      this.context.strokeStyle = this.color;
      this.context.lineTo(event.offsetX, event.offsetY);
      this.context.stroke();
    },
    setPen() {
      this.activeTool = 'pen';
      this.lineWidth = 2;
      this.color = '#000000';
    },
    setBrush() {
      this.activeTool = 'brush';
      this.lineWidth = 10;
    },
    setEraser() {
      this.activeTool = 'eraser';
      this.lineWidth = 10;
      this.color = '#FFFFFF';
    },
    clearCanvas() {
      this.activeTool = 'clear';
      const canvas = this.$refs.canvas;
      this.context.clearRect(0, 0, canvas.width, canvas.height);
    },
    playPredefinedText(imageFeedback) {
      if (imageFeedback.trim() === "") {
        alert("Please enter some text.");
        return;
      }

      const utterance = new SpeechSynthesisUtterance(imageFeedback);
      speechSynthesis.speak(utterance);
    },
  },
};
</script>

<style scoped>
.drawing_system_wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-left: 100px;
}

.tools {
  margin-top: 10px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.canvas_click_button {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: rgb(183, 195, 237);
  color: black;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.canvas_click_button.active {
  background-color: rgb(128, 63, 202);
  color: white;
}

.the_original_canvas {
  border: 0.5px solid rgb(182, 182, 182);
  border-radius: 10px;
  background-color: white;
}
</style>