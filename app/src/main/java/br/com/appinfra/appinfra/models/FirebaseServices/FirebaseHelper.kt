package br.com.appinfra.appinfra.models.FirebaseServices

import br.com.appinfra.appinfra.models.beans.Complaint
import com.google.firebase.database.*
import java.util.*

/**
 * Created by alber on 17/11/2017.
 */

class FirebaseHelper(internal var db: DatabaseReference) {

    internal var saved: Boolean? = null
    internal var Complaints = ArrayList<Complaint>()

    //WRITE IF NOT NULL
    fun save(Complaint: Complaint?): Boolean? {
        if (Complaint == null) {
            saved = false
        } else {
            try {
                db.child("list_complaints").push().setValue(Complaint)
                saved = true
            } catch (e: DatabaseException) {
                e.printStackTrace()
                saved = false
            }

        }
        return saved
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    fun fetchData(dataSnapshot: DataSnapshot): ArrayList<Complaint> {
        Complaints.clear()
        for (ds in dataSnapshot.children) {
            val Complaint = ds.getValue(Complaint::class.java)
            if (Complaint != null) {
                Complaints.add(Complaint)
            }
        }
        return Complaints
    }



    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    fun retrieve(): ArrayList<Complaint> {

        db.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                fetchData(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                fetchData(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                fetchData(dataSnapshot)
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                fetchData(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        return Complaints
    }



}
