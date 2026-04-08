package com.example.smartbuddyai.core.data.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseModule {
    val firebaseStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}