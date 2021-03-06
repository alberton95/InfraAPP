package br.com.appinfra.appinfra.views

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
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.FirebaseServices.FirebaseHelper
import br.com.appinfra.appinfra.models.Util.CheckConn
import br.com.appinfra.appinfra.models.models.beans.Config.Config
import br.com.appinfra.appinfra.views.adapter.AdapterComplaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.content_principal.*

class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var helper: FirebaseHelper
    lateinit var adapter: AdapterComplaint
    lateinit var rv: RecyclerView
    var mRegistrationBroadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal2)

        // Initialize Push Notifications Firebase
        mRegistrationBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Config.STR_PUSH) {
                    val message = intent.getStringExtra("message")
                    showNotification("InfraAPP", message)
                }
            }

        }

        initilizeFirebase()
        initializeRecyclerView()
        refreshDataFirebase()

        // Action Floating Button
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { insertComplaint() }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        // Get navigation view and set name user
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        var header: View = navigationView!!.getHeaderView(0);
        var tvUser = header.findViewById(R.id.tvUser) as TextView
        var user: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            tvUser.text = user.email;
        }else{
            tvUser.text = "Usuário"
        }


        val swiperefesh = findViewById(R.id.swiperefresh) as SwipeRefreshLayout

        swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (CheckConn.verifyConnection(this@PrincipalActivity)) {
                    refreshDataFirebase()
                    swiperefesh.setRefreshing(false);
                } else {
                    Toast.makeText(this@PrincipalActivity, "Não foi possivel recarregar! Verifique sua conexão.", Toast.LENGTH_SHORT).show()
                    swiperefesh.setRefreshing(false);
                }


            }
        }
        );
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, IntentFilter("registrationComplete"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, IntentFilter(Config.STR_PUSH))
    }


    private fun initilizeFirebase() {
        // Initiliaze Firebase
        val db = FirebaseDatabase.getInstance().reference
        helper = FirebaseHelper(db)
    }

    fun initializeRecyclerView() {

        // Initialize RecyclerView
        rv = findViewById(R.id.rv_questions) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        refreshDataFirebase()

    }

    fun refreshDataFirebase() {
        // Refresh Data Firease and Initialize Adapter
        adapter = AdapterComplaint(this, helper.retrieve())
        rv.adapter = adapter
    }

    // Show Notifications Push Firebase
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

    fun logout(view: View) {

        try {
            FirebaseAuth.getInstance().signOut()
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            editor.clear()
            editor.commit()
            activityLoguin()
        } catch (e: Exception) {
        }

    }

    fun activityLoguin() {
        val changePage = Intent(this, LoginActivity::class.java)
        startActivity(changePage)
    }


}