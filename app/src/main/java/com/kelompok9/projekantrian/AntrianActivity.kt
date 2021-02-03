package com.kelompok9.projekantrian

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kelompok9.projekantrian.helper.Antrian
import com.kelompok9.projekantrian.helper.Session
import com.kelompok9.projekantrian.helper.User
import kotlinx.android.synthetic.main.antrian_activity.*
import kotlinx.android.synthetic.main.form_antrian_activity.*
import kotlinx.android.synthetic.main.form_antrian_activity.btn_ambil_antrian
import kotlinx.android.synthetic.main.form_antrian_activity.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class FormAntrianActivity : AppCompatActivity() {

    var nomor_antrian: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_antrian_activity)

        btn_back.setOnClickListener {
            finish()
        }

        btn_ambil_antrian.setOnClickListener {
            if (nomor_antrian == 5) {
                Toast.makeText(this, "Antrian Sudah Penuh", Toast.LENGTH_SHORT).show()
            } else {
                tambahAntrian()
                tambahDaftar()
            }
        }
    }

    private fun tambahDaftar() {
        val jsonobj = JSONObject()
        jsonobj.put("id", Session.id)

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/daftar/create"

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener { response ->

            },
            Response.ErrorListener {
                Toast.makeText(this, "Gagal Daftar", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonRequest)
    }

    private fun tambahAntrian() {
        val jsonobj = JSONObject()
        jsonobj.put("usia", tf_usia.editText?.text.toString())
        jsonobj.put("alamat", tf_alamat.editText?.text.toString())
        jsonobj.put("nohp", tf_nohp.editText?.text.toString())
        jsonobj.put("gender", tf_gender.editText?.text.toString())

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.100.85:8012/antrian-api/public/api/antrian/create"

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            Response.Listener { response ->
                Toast.makeText(
                    this,
                    "Tambah Data Berhasil",
                    Toast.LENGTH_SHORT
                ).show()
                GlobalScope.launch {
                    delay(2000L)
                    val intent = Intent(this@FormAntrianActivity, QueueActivity::class.java)
                    startActivity(intent)
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