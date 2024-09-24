import boto3
from botocore.exceptions import NoCredentialsError

def download_file_from_space(space_name, file_name, local_file_name, region, access_key, secret_key):
    try:
        # Connect to the DigitalOcean Space
        session = boto3.session.Session()
        client = session.client('s3',
                                region_name=region,
                                endpoint_url=f'https://{region}.digitaloceanspaces.com',
                                aws_access_key_id=access_key,
                                aws_secret_access_key=secret_key)

        client.download_file(space_name, file_name, local_file_name)
        return True
    
    except NoCredentialsError:
        print("Credentials not available")
        return False
    except Exception as e:
        print(f"Error occurred: {e}")
        return False
    

# if __name__ == "__main__":
#     SPACE_NAME="wiggle-space"
#     LOCAL_FILE_NAME="lib/sub_video.mp4"
#     REGION="nyc3"
#     ACCESS_KEY="DO00ND8KPADVKJDN6Z8V"
#     SECRET_KEY="t4KrqnMCaQuzgwFwNzgjyxFk2wDq9Bkx/doXKioSyM4"

#     video_name = "32122e673fec4be884d7.mp4"
#     print(download_file_from_space(SPACE_NAME, video_name, LOCAL_FILE_NAME, REGION, ACCESS_KEY, SECRET_KEY))