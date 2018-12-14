package chatbot.foxy.com.chatbotapp

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import chatbot.foxy.com.chatbotapp.fragments.PhoneNumberFragment
import chatbot.foxy.com.chatbotapp.fragments.VerificationFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class LogInActivity : AppCompatActivity(), PhoneNumberFragment.VerifyCode, VerificationFragment.CodeEntered {


    lateinit var dialog: ProgressDialog
    lateinit var verification: String
    var phoneNumberVerfiyCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
            signInWithPhoneAuthCredential(credential);
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            toast(exception.toString()).show()
            if (exception is FirebaseAuthInvalidCredentialsException) {
                toast("Invalid Credential").show()
            } else if (exception is FirebaseTooManyRequestsException) {
                toast("Server is busy right now").show()
            }

        }

        override fun onCodeSent(verficationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
            verification = verficationId ?: ""
            toast("Verification code has been sent").show()
            dialog.dismiss();
            supportFragmentManager.transaction(allowStateLoss = true) {
                replace(R.id.root, VerificationFragment(), "LOG_IN")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity<MainActivity>()
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in)
        supportFragmentManager.transaction(allowStateLoss = true) {
            replace(R.id.root, PhoneNumberFragment(), "LOG_IN")
        }
//        loginButton.setOnClickListener {
//            dialog = indeterminateProgressDialog(message = "Signing In.....", title = "Please Wait")
//
//            val phoneNumber = phoneNumberEditText.text.toString()
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, phoneNumberVerfiyCallback);
//        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential?) {
        dialog.hide()
        FirebaseAuth.getInstance().signInWithCredential(credential!!).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity<MainActivity>()
            } else {
                toast("Something Went wrong").show()
            }
        }
    }

    override fun onVerifyCode(phone: String) {
        dialog = indeterminateProgressDialog(message = "Signing In.....", title = "Please Wait")
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, phoneNumberVerfiyCallback);
    }

    override fun onCodeEntered(code: String) {
        dialog = indeterminateProgressDialog(message = "Signing In.....", title = "Please Wait")
        dialog.show()
        val credential = PhoneAuthProvider.getCredential(verification, code)
        signInWithPhoneAuthCredential(credential)
    }
}
