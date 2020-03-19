package com.example.mynewslist.News

import android.content.Context
import org.w3c.dom.Document
import java.util.ArrayList

interface NewsContract {

    interface View {
        fun refresh()//presenter에서 activity를 새로고침 하고싶을때 사용
        fun setView()//뷰 설정
    }

    interface Presenter {
        var view: View//activiy 그 자체, presenter에서 위에 View의 함수를 실행할 수 있음
        var mContext:Context//presenter에서 activity 조작하기 위한 권한

        //아이템 불러오기
        fun loadItems(list: ArrayList<NewsModel>, adapter: NewsAdapter)
    }

}