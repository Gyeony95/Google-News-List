package com.example.mynewslist.News

class NewsModel(
    var newsImage:String,//이미지 주소
    var newsTitle:String,//제목
    var newsScript:String,//본문 일부 발췌
    var firstKeyward:String,//첫번째 topic
    var secondKeyward:String,//두번째 topic
    var thirdKeyward:String,//세번째 topic
    var newsURL:String//뉴스 원문 url
)