<script>
import '../../css/global.css';
import '../../css/page1.css';
</script>

<template>
    <div class="page-21">
      <div class="frame-group">
        <div class="frame-container">
          <div class="blubs-parent">
            <img class="blubs-icon" alt="" src="@/assets/public//blubs.svg" />

            <div class="vector-parent">
              <img class="frame-child" alt="" src="@/assets/public//rectangle-16.svg" />

              <img
                class="d3f1560d-eb21-4cb8-90b4-1efd77-icon"
                alt=""
                src="@/assets/public//d3f1560deb214cb890b41efd77a3936d-1@2x.png"
              />

              <div class="frame-item"></div>
            </div>
          </div>
          <router-link to="/">
          <img
            class="image-132-icon1"
            loading="lazy"
            alt=""
            src="@/assets/public//image-132@2x.png"
          /></router-link>
        </div>
        <h2 class="empower-your-life-container">
          <p class="empower-your-life">Empower your lIFE.</p>
          <p class="empower-your-life">ENJOY IT.</p>
          <p class="empower-your-life">let’s Sign Up.</p>
        </h2>
      </div>
      <div class="account-form-wrapper">
        <div class="account-form">
          <h1 class="create-account">create account</h1>
          <div class="form-fields">
            <div class="name-fields">
              <div class="first-name">
                <div class="first-name-child"></div>
                <div class="first-name-input">
                  <a class="name">name</a>
                </div>
              </div>
              <div class="first-name">
                <div class="first-name-child"></div>
                <input
                  class="last-name-input"
                  placeholder="User ID"
                  type="text"
                />
              </div>
            </div>
            <div class="name-fields1">
              <div class="email">
                <div class="email-child"></div>
                <div class="email1">
                  <div class="mobile-no">Mobile No</div>
                </div>
              </div>
              <div class="email2">
                <div class="email-child"></div>
                <input class="email3" placeholder="NID no." type="text" />
              </div>
            </div>
            <div class="contact-fields">
              <div class="email-date-field">
                <div class="email4">
                  <div class="email-child"></div>
                  <input
                    class="email5"
                    placeholder="Child’s Date of Birth (DD/MM/YYYY)"
                    type="text"
                  />
                </div>
              </div>
              <div class="email-date-field">
                <div class="email4">
                  <div class="email-child"></div>
                  <input class="email7" placeholder="email" type="text" />
                </div>
              </div>
              <div class="email">
                <div class="email-child"></div>
                <div class="password-wrapper">
                  <div class="password1">password</div>
                </div>
              </div>
              <div class="create-parent">
                <div class="create">
                  <b class="create-account1">create account</b>
                </div>
                <div class="already-have-an-container">
                  <span class="already-have-an"
                    >Already have an account? You can
                  </span>
                  <router-link to="/login"><span class="sign-in-here">Sign in here !</span></router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>