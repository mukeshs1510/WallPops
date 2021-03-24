package com.mbs.wallpops.DatabaseRepos

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RealtimeDatabase {
    companion object{
        private var instance: RealtimeDatabase? = null
        private var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        private var user: FirebaseUser? = null
        private var userUid: String? = null

        fun getInstance(context: Context) {
            if(instance == null) {
                instance = RealtimeDatabase(context)
            }
            instance
        }

    }

    private constructor() {}

    private constructor(context: Context) {
        try {
            user = FirebaseAuth.getInstance().currentUser
            if(user == null) {
                userUid = user!!.uid
            }
        } catch (e: Exception) {
            Toast.makeText(context,"Something went wrong, Please restart the app",Toast.LENGTH_SHORT)
        }
    }

    fun getReference(): DatabaseReference {
        if(reference == null) {
            FirebaseDatabase.getInstance().getReference()
        }
        return reference
    }

    fun getUserUid(): String {
        return userUid!!
    }

}


//class RepositoryData {
//    private var reference: DatabaseReference? = null
//    private var user: FirebaseUser? = null
//    var userId: String? = null
//        private set
//
//    private constructor() {}
//    private constructor(context: Context) {
//        try {
//            user = FirebaseAuth.getInstance().currentUser
//            if (user != null) {
//                userId = user!!.uid
//                reference = FirebaseDatabase.getInstance().getReference("customers").child(
//                    userId!!
//                )
//            } else {
//                Toast.makeText(context, "Something went wrong, RESTART AGAIN ", Toast.LENGTH_LONG)
//                    .show()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    val referenceOfDB: DatabaseReference
//        get() = if (reference == null) FirebaseDatabase.getInstance().reference
//        else
//            reference
//
//    companion object {
//        private var instance: RepositoryData? = null
//        fun getInstance(mcontext: Context): RepositoryData? {
//            if (instance == null) {
//                instance = RepositoryData(mcontext)
//            }
//            return instance
//        }
//    }
//}
