package com.eap.bankin_challenge_jerome_eap

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    val resourcesList = mutableListOf<ResourceModel>()
    val handler = android.os.Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.addItemDecoration(DividerItemDecoration(
            recycler_view.context,
            (recycler_view.layoutManager as LinearLayoutManager).orientation
        ))
        recycler_view.adapter = ResourcesAdapter(resourcesList)
        FetchData().start()
    }

    inner class FetchData : Thread() {
        private var data = ""

        override fun run() {
            try {
                val url =
                    URL("https://raw.githubusercontent.com/bankin-engineering/challenge-android/master/categories.json")
                val httpURLConnection = url.openConnection()
                val inputStream = httpURLConnection.getInputStream()
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                var line = bufferedReader.readLine()

                while (line != null) {
                    data += line
                    line = bufferedReader.readLine()
                }

                if (data.isNotEmpty()) {
                    val jsonObject = JSONObject(data)
                    val resources = jsonObject.getJSONArray("resources")

                    for (i in 0 until resources.length()) {
                        val jsonResource = resources.getJSONObject(i)
                        val resourceParent =
                            if (jsonResource.isNull("parent")) {
                                null
                            } else {
                                ResourceParent(
                                    jsonResource.getJSONObject("parent").getInt("id"),
                                    jsonResource.getJSONObject("parent").getString("resource_uri"),
                                    jsonResource.getJSONObject("parent").getString("resource_type")
                                )
                            }
                        val resource = ResourceModel(
                            jsonResource.getInt("id"),
                            jsonResource.getString("resource_uri"),
                            jsonResource.getString("resource_type"),
                            jsonResource.getString("name"),
                            resourceParent,
                            jsonResource.getBoolean("custom"),
                            jsonResource.getBoolean("other"),
                            jsonResource.getBoolean("is_deleted"),
                        )
                        resourcesList.add(resource)
                    }
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            handler.post { recycler_view.adapter!!.notifyDataSetChanged() }
        }
    }

}