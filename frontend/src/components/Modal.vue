<template>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" @click="$emit('close')">&times;</button>
            <div class="space_maker"></div>


            
            <!-- Create new profile -->
            <div class="space_maker"></div>
            <button id="open_form" @click="toggleFormVisibility">
                + Create new profile
            </button>

            <div class="new_profile_form" v-if="showForm">
                <form>
                    <div class="mb-3">
                        <input type="text" name="child_name" class="form-control" id="formGroupExampleInput"
                            placeholder="Child name">
                    </div>
                    <div class="mb-3">
                        <label for="formGroupExampleInput2" class="form-label">Date of birth</label>
                        <input type="date" name="dob" class="form-control" id="formGroupExampleInput2">
                    </div>
                    <button type="submit" class="btn btn-primary" style="float: right; margin-top: 10px;">Create profile</button>
                </form>
            </div>

            <slot></slot>
        </div>
    </div>
</template>


<script>
export default {
    name: 'Modal',
    data() {
        return {
            showForm: false, // Controls form visibility
        };
    },
    methods: {
        toggleFormVisibility() {
            this.showForm = !this.showForm; // Toggles the form's visibility
        },
    },
};
</script>

<style scoped>
.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    width: 400px;
    position: relative;
}

.close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
}

.close-btn:hover {
    color: red;
}

.space_maker {
    width: 2px;
    height: 50px;
}

.new_profile_form {
    margin-top: 20px;
}
</style>