<template>
  <div class="auth_page_wrapper">
    <div class="form-structor">
      <div :class="['signup', { 'slide-up': currentForm === 'login' }]">
        <h2 class="form-title" @click="toggleForm('signup')">
          Verify Number
        </h2><br>

        <div class="form-holder">
          <input v-model="passResetData.phone" type="text" name="password" class="input" placeholder="Phone" />
        </div>
        <div class="resendOtp" @click="sendAnOTP" style="margin-left: 5px; color: black;">Send an OTP</div>

        <div class="form-holder" style="margin-top: 10px;">
          <input v-model="passResetData.otp" type="text" name="otp" class="input" placeholder="OTP" />
          <input v-model="passResetData.password" type="password" name="password" class="input" placeholder="Password" />
        </div>
        <button class="submit-btn" @click="requestPasswordChange">Change Password</button>
        <div style="height: 10px;"></div>
        <router-link to="/auth/" style="color: black; font-size: 13px;">or Register with new phone
          number?</router-link>

        <div class="message_show_alart_view">
          <p style="font-weight: 500;" v-if="message">{{ message }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie';


export default {
  mounted() {
    const phone = this.$route.query.phone || ''; // Set phone from query param if present
    this.passResetData.phone = phone; // Assign phone to passResetData.phone
  },
  computed: {
    asd_status() {
      return this.$route.query.asd_status || 'Unknown'; // Default to avoid undefined
    },
    confidence() {
      return this.$route.query.confidence || 'N/A'; // Default value
    },
  },
  name: 'AuthForm',
  data() {
    return {
      currentForm: 'signup',
      passResetData: {
        otp: '',
        password: '',
        phone: '',
      },
      message: '',
    };
  },
  methods: {
    toggleForm(form) {
      this.currentForm = form;
    },
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    },

    async requestPasswordChange() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/reset_password', {
          phone: this.passResetData.phone,
          password: this.passResetData.password,
          otp: this.passResetData.otp
        });
        
        console.log(response.data)


        this.message = response.data.requestMessage;
        if (response.data.requestSuccess == true) {
          this.$router.push({path: '/auth/login/'});
        }
        else{
          this.message = "Incorrect OTP. Please retry.";
        }
      } 
      catch (error) {
        console.log(error);
        this.message = "Request invalid.";
        // this.message = "Error." + error;
      }
    },

    async sendAnOTP() {
      const response = await axios.get(`http://localhost:8080/api/v1/auth/send/otp/${this.passResetData.phone}`);
      this.message = 'Code has been sent.';
    }
  }
};
</script>


<style scoped>
@import url("https://fonts.googleapis.com/css?family=Fira+Sans");

html,
body {
  position: relative;
  min-height: 100vh;
  background-color: #e1e8ee;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Fira Sans", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.auth_page_wrapper {
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #e1e8ee;
  overflow-x: hidden;
  overflow-y: hidden;
}

.form-structor {
  background-color: #222;
  border-radius: 15px;
  height: 550px;
  width: 350px;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;

  &::after {
    content: '';
    opacity: 0.8;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-repeat: no-repeat;
    background-position: left bottom;
    background-size: 500px;
    background-image: url('https://images.unsplash.com/photo-1503602642458-232111445657?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bf884ad570b50659c5fa2dc2cfb20ecf&auto=format&fit=crop&w=1000&q=100');
  }

  .signup {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 65%;
    z-index: 5;
    transition: all 0.3s ease;

    &.slide-up {
      top: 5%;
      transform: translate(-50%, 0%);
      transition: all 0.3s ease;
    }

    &.slide-up .form-holder,
    &.slide-up .submit-btn {
      opacity: 0;
      visibility: hidden;
    }

    &.slide-up .form-title {
      font-size: 1em;
      cursor: pointer;
    }

    &.slide-up .form-title span {
      margin-right: 5px;
      opacity: 1;
      visibility: visible;
      transition: all 0.3s ease;
    }

    .form-title {
      color: #fff;
      font-size: 1.7em;
      text-align: center;
      cursor: pointer;

      span {
        color: rgba(0, 0, 0, 0.4);
        opacity: 0;
        visibility: hidden;
        transition: all 0.3s ease;
      }
    }

    .form-holder {
      border-radius: 15px;
      background-color: #fff;
      overflow: hidden;
      margin-top: 50px;
      opacity: 1;
      visibility: visible;
      transition: all 0.3s ease;

      .input {
        border: 0;
        outline: none;
        box-shadow: none;
        display: block;
        height: 33px;
        line-height: 30px;
        padding: 8px 15px;
        border-bottom: 1px solid #eee;
        width: 100%;
        font-size: 12px;
        background-color: white;
        color: black;



        &:last-child {
          border-bottom: 0;
        }

        &::placeholder {
          color: rgba(0, 0, 0, 0.4);
        }
      }
    }

    .submit-btn {
      background-color: rgba(0, 0, 0, 0.4);
      color: rgba(255, 255, 255, 0.7);
      border: 0;
      border-radius: 15px;
      display: block;
      margin: 15px auto;
      padding: 15px 45px;
      width: 100%;
      font-size: 13px;
      font-weight: bold;
      cursor: pointer;
      opacity: 1;
      visibility: visible;
      transition: all 0.3s ease;

      &:hover {
        background-color: rgba(0, 0, 0, 0.8);
        transition: all 0.3s ease;
      }
    }
  }

  .login {
    position: absolute;
    top: 20%;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #fff;
    z-index: 5;
    transition: all 0.3s ease;

    &::before {
      content: '';
      position: absolute;
      left: 50%;
      top: -20px;
      transform: translate(-50%, 0);
      background-color: #fff;
      width: 200%;
      height: 250px;
      border-radius: 50%;
      z-index: 4;
      transition: all 0.3s ease;
    }

    .center {
      position: absolute;
      top: calc(50% - 10%);
      left: 50%;
      transform: translate(-50%, -50%);
      width: 65%;
      z-index: 5;
      transition: all 0.3s ease;

      .form-title {
        color: #000;
        font-size: 1.7em;
        text-align: center;
        cursor: pointer;

        span {
          color: rgba(0, 0, 0, 0.4);
          opacity: 0;
          visibility: hidden;
          transition: all 0.3s ease;
        }
      }

      .form-holder {
        border-radius: 15px;
        background-color: #fff;
        border: 1px solid #eee;
        overflow: hidden;
        margin-top: 50px;
        opacity: 1;
        visibility: visible;
        transition: all 0.3s ease;

        .input {
          border: 0;
          outline: none;
          box-shadow: none;
          display: block;
          height: 36px;
          line-height: 30px;
          padding: 8px 15px;
          border-bottom: 1px solid #eee;
          width: 100%;
          font-size: 12px;
          background-color: white;
          color: black;

          &:last-child {
            border-bottom: 0;
          }

          &::placeholder {
            color: rgba(0, 0, 0, 0.4);
          }
        }
      }

      .submit-btn {
        background-color: #6b92a4;
        color: rgba(255, 255, 255, 0.7);
        border: 0;
        border-radius: 15px;
        display: block;
        margin: 15px auto;
        padding: 15px 45px;
        width: 100%;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
        opacity: 1;
        visibility: visible;
        transition: all 0.3s ease;

        &:hover {
          background-color: rgba(0, 0, 0, 0.8);
          transition: all 0.3s ease;
        }
      }
    }

    &.slide-up {
      top: 90%;
      transition: all 0.3s ease;
    }

    &.slide-up .center {
      top: 10%;
      transform: translate(-50%, 0%);
      transition: all 0.3s ease;
    }

    &.slide-up .form-holder,
    &.slide-up .submit-btn {
      opacity: 0;
      visibility: hidden;
      transition: all 0.3s ease;
    }

    &.slide-up .form-title {
      font-size: 1em;
      margin: 0;
      padding: 0;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    &.slide-up .form-title span {
      margin-right: 5px;
      opacity: 1;
      visibility: visible;
      transition: all 0.3s ease;
    }
  }
}


.resendOtp {
  height: 30px;
  background-color: transparent;
  padding-left: 0px;
  font-size: 15px;
  padding: 0px;
  margin-top: 15px;
  cursor: pointer;
  color: rgb(39, 64, 227);
  font-weight: 500;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}

.message_show_alart_view {
  width: 100%;
  height: 50px;
  position: absolute;
  margin-top:35px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(17, 93, 225);
}
</style>