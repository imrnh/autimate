<template>
    <div style="display: flex; align-items: center; width: calc(100vw - 300px); height: 95vh;
    flex-direction: column; justify-content: center;">
      <canvas
          ref="canvas"
          id="drawingCanvas"
          width="1200"
          height="700"
          @mousedown="startDrawing"
          @mouseup="stopDrawing"
          @mousemove="draw"
          style="border:0.5px solid gray; border-radius: 10px; background-color: white;"
      ></canvas>
      <div class="tools">
        <button @click="setPen">Pen</button>
        <button @click="setBrush">Brush</button>
        <button @click="setEraser">Eraser</button>
        <button @click="clearCanvas">Clear</button>
        <div>
          <label style="margin-top: -5px; margin-left: 30px;">Color: </label>
          <input type="color" style="margin-top: 10px; margin-left: 10px;" v-model="color" />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DrawingCanvas',
    data() {
      return {
        isDrawing: false,
        context: null,
        lineWidth: 2,
        color: '#000000',
      };
    },
    mounted() {
      const canvas = this.$refs.canvas;
      this.context = canvas.getContext('2d');
    },
    methods: {
      startDrawing(event) {
        this.isDrawing = true;
        this.context.beginPath();
        this.context.moveTo(event.offsetX, event.offsetY);
      },
      stopDrawing() {
        this.isDrawing = false;
        this.context.closePath();
      },
      draw(event) {
        if (!this.isDrawing) return;
        this.context.lineWidth = this.lineWidth;
        this.context.strokeStyle = this.color;
        this.context.lineTo(event.offsetX, event.offsetY);
        this.context.stroke();
      },
      setPen() {
        this.lineWidth = 2;
      },
      setBrush() {
        this.lineWidth = 10;
      },
      setEraser() {
        this.lineWidth = 10;
        this.color = '#FFFFFF'; // Set the color to white for eraser
      },
      clearCanvas() {
        const canvas = this.$refs.canvas;
        this.context.clearRect(0, 0, canvas.width, canvas.height);
      },
    },
  };
  </script>
  
  <style scoped>
  .tools {
    margin-top: 10px;
    display: flex;
    gap: 10px;
    align-items: center;
  }
  </style>
  