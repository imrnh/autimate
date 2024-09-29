<script>
import axios from 'axios';
import Cookies from 'js-cookie';

export default {
    name: 'DoctorForm',
    data() {
        return {
            doctorData: {
                id: null,
                phone: '',
                name: '',
                description: '',
                experienceCount: '',
                gender: '',
                specialities: '',
                image: '',
                officeHours: '',
                website: '',
                longitude: '',
                latitude: '',
                address: '',
                ratings: ''
            },
            message: " ",
            isEdit: false // Track if it's an edit operation
        };
    },
    mounted() {
        this.loadDoctorData();
    },
    methods: {
        loadDoctorData() {
            // Check if there is doctor data passed via query parameters
            const doctorQuery = this.$route.query.doctorData;
            if (doctorQuery) {
                this.doctorData = JSON.parse(doctorQuery);
                this.isEdit = true; // Set to true since we're editing
            }
        },
        handleImageUpload(event) {
            this.doctorData.image = event.target.files[0]; // Store the file
        },
        async submitDoctor(e) {
            e.preventDefault();
            let formData = new FormData();

            // Prepare doctor data without the image for submission
            let doctorDataCopy = { ...this.doctorData };
            const isUpdating = this.isEdit;

            if (!isUpdating) {
                delete doctorDataCopy.image; // Exclude image for new doctors
            }

            // Append doctorData as JSON blob
            formData.append('doctorData', new Blob([JSON.stringify(doctorDataCopy)], { type: 'application/json' }));

            // Append the image if it is being uploaded
            if (this.doctorData.image && !isUpdating) { // Ensure image is present only for create
                formData.append('image', this.doctorData.image);
            }

            try {
                const token = Cookies.get('admin_token');
                let response;

                if (isUpdating) {
                    // Update doctor request
                    response = await axios.put(`http://localhost:8080/api/v1/doctor/update/${this.doctorData.id}`, formData, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                        }
                    });
                } else {
                    // Create doctor request
                    response = await axios.post('http://localhost:8080/api/v1/doctor/create', formData, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                        }
                    });
                }

                console.log(response.data)

                if (response.data.responseStat) {
                    this.message = isUpdating ? "Doctor updated successfully!" : "Doctor created successfully!";
                    this.resetForm();

                    //move to doctors.
                    this.$router.push({
                        path: '/admin/doctors'
                    })
                } else {
                    this.message = response.data.responseMessage;
                }
            } catch (error) {
                // Enhanced error message handling
                if (error.response && error.response.data && error.response.data.responseMessage) {
                    this.message = "Error: " + error.response.data.responseMessage;
                } else {
                    this.message = "Error. " + error.message;
                }
            }
        },
        resetForm() {
            this.doctorData = {
                id: null,
                phone: '',
                name: '',
                description: '',
                experienceCount: '',
                gender: '',
                specialities: '',
                image: '',
                officeHours: '',
                website: '',
                longitude: '',
                latitude: '',
                address: '',
                ratings: ''
            };
        }
    }
};
</script>


