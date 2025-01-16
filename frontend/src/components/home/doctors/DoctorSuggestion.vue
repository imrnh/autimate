<template>
    <div class="container" style="margin-top: 30px; min-width: calc(100vw - 240px);">
        <select name="doctor_sorting" v-model="selectedSorting" @change="onSortingChange"
            style="width: 200px; height: 40px; border-radius: 10px; background-color: transparent; border: 1px solid gray; color: black; padding-left: 10px;">
            <option value="nearby">Nearest</option>
            <option value="highest-rated">Highest Rated</option>
        </select>
        <br><br>

        <!-- Header Row -->
        <div class="list-group-item"
            style="display: flex; font-weight: bold; padding: 15px; background-color: #f1f1f1;">
            <div style="width: 10%; text-align: center;">Image</div>
            <div style="width: 15%;">Name</div>
            <div style="width: 28%;">Description</div>
            <div style="width: 15%;">Specialities</div>
            <div style="width: 17%;">Office Hours</div>
            <div style="width: 7%;">Experience</div>
            <div style="width: 8%;">Phone</div>
        </div>

        <div class="list-group">
            <div class="list-group-item" v-for="doctor in doctors" :key="doctor.id"
                style="display: flex; align-items: center; padding: 8px; border-radius: 10px; gap: 15px; border: 0; margin-top: 5px;">

                <!-- Doctor Image -->
                <div style="width: 8%; text-align: center;">
                    <img :src="doctor.image ? 'http://localhost:8080' + doctor.image : './public/image-3@2x.png'"
                        alt="Doctor Image" style="width: 70px; height: 70px; object-fit: cover; border-radius: 100px; ">
                </div>

                <!-- Doctor Info -->
                <div style="width: 15%; display: flex; flex-direction: column; align-items: flex-start; justify-content: flex-start;">
                    <h5 style="font-weight: 800; font-size: 1rem; color: #343a40;">{{ doctor.name }}</h5>
                    <p style="margin-top: 0px; font-size: 10px;">{{ doctor.address }}</p>
                </div>

                <div style="width: 28%;">
                    <p>{{ doctor.description || 'No description available' }}</p>
                </div>

                <div style="width: 15%;">
                    <p>{{ doctor.specialities || "" }}</p>
                </div>

                <div style="width: 17%;">
                    <p style="margin-top: 10px;">{{ doctor.officeHours }}</p>
                    <!-- <p style="margin-top: 10px;">{{ doctor.phone }}</p> -->
                </div>
                
                <div style="width: 7%;">
                    <p style="color: brown; font-weight: 600;">{{ doctor.experienceCount || '_' }} years</p>
                </div>

                <div style="width:8%;">
                    <p style="color: darkolivegreen;"><i class="fa fa-map-marker" aria-hidden="true"></i> {{
                        doctor.phone || "" }}</p>
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


<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap');

.list-group-item {
    font-family: "Inter", serif;
    font-size: 14px;
}
</style>