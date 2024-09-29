<template>
    <div id="videoChat" ref="root"></div>
</template>

<script>
import { ZegoUIKitPrebuilt } from '@zegocloud/zego-uikit-prebuilt';
import Cookies from 'js-cookie';
import axios from 'axios';

// get token
function generateToken(tokenServerUrl, userID) {
    // Obtain the token interface provided by the App Server
    return fetch(
        `${tokenServerUrl}/access_token?userID=${userID}&expired_ts=7200`,
        {
            method: 'GET',
        }
    ).then((res) => res.json());
}

function randomID(len) {
    let result = '';
    if (result) return result;
    var chars = '12345qwertyuiopasdfgh67890jklmnbvcxzMNBVCZXASDQWERTYHGFUIOLKJP',
        maxPos = chars.length,
        i;
    len = len || 5;
    for (i = 0; i < len; i++) {
        result += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return result;
}

function getUrlParams(url = window.location.href) {
    const urlStr = url.split('?')[1];
    return new URLSearchParams(urlStr);
}


export default {
    name: 'App',
    components: {},
    mounted() {
        const roomID = getUrlParams().get('roomID') || randomID(5);
        const userID = randomID(5);
        const userName = randomID(5);
        let role_str = getUrlParams(window.location.href).get('role') || 'Host';
        const role =
            role_str === 'Host'
                ? ZegoUIKitPrebuilt.Host
                : role_str === 'Cohost'
                    ? ZegoUIKitPrebuilt.Cohost
                    : ZegoUIKitPrebuilt.Audience;

        let sharedLinks = [];
        if (role === ZegoUIKitPrebuilt.Host || role === ZegoUIKitPrebuilt.Cohost) {
            sharedLinks.push({
                name: 'Join as co-host',
                url:
                    window.location.origin +
                    window.location.pathname +
                    '?roomID=' +
                    roomID +
                    '&role=Cohost',
            });
        }
        sharedLinks.push({
            name: 'Join as audience',
            url:
                window.location.origin +
                window.location.pathname +
                '?roomID=' +
                roomID +
                '&role=Audience',
        });

      
        // generate token
        generateToken('https://nextjs-token.vercel.app/api', userID).then((res) => {
            const token = ZegoUIKitPrebuilt.generateKitTokenForProduction(
                1484647939,
                res.token,
                roomID,
                userID,
                userName
            );
            // create instance object from token
            const zp = ZegoUIKitPrebuilt.create(token);
            // start the call
            zp.joinRoom({
                container: this.$refs.root,
                scenario: {
                    mode: ZegoUIKitPrebuilt.LiveStreaming,
                    config: {
                        role,
                    },
                },
                sharedLinks,
            });

            // // Listen for when the host leaves the room
            // ZegoUIKitPrebuilt.on('roomStateUpdate', (state) => {
            //     const token = Cookies.get("token");
            //     if (state === 'DISCONNECTED' && role === ZegoUIKitPrebuilt.Host && token) {
            //         axios.delete(`https://localhost:8080/api/v1/calls/delete_room/${roomID}`, {
            //             headers: {
            //                 'Authorization': `Bearer ${token}`
            //             }
            //         }).then(response => {
            //             console.log('Room deletion tracked:', response.data);
            //         }).catch(error => {
            //             console.error('Error tracking room deletion:', error);
            //         });
            //     }
            // });
        });
    },
};
</script>

<style>
#videoChat {
    height: 100%;
    max-height: 96vh;
    width: calc(100vw - 280px);
    max-width: calc(100vw - 280px);
}
</style>