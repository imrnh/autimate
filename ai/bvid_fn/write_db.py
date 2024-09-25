import pymongo
from pymongo import MongoClient
from datetime import datetime

from bvid_fn.config import mongodb_cfg as c


uri = f"mongodb+srv://{c.MONGO_USER}:{c.MONGO_PASSWORD}@{c.MONGO_CLUSTER}/{c.MONGO_DATABASE}?retryWrites=true&w=majority"
client = MongoClient(uri)
db = client[c.MONGO_DATABASE]

collection = db['asd_ex']

def push_to_db(username, request_id, asd_status, confidence):
    data = {
        "username": username,
        "requestID": request_id,
        "testType": "video",
        "testDate": datetime.now(),
        "asdStatus": asd_status,
        "confidence": confidence,
    }

    collection.insert_one(data)
