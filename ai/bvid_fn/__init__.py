import os
import modal
import modal.gpu
from http import HTTPStatus

from bvid_fn.do import download_file_from_space
from bvid_fn.inference import autism_prediction


image = modal.Image.debian_slim(python_version="3.11").pip_install(
        "onnx", "onnxruntime", "boto3", 
        "opencv-python", "requests", "modal").apt_install("libglu1-mesa-dev", "libglib2.0-0")

app = modal.App(image=image, name="autism_video_analysis_fn")



#Env variables
MODEL_PATH = "/storage/model.onnx"
SPACE_NAME="wiggle-space"
LOCAL_FILE_NAME="/storage/sub_video.mp4"
REGION="nyc3"
ACCESS_KEY="DO00ND8KPADVKJDN6Z8V"
SECRET_KEY="t4KrqnMCaQuzgwFwNzgjyxFk2wDq9Bkx/doXKioSyM4"



@app.function(gpu=modal.gpu.T4(count=1), timeout=120, volumes={"/storage": modal.Volume.from_name("wwvolume")})
@modal.web_endpoint(method="POST")
def main(video_name: str) -> str:
    download_status = download_file_from_space(SPACE_NAME, video_name, LOCAL_FILE_NAME, REGION, ACCESS_KEY, SECRET_KEY)

    if download_status:   
        body_json = autism_prediction(MODEL_PATH, LOCAL_FILE_NAME)

        return {"statusCode" : HTTPStatus.OK, 
                "body" : body_json}  

    else:
        return {"statusCode" : HTTPStatus.NOT_FOUND, 
                "message" : "File download error. Please re-send the request."}  
