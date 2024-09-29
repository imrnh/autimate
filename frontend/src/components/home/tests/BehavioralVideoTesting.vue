<script>
import "../../../css/global.css";
import "../../../css/page5.css";
import axios from 'axios';
import Cookies from 'js-cookie';  // Install js-cookie if you haven't: npm install js-cookie


export default {
    data() {
        return {
            selectedActivity: '',
            videoFile: null,
            presignedURL: '',
            presignedData: {},
            isLoading: false,
            filename: '',
            buttonText: 'Check Result',
            questionAnswers: null,
        };
    },
    mounted() {
        const questionAnswersQuery = this.$route.query.questionAnswers;
        if (questionAnswersQuery) {
            this.questionAnswers = JSON.parse(questionAnswersQuery);
            // console.log('Question Answers:', this.questionAnswers);
        }
    },

    methods: {
        async fetchPresignedURL() {
            try {
                const token = Cookies.get('token');
                const response = await axios.get('http://localhost:8080/api/v1/aex/url/presigned', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                console.log(response)
                this.presignedData = response.data;
                this.presignedURL = this.presignedData.preSignedUrl;
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
                console.log(this.presignedData)
                const response = await axios.put(this.presignedURL, this.videoFile, {
                    headers: {
                        'Content-Type': this.videoFile.type,
                    },
                });

                if (response.status !== 200) {
                    throw new Error('Upload failed: ' + response.statusText);
                    this.buttonText = "Check Result";
                }

                this.buttonText = 'Analysing...';

                const token = Cookies.get('token');

                const convertedAnswers = this.questionAnswers.map(answer => {
                    if (answer === null || answer === undefined || answer === 'undefined') {
                        return 0; // Convert null and undefined to 0
                    } else if (answer === "1") {
                        return 1; // Convert "1" to integer 1
                    } else if (answer === "0") {
                        return 0; // Convert "0" to integer 0
                    } else {
                        const parsed = parseInt(answer, 10);
                        return isNaN(parsed) ? 0 : parsed; // Return parsed value or 0 if NaN
                    }
                }).filter((_, index) => index < 14); // Filter to get only the first 14

                // Construct the request body
                const requestBody = {
                    "arrq10": convertedAnswers,
                    "video_name": this.presignedData.uuid // Add the UUID as needed
                };

                // Send the POST request with body
                const responseInvoke = await axios.post('http://localhost:8080/api/v1/aex/invoke', requestBody, {
                    headers: {
                        Authorization: `Bearer ${token}` // Include your token if needed
                    }
                });


                console.log(responseInvoke);

                const req_id = this.presignedData.uuid.split('.mp4').join('');

                this.sleep(10000).then(async () => {
                    this.$router.push({
                        path: '/dashboard/aex/r',
                        query: {
                            req_id: req_id
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
    <section class="all-contents2"
        style="display: flex; justify-content: baseline; align-items: center; width: calc(100vw - 280px);">
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
