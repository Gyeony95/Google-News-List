package com.example.mynewslist.News

import android.content.Context
import org.w3c.dom.Document
import java.util.ArrayList

interface NewsContract {

    interface View {
        fun refresh()
    }

    interface Presenter {

        var view: View
        var mContext:Context

        fun loadItems(list: ArrayList<NewsModel>, adapter: NewsAdapter)
    }

}