<template>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="//fonts.googleapis.com/css2?family=Kumbh+Sans:wght@300;400;700&display=swap" rel="stylesheet">
    <div class="doctorform" style="width: calc(100vw - 300px);">
        <h1 style="font-size: 25px; font-weight: 800; margin-top: 5px;">Create New Doctor</h1>
        <div class="container">
            <div class="w3l-form-info">
                <div class="w3_info" style="width: 100%; margin-top: -10px;">
                    <h2>Doctor Details</h2>
                    <form action="#" method="post" style="width: 100%; margin-left: 10px">
                        <div class="row">
                            <div class="col-5">
                                <div class="input-group">
                                    <span><i class="fas fa-phone" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.phone" type="text" placeholder="Phone" required>
                                </div>
                            </div>
                            <div class="col-1"></div>
                            <div class="col-5">
                                <div class="input-group">
                                    <span><i class="fas fa-user" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.name" type="text" placeholder="Name" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">


                            <div class="input-group" style="width: 90%;">
                                <span><i class="fas fa-pencil-alt" aria-hidden="true"></i></span>
                                <textarea rows="2" cols="90" style="border: 0; background-color: white; color: black ;padding-top: 20px; padding-left: 45px;"
                                    v-model="doctorData.description" placeholder="Description" required></textarea>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-5">
                                <div class="input-group">
                                    <span><i class="fas fa-briefcase" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.experienceCount" type="text"
                                        placeholder="Experience (e.g., 19 years)" required>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <span><i class="fas fa-venus-mars" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.gender" type="text" placeholder="Gender" required>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <span><i class="fas fa-venus-mars" aria-hidden="true"></i></span>
                                    <input style="color: black; background-color: white; border: 0; height: 47px;" v-model="doctorData.ratings" type="number" step="0.1" placeholder="Rating" required>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-5">
                                <div class="input-group">
                                    <span><i class="fas fa-stethoscope" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.specialities" type="text" placeholder="Specialities"
                                        required>
                                </div>
                            </div>
                            <div class="col-1"></div>
                            <div class="col-5">


                                <div class="input-group">
                                    <span><i class="fas fa-image" aria-hidden="true"></i></span>
                                    <input style="color: black;" type="file" @change="handleImageUpload" required>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-5">


                                <div class="input-group">
                                    <span><i class="fas fa-clock" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.officeHours" type="text" placeholder="Office Hours"
                                        required>
                                </div>
                            </div>
                            <div class="col-1"></div>
                            <div class="col-5">

                                <div class="input-group">
                                    <span><i class="fas fa-globe" aria-hidden="true"></i></span>
                                    <input style="color: black;" v-model="doctorData.website" type="text" placeholder="Website">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="input-group" style="width: 90%;">
                                <span><i class="fas fa-map" aria-hidden="true"></i></span>
                                <input style="color: black;" v-model="doctorData.address" type="text" placeholder="Address" required>
                            </div>
                        </div>



                        <button class="btn btn-primary btn-block" @click="submitDoctor">{{ isEdit ? 'Update Doctor' : 'Create Doctor' }}</button>
                    </form>
                    <div style="width: 10px; height: 20px;"></div>
                    <div class="message_show_alert_view">
                        <p style="font-weight: 500; color: red; font-size: 18px;" v-if="message">{{ message }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>



<style>
/*--
Author: W3layouts
Author URL: http://w3layouts.com
--*/

/* reset code */
html {
    scroll-behavior: smooth;
}

body,
html {
    margin: 0;
    padding: 0;
    color: #585858;
}

* {
    box-sizing: border-box;
    font-family: 'Kumbh Sans', sans-serif;
}
input{
    color: black;
}

/*  wrapper */
.wrapper {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
}

@media (min-width: 576px) {
    .wrapper {
        max-width: 540px;
    }
}

@media (min-width: 768px) {
    .wrapper {
        max-width: 720px;
    }
}

@media (min-width: 992px) {
    .wrapper {
        max-width: 960px;
    }
}

@media (min-width: 1200px) {
    .wrapper {
        max-width: 1140px;
    }
}

/*  /wrapper */

.d-grid {
    display: grid;
}

button,
input,
select {
    /* -webkit-appearance: none; */
    outline: none;
}

button,
.btn,
select {
    cursor: pointer;
}

a {
    text-decoration: none;
}

h1,
h2,
h3,
h4,
h5,
h6,
p,
ul,
ol {
    margin: 0;
    padding: 0;
}

body {
    background: #f1f1f1;
    margin: 0;
    padding: 0;
}

form,
fieldset {
    border: 0;
    padding: 0;
    margin: 0;
}

img {
    max-width: 100%;
}

/*-- //Reset-Code --*/

/*-- form styling --*/
.signinform {
    padding: 40px 40px;
    justify-content: center;
    display: grid;
    grid-template-rows: 1fr auto 1fr;
    align-items: center;
    min-height: 98vh;
    min-width: 99vw;
}

input[type="text"],
input[type="email"],
input[type="Password"] {
    font-size: 17px;
    font-weight: 500;
    color: #999;
    text-align: left;
    padding: 14px 15px 12px 35px;
    width: 100%;
    display: inline-block;
    box-sizing: border-box;
    border: none;
    outline: none;
    background: transparent;
    letter-spacing: .5px;
}

.input-group {
    margin-bottom: 25px;
    padding: 0px 0px;
    border-bottom: 1px solid #e5e5e5;
    position: relative;
}

.btn-block {
    display: block;
    width: 50%;
    margin: 0 auto;
}

.btn:active {
    outline: none;
}

.btn-primary {
    color: #fff;
    background-color: #9146ff;
    margin-top: 30px;
    outline: none;
    width: 100%;
    padding: 15px 15px;
    cursor: pointer;
    font-size: 18px;
    font-weight: 600;
    border-radius: 6px;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    -ms-border-radius: 6px;
    -o-border-radius: 6px;
    border: none;
    text-transform: capitalize;
}


.btn-primary:hover {
    background-color: #7d32eb;
    color: #fff;
}

.form-row.bottom {
    display: flex;
    justify-content: space-between;
}

.form-row .form-check input[type="checkbox"] {
    display: none;
}

.form-row .form-check input[type="checkbox"]+label:before {
    border-radius: 3px;
    border: 1px solid #e2e2e2;
    color: transparent;
    content: "\2714";
    display: inline-block;
    height: 18px;
    margin-right: 5px;
    transition: 0.2s;
    vertical-align: inherit;
    width: 18px;
    text-align: center;
    line-height: 20px;
}

.form-row .form-check input[type="checkbox"]:checked+label:before {
    background-color: #9146ff;
    border-color: #9146ff;
    color: #fff;
}

.form-row .form-check input[type="checkbox"]+label {
    cursor: pointer;
    color: #888;
}

.w3_info h2 {
    display: inline-block;
    font-size: 25px;
    line-height: 35px;
    margin-bottom: 20px;
    font-weight: 600;
    color: #3b3663;
}

.w3_info h4 {
    display: inline-block;
    font-size: 15px;
    padding: 8px 0px;
    color: #444;
    text-transform: capitalize;
}


a.btn.btn-block.btn-social.btn-facebook {
    display: block;
    width: 100%;
    padding: 10px 0px;
    text-align: center;
    font-size: 16px;
    font-weight: 600;
}

h1 {
    text-align: center;
    font-size: 40px;
    font-weight: 500;
    color: #3b3663;
}

.w3_info {
    box-sizing: border-box;
    padding: 3em 3.5em;
    width: 450px;
    background: #fff;
    box-shadow: 2px 9px 49px -17px rgba(0, 0, 0, 0.1);
}


.w3l-form-info {
    margin: 40px 0;
}


.w3_info p {
    padding-bottom: 30px;
    text-align: center;
}

p.account,
p.account a {
    text-align: center;
    padding-top: 20px;
    padding-bottom: 0px;
    font-size: 16px;
    color: #888;
}

p.account a {
    color: #9146ff;
}

p.account a:hover {
    text-decoration: underline;
}

a.forgot {
    color: #3b3663;
    margin-top: 2px;
}

a.forgot:hover {
    text-decoration: underline;
}

h3.w3ls {
    margin: 10px 0px;
    padding-left: 60px;
}

h3.agileits {
    padding-left: 10px;
}

.container {
    max-width: 890px;
    margin: 0 auto;
}

.input-group i.fa,
.input-group i.fas {
    font-size: 16px;
    vertical-align: middle;
    box-sizing: border-box;
    float: left;
    width: 6%;
    margin-top: 13px;
    text-align: center;
    color: #999;
    opacity: .5;
    position: absolute;
    left: 0;
}

h5 {
    text-align: center;
    margin: 10px 0px;
    font-size: 15px;
    font-weight: 600;
    color: #000;
}

.footer p {
    text-align: center;
    font-size: 18px;
    line-height: 28px;
    color: #777;
}

.footer p a {
    color: #9146ff;
}

.footer p a:hover {
    text-decoration: underline;
}

p.continue {
    margin-top: 25px;
    padding: 0;
    margin-bottom: 20px;
    color: #999;
    opacity: .8;
}

p.continue span {
    position: relative;
}

p.continue span:before {
    position: absolute;
    content: '';
    height: 1px;
    background: #999;
    width: 89%;
    left: -100%;
    top: 5px;
    opacity: .5;
}

p.continue span:after {
    position: absolute;
    content: '';
    height: 1px;
    background: #999;
    width: 89%;
    right: -100%;
    top: 5px;
    opacity: .5;
}

.social-login {
    display: grid;
    grid-auto-flow: column;
    grid-gap: 15px;
    margin-bottom: 10px;
    justify-content: center;
}

.facebook {
    width: 46px;
    height: 46px;
    line-height: 46px;
    border-radius: 50%;
    text-align: center;
    font-size: 15px;
    color: #fff;
    background: #3b5998;
}

span.fab {
    line-height: 46px;
}

.facebook:hover {
    background: #3b5998;
    color: #fff;
}

.twitter {
    width: 46px;
    height: 46px;
    line-height: 46px;
    border-radius: 50%;
    text-align: center;
    font-size: 15px;
    color: #fff;
    background: #1da1f2;
}

.twitter:hover {
    background: #1da1f2;
    color: #fff;
}

.google {
    width: 46px;
    height: 46px;
    line-height: 46px;
    border-radius: 50%;
    text-align: center;
    font-size: 15px;
    color: #fff;
    background: #ea4335;
}

.google:hover {
    background: #ea4335;
    color: #fff;
}

.facebook:hover,
.twitter:hover,
.google:hover {
    opacity: .8;
}

::-webkit-input-placeholder {
    /* Edge */
    color: #aaa;
}

:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    color: #aaa;
}

