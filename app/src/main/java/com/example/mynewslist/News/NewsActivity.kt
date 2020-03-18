package com.example.mynewslist.News

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewslist.*
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(),
    NewsContract.View {
    private val TAG = "NewsActivity"
    internal lateinit var presenter: NewsPresenter
    var arrayList = arrayListOf<NewsModel>()
    val mAdapter = NewsAdapter(this, arrayList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        news_recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        news_recyclerview.layoutManager = lm
        news_recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenter = NewsPresenter().apply {
            view = this@NewsActivity
            mContext = this@NewsActivity
        }
        presenter.loadItems(arrayList, mAdapter)

    }

    //새로고침
    override fun refresh() {
        mAdapter.notifyDataSetChanged()
    }
}
