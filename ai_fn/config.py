from types import SimpleNamespace

mongodb_cfg = SimpleNamespace(
    MONGO_USER = "asd",
    MONGO_PASSWORD = "sugarcat",
    MONGO_CLUSTER = "asdcluster.pn2r4.mongodb.net",
    MONGO_DATABASE = "asd"
)



env_cfg = SimpleNamespace(
    MODEL_PATH = "/storage/model.onnx",
    SPACE_NAME="autimatespace",
    LOCAL_FILE_NAME="/storage/sub_video.mp4",
    REGION="nyc3",
    ACCESS_KEY="DO00BPELN7AQJB3HKY22",
    SECRET_KEY="OQmzaHbN8kt1vzYvNQI6EUS8ek1U44CFumsLO0HNFfk",
    Q_MODEL_PATH = "/storage/q10_model.onnx"
)


SECRET_TOKEN = "e00bde8340ee9d617c9b119df8cfdc8f6329304278247fe8aa240eb343529f4001a11fc777fa70f7a07b05694aeff242029b07279d22dea1f4dada5e671d7f70ef1682145e7e30f7cd41dbc27c7bdaf3e803c397744587fa9b813706553ecf7000567eaa3be58137759b4fa8f07054f0d3d5b60096f6a00c46d3d405b220054f"