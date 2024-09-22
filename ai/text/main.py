import uuid
import onnxruntime as ort
import numpy as np


def autism_prediction(model_path, data_array):
    # Data preperation
    data_array = np.array(data_array).astype(np.float32)
    data_array = {'input': data_array}

    # Model building.
    ort_session = ort.InferenceSession(model_path)
    output = ort_session.run(None, data_array)
    
    return output



def main(data_array):
    print(autism_prediction("q10_autism_detection_model.onnx", data_array))


main([ 1., 0., 1., 1., 1., 0., 1., 1., 1., 1., 0., 8., 26., 0.])