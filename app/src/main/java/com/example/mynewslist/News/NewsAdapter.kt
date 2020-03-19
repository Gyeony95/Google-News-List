package com.example.mynewslist.News

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewslist.R
import com.example.mynewslist.WebPostActivity
import com.squareup.picasso.Picasso
import java.util.ArrayList

class NewsAdapter(val context: Context, val arrayList: ArrayList<NewsModel>):
    RecyclerView.Adapter<NewsAdapter.Holder>(){

    fun addItem(item: NewsModel) {//아이템 추가
        arrayList.add(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //모델의 변수들 정의하는부분
        val news_title = itemView.findViewById<TextView>(R.id.news_title)
        val news_script = itemView.findViewById<TextView>(R.id.news_script)
        val news_first_topic = itemView.findViewById<TextView>(R.id.news_first_topic)
        val news_second_topic = itemView.findViewById<TextView>(R.id.news_second_topic)
        val news_third_topic = itemView.findViewById<TextView>(R.id.news_third_topic)
        val news_image = itemView.findViewById<ImageView>(R.id.news_image)


        fun bind(news_list: NewsModel, context: Context) {

            //각 아이템에 값을 넣어주는 부분
            news_title.text = news_list.newsTitle
            news_script.text = news_list.newsScript
            news_first_topic.text = news_list.firstKeyward
            news_second_topic.text = news_list.secondKeyward
            news_third_topic.text = news_list.thirdKeyward
            Picasso.get().load(news_list.newsImage).into(news_image)

            //뉴스 상세보기 화면으로 이동
            itemView.setOnClickListener {
                //필요한 정보들을 인텐트로 넘겨줌
                val intent = Intent(context, WebPostActivity::class.java)
                intent.putExtra("news_url", news_list.newsURL)
                intent.putExtra("news_title", news_list.newsTitle)
                intent.putExtra("news_topic_1", news_list.firstKeyward)
                intent.putExtra("news_topic_2", news_list.secondKeyward)
                intent.putExtra("news_topic_3", news_list.thirdKeyward)
                context.startActivity(intent)
            }

        }
    }

}