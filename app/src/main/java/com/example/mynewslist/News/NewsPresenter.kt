package com.example.mynewslist.News

import android.content.Context
import android.util.Log
import com.example.mynewslist.Network.XmlParser
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.w3c.dom.Element
import java.util.*
import kotlin.collections.HashMap


class NewsPresenter: NewsContract.Presenter {
    override lateinit var view: NewsContract.View
    override lateinit var mContext: Context
    private val TAG = "NewsPresenter"

    //아이템 불러오기
    override fun loadItems(list: ArrayList<NewsModel>, adapter: NewsAdapter) {
        val okx: XmlParser = XmlParser(mContext,"https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")
        val mDoc = okx.execute().get()

        val itemNodeList = mDoc!!.getElementsByTagName("item")
        for (i in 0 until itemNodeList.length) {
            val node = itemNodeList.item(i)
            val element = node as Element
            val titleNodeList = element.getElementsByTagName("title")
            val urlNodeList = element.getElementsByTagName("link")

            val title = titleNodeList.item(0).childNodes.item(0).nodeValue
            val url = urlNodeList.item(0).childNodes.item(0).nodeValue

            //Anko라이브러리로 비동기 처리
            doAsync {
                val mDoc2 = Jsoup.connect(url).get()
                //Log.e("mDoc2","mDoc2 = "+mDoc2)

                //아이템 추가
                val mImage = mDoc2.select("meta[property=og:image]").get(0).attr("content")
                val mScript = mDoc2.select("meta[property=og:description]").get(0).attr("content")
                var firstTopic = "xxx"
                var secondTopic = "xxx"
                var thirdTopic = "xxx"
                var strArr = mScript.split(" ")
                var firstHm = HashMap<String, Int>() //해시맵선언

                for (i in 0 until strArr.size) {
                    if(!strArr.get(i).equals("")){
                        val count = firstHm.getOrDefault(strArr.get(i), 0) //해시맵에 단어가 등록되어있지 않으면 0
                        firstHm[strArr.get(i)] = count + 1 //처음등록이면 1 두번째등록이면 2 이렇게 중복체크를해줌
                    }
                }



                var largeNum = 0
                Collections.sort(strArr)
                largeNum = getLargeNum(firstHm, largeNum, strArr)
                for (i in 0 until strArr.size) {
                    if(firstHm.getOrDefault(strArr.get(i), 0) == largeNum){
                        firstTopic = strArr.get(i)
                        firstHm[strArr.get(i)] = -1
                        largeNum = 0
                    }
                }



                largeNum = getLargeNum(firstHm, largeNum, strArr)
                for (i in 0 until strArr.size) {
                    if(firstHm.getOrDefault(strArr.get(i), 0) == largeNum){
                        secondTopic = strArr.get(i)
                        firstHm[strArr.get(i)] = -1
                        largeNum = 0
                    }
                }


                largeNum = getLargeNum(firstHm, largeNum, strArr)
                for (i in 0 until strArr.size) {
                    if(firstHm.getOrDefault(strArr.get(i), 0) == largeNum){
                        thirdTopic = strArr.get(i)
                        firstHm[strArr.get(i)] = -1
                        largeNum = 0
                    }
                }

                adapter.addItem(NewsModel(mImage, title, mScript, firstTopic, secondTopic, thirdTopic, url))

                view.refresh()
                adapter.notifyDataSetChanged()
            }
        }

    }

    fun getLargeNum(firstHm:HashMap<String, Int>,largeNum:Int, strArr: List<String>):Int{
        for (i in 0 until strArr.size) {
            if(firstHm.getOrDefault(strArr.get(i), 0) > largeNum){
                return firstHm.getOrDefault(strArr.get(i), 0)
            }
        }
        return 0
    }


/*
    fun test(participant:ArrayList<String>, completion:ArrayList<String>):String{
        var result = ""
        val completionHm = HashMap<String, Int>() //해시맵선언

        for (i in 0 until completion.size) { //완주자를 모두 해시맵에 넣어놓음
            val count = completionHm.getOrDefault(completion.get(i), 0) //동명이인 카운트, 해시맵에 완주자가 등록되어있지 않으면 0
            completionHm[completion.get(i)] = count + 1 //처음등록이면 1 두번째등록이면 2 이렇게 중복체크를해줌
        }

        for (i in 0 until participant.size) { //참가자 길이만큼 반복
            if (completionHm.getOrDefault(participant.get(i), 0) === 0) { //완주자목록에 없으면 0을반환하고 그즉시 리턴
                result = participant.get(i)
                return result
            } else { //완주자목록에 있을시 동명이인 카운트를 하나씩 줄여가며 다시 put해줌(덮어쓰기)
                completionHm[participant.get(i)] = completionHm[participant.get(i)]!! - 1
            }
        }

        return result
    }
*/

}




