package com.mbs.wallpops

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        var email = emailLogin.text.toString()
        var password = passwordLogin.text.toString()

        loginBtn.setOnClickListener() {
            if(!email.isEmpty() && !password.isEmpty()){
                login(email,password)
            }
        }

        loginToRegisterText.setOnClickListener() {
            navController!!.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun login(email: String, password: String) {
        this.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as AppCompatActivity, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    navController!!.navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Snackbar.make((activity as AppCompatActivity).findViewById(android.R.id.content),"Failed!", Snackbar.LENGTH_SHORT).show()
                }
            })

    }

}