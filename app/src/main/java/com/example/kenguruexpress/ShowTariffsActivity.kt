package com.example.kenguruexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenguruexpress.adapters.TarrifsAdapter
import com.example.kenguruexpress.models.websocket.WebSocketResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_show_tariffs.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class ShowTariffsActivity : AppCompatActivity() {

    var tariffList : ArrayList<WebSocketResponse> = arrayListOf()

    private lateinit var webSocketClient: WebSocketClient

    var adapterTarrif: TarrifsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tariffs)

        initWebSocket()

        rcViewTarrif.hasFixedSize()
        rcViewTarrif.layoutManager = LinearLayoutManager(this)
        adapterTarrif = TarrifsAdapter(tariffList, this)
        rcViewTarrif.adapter = adapterTarrif
    }

    private fun initWebSocket() {
        val departureId = intent.getIntExtra("id", 0)
        val webSocketUrl = "ws://68.183.30.45/ws/calculation/408/"

        val coinbaseUri = URI(webSocketUrl)
        createWebSocketClient(coinbaseUri)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage: $message")
                getTarrifList(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
            }

            override fun onError(ex: Exception?) {
                Log.d(TAG, "onError: ${ex?.message}")
            }

        }
    }

    private fun getTarrifList(message: String?) {
        message?.let {
            val moshi = Moshi.Builder()
                    .build()
            val adapter: JsonAdapter<WebSocketResponse> = moshi.adapter(WebSocketResponse::class.java)
            val tarrList = adapter.fromJson(message)
            if (tarrList != null && tarrList.completed != true) {
                tariffList.add(tarrList)
            } else {
                runOnUiThread {
                    adapterTarrif?.updateAdapter(tariffList)
                }
                webSocketClient.close()
                if (tariffList.isEmpty()) {
                    emptyTariffsTitle.text = this.resources.getString(R.string.emptyTariffs)
                    emptyTariffsTitle.visibility = View.VISIBLE
                } else {
                    emptyTariffsTitle.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val TAG = "WebSocket"
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }
}