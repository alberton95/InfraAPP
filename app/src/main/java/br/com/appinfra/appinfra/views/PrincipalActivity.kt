package br.com.appinfra.appinfra.views

import android.app.Dialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.adapter.AdapterComplaint
import br.com.appinfra.appinfra.models.FirebaseServices.FirebaseHelper
import br.com.appinfra.appinfra.models.beans.Complaint
import br.com.appinfra.appinfra.models.models.beans.Config.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var db: DatabaseReference
    lateinit var helper: FirebaseHelper
    lateinit var adapter: AdapterComplaint
    lateinit var rv: RecyclerView
    var mRegistrationBroadcastReceiver: BroadcastReceiver? = null

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, IntentFilter("registrationComplete"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, IntentFilter(Config.STR_PUSH))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal2)

        // Initialize Push Notifications Firebase
        mRegistrationBroadcastReceiver = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent) {
                if(intent.action == Config.STR_PUSH){
                    val message = intent.getStringExtra("message")
                    showNotification("InfraAPP", message)
                }
            }

        }

        initilizeFirebase()
        initializeRecyclerView()

        // Action Floating Button
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { displayInputDialog() }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initilizeFirebase() {
        // Initiliaze Firebase
        db = FirebaseDatabase.getInstance().reference
        helper = FirebaseHelper(db)
    }

    fun initializeRecyclerView() {

        // Initialize RecyclerView
        rv = findViewById(R.id.rv_questions) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)

        // Initialize Adapter
        adapter = AdapterComplaint(this, helper.retrieve())
        rv.adapter = adapter
    }

    private fun showNotification(title: String, message: String?) {
        val intent = Intent(applicationContext, PrincipalActivity::class.java)
        val contentIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(applicationContext)
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, builder.build())
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.insertComplaint) {
            insertComplaint()
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun insertComplaint() {
        val changePage = Intent(this, InsertDescriptionActivity::class.java)
        startActivity(changePage)
    }

    private fun displayInputDialog() {

        // Create Dialog
        val d = Dialog(this)
        d.setContentView(R.layout.input_dialog)
        val btSave = d.findViewById(R.id.btSave) as Button
        val etTitle = d.findViewById(R.id.etTitle) as EditText
        val etCity = d.findViewById(R.id.etCity) as EditText
        val etDescription = d.findViewById(R.id.etDescription) as EditText
        val etNeighborhood = d.findViewById(R.id.etNeighborhood) as EditText
        val etAdress = d.findViewById(R.id.etAdress) as EditText

        // Action Button Save
        btSave.setOnClickListener {

            // Get Data
            val title = etTitle.text.toString()
            val city = etCity.text.toString()
            val desc = etDescription.text.toString()
            val neighborhood = etNeighborhood.text.toString()
            val adress = etAdress.text.toString()

            // Set Data
            val s = Complaint()
            s.title = title
            s.description = desc
            s.city = city
            s.neighborhood = neighborhood
            s.adress = adress
            s.status = "true"
            s.image = "URL DA IMAGEM"

            // Save Data
            if (title != null && title.length > 0) {
                if (helper.save(s)!!) {
                    Toast.makeText(this, "Reclamação enviada com sucesso!", Toast.LENGTH_SHORT).show()
                    d.hide()
                    adapter = AdapterComplaint(this, helper.retrieve())
                    rv.adapter = adapter
                }
            } else {
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show()
            }
        }
        d.show()
    }

    fun logout(view: View){

        try {
            FirebaseAuth.getInstance().signOut()
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            editor.clear()
            editor.commit()
            activityLoguin ()
        }catch (e: Exception){
        }

    }

    fun activityLoguin () {
        val changePage = Intent(this, LoginActivity::class.java)
        startActivity(changePage)
    }


}