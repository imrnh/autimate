<template>
    <section class="wtestres">
        <h1 style="margin-top: 50px;">Test Result</h1>
        <div style="margin-top: 10px; margin-bottom: 50px; width: 1px; height: 10px;"></div>

        <table class="table" style="width: 500px;">
            <tbody>
                <tr>
                    <th class="table-dark">Autism Status</th>
                    <td>{{ asd_status }}</td>
                </tr>

                <tr>
                    <th class="table-dark">Prediction Confidence</th>
                    <td>{{ confidence }}%</td>
                </tr>
            </tbody>
        </table>
    </section>
</template>

<script>
export default {
    mounted() {
        console.log('Query params:', this.$route.query); // Check if query params are showing
    },
    computed: {
        asd_status() {
            return this.$route.query.asd_status || 'Unknown'; // Default to avoid undefined
        },
        confidence() {
            return this.$route.query.confidence || 'N/A'; // Default value
        },
    },
};
</script>



<style>
.wtestres{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>