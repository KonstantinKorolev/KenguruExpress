package com.example.kenguruexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.util.concurrent.TimeUnit

class ShowTariffsActivity : AppCompatActivity() {

    var trafficList: ArrayList<WebSocketResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tariffs)

        val departureId = intent.getStringExtra("id")
        val defaultWebSocket = "ws://68.183.30.45/ws/calculation/${departureId}/"

        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(defaultWebSocket)
            .build()
        val wsListener = WebSocketList()
        val webSocket = client.newWebSocket(request, wsListener)
    }

    private class WebSocketList: WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: WebSocketResponse?) {
            // дописать получения данных с сервера
            val trafficListResponse = ShowTariffsActivity()
            trafficListResponse.trafficList = // ..
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.d("WebSocket", t.message.toString())
        }

        companion object {
            private val NORMAL_CLOSURE_STATUS = 1000
        }
    }
}
