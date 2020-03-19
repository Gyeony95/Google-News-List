import requests#특정웹페이지에 요청을 보냄
import xmltodict
import time

def dustParsing():
    url = "https://news.chosun.com/site/data/html_dir/2020/03/18/2020031801456.html"

    req = requests.get(url).content
    xmlObject = xmlobject.parse(req)
    allData = xmlObject['response']['body']['items']['item']
    return allData