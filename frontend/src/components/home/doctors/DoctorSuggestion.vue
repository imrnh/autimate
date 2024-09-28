<template>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.0/css/font-awesome.min.css"
        integrity="sha512-FEQLazq9ecqLN5T6wWq26hCZf7kPqUbFC9vsHNbXMJtSZZWAcbJspT+/NEAQkBfFReZ8r9QlA9JHaAuo28MTJA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />

        
    <div class="all-contents4" style="width: calc(100vw - 270px); height: 98vh;">

        <div class="content-items" v-for="doctor in doctors" :key="doctor.id" style="display: flex; align-items: flex-start; justify-content: flex-start;">
            <div class="item-6" style="border: 1px solid rgb(239, 239, 239); position: relative; height: 330px;">
                <img class="image-icon3" loading="lazy"
                    :src="doctor.image ? 'http://localhost:8080' + doctor.image : './public/image-3@2x.png'"
                    alt="Doctor Image" />

                <div class="profile-info">
                    <b class="text">{{ doctor.name }}</b>
                    <!-- <div class="pricing">{{ doctor.experienceCount || 'N/A' }}</div> -->
                    <div class="location-on-black-24dp-2-parent" style="margin-top: -10px;">
                        <div class="doane-street-fremont-ca-94538-wrapper">
                            <div class="doane-street-fremont"
                                style="position: absolute; left: 0; margin-left: 10px; margin-top: 4px;">
                                <i class="fa fa-map-marker" aria-hidden="true"></i>
                                {{ doctor.address || 'Address not available' }}
                            </div>
                        </div>
                    </div>
                    <div style="width: 1px; height: 10px;"></div>
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
            doctors: [] // This will hold the doctor data
        };
    },
    mounted() {
        this.fetchDoctors();
    },
    methods: {
        async fetchDoctors() {
            const token = Cookies.get('token');

            const ipAddress = await this.getUserIpAddress(); // Get user's IP address

            try {
                const response = await axios.get('http://localhost:8080/api/v1/doctor/get-all', {
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
    }
};
</script>
