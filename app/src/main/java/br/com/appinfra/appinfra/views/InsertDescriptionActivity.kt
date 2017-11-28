package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.appinfra.appinfra.R
import kotlinx.android.synthetic.main.activity_insert_description.*

class InsertDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_description)

        // Action Button Send Description
        sendDescription.setOnClickListener {

            if(etTitle.text.isNotEmpty() && etDescription.text.isNotEmpty()){
                var title = etTitle.text.toString()
                var description = etDescription.text.toString()

                val it = Intent(this, MapsActivity::class.java)
                it.putExtra("title", title)
                it.putExtra("description", description)
                startActivity(it)
            }else{
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_LONG).show()
            }

        }

    }

}
