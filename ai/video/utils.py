import requests

def download_from_url(url, file_path):
    response = requests.get(url)

    try:
        if response.status_code == 200:
            with open(file_path, "r") as f:
                f.write(response.content) 
            return True
    except Exception as e:
        return False