::placeholder {
    color: #aaa;
}

/** Responsive **/
@media screen and (max-width: 1440px) {}


@media screen and (max-width: 991px) {
    .w3_info h2 {
        font-size: 24px;
    }

    h1 {
        font-size: 35px;
    }
}

@media screen and (max-width: 900px) {}

@media screen and (max-width: 800px) {
    .w3_info h2 {
        font-size: 23px;
    }

    .w3l-form-info {
        flex-direction: column;
    }

    .w3l_form {
        order: 2;
    }

    .container {
        max-width: 550px;
    }

}


@media screen and (max-width: 736px) {
    .w3_info h2 {
        font-size: 22px;
    }
}

@media screen and (max-width: 667px) {
    .w3_info {
        padding: 3em 3em;
    }

    .w3l-form-info {
        margin: 20px 0 30px;
    }
}

@media screen and (max-width: 640px) {
    h1 {
        font-size: 37px;
    }
}

@media screen and (max-width: 480px) {
    .w3l_form {
        padding: 0em;
    }

    .w3_info {
        padding: 2em 2em;
        width: 430px;
    }

    h1 {
        font-size: 34px;
    }
}

@media screen and (max-width: 415px) {

    h1 {
        font-size: 30px;
    }

    .w3_info {
        width: 380px;
    }

    .signinform {
        padding: 40px 20px;
    }
}

@media screen and (max-width: 384px) {

    .signinform {
        padding: 30px 15px;
    }

    .w3_info {
        width: 350px;
    }

    .twitter,
    .google,
    .facebook {
        width: 40px;
        height: 40px;
        line-height: 40px;
        font-size: 13px;
    }

    span.fab {
        line-height: 40px;
    }

    .social-login {
        grid-gap: 10px;
    }

    a.forgot,
    .form-row .form-check input[type="checkbox"]+label {
        font-size: 15px;
    }
}

@media screen and (max-width: 375px) {
    .w3_info {
        width: 335px;
        padding: 2em 1.5em;
    }

    .footer p {
        font-size: 17px;
    }

    input[type="text"],
    input[type="email"],
    input[type="Password"] {
        font-size: 16px;
    }
}

@media screen and (max-width: 320px) {
    h1 {
        font-size: 25px;
    }

    .w3_info h2 {
        font-size: 18px;
    }

    .btn-primary {
        padding: 13px 12px;
        font-size: 13px;
    }

    input[type="text"],
    input[type="email"],
    input[type="password"] {
        font-size: 13px;
    }

    .footer p {
        font-size: 13px;
    }

    .footer p a {
        font-size: 13px;
    }
}

/** /Responsive **/

/*-- //form styling --*/
</style>