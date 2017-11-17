package br.com.appinfra.appinfra;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.appinfra.appinfra.adapter.AdapterComplaint;
import br.com.appinfra.appinfra.models.FirebaseServices.FirebaseHelper;
import br.com.appinfra.appinfra.models.beans.Complaint;

public class MainActivity extends AppCompatActivity {

    DatabaseReference db;
    FirebaseHelper helper;
    AdapterComplaint adapter;
    RecyclerView rv;
    EditText etTitle, etCity, etNeighborhood, etDescription, etAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal2);

        //INITIALIZE RV
        rv= (RecyclerView) findViewById(R.id.rv_questions);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //INITIALIZE FB
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);

        //ADAPTER
        adapter=new AdapterComplaint(this,helper.retrieve());
        rv.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });
    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        //CREATE DIALOG
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        etTitle= (EditText) d.findViewById(R.id.etTitle);
        etDescription= (EditText) d.findViewById(R.id.etDescription);
        etCity= (EditText) d.findViewById(R.id.etCity);
        etNeighborhood= (EditText) d.findViewById(R.id.etNeighborhood);
        etAdress= (EditText) d.findViewById(R.id.etAdress);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String title= etTitle.getText().toString();
                String city= etCity.getText().toString();
                String desc = etDescription.getText().toString();
                String neighborhood = etNeighborhood.getText().toString();
                String adress = etAdress.getText().toString();

                //SET DATA
                Complaint s = new Complaint();
                s.setTitle(title);
                s.setDescription(desc);
                s.setCity(city);
                s.setNeighborhood(neighborhood);
                s.setAdress(adress);
                s.setStatus("true");
                s.setImagem("URL DA IMAGEM");

                //SAVE
                if(title != null && title.length()>0)
                {
                    if(helper.save(s))
                    {
                        etTitle.setText("");
                        etCity.setText("");
                        etNeighborhood.setText("");
                        adapter=new AdapterComplaint(MainActivity.this,helper.retrieve());
                        rv.setAdapter(adapter);
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }
}

