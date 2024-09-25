<template>
    <div class="dashboard">
  
      <!-- Previous Test History -->
      <section class="previous_test_history">
        <h1 class="prev_test_h1">Previous test results.</h1>
        <div class="previous_test_results">
          <table class="table table-dark table-bordered table-striped">
            <thead class="thead-dark">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Date performed</th>
                <th scope="col">Test Type</th>
                <th scope="col">Result</th>
                <th scope="col">Confidence (%)</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(test, index) in testResults" :key="test.id">
                <th scope="row">{{ index + 1 }}</th>
                <td>{{ formatDate(test.testDate) }}</td>
                <td>{{ test.testType }}</td>
                <td>{{ getResultDescription(test.asdStatus) }}</td>
                <td>{{ (test.confidence * 100).toFixed(2) }}%</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        testResults: [],  // Will hold the fetched test results
      };
    },
    methods: {
      // Fetch test results from the API
      async fetchTestResults() {
        try {
          const response = await axios.get(`http://localhost:8080/api/ex/get-result/username/90900`);
          this.testResults = response.data;
          console.log(response)
        } catch (error) {
          console.error("Error fetching test results:", error);
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
      // Map the asdStatus value to a readable result
      getResultDescription(asdStatus) {
        return asdStatus === "1" ? "Control (no autism)" : "Autism Positive";
      },
    },
    mounted() {
      // Fetch test results when the component is mounted
      this.fetchTestResults();
    },
  };
  </script>
  
  <style>
  /* Add your styles if needed */
  </style>
  

<style>
.dashboard {
    margin: 20px;
}

.previous_test_results {
    margin-top: 30px;
    max-width: 70%;
}

.previous_suggested_doctors {
    margin-top: 100px;
}
</style>