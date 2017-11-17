package br.com.appinfra.appinfra.models.FirebaseServices;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import br.com.appinfra.appinfra.models.beans.Complaint;

/**
 * Created by alber on 17/11/2017.
 */

public class FirebaseHelper {

        DatabaseReference db;
        Boolean saved=null;
        ArrayList<Complaint> Complaints=new ArrayList<>();
        /*
         PASS DATABASE REFRENCE
     */
        public FirebaseHelper(DatabaseReference db) {
            this.db = db;
        }
        //WRITE IF NOT NULL
        public Boolean save(Complaint Complaint)
        {
            if(Complaint==null)
            {
                saved=false;
            }else
            {
                try
                {
                    db.child("list_complaints").push().setValue(Complaint);
                    saved=true;
                }catch (DatabaseException e)
                {
                    e.printStackTrace();
                    saved=false;
                }
            }
            return saved;
        }
        //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
        private void fetchData(DataSnapshot dataSnapshot)
        {
            Complaints.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                Complaint Complaint = ds.getValue(Complaint.class);
                Complaints.add(Complaint);
            }
        }
        //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
        public ArrayList<Complaint> retrieve()
        {
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return Complaints;
        }

    }
