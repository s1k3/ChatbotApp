package chatbot.foxy.com.chatbotapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import chatbot.foxy.com.chatbotapp.R
import kotlinx.android.synthetic.main.verification_code_fragment.view.*

class VerificationFragment : Fragment() {

    lateinit var codeEntered: CodeEntered

    public interface CodeEntered {
        public fun onCodeEntered(code: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.verification_code_fragment, null)
        view.verifyButton.setOnClickListener {
            val code = view.verificationCode.text.toString();
            codeEntered.onCodeEntered(code)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        codeEntered = context as AppCompatActivity as CodeEntered
    }
}