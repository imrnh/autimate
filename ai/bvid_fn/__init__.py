import os
import modal
import modal.gpu
from http import HTTPStatus

from bvid_fn.do import download_file_from_space
from bvid_fn.inference import autism_prediction
from bvid_fn.write_db import push_to_db
from bvid_fn.config import env_cfg as e


image = modal.Image.debian_slim(python_version="3.11").pip_install(
        "onnx", "onnxruntime", "boto3", 
        "opencv-python", "requests", "modal", "pymongo", "python-dotenv").apt_install("libglu1-mesa-dev", "libglib2.0-0")

app = modal.App(image=image, name="autism_video_analysis_fn")




@app.function(gpu=modal.gpu.T4(count=1), timeout=120, volumes={"/storage": modal.Volume.from_name("wwvolume")})
@modal.web_endpoint(method="POST")
def main(username: str, video_name: str) -> str:
        request_id = video_name.split(".mp4")[0]

        download_status = download_file_from_space(e.SPACE_NAME, video_name, e.LOCAL_FILE_NAME, e.REGION, e.ACCESS_KEY, e.SECRET_KEY)

        if download_status:   
                asd_stat, confid = autism_prediction(e.MODEL_PATH, e.LOCAL_FILE_NAME)
                
                return {"statusCode" : HTTPStatus.OK}

        else:
                return {"statusCode" : HTTPStatus.INTERNAL_SERVER_ERROR}