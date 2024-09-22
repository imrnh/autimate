<script>
import "../../../css/global.css";
import "../../../css/page5.css";


export default {
    data() {
        return {
            selectedActivity: '',
            videoURL: null,
            mediaRecorder: null,
            recordedChunks: [],
            // ffmpeg: createFFmpeg({ log: true }),
        };
    },
    methods: {
        async startRecording() {
            const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
            this.mediaRecorder = new MediaRecorder(stream);

            this.mediaRecorder.ondataavailable = (event) => {
                if (event.data.size > 0) {
                    this.recordedChunks.push(event.data);
                }
            };

            this.mediaRecorder.start();

            // Stop recording after 20 seconds
            setTimeout(() => {
                this.mediaRecorder.stop();
                stream.getTracks().forEach(track => track.stop()); // Stop all media tracks
                console.log(this.recordedChunks);
                this.saveVideo();
            }, 4000);
        },
        saveVideo() {
            const blob = new Blob(this.recordedChunks, { type: 'video/webm' }); // You can adjust the type if needed
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = 'C:/Users/muimr/Desktop/javafest/frontend/recorded_video.webm'; // Set the filename
            document.body.appendChild(a);
            a.click(); 
            window.URL.revokeObjectURL(url);

            console.log('Video saved!');
        },
        async convertVideoToMP4() {
            await this.ffmpeg.load();
            const blob = new Blob(this.recordedChunks, { type: 'video/webm' });
            const fileName = `video_${Date.now()}.webm`;
            this.ffmpeg.FS('writeFile', fileName, await fetchFile(blob));

            await this.ffmpeg.run('-i', fileName, 'output.mp4'); // Convert to MP4

            const data = this.ffmpeg.FS('readFile', 'output.mp4');
            const videoBlob = new Blob([data.buffer], { type: 'video/mp4' });
            const url = URL.createObjectURL(videoBlob);
            
            // Trigger download
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = `video_${Date.now()}.mp4`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);

            this.videoURL = url; // Set video URL for playback
            this.recordedChunks = []; // Clear the recorded chunks for the next recording
        },
        checkResult() {
            alert('Check result logic here!');
        }
    }
};
</script>

<template>
    <section class="all-contents2" style="display: flex; justify-content: baseline; align-items: center;">
        <div class="heading-frame" style="display: flex; align-items: flex-start; justify-content: flex-start;">
            <h1 class="heading2" style="font-size: 50px;">Behavioral Video Analysis</h1>
        </div>

        <br>
        <div class="content-body">
            <div class="auto-layout-vertical">
                <div class="auto-layout-vertical1" style="margin-top: -40px;">
                    <!-- <h4 style="font-size: 23px;">Select an activity</h4> <br> -->
                    <select style="height: 60px; border-radius: 15px; font-size: 20px; font-weight: 200;" class="form-select" aria-label="Default select example">
                        <option selected>Select an activity to test on</option>
                        <option value="1">Eat</option>
                        <option value="2">Shoot Ball</option>
                        <option value="3">Chew</option>
                        <option value="3">Flic flac</option>
                        <option value="3"> Shake Hands</option>
                    </select> <br>

                    <h3 style="font-weight: 500; font-size: 23px;">
                        Upload a 15-20 sec video of your child doing any of the selected activity.
                    </h3>
                    <div class="algorithm-require-you-container">
                        <p class="algorithm-require-you">&nbsp;</p>
                    </div>
                </div>
                <div class="auto-layout-horizontal">
                    <div class="auto-layout-vertical2">
                        <img class="iconlyboldimage" loading="lazy" alt=""
                            src="@/assets/public/iconlyboldimage@2x.png" />

                        <div class="select-file">Select file</div>
                    </div>
                </div>
                <div class="auto-layout-horizontal1">
                    <img class="themelight-divider-icon" loading="lazy" alt=""
                        src="@/assets/public/themelight-divider.svg" />
                    <br><br>
                    <div style="margin-top: 20px;" class="or">or</div>
                    <img class="themelight-divider-icon" loading="lazy" alt=""
                        src="@/assets/public/themelight-divider-1.svg" />
                </div>
                <button class="typebutton-type-2secondary" @click="startRecording">
                    <div class="auto-layout-horizontal2">
                        <img class="iconlyboldcamera" alt="" src="@/assets/public/iconlyboldcamera@2x.png" />

                        <b class="button8">Open Camera & Take Photo</b>
                        <img class="iconlyboldarrow-right" alt="" src="@/assets/public/iconlyboldarrow--right@2x.png" />
                    </div>
                </button>
            </div>
        </div>
        <button class="button6">
            <div style="font-size: 20px;">Check Result</div>
        </button>
    </section>


</template>

<style></style>