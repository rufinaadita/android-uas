package com.kelompok9.projekantrian


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kelompok9.projekantrian.helper.Antrian
import com.kelompok9.projekantrian.helper.Data
import com.kelompok9.projekantrian.helper.Session
import com.kelompok9.projekantrian.helper.User
import kotlinx.android.synthetic.main.antrian_activity.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.form_antrian_activity.*
import kotlinx.android.synthetic.main.form_antrian_activity.btn_ambil_antrian

class QueueActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    var nomor_antrian: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.antrian_activity)

        val user = intent.extras?.getSerializable(EXTRA_DATA) as User

        tf_username.text = user.name

        btn_ambil_antrian.setOnClickListener {
            if (nomor_antrian == 5) {
                Toast.makeText(this, "Antrian Sudah Penuh", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, FormAntrianActivity::class.java))
            }
        }

        currentAntrian()
        dataAntrian()
        restAntrian()
        lastAntrian()

        btn_refresh.setOnClickListener {
            currentAntrian()
            dataAntrian()
            restAntrian()
            lastAntrian()

            Toast.makeText(this, "Reload Data Antrian", Toast.LENGTH_SHORT).show()
        }

        btn_logout.setOnClickListener {
            Session.id = ""
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun restAntrian() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/daftar/rest/${Session.id}"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getString("rest")
                tv_restQueue.text = data
            },
            Response.ErrorListener {
                Toast.makeText(this, "Gagal Antri", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonRequest)
    }

    private fun currentAntrian() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/daftar/current"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getJSONObject("data")
                tv_currentQueue.text = data.getString("nomor_antrian")
            },
            Response.ErrorListener {
                Toast.makeText(this, "Gagal Antri", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonRequest)
    }

    private fun dataAntrian() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/daftar/${Session.id}"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getString("nomor_antrian")
                val status = response.getString("status_antri")
                tv_antri.text = data
                if(status == "true") {
                    btn_ambil_antrian.isEnabled = false
                    btn_ambil_antrian.setBackgroundColor(Color.GRAY)
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Gagal Antri", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonRequest)
    }

    private fun lastAntrian() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/daftar/last"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getString("nomor_antrian")
                nomor_antrian = data.toInt()
            },
            Response.ErrorListener {
                Toast.makeText(this, "Gagal Antri", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonRequest)
    }


}