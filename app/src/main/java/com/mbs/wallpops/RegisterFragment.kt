package com.mbs.wallpops

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        resigsterToLoginText.setOnClickListener() {
            navController!!.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        buttonSignup.setOnClickListener() {

            var emailSignup = emailSignup.text.toString()
            var passwordSignup = passwordSignup.text.toString()
            var nameOfUserSignup = nameSignup.text.toString()
            var confirmPasswordSignup = passwordConfirmSignup.text.toString()

            if(!emailSignup.isEmpty() && !passwordSignup.isEmpty() && !nameOfUserSignup.isEmpty() && !confirmPasswordSignup.isEmpty()){
                if(passwordSignup.equals(confirmPasswordSignup)) {

                    register(emailSignup, passwordSignup)

                } else {
                    Snackbar.make(
                        (activity as AppCompatActivity).findViewById(android.R.id.content),
                        "Please check the password",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                Snackbar.make(
                    (activity as AppCompatActivity).findViewById(android.R.id.content),
                    "Please fill your Details",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun register(email: String, password: String) {

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

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            (activity as AppCompatActivity),
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    navController!!.navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    dialog.dismiss()
                    Snackbar.make(
                        (activity as AppCompatActivity).findViewById(android.R.id.content),
                        "Error registering, try again later :(",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
            })
    }
}