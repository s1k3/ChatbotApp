package chatbot.foxy.com.chatbotapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import chatbot.foxy.com.chatbotapp.R
import kotlinx.android.synthetic.main.phone_number_verify_fragment.view.*
import java.lang.ClassCastException

class PhoneNumberFragment : Fragment() {

    lateinit var verifyCode: VerifyCode

    public interface VerifyCode {
        public fun onVerifyCode(phone: String): Unit
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.phone_number_verify_fragment, null)
        view.loginButton.setOnClickListener {
            val phone = view.phoneNumberEditText.text.toString();
            verifyCode.onVerifyCode(phone)
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context ?: throw ClassCastException("context is null")
        verifyCode = context as AppCompatActivity as VerifyCode
    }

}