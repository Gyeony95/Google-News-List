package com.example.mynewslist.News

import android.content.Context
import android.util.Log
import com.example.mynewslist.Network.XmlParser
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.w3c.dom.Element
import java.lang.Exception
import java.util.*
import java.util.Collections.sort


class NewsPresenter: NewsContract.Presenter {
    override lateinit var view: NewsContract.View
    override lateinit var mContext: Context
    private val TAG = "NewsPresenter"
    var topic = arrayListOf<String>()


    //아이템 불러오기
    override fun loadItems(list: ArrayList<NewsModel>, adapter: NewsAdapter) {
        val okx: XmlParser = XmlParser(mContext,"https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")//뉴스 주소
        val mDoc = okx.execute().get()

        val itemNodeList = mDoc!!.getElementsByTagName("item")
        for (i in 0 until itemNodeList.length) {//아이템그룹의 수만큼 반복
            val node = itemNodeList.item(i)
            val element = node as Element
            val titleNodeList = element.getElementsByTagName("title")
            val urlNodeList = element.getElementsByTagName("link")

            val title = titleNodeList.item(0).childNodes.item(0).nodeValue
            val url = urlNodeList.item(0).childNodes.item(0).nodeValue

            //Anko라이브러리로 비동기 처리
            doAsync {
                val mDoc2 = Jsoup.connect(url).get()
                //아이템 추가
                val mImage = mDoc2.select("meta[property=og:image]").get(0).attr("content")//이미지주소 얻어옴
                val mScript = mDoc2.select("meta[property=og:description]").get(0).attr("content")//본문일부 얻어옴
                var strArr = mScript.split(" ")//공백기준으로 문자열 나눔
                var firstHm = HashMap<String, Int>() //해시맵선언

                //단어별로 출연빈도를 해시맵에 저장함
                for (i in 0 until strArr.size) {
                    if(strArr.get(i).length >= 2){//길이가 2 이상일때
                        try{
                            val count = firstHm.getOrDefault(strArr.get(i), 0) //해시맵에 단어가 등록되어있지 않으면 0
                            firstHm[strArr.get(i)] = count + 1 //처음등록이면 1 두번째등록이면 2 이렇게 중복체크를해줌
                        }catch (e:Exception){ }

                    }
                }
                var largeNum = 0//출연빈도
                sort(strArr)//오름차순정렬
                for(i in 0..3){//3회 반복
                    largeNum = getLargeNum(firstHm, largeNum, strArr)//가장 출연빈도가 높은 수를 얻어옴
                    firstHm = getKeyWord(firstHm, largeNum, strArr)
                    largeNum = 0
                }
                //adapter.addItem(NewsModel(mImage, title, mScript, topic[0], topic[1], topic[2], url))
                try{
                    list.add(NewsModel(mImage, title, mScript, topic[0], topic[1], topic[2], url))
                }catch(e:Exception){
                    list.add(NewsModel(mImage, title, mScript, "예외1", "예외2", "예외3", url))
                }
                topic.clear()
                if( i == itemNodeList.length-1){//마지막이면
                    Log.e("itemNodeList",i.toString())
                    Log.e("itemNodeList",itemNodeList.length.toString())
                    view.setView()
                }
            }
        }
    }




    //가장 출연빈도가 높은 수를 얻어옴
    fun getLargeNum(firstHm:HashMap<String, Int>,largeNum:Int, strArr: List<String>):Int{
        for (i in 0 until strArr.size) {
            if(firstHm.getOrDefault(strArr.get(i), 0) > largeNum){
                return firstHm.getOrDefault(strArr.get(i), 0)
            }
        }
        return 0
    }
    //가장 출연빈도가 높은걸 topic배열에 저장하고 value를 -1로 바꿔서 제일 뒤로보냄
    fun getKeyWord(firstHm:HashMap<String, Int>,largeNum:Int, strArr: List<String>):HashMap<String, Int>{
        for (i in 0 until strArr.size) {
            if(firstHm.getOrDefault(strArr.get(i), 0) == largeNum){
                topic.add(strArr.get(i).trim())//좌우공백제거하고 추가
                firstHm[strArr.get(i)] = -1
            }
        }
        //출연빈도 높은거 뒤로보낸 해시맵을 리턴
        return firstHm
    }



}




