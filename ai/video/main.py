import uuid
import onnxruntime as ort
import numpy as np
from video_onnx_proc import get_video_tensor
from utils import download_from_url


def autism_prediction(model_path, video_path):
    # Data preperation
    video_tensor = get_video_tensor(video_path)
    video_tensor = np.expand_dims(video_tensor, 0)
    video_tensor = {'input': video_tensor}

    # Model building.
    ort_session = ort.InferenceSession(model_path)
    index, confidence = ort_session.run(None, video_tensor)
    
    return index, confidence[index]



def main(url):
    video_file_path = "lib/p_video.mp4"
    if download_from_url(url, video_file_path):
        return autism_prediction("lib/behavioral_video_classifier_model.onnx", video_file_path)


main("3")