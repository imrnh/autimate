<script setup>
import '../../css/global.css';
import '../../css/page3.css';
</script>

<template>
  <div class="left-side-menu">
    <div class="frame-parent1">
      <div class="frame-wrapper">
        <div class="frame-parent2">
          <div class="profile-image-wrapper">
            <img class="profile-image-icon" loading="lazy" alt="" src="@/assets/public/profile-image@2x.png" />
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
          <div class="pet-shop-wrapper">
            <div class="pet-shop">
              <div class="sobre-ns-lojas-pet-shop-conta">
                <img class="medical-instrument-stethoscope-icon" alt="" style="width: 20px; height: 20px;"
                  src="@/assets/public/medicalinstrumentstethoscopestreamlineultimate.svg" />

                <!-- <router-link to="/dashboard/select-test" style="color: white;"> -->
                  <div @click="$emit('showAutismTestingModal')" style="cursor: pointer;" class="testing">TESTING</div>
                <!-- </router-link> -->
              </div>
              <div class="pet-shop-child"></div>
            </div>
          </div>
          <div class="discover">
            <div class="online-doctor-desktop-2-strea-parent">
              <img class="online-doctor-desktop-2-strea-icon" loading="lazy" alt="" style="width: 20px; height: 20px;"
                src="@/assets/public/therapy.png" />

              <router-link style="color: white" to="/dashboard/therapy">
                <div class="therapy">THERAPY</div>
              </router-link>
            </div>
          </div>
          <div class="inbox">
            <div class="union-video-chat">
              <div class="union-parent">
                <img class="union-icon" loading="lazy" style="width: 20px; height: 20px;" alt="" src="@/assets/public/video_call.png" />

                <router-link style="color: white" to="/dashboard/chat">
                  <div class="video-chat">Video Chat</div>
                </router-link>
              </div>
            </div>
          </div>
          <div class="analytics">
            <div class="vector-group">
              <img class="vector-icon" loading="lazy" style="width: 20px; height: 20px;" alt="" src="@/assets/public/game-controller.png" />

              <router-link style="color: white" to="/dashboard/game">
                <div class="games">GAMES</div>
              </router-link>
            </div>
          </div>
          <div class="inbox">
            <div class="union-video-chat">
              <div class="sobre-ns-lojas-pet-shop-conta">
                <img class="group-icon" loading="lazy" style="width: 20px; height: 20px;" alt="" src="@/assets/public/doctor.png" />

                <router-link style="color: white" to="/dashboard/doctors">
                  <div class="nearby-doctor">Nearby Doctor</div>
                </router-link>
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
            src="@/assets/public/shieldexclamationoutline.svg" />

          <div class="help-support">Help & Support</div>
        </div>
      </div>
      <div class="shield-upload1">
        <div class="sobre-ns-lojas-pet-shop-conta">
          <img class="medical-instrument-stethoscope-icon" loading="lazy" alt=""
            src="@/assets/public/uploadoutline.svg" />

          <div class="log-out">Log Out</div>
        </div>
      </div>
    </div>
  </div>
</template>