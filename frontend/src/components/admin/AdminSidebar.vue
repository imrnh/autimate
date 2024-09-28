<script setup>
import '../../css/global.css';
import '../../css/page3.css';
</script>

<template>
  <div class="left-side-menu" style="min-width: 230px; height: 97vh;">
    <div class="frame-parent1">
      <div class="frame-wrapper">
        <div class="frame-parent2">
          <div class="name-parent"
            style="width: 228px; position: absolute; margin-left: -41px; display: flex; align-items: center; justify-content: center; margin-top: 90px;">

          </div>
        </div>
      </div>
      <br>
      <div class="options">
        <div class="discover">
          <div class="online-doctor-desktop-2-strea-parent">
            <img class="online-doctor-desktop-2-strea-icon" style="width: 20px; height: 20px;" loading="lazy" alt=""
              src="@/assets/public/home.png" />

            <router-link style="color: white" to="/dashboard/">
              <div class="therapy">Home</div>
            </router-link>
          </div>
        </div>
        <nav class="pet">

          <div class="discover">
            <div class="online-doctor-desktop-2-strea-parent">
              <img class="online-doctor-desktop-2-strea-icon" loading="lazy" alt="" style="width: 20px; height: 20px;"
                src="@/assets/public/therapy.png" />

              <router-link style="color: white" to="/admin/therapy">
                <div class="therapy">Therapy</div>
              </router-link>
            </div>
          </div>

          <div class="inbox">
            <div class="union-video-chat">
              <div class="sobre-ns-lojas-pet-shop-conta">
                <img class="group-icon" loading="lazy" style="width: 20px; height: 20px;" alt=""
                  src="@/assets/public/doctor.png" />
                <router-link style="color: white" to="/admin/doctors">
                  <div class="nearby-doctor">Doctors</div>
                </router-link>
              </div>
            </div>
          </div>
        </nav>


      </div>
    </div>
    <div class="help-support-upload" style="position: absolute; bottom: 0; margin-bottom: 50px;">
      <div class="shield-upload1">
        <div class="sobre-ns-lojas-pet-shop-conta">
          <img class="medical-instrument-stethoscope-icon" loading="lazy" alt=""
            src="@/assets/public/uploadoutline.svg" />
          <div class="log-out" @click="logout" style="cursor: pointer;">Log Out</div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import Cookies from 'js-cookie';

export default {
  name: 'AdminDashboard',
  methods: {
    logout() {
      // Delete cookies
      Cookies.remove('role');
      Cookies.remove('admin_token');
      Cookies.remove('admin_name');

      // Redirect to login page or any other page
      this.$router.push({
        path: '/admin/login'
      });
    }
  }
};
</script>