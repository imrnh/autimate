<template>

<br>
<br>
<h1>Therapy</h1> <br>
    <div 
    style="width: calc(100vw - 300px); height: 80vh; display: flex; flex-wrap: wrap; align-items: center; justify-content: center;">
        <router-link to="/dashboard/therapy/socialskill">
            <div class="card text-white bg-dark mb-3" style="max-width:20rem; margin: 15px;">
                <div class="card-body">
                    <h5 class="card-title" style="font-size: 22px;">Learn Social Skills</h5><br>
                    <p class="card-text" style="color: gray">Some interactive social interactions quick tips.</p>
                </div>
            </div>

        </router-link>

        <router-link to="/dashboard/therapy/drawing">
            <div class="card text-white bg-dark mb-3" style="max-width:20rem; margin: 15px;">
                <div class="card-body">
                    <h5 class="card-title" style="font-size: 22px;">Drawing</h5><br>
                    <p class="card-text" style="color: gray">An interactive drawing panel. Draw whatever you want.</p>
                </div>
            </div>

        </router-link>

        
        <router-link to="/dashboard/therapy/funalp">
            <div class="card text-white bg-dark mb-3" style="max-width:20rem; margin: 15px;">
                <div class="card-body">
                    <h5 class="card-title" style="font-size: 22px;">Learn Alphabet</h5><br>
                    <p class="card-text" style="color: gray">An interactive drawing panel. Draw whatever you want.</p>
                </div>
            </div>

        </router-link>

    </div>

</template>