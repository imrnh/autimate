<template>
  <div class="auth_page_wrapper">
    <div class="form-structor">
      <div :class="['signup', { 'slide-up': currentForm === 'login' }]">
        <h2 class="form-title" @click="toggleForm('signup')">
          <span>or</span>Sign up
        </h2>
        <div class="form-holder">
          <input v-model="signupData.fullName" type="text" name="fullname" class="input" placeholder="Name" />
          <input v-model="signupData.phone" type="text" name="phoneNumber" class="input" placeholder="Phone" />
          <input v-model="signupData.password" type="password" name="password" class="input" placeholder="Password" />
        </div>
        <button class="submit-btn" @click="signup">Sign up</button>
        <router-link to="/auth/otp/" style="color: black; font-size: 13px;">or Verify phone number</router-link>
      </div>

      <div :class="['login', { 'slide-up': currentForm === 'signup' }]">
        <div class="center">
          <h2 class="form-title" @click="toggleForm('login')">
            <span>or</span>Log in
          </h2>
          <div class="form-holder">
            <input v-model="loginData.phone" type="text" name="phoneNumber" class="input" placeholder="Phone" />
            <input v-model="loginData.password" type="password" name="password" class="input" placeholder="Password" />
          </div>
          <button class="submit-btn" @click="login">Log in</button>
          <router-link to="/auth/password/reset/" style="color: black; font-size: 13px;">Forgot password?</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AuthForm',
  data() {
    return {
      currentForm: 'login',
      signupData: {
        fullName: '',
        phone: '',
        password: ''
      },
      loginData: {
        phone: '',
        password: ''
      }
    };
  },
  methods: {
    toggleForm(form) {
      this.currentForm = form;
    },
    async signup() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/register', {
          fullName: this.signupData.fullName,
          phone: this.signupData.phone,
          password: this.signupData.password
        });
        // console.log('Signup successful', response.data);
        
      } catch (error) {
        console.error('Signup error', error);
      }
    },
    async login() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/login', {
          phone: this.loginData.phone,
          password: this.loginData.password
        });
        console.log('Login successful', response.data);
        // Handle success (e.g., store token, redirect)
      } catch (error) {
        console.error('Login error', error);
        // Handle error (e.g., show error message to user)
      }
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

.auth_page_wrapper{
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
</style>
