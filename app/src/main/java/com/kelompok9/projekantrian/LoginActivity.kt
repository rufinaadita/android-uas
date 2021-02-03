package com.kelompok9.projekantrian

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kelompok9.projekantrian.helper.Session
import com.kelompok9.projekantrian.helper.User
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        Session.init(applicationContext)

        val jsonobj = JSONObject()

        loginButton.setOnClickListener {
            jsonobj.put("email", tf_mail.editText?.text.toString())
            jsonobj.put("password", tf_password.editText?.text.toString())

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.85:8012/antrian-api/public/api/user/login"

            val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                    Response.Listener { response ->
                        val data = response.getJSONObject("data")
                        val user = User(
                                id = data.getString("id"),
                                name = data.getString("name"),
                                email = data.getString("email")
                        )
                        Session.id = user.id
                        Toast.makeText(this, "Anda Berhasil Login ${data.getString("name")}", Toast.LENGTH_SHORT).show()
                        GlobalScope.launch {
                            delay(2000L)
                            val intent = Intent(this@LoginActivity, QueueActivity::class.java)
                            intent.putExtra(QueueActivity.EXTRA_DATA, user)
                            startActivity(intent)
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
                    })

            queue.add(jsonRequest)

        }

        tv_signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}