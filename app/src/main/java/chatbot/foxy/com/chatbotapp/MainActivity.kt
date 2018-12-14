package chatbot.foxy.com.chatbotapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import chatbot.foxy.com.chatbotapp.chatkit.*
import chatbot.foxy.com.chatbotapp.net.VolleySingleton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.firebase.auth.FirebaseAuth
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    var user = User()
    var chatBot = ChatBot()
    var adapter = MessagesListAdapter<Message>(user.id, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        messagesList.setAdapter(adapter)
        input.setInputListener {
            send(it.toString())
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity<LogInActivity>()
            }
        }
        return true
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }

    fun send(command: String) {

        adapter.addToStart(Message(user, command), true)
        val message = Message(chatBot, "Typing...")
        adapter.addToStart(message, true)
        //building the request queue
        val request = object : StringRequest(Request.Method.POST, Config.URL,
                Response.Listener {
                    adapter.delete(message)
                    adapter.addToStart(Message(chatBot, it), true)
                },
                Response.ErrorListener { adapter.addToStart(Message(chatBot, "NetWork Error"), true) }) {
            override fun getParams() = mapOf("command" to command)
            override fun getHeaders() = mapOf("Content-Type" to "application/x-www-form-urlencoded")
        }
        //Adding to volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }
}
