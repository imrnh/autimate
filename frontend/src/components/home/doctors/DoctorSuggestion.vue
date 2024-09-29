<template>
    <div class="container" style="margin-top: 30px; min-width: calc(100vw - 240px);">

        <select name="doctor_sorting" v-model="selectedSorting" @change="onSortingChange" 
            style="width: 200px; height: 40px; border-radius: 10px; background-color: transparent; border: 1px solid gray; color: black; padding-left: 10px;">
            <option value="nearby">Nearest</option>
            <option value="highest-rated">Highest Rated</option>
        </select>

        <br>
        <br>
        <div class="row" style="display: flex; flex-wrap: wrap; gap: 0px; padding: 0;">
            <div class="col-md-4" v-for="doctor in doctors" :key="doctor.id" style="width: 500px; height: 330px;">
                <div class="card mb-4 shadow-sm"
                    style="border-radius: 15px; overflow: hidden; border: none; padding: 8px;">
                    <div style="display: flex; flex-direction: row; align-items: center;">
                        <!-- Doctor Image -->
                        <img style="width: 180px; height: 180px; object-fit: cover; border-radius: 15px; position: absolute; top: 0; margin-top: 10px;"
                            :src="doctor.image ? 'http://localhost:8080' + doctor.image : './public/image-3@2x.png'"
                            alt="Doctor Image">

                        <!-- Doctor Info -->
                        <div
                            style="padding: 5px; width: 300px; height: 260px; background-color: #f8f9fa; flex-grow: 1; border-radius: 15px;">
                            <h5 style="font-weight: 800; font-size: 1.5rem; color: #343a40; 
                                position: absolute; left: calc(180px + 35px); top: 0; margin-top: 20px;">
                                {{ doctor.name }}</h5>

                            <p style="position: absolute; top: 0; margin-top: 55px; left: 0; margin-left: 215px;"><i
                                    style="color: gold" class="fa fa-star" aria-hidden="true"></i> &nbsp;{{
                                        doctor.ratings || "-" }} </p>

                            <p style="position: absolute; left: calc(180px + 35px); top: 0; margin-top: 80px;">{{
                                doctor.description || 'No description available' }}</p>
                            <p
                                style="position: absolute; left: calc(180px + 35px); top: 0; margin-top: 130px; color: brown; font-weight: 600;">
                                {{
                                    doctor.experienceCount || '_' }} experience</p>
                            <p style="position: absolute; left: calc(180px + 35px); top: 0; margin-top: 155px;"><b
                                    style="color: blueviolet">Specialities:</b> {{ doctor.specialities || "" }}</p>

                            <p
                                style="position: absolute; bottom: 0; margin-bottom: 44px; left: 0; margin-left: 15px;color: darkolivegreen;">
                                <i class="fa fa-map-marker" aria-hidden="true"></i> {{ doctor.address || "" }}<br>
                            </p>

                            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; | &nbsp; &nbsp; &nbsp;

                            <p class="card-text"
                                style="color: #6c757d; line-height: 1.6; position: absolute; bottom: 0; 
                                margin-bottom: 15px; left: 0; margin-left: 15px; display: flex; align-items: self-start; justify-content: space-around;">
                            <p>{{ doctor.phone }}</p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a :href="doctor.website" target="_blank"
                                style="color: #007bff; text-decoration: none;">Website</a><br>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import '../../../css/global.css';
import '../../../css/page7.css';

import axios from 'axios';
import Cookies from 'js-cookie';

export default {
    data() {
        return {
            doctors: [], // This will hold the doctor data
            selectedSorting: 'nearby' // Default sorting option
        };
    },
    mounted() {
        this.fetchDoctors('nearby'); // Fetch doctors for the default sorting option
    },
    methods: {
        async fetchDoctors(sorting) {
            const token = Cookies.get('token');
            const ipAddress = await this.getUserIpAddress(); // Get user's IP address
            let endpoint = '';

            if (sorting === 'highest-rated') {
                endpoint = 'http://localhost:8080/api/v1/doctor/list/ratings';
            } else {
                endpoint = 'http://localhost:8080/api/v1/doctor/list/nearby';
            }

            try {
                const response = await axios.get(endpoint, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-IP-Address': ipAddress
                    }
                });
                this.doctors = response.data; // Assuming the response is in the expected format
            } catch (error) {
                console.error('Error fetching doctors:', error);
            }
        },
        async getUserIpAddress() {
            try {
                const response = await axios.get('https://api.ipify.org?format=json');
                return response.data.ip; // Returns the user's IP address
            } catch (error) {
                console.error('Error fetching IP address:', error);
                return '0.0.0.0'; // Default IP if unable to fetch
            }
        },
        onSortingChange() {
            this.fetchDoctors(this.selectedSorting); // Fetch doctors based on the selected sorting option
        }
    }
};
</script>
