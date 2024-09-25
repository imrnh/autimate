<script>
import "../../../css/global.css";
import "../../../css/page5.css";
import axios from 'axios';

export default {
    data() {
        return {
            selectedActivity: '',
            videoFile: null,
            presignedURL: '',
            presignedData: {},
            isLoading: false,
            filename: '',
            buttonText: 'Check Result'
        };
    },
    methods: {
        async fetchPresignedURL() {
            try {
                const response = await axios.get('http://localhost:8080/api/ex/pre-signed-url');
                this.presignedData = response.data;
                this.presignedURL = this.presignedData.presignedUrl;
            } catch (error) {
                console.error("Error fetching presigned URL:", error);
                alert("Failed to fetch presigned URL. Please try again.");
            }
        },

        onFileSelected(event) {
            this.videoFile = event.target.files[0];
            this.filename = this.videoFile.name;
        },
        sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        },
        async uploadFile() {
            //validate file exist or not.
            if (!this.videoFile) {
                alert("Please select a file before uploading.");
                return;
            }

            try {
                this.isLoading = true;
                this.buttonText = 'Uploading video...';
                await this.fetchPresignedURL();
                const response = await axios.put(this.presignedURL, this.videoFile, {
                    headers: {
                        'Content-Type': this.videoFile.type,
                    },
                });

                if (response.status !== 200) {
                    throw new Error('Upload failed: ' + response.statusText);
                    this.buttonText = "Check Result";
                }

                //then invoke the function on the following file.
                this.buttonText = 'Analysing...';
                await axios.post(`http://localhost:8080/api/ex/invoke-video-ex/${this.presignedData.uuid}`);

                console.log(this.presignedData.uuid)

                this.sleep(10000).then(async () => {
                    
                    //request for the result.
                    const req_id = this.presignedData.uuid.split('.mp4').join('');
                    const response = await axios.get(`http://localhost:8080/api/ex/get-result/req-id/${req_id}`);
                    var rdata = response.data

                    console.log(rdata)

                    var asdStatus = "Negative";
                    if(rdata.asdStatus == 0){
                        asdStatus = "Positive";
                    }
                    var confidence = rdata.confidence * 100
                    confidence = parseFloat(confidence.toFixed(2))


                    this.isLoading = false;
                    this.buttonText = 'Check Result';

                    this.$router.push({
                        path: '/dashboard/video-test-result/',
                        query: {
                            asd_status: asdStatus,
                            confidence: confidence,
                        }
                    });

                });


            } catch (error) {
                console.error("Error uploading video:", error);
                alert("Error uploading video. Please try again.");
                this.isLoading = false;
                this.buttonText = 'Check Result';
            }
        },
        async fetchResult() {
            try {
                
            } catch (error) {
                console.error('Error fetching the result:', error);
            }
        },

        async checkResult() {
            this.uploadFile();
        }
    }
};
</script>

<template>
    <section class="all-contents2" style="display: flex; justify-content: baseline; align-items: center;">
        <div class="heading-frame" style="display: flex; align-items: flex-start; justify-content: flex-start;">
            <h1 class="heading2" style="font-size: 50px;">Behavioral Video Analysis</h1>
        </div>

        <br><br>
        <div class="content-body">
            <div class="auto-layout-vertical">
                <div class="auto-layout-vertical1" style="margin-top: -40px;">
                    <h3 style="font-weight: 500; font-size: 23px;">
                        Upload a 15-20 sec video of your child doing any of the selected activities.
                    </h3>
                </div>

                <!-- File Input for Selecting the File -->
                <br><br>
                <div class="auto-layout-horizontal">
                    <div class="auto-layout-vertical2">
                        <img class="iconlyboldimage" loading="lazy" alt=""
                            src="@/assets/public/iconlyboldimage@2x.png" />
                        <input type="file" @change="onFileSelected" accept="video/*"
                            style="display: none; width: 100px; height: 100px;" id="fileInput" />
                        <label for="fileInput" class="select-file" style="cursor: pointer;">Select file</label>
                    </div>
                </div>

                <p style="font-size: 16px; margin-top: 20px">{{ filename }}</p>
                <!-- <div class="auto-layout-horizontal1">
                    <img class="themelight-divider-icon" loading="lazy" alt=""
                        src="@/assets/public/themelight-divider.svg" />
                    <br><br>
                    <div style="margin-top: 20px;" class="or">or</div>
                    <img class="themelight-divider-icon" loading="lazy" alt=""
                        src="@/assets/public/themelight-divider-1.svg" />
                </div> -->
            </div>
        </div>
        <br>

        <button class="button6" @click="checkResult">
            <div style="font-size: 20px; display: flex; align-items: center;">
                <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"
                    style="margin-right: 10px;"></span>
                {{ buttonText }} <!-- Use buttonText instead of hardcoded text -->
            </div>
        </button>

        <div class="" style="width: 20px; height: 10px;"></div>
    </section>
</template>

<style></style>
