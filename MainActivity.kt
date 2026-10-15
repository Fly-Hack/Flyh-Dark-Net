package com.flyh.darknet

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.VpnService
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.flyh.darknet.transport.ServerProfile

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var host: EditText
    private lateinit var tlsPort: EditText
    private lateinit var user: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(12, 13, 19)
        window.navigationBarColor = Color.rgb(12, 13, 19)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(Color.rgb(12, 13, 19))
        }
        fun heading(s: String, size: Float, color: Int): TextView = TextView(this).apply {
            text = s; textSize = size; setTextColor(color); gravity = Gravity.CENTER
        }
        layout.addView(heading("◆ FLYH DARK NET", 25f, Color.WHITE))
        layout.addView(heading("SECURE SSH + TLS", 13f, Color.rgb(240, 68, 82)))
        status = heading("● Desconectado", 18f, Color.LTGRAY)
        status.setPadding(0, 50, 0, 40)
        layout.addView(status)
        fun field(hintText: String, numeric: Boolean = false): EditText = EditText(this).apply {
            hint = hintText
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            if (numeric) inputType = InputType.TYPE_CLASS_NUMBER
            layout.addView(this, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
        host = field("Domínio TLS do teu servidor")
        tlsPort = field("Porta TLS (ex.: 443)", true)
        user = field("Utilizador SSH")
        val button = Button(this).apply { text = "Verificar configuração" }
        layout.addView(button)
        button.setOnClickListener {
            val profile = ServerProfile(host.text.toString().trim(), tlsPort.text.toString().toIntOrNull(), user.text.toString().trim())
            val error = profile.validationError()
            if (error != null) {
                status.text = "● $error"
            } else {
                status.text = "● Perfil válido — túnel ainda não implementado"
                Toast.makeText(this, "Configuração local validada. Nenhuma VPN foi iniciada.", Toast.LENGTH_LONG).show()
            }
        }
        val consent = Button(this).apply { text = "Solicitar autorização VPN" }
        layout.addView(consent)
        consent.setOnClickListener {
            val intent = VpnService.prepare(this)
            if (intent != null) startActivityForResult(intent, 100) else
                Toast.makeText(this, "Autorização VPN já concedida", Toast.LENGTH_SHORT).show()
        }
        val note = heading("Versão 0.1: interface e autorização. Sem conexão SSH/TLS nem encaminhamento de tráfego.", 12f, Color.GRAY)
        note.setPadding(0, 36, 0, 0)
        layout.addView(note)
        setContentView(ScrollView(this).apply { addView(layout) })
    }

    @Deprecated("Used for compatibility with Android 9+")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            status.text = if (resultCode == RESULT_OK) "● Autorização concedida; VPN inativa" else "● Autorização recusada"
        }
    }
}
