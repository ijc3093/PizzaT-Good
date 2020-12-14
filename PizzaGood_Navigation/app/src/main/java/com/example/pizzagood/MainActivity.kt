package com.example.pizzagood

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.pizzagood.Menu_Diag
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.location_fragment.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSIONS = 1
    private val REQUEST_CODE_GOOGLE_PLAY_SERVICES = 2

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        var appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


    //for menus
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_infor -> Menu_Diag().show(supportFragmentManager, "Information")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart(){
        super.onStart()
        var result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if(result != ConnectionResult.SUCCESS){
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, result, REQUEST_CODE_GOOGLE_PLAY_SERVICES)
            dialog.show()
        }
    }//onStart



    fun getLocationClick(view: View){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == REQUEST_CODE_PERMISSIONS){

            updateLocation()
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            RationaleDialog ().show(supportFragmentManager, "rationale")
        }else{
            doPermissionRequest()
        }
    }


    private fun updateLocation(){
        val client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        client.lastLocation.addOnSuccessListener {
            latitude.text = null
            longitude.text = null
            address.text = null

            if(it == null){
                latitude.text = "Location not available, please try again again later"
            }else{
                text_here.text = "I'm here in Rochester, NY"
                latitude.text = "Latitude: " +it.latitude
                longitude.text = "Longitude: " +it.longitude
                AddressLookup().execute(it)
            }
        }
    }

    private fun doPermissionRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            REQUEST_CODE_PERMISSIONS ->{
                if(grantResults.isNotEmpty()){
                    if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED){
                        updateLocation()
                    }else if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION))
                        DeviceSettingsDialog().show(supportFragmentManager, "Settings")
                }//NOT EMPTY
                return
            }//case

        }//when

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }//onRequestPermissionsResult

    @SuppressLint("StaticFieldLeak")
    internal inner class AddressLookup: AsyncTask<Location, Void, Address>(){
        override fun doInBackground(vararg locations: Location): Address? {
            val location = locations[0]!!
            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
            try{
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if(addresses !=null && !addresses.isEmpty()){
                    return addresses[0]
                }
            }catch (e: IOException){
                e.printStackTrace()
            }
            return null
        }//doinbackground

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(addressLoc: Address?){
            if(addressLoc == null){
                address.text = "Address not available"
                return
            }
            for(i in 0..addressLoc.maxAddressLineIndex){
                address.append(addressLoc.getAddressLine(i)+ "\n")
            }
        }
    }//addresslookup

    class RationaleDialog: DialogFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            isCancelable = false
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return AlertDialog.Builder(activity!!)
                .setMessage("This demonstration requires you to grant permission.")
                .setPositiveButton(android.R.string.ok) { _,_ ->

                    (activity!! as MainActivity).doPermissionRequest()
                }
                .create()
        }

    }//RationaleDialog


    class DeviceSettingsDialog: DialogFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            isCancelable = false
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return AlertDialog.Builder(activity!!)
                .setMessage("You didn't want to be asked again, but we need permission.")
                .setPositiveButton(android.R.string.ok) { _,_ ->

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package"
                        ,activity!!.packageName,null)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .create()
        }

    }//DeviceSettingsDialog

}