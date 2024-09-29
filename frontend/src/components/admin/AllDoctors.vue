<template>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.0/css/font-awesome.min.css"
        integrity="sha512-FEQLazq9ecqLN5T6wWq26hCZf7kPqUbFC9vsHNbXMJtSZZWAcbJspT+/NEAQkBfFReZ8r9QlA9JHaAuo28MTJA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />

    <div class="doctor_view_wrapper">
        <br>
        <router-link to="/admin/doctors/add">
            <button type="button" class="btn btn-outline-primary"
                style="position: absolute; right: 0; margin-right: 20px; margin-bottom: 50px;">+ Add new doctor</button>
        </router-link>
        <br>

        <div class="all-contents4" style="width: calc(100vw - 270px); height: 89vh; margin-top: 30px;">
            <div class="content-items" v-for="doctor in doctors" :key="doctor.id">
                <div class="item-6" style="border: 1px solid rgb(239, 239, 239); position: relative; height: 330px;">
                    <div class="bg"></div>
                    <img class="image-icon3" loading="lazy"
                        :src="doctor.image ? 'http://localhost:8080' + doctor.image : './public/image-3@2x.png'"
                        alt="Doctor Image" />

                    <div class="profile-info">
                        <b class="text">{{ doctor.name }}</b>
                        <!-- <div class="pricing">{{ doctor.experienceCount || 'N/A' }}</div> -->
                        <div class="location-on-black-24dp-2-parent" style="margin-top: -10px;">
                            <div class="doane-street-fremont-ca-94538-wrapper">
                                <div class="doane-street-fremont"
                                    style="position: absolute; left: 0; margin-left: 10px; margin-top: -2px;">
                                    <i class="fa fa-map-marker" aria-hidden="true"></i>
                                    {{ doctor.address || 'Address not available' }}
                                </div>
                            </div>
                        </div>
                        <div style="width: 1px; height: 30px;"></div>
                        <div class="control_buttons">
                            <button class="edit_doc" @click="editDoctor(doctor)"><i class="fa fa-pencil"
                                    aria-hidden="true"></i> Edit</button>
                            <button class="delete_doc" @click="deleteDoctor(doctor.id)"><i class="fa fa-trash-o"
                                    aria-hidden="true"></i> Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import '../../css/global.css';
import '../../css/page7.css';

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
            const token = Cookies.get('admin_token');

            const ipAddress = await this.getUserIpAddress(); // Get user's IP address

            try {
                const response = await axios.get('http://localhost:8080/api/v1/doctor/list/nearby', {
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
        editDoctor(doctor) {
            // Redirect to edit page and pass doctor data as query params
            this.$router.push({
                path: '/admin/doctors/add',
                query: {
                    doctorData: JSON.stringify({
                        id: doctor.id,
                        phone: doctor.phone,
                        name: doctor.name,
                        description: doctor.description,
                        experienceCount: doctor.experienceCount,
                        gender: doctor.gender,
                        specialities: doctor.specialities,
                        officeHours: doctor.officeHours,
                        website: doctor.website,
                        longitude: doctor.longitude,
                        latitude: doctor.latitude,
                        address: doctor.address,
                        ratings: doctor.ratings,
                    })
                }
            });
        },
        async deleteDoctor(doctorId) {
            const token = Cookies.get('admin_token'); // Fetch the admin token from cookies
            if (!token) {
                console.error('Access Denied: No admin token found.');
                return; // Optionally, you can show a message to the user here
            }

            try {
                const response = await axios.delete(`http://localhost:8080/api/v1/doctor/delete/${doctorId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                console.log('Doctor deleted successfully:', response.data);
                this.fetchDoctors(); // Refresh the list of doctors
            } catch (error) {
                console.error('Error deleting doctor:', error);
            }
        },
    }
};
</script>



<style>
.control_buttons {
    width: 100%;
    height: 30px;
    position: absolute;
    bottom: 0;
    left: 0;

    border-bottom-left-radius: 15px;
    border-bottom-right-radius: 15px;
}

.edit_doc {
    width: 50%;
    background-color: rgb(41, 41, 156);
    border-radius: 0;
    border-bottom-left-radius: 15px;
    height: 30px;
    font-size: 13px;
    color: white;
    opacity: 0.8;
}

.delete_doc {
    width: 50%;
    background-color: rgb(144, 44, 44);
    border-radius: 0;
    border-bottom-right-radius: 15px;
    height: 30px;
    font-size: 13px;
    color: white;
}
</style>