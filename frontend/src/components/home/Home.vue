<template>
  <div class="dashboard">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
      integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw=="
      crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- Previous Test History -->
    <section class="previous_test_history">
      <h1 class="prev_test_h1" style="position: absolute; font-size: 25px; ">Previous test results.</h1>
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

    <section class="child_profiles">
      <h2 style="font-size: 20px;">Child Profiles</h2>
      <br>
      <table class="table">
        <tbody>
          <tr v-for="(child, index) in children" :key="child.id">

            <td v-if="!child.activeSession" style="width: 170px;">{{ child.name }}</td>
            <td v-if="!child.activeSession">
              <div @click="switchProfile(child)"
                style="background-color: transparent; color: black; margin-top: 0px; font-size: 20px;">
                <i class="fa fa-toggle-off" aria-hidden="true"></i>
              </div>
            </td>

          </tr>
        </tbody>
      </table>
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
      children: [], // Will hold the fetched child profiles
      childName: "",
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


    async fetchChildren() {
      try {
        const token = Cookies.get('token');
        const response = await axios.get(`http://localhost:8080/api/v1/child/list`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        this.children = response.data;
        this.checkActiveSession();
      } catch (error) {
        console.error("Error fetching child profiles:", error);
      }
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
    async checkActiveSession() {
      const activeChild = this.children.find(child => child.activeSession);
      if (activeChild) {
        Cookies.set('child_id', activeChild.id);
        Cookies.set('child_name', activeChild.name);
        Cookies.set('child_age', activeChild.age);
        Cookies.set('child_gender', activeChild.gender);
        this.childName = activeChild.name;
      } else if (this.children.length > 0) {
        await this.toggleActiveSession(this.children[0].id, this.children[0].name);
      }
    },
    async switchProfile(child) {
      try {
        const token = Cookies.get('token'); // Fetch token from cookie
        await axios.post(`http://localhost:8080/api/v1/child/toggle_active_session/${child.id}`, {}, {
          headers: { Authorization: `Bearer ${token}` }
        });
        // Update the cookie with the new child's id and name
        Cookies.set('child_id', child.id);
        Cookies.set('child_name', child.name);
        this.childName = child.name; // Set the childName correctly
        // Reload the page after switching profile
        window.location.reload();
      } catch (error) {
        console.error("Error switching profile:", error);
      }
    },
    async toggleActiveSession(childId, childName) {
      try {
        const token = Cookies.get('token'); // Fetch token from cookie
        await axios.post(`http://localhost:8080/api/v1/child/toggle_active_session/${childId}`, {}, {
          headers: { Authorization: `Bearer ${token}` }
        });
        // Save the childId and childName in the cookie
        Cookies.set('child_id', childId);
        Cookies.set('child_name', childName);
        this.childNme = childName;
      } catch (error) {
        console.error("Error toggling active session:", error);
      }
    },
  },
  mounted() {
    // Fetch test results and child profiles when the component is mounted
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
</style>
