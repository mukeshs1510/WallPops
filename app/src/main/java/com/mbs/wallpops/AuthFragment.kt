package com.mbs.wallpops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var navController: NavController? = null
    private var floatView: Float = 0F

    private val RC_SIGN_IN = 234
    lateinit var mGoogleSignInClient: GoogleSignInOptions
    val Req_Code:Int=123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        if(firebaseAuth.currentUser != null) {
            navController!!.navigate(R.id.action_authFragment_to_homeFragment)
            //(activity as AppCompatActivity).supportFragmentManager.beginTransaction().remove(this).commit()

        }

        animateButtons()
        getStartedBtn.setOnClickListener() {
            navController!!.navigate(R.id.action_authFragment_to_registerFragment)
        }
        loginBtnAuth.setOnClickListener() {
            navController!!.navigate(R.id.action_authFragment_to_loginFragment)
        }

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(, gso);

    }

    private fun animateButtons() {
        googleLogin.translationY = 300F
        fbLogin.translationY = 300F
        twitterLogin.translationY = 300F

        googleLogin.alpha = floatView
        fbLogin.alpha = floatView
        twitterLogin.alpha = floatView

        googleLogin.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(250).start()
        fbLogin.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(450).start()
        twitterLogin.animate().translationY(0F).alpha(1F).setDuration(1000).setStartDelay(650).start()
    }

}