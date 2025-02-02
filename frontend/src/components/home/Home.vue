<template>
  <div class="dashboard">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
      integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw=="
      crossorigin="anonymous" referrerpolicy="no-referrer" />

    <section class="previous_test_history" v-if="children && children.length > 0">
      <h1 class="prev_test_h1" style="position: absolute; font-size: 25px;">Previous test results.</h1> <br>
      <br><br>
      <div class="previous_test_results">
        <table class="table table-bordered table-striped">
          <thead class="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Date performed</th>
              <th scope="col">Q10 (Possibility of Autism)</th>
              <th scope="col">Result</th>
              <th scope="col">Confidence (%)</th>
              <th scope="col">Suggested Therapies</th>
              <th scope="col">Suggested Games</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(test, index) in testResults" :key="test.id">
              <th scope="row">{{ index + 1 }}</th>
              <td>{{ formatDate(test.testDate) }}</td>
              <td style="color: red;">{{ Number(test.q10 * 100).toFixed(2) }} %</td>
              <td>{{ getResultDescription(test.asdStatus) }}</td>
              <td>{{ (test.confidence * 100).toFixed(2) }}%</td>
              <td>{{ test.therapies }}</td>
              <td>{{ test.games }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>

    <section class="child_profiles child_profile_card" v-if="children && children.length > 0">
      <!-- Managing Child Profile -->
      <select v-model="activeChildId" @change="handleSelect" class="child_selection_bar">
        <option v-for="child in children" :key="child.id" :value="child.id">
          {{ child.name }}
        </option>
        <option value="newchprofile" class="child_profile_adding_button">+ Add New Profile</option>
        <!-- New option added -->
      </select>
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
    async fetchTestResults() {
      try {
        const token = Cookies.get('token');
        const response = await axios.get(`http://localhost:8080/api/v1/aex/lists`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        this.testResults = response.data.map(test => ({
          id: test.id,
          testDate: test.testDate,
          asdStatus: test.vid_res,
          confidence: test.vid_confid,
          q10: test.q10,
          therapies: test.suggested_therapies,
          games: test.suggested_games
        }));
      } catch (error) {
        console.error("Error fetching test results:", error);
      }
    },

    // async fetchChildren() {
    //   try {
    //     const token = Cookies.get('token');
    //     const response = await axios.get(`http://localhost:8080/api/v1/child/list`, {
    //       headers: { Authorization: `Bearer ${token}` }
    //     });
    //     this.children = response.data || []; // Ensure empty array if null
    //     this.activateChildSession();
    //   } catch (error) {
    //     console.error("Error fetching child profiles:", error);
    //   }
    // },
    async fetchChildren() {
      try {
        const token = Cookies.get('token');
        const response = await axios.get(`http://localhost:8080/api/v1/child/list`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        this.children = response.data || []; // Ensure empty array if null

        if (this.children.length === 0) {
          this.$router.push('/dashboard/newchprofile'); // Redirect to create new child profile
        } else {
          this.activateChildSession();
        }
      } catch (error) {
        console.error("Error fetching child profiles:", error);
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

    getResultDescription(asdStatus) {
      return asdStatus === "1" ? "Control (no autism)" : "Autism Positive";
    },

    async activateChildSession() {
      const activeChild = this.children.find(child => child.activeSession);
      console.log("Active: ", activeChild);

      if (activeChild) {
        Cookies.set('child_id', activeChild.id);
        Cookies.set('child_name', activeChild.name);

        //initialize activeChildId
        const savedChildId = Cookies.get('child_id');
        this.activeChildId = savedChildId || (this.children.length ? this.children[0].id : null);
      }
    },

    handleSelect(event) {
      const selectedId = event.target.value;
      if (selectedId === "newchprofile") {
        console.log("Redirecting to add new profile...");
        this.$router.push('newchprofile'); // Adjust the route to your desired page
      } else {
        const selectedChild = this.children.find(child => child.id === selectedId);
        if (selectedChild) {
          this.switchProfile(selectedChild);
        }
      }
    },

    //If a child choosen, update profile
    async switchProfile(child) {
      try {
        console.log('Switching profile to:', child);
        const token = Cookies.get('token');
        await axios.post(`http://localhost:8080/api/v1/child/toggle_active_session/${child.id}`, {}, {
          headers: { Authorization: `Bearer ${token}` }
        });
        Cookies.set('child_id', child.id);
        Cookies.set('child_name', child.name);
        window.location.reload();
      } catch (error) {
        console.error("Error switching profile:", error);
      }
    },
  },


  mounted() {
    this.fetchTestResults();
    this.fetchChildren();
  },
};
</script>

<style>
.dashboard {
  margin: 20px;
  display: flex;
  width: calc(100vw - 300px);
}

.previous_test_history {
  margin-top: 40px;
  max-width: 70%;
  width: 800px;
  position: relative;
}

.child_profiles {
  position: absolute;
  right: 0;
  top: 0;
  margin-top: 20px;
  margin-right: 30px;
  width: 230px;
}

.child_selection_bar {
  border-radius: 10px;
  width: 170px;
  height: 40px;
  margin-top: 13px;
  border: 0;
  padding-left: 10px;
  position: absolute;
  right: 0;
  margin-right: 30px;
}

.child_profile_adding_button {
  color: rgb(11, 174, 65);
}
</style>