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
                            <th scope="col">Age (mo)</th>
                            <th scope="col">Result</th>
                            <th scope="col">Confidence</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="row">1</th>
                            <td>12 Sep 2024</td>
                            <td>Video</td>
                            <td>22</td>
                            <td>Control (no autism)</td>
                            <td>59.5%</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>


        <!-- Previous suggested doctors history. -->
        <section class="previous_suggested_doctors">
            <h1 class="prev_test_h1">Previous test results.</h1>
            <div class="suggested_doctors_cards">

            </div>
        </section>


    </div>
</template>


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