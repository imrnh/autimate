<template>
    <div class="newch_dashboard">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
            integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />


        <!-- New child adding form -->
        <section>
            <div class="profile_creator_card">
                <h2 class="childInfoH2">Child Information</h2> <br>
                <form id="childForm" @submit.prevent="submitChildForm">
                    <input type="text" class="input_field_newchprofile" id="name" v-model="newChild.name"
                        placeholder="Full Name"
                        :style="{ backgroundColor: 'rgba(247, 247, 247, 1)', width: '350px', height: '52px' }" required>
                    <select id="gender" class="input_field_newchprofile" v-model="newChild.gender" required>
                        <option value="" disabled selected>Select Gender</option>
                        <option value="female">Female</option>
                        <option value="male">Male</option>
                    </select>

                    <label
                        :style="{ marginLeft: '52px', fontSize: '14px', marginTop: '16px', marginBottom: '-10px' }">Date
                        of Birth</label>
                    <input type="date" class="input_field_newchprofile" id="dob" placeholder="Date of Birth"
                        v-model="newChild.dob" required><br><br>
                    <button type="submit" class="newchbutton"> + &nbsp; Add Child</button>
                </form>
            </div>

        </section>


    </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie';

export default {
    data() {
        return {
            testResults: [], // Will hold the fetched test results
            children: null,  // Will hold the fetched child profiles
            newChild: { name: '', dob: '', gender: '' }, // New child profile data
            selectedFile: null, // File for upload
            activeChildId: null, // Hold the selected active child ID
        };
    },
    methods: {
        //fetch all profile to check if profile of this child already added or not.
        async fetchChildren() {
            try {
                const token = Cookies.get('token');
                const response = await axios.get(`http://localhost:8080/api/v1/child/list`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                this.children = response.data || []; // Ensure empty array if null
                this.activateChildSession();
            } catch (error) {
                console.error("Error fetching child profiles:", error);
            }
        },

        async submitChildForm() {
            try {
                // Prepare the child data as an object, not FormData
                const childData = {
                    name: this.newChild.name,
                    dob: this.newChild.dob,
                    gender: this.newChild.gender
                };


                console.log(childData);

                // Convert the object to JSON and send it with a POST request
                const token = Cookies.get('token');
                const response = await axios.post(`http://localhost:8080/api/v1/child/add`, childData, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json' // Ensure content type is set to JSON
                    }
                });

                alert("Child profile created successfully!");
            } catch (error) {
                console.error("Error creating child profile:", error);
                alert("Failed to create child profile.");
            }
        },

        handleFileUpload(event) {
            this.selectedFile = event.target.files[0]; // Store the selected file
        },

        formatDate(dateString) {
            const date = new Date(dateString);
            return date.toLocaleDateString('en-US', {
                day: '2-digit',
                month: 'short',
                year: 'numeric',
            });
        },
    },


    mounted() {

    },
};
</script>

<style>
.newch_dashboard {
    margin: 20px;
    display: flex;
    width: calc(100vw - 300px);
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
}

.profile_creator_card {
    width: 430px;
    height: 500px;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: calc((100vh - 550px)/2);
    border-radius: 10px;
}

.childInfoH2 {
    font-family: "Poppins", serif;
    font-weight: 500;
    color: black;
    font-size: 25px;
    margin-left: 45px;
}

.input_field_newchprofile {
    width: 350px;
    height: 50px;
    color: black;
    border: 0;
    border-radius: 20px;
    background-color: rgba(247, 247, 247, 1);
    padding-left: 20px;
    margin-top: 10px;
    margin-left: 40px;
}

.newchbutton {
    margin-top: 10px;
    margin-left: calc((430px - 200px) /2);
    background-color: blue;
    width: 200px;
    height: 47px;
    font-size: 18px;
    border: 0;
    border-radius: 100px;
}
</style>