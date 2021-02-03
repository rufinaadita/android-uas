package com.kelompok9.projekantrian
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.register_activity.*
import org.json.JSONObject

class RegisterActivity {
    class RegisterActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.register_activity)

            btn_back.setOnClickListener {
                finish()
            }

            tv_signin.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            btn_signup.setOnClickListener {
                registerRequest()
            }
        }

        private fun registerRequest() {
            val jsonobj = JSONObject()
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.100.85:8012/antrian-api/public/api/user/register"

            jsonobj.put("name", tf_name.editText?.text.toString())
            jsonobj.put("email", tf_email.editText?.text.toString())
            jsonobj.put("password", tf_password.editText?.text.toString())

            val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonobj,
                    Response.Listener {
                        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()

                        Handler().postDelayed({
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        },2000)
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
                    })

            queue.add(jsonRequest)
        }
    }
}