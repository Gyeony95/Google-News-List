package com.example.mynewslist.Network

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser(var mContext: Context, var mUrl:String) :
    AsyncTask<String?, Void?, Document?>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }
    override fun doInBackground(vararg params: String?): Document? {
        val url: URL
        var doc: Document? = null
        try {
            url = URL(mUrl)
            val dbf =
                DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
        } catch (e: Exception) {
            Log.e("XmlParser", e.message)
        }
        return doc    }


    override fun onPostExecute(doc: Document?) {
        super.onPostExecute(doc)
        /*
        val itemNodeList = doc!!.getElementsByTagName("item")
        for (i in 0 until itemNodeList.length) {
            val node = itemNodeList.item(i)
            val element = node as Element
            val titleNodeList = element.getElementsByTagName("title")
            val title = titleNodeList.item(0).childNodes.item(0).nodeValue
            Log.e("title", title)
        }
        */
    }


}