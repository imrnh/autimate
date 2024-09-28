<script>
import '../../css/global.css';
import '../../css/page7.css';
</script>


<template>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.0/css/font-awesome.min.css"
        integrity="sha512-FEQLazq9ecqLN5T6wWq26hCZf7kPqUbFC9vsHNbXMJtSZZWAcbJspT+/NEAQkBfFReZ8r9QlA9JHaAuo28MTJA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <div class="all-contents4" style="width: calc(100vw - 270px); height: 98vh;">
        <div class="content-items">
            <div class="content-item">

                <div class="item-6" style="border: 1px solid rgb(239, 239, 239); position: relative; height: 300px;">
                    <div class="bg"></div>
                    <img class="image-icon3" loading="lazy" alt="" src="./public/image-3@2x.png" />

                    <div class="profile-info">
                        <b class="text">Meadow View</b>
                        <div class="pricing">$ 9540.99</div>
                        <div class="location-on-black-24dp-2-parent">
                            <img class="location-on-black-24dp-2-icon" loading="lazy" alt=""
                                src="./public/location-on-black-24dp-2.svg" />

                            <div class="doane-street-fremont-ca-94538-wrapper">
                                <div class="doane-street-fremont"
                                    style="position: absolute; left: 0; margin-left: 10px;">
                                    <i class="fa fa-map-marker" aria-hidden="true"></i>
                                    Doane Street, Fremont CA 94538
                                </div>
                            </div>
                        </div>
                        <div style="width: 1px; height: 10px;"></div>
                        <div class="control_buttons">

                            <button class="edit_doc"><i class="fa fa-pencil" aria-hidden="true"></i> Edit</button>
                            <button class="delete_doc"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</button>

                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
</template>



<style>
.control_buttons {
    width: 100%;
    height: 30px;
    position: absolute;
    bottom: 0;
    left: 0;

    border-bottom-left-radius: 15px;
    border-bottom-right-radius: 15px;
}

.edit_doc {
    width: 50%;
    background-color: rgb(41, 41, 156);
    border-radius: 0;
    border-bottom-left-radius: 15px;
    height: 30px;
    font-size: 13px;
    color: white;
    opacity: 0.8;
}

.delete_doc {
    width: 50%;
    background-color: rgb(144, 44, 44);
    border-radius: 0;
    border-bottom-right-radius: 15px;
    height: 30px;
    font-size: 13px;
    color: white;
}
</style>