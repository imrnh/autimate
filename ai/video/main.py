import uuid
import onnxruntime as ort
import numpy as np
from video_onnx_proc import get_video_tensor
from utils import download_from_url

from time import perf_counter


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
    video_file_path = "lib/12.mp4"
    st = perf_counter()
    autism_prediction("lib/behavioral_video_classifier_model.onnx", video_file_path)

    ed = perf_counter()
    print(round(ed-st, 2))

main("3")