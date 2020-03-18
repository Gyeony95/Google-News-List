package com.example.mynewslist.News

import android.content.Context
import android.util.Log
import com.example.mynewslist.Network.XmlParser
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.w3c.dom.Element
import java.util.*
import kotlin.system.measureNanoTime


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
                Log.e("mDoc2","mDoc2 = "+mDoc2)

                //아이템 추가
                var mImage = mDoc2.select("meta[property=og:image]").get(0).attr("content")
                var mScript = mDoc2.select("meta[property=og:description]").get(0).attr("content")
                //Log.e("mImage",mImage)
                //Log.e("mScript",mScript)
                adapter.addItem(NewsModel(mImage, title, mScript, "123", "456", "789", url))
                adapter.notifyDataSetChanged()
            }
        }

    }


}




