<template>

    <br>
    <br>
    <h1>Game</h1> <br>
        <div style="width: calc(100vw - 300px); height: 80vh; display: flex; flex-wrap: wrap; align-items: center; justify-content: center;">
            <router-link to="/dashboard/game/snake">
                <div class="card text-white bg-dark mb-3" style="max-width:20rem; margin: 15px;">
                    <div class="card-body">
                        <h5 class="card-title" style="font-size: 22px;">Snake Game</h5><br>
                    </div>
                </div>
    
            </router-link>

            <router-link to="/dashboard/game/tapgame">
                <div class="card text-white bg-dark mb-3" style="max-width:20rem; margin: 15px;">
                    <div class="card-body">
                        <h5 class="card-title" style="font-size: 22px;">Tap the ball</h5><br>
                    </div>
                </div>
    
            </router-link>
        </div>

        
    
    </template>