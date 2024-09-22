<script setup>
import '../css/global.css';
import '../css/page3.css';
</script>

<template>
  <div class="left-side-menu">
    <div class="frame-parent1">
      <div class="frame-wrapper">
        <div class="frame-parent2">
          <div class="profile-image-wrapper">
            <img class="profile-image-icon" loading="lazy" alt="" src="../assets/public/profile-image@2x.png" />
          </div>
          <div class="name-parent"
            style="width: 228px; position: absolute; margin-left: -41px; display: flex; align-items: center; justify-content: center; margin-top: 90px;">
            <h3 class="name3" style="font-size: 20px; color: white;">Full Name</h3>
            <div class="title">
              <button @click="$emit('openModal')"
                style="font-size: 10px; background-color: transparent; color: gray; margin-top: -12px;">Change child
                profile</button>

            </div>
          </div>
        </div>
      </div>
      <br>
      <div class="options">
        <nav class="pet">
          <div class="pet-shop-wrapper">
            <div class="pet-shop">
              <div class="sobre-ns-lojas-pet-shop-conta">
                <img class="medical-instrument-stethoscope-icon" alt=""
                  src="../assets/public/medicalinstrumentstethoscopestreamlineultimate.svg" />

                <div class="testing">TESTING</div>
              </div>
              <div class="pet-shop-child"></div>
            </div>
          </div>
          <div class="discover">
            <div class="online-doctor-desktop-2-strea-parent">
              <img class="online-doctor-desktop-2-strea-icon" loading="lazy" alt=""
                src="../assets/public/onlinedoctordesktop2streamlineultimate.svg" />

              <div class="therapy">THERAPY</div>
            </div>
          </div>
          <div class="inbox">
            <div class="union-video-chat">
              <div class="union-parent">
                <img class="union-icon" loading="lazy" alt="" src="../assets/public/union.svg" />

                <div class="video-chat">Video Chat</div>
              </div>
            </div>
          </div>
          <div class="analytics">
            <div class="vector-group">
              <img class="vector-icon" loading="lazy" alt="" src="../assets/public/vector.svg" />

              <div class="games">GAMES</div>
            </div>
          </div>
          <div class="inbox">
            <div class="union-video-chat">
              <div class="sobre-ns-lojas-pet-shop-conta">
                <img class="group-icon" loading="lazy" alt="" src="../assets/public/group@2x.png" />

                <div class="nearby-doctor">Nearby Doctor</div>
              </div>
            </div>
          </div>
        </nav>
      </div>
    </div>
    <div class="help-support-upload">
      <div class="shield-upload">
        <div class="sobre-ns-lojas-pet-shop-conta">
          <img class="medical-instrument-stethoscope-icon" loading="lazy" alt=""
            src="../assets/public/shieldexclamationoutline.svg" />

          <div class="help-support">Help & Support</div>
        </div>
      </div>
      <div class="shield-upload1">
        <div class="sobre-ns-lojas-pet-shop-conta">
          <img class="medical-instrument-stethoscope-icon" loading="lazy" alt=""
            src="../assets/public/uploadoutline.svg" />

          <div class="log-out">Log Out</div>
        </div>
      </div>
    </div>
  </div>
</template>