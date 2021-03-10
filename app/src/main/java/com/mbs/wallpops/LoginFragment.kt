package com.mbs.wallpops

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var navController: NavController? = null
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        loginBtn.setOnClickListener() {

            var email = emailLogin.text.toString()
            var password = passwordLogin.text.toString()

            if(!email.isEmpty() && !password.isEmpty()){
                login(email,password)
            } else {
                Snackbar.make((activity as AppCompatActivity).findViewById(android.R.id.content),"Please fill your credentials", Snackbar.LENGTH_SHORT).show()
            }
        }

        loginToRegisterText.setOnClickListener() {
            navController!!.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun login(email: String, password: String) {

        val dialog = Dialog(activity as AppCompatActivity)
        dialog.setContentView(R.layout.dialog_loader)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = android.R.style.Animation_Dialog
        dialog.show()

        this.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as AppCompatActivity, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    navController!!.navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Snackbar.make((activity as AppCompatActivity).findViewById(android.R.id.content),"Failed!", Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            })

    }

}