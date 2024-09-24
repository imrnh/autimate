from types import SimpleNamespace

mongodb_cfg = SimpleNamespace(
    MONGO_USER = "asd",
    MONGO_PASSWORD = "sugarcat",
    MONGO_CLUSTER = "asdcluster.pn2r4.mongodb.net",
    MONGO_DATABASE = "asd"
)



env_cfg = SimpleNamespace(
    MODEL_PATH = "/storage/model.onnx",
    SPACE_NAME="wiggle-space",
    LOCAL_FILE_NAME="/storage/sub_video.mp4",
    REGION="nyc3",
    ACCESS_KEY="DO00ND8KPADVKJDN6Z8V",
    SECRET_KEY="t4KrqnMCaQuzgwFwNzgjyxFk2wDq9Bkx/doXKioSyM4"
)