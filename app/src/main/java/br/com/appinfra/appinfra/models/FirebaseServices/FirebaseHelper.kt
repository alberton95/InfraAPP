package br.com.appinfra.appinfra.models.FirebaseServices

import br.com.appinfra.appinfra.models.beans.Complaint
import com.google.firebase.database.*
import java.util.*

/**
 * Created by alber on 17/11/2017.
 */

// Class Functions Complaints Firebase
class FirebaseHelper(internal var db: DatabaseReference) {

    internal var saved: Boolean? = null
    internal var Complaints = ArrayList<Complaint>()

    // Save Complaint
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

    // Read Complaints in Firebase
    fun retrieve(): ArrayList<Complaint> {

        db.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Complaints.clear()
                for (ds in dataSnapshot.children) {
                    val Complaint = ds.getValue(Complaint::class.java)
                    if (Complaint != null) {
                        Complaints.add(0,Complaint)
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Complaints.clear()
                for (ds in dataSnapshot.children) {
                    val Complaint = ds.getValue(Complaint::class.java)
                    if (Complaint != null) {
                        Complaints.add(Complaint)
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Complaints.clear()
                for (ds in dataSnapshot.children) {
                    val Complaint = ds.getValue(Complaint::class.java)
                    if (Complaint != null) {
                        Complaints.add(Complaint)
                    }
                }
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                Complaints.clear()
                for (ds in dataSnapshot.children) {
                    val Complaint = ds.getValue(Complaint::class.java)
                    if (Complaint != null) {
                        Complaints.add(Complaint)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        return Complaints
    }



}
