<script>
import '../../../css/global.css';
import '../../../css/page6.css';

import axios from 'axios';
import Cookies from 'js-cookie';

export default {
    data() {
        return {
            childId: null,
            childName: null,
            req_id: '',
        };
    },
    async mounted() {
        this.childId = Cookies.get('childId');
        this.childName = Cookies.get('child_name');
        this.req_id = this.$route.query.req_id;

        if (this.req_id) {
            try {
                const token = Cookies.get('token');
                const response = await axios.get(`http://localhost:8080/api/v1/aex/res/${this.req_id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const rdata = response.data;

                {/* this.asdStatus = rdata.asdStatus === 0 ? "Positive" : "Negative";
        this.confidence = parseFloat((rdata.confidence * 100).toFixed(2)); */}
            } catch (error) {
                console.error("Error fetching result:", error);
                alert("Error fetching result. Please try again.");
            }
        }
    }
}

</script>


<template>
    <div class="tres_wrapper"
        style="width: calc(100vw - 400px); display: flex; flex-direction: column; margin-left: 100px; justify-content: center;">






        <img class="cardcontent-icon" loading="lazy" alt="" src="./public/vector-153.svg" />
        <img class="stars-sec-icon" loading="lazy" alt="" src="./public/vector-155.svg" />

        <div class="score-details" style="margin-top: 60px; margin-right: -40px;">
            <div class="full-name-score">
                <h3 class="full-names-score"
                    style="font-size: 25px;font-family: 'Poppins'; text-decoration: none; color: rgb(50, 50, 230);">{{
                        childName }}’s
                    <font color="gray" style="font-family: 'Poppins';">ASD Test Score</font>
                </h3>
            </div>



            <div class="row_res">

                <!-- Progress Bar -->
                <div class="circular_shaped_res">
                    <div class="progress-bars-wrapper" style="margin-top: 30px;">
                        <div class="progress-bars">
                            <div class="progress-bar">
                                <img class="subtract-icon" loading="lazy" alt="" src="@/assets/public/subtract.svg" />

                                <img class="progress-bar-child" alt="" src="@/assets/public/ellipse-211.svg" />

                                <img class="progress-bar-item" alt="" src="@/assets/public/ellipse-212.svg" />
                            </div>
                            <b class="link">66%</b>
                        </div>
                    </div>

                    <div class="advice">
                        <div class="adviceneed-improvement-wrapper" style="margin-top: 20px;">
                            <div class="adviceneed-improvement" style="font-family: 'Inter'">
                                Needs Improvement
                            </div>
                        </div>

                    </div>
                </div>



                <!-- Bar Result -->
                <div class="bar_result">
                    <div class="chart">
                        <div class="chart-container">
                            <div class="pagination">
                                <div class="pagination-controls">
                                    <img class="up-kontur-34-icon" loading="lazy" alt=""
                                        src="@/assets/public/up--kontur34.svg" />

                                    <img class="up-kontur-34-icon" loading="lazy" alt=""
                                        src="@/assets/public/up--kontur35.svg" />

                                    <img class="up-kontur-34-icon" loading="lazy" alt=""
                                        src="@/assets/public/down--kontur7.svg" />
                                </div>
                            </div>
                            <div class="details">

                                <div class="user">
                                    <div class="paragraph-sec">100</div>
                                    <div class="user-name">
                                        <div class="row">50</div>
                                    </div>
                                    <div class="colmd">0</div>
                                </div>
                                <div class="quiz">
                                    <div class="quiz-content">
                                        <img class="testimonials-card-icon" loading="lazy" alt=""
                                            src="@/assets/public/vector-153.svg" />

                                        <div class="quiz-content-inner">
                                            <div class="frame-parent11">
                                                <div class="frame-parent12">
                                                    <div class="icn-bxsstar-wrapper">
                                                        <div class="icn-bxsstar4"></div>
                                                    </div>
                                                    <div class="mcq1">MCQ</div>
                                                </div>
                                                <div class="frame-parent13">
                                                    <div class="icn-bxsstar-container">
                                                        <div class="icn-bxsstar4"></div>
                                                    </div>
                                                    <div class="bv11">BV1</div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <div class="bottom_bar">
            <div class="click-here-to-container" style="font-size: 20px;">
                <router-link to="/dashboard/doctors">
                    Check nearby doctors.
                </router-link>
            </div>
            <h3 style="font-size: 20px; margin-top: 15px; color: orangered;">or, Start with suggested therapies and
                Games</h3>

            <div class="therapy_redirects">
                <button class="therapy_suggestion_button" style="margin-left: -10px;"> Speech Therapy </button>
                <button class="therapy_suggestion_button"> Memory Therapy </button>
            </div>

        </div>

    </div>
</template>


<style>
.row_res {
    width: calc(100vw - 400px);
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: space-around;
    top: 0;
    margin-top: 200px;
}


.sugg_therapies {
    position: absolute;
    left: 0;
    bottom: 0;
    margin-left: 335px;
    font-family: 'Poppins';
}

.bottom_bar {
    position: absolute;
    bottom: 0;
    height: 280px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
}

.therapy_suggestion_button {
    width: 130px;
    height: 80px;
    background-color: white;
    color: rgb(62, 79, 163);
    margin: 10px;
    font-size: 15px;
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
}
</style>