package com.example.googleassistantcloning

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.googleassistantcloning.utils.UiUtils
import com.example.googleassistantcloning.utils.UiUtils.Commands
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class ExploreActivity : AppCompatActivity() {
    private val apiKey = "5b61571e06a9ceaaaa9169003fe5d343"
    private var baseUrl = "https://api.openweathermap.org/data/2.5/weather"

    private lateinit var textView: TextView
    private lateinit var greetings: TextView
    private lateinit var today: TextView
    private lateinit var wind: TextView
    private lateinit var weatherLayout: LinearLayout
    private lateinit var cardViewWeather: CardView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        UiUtils.setCustomActionBar(supportActionBar, this)
        textView = findViewById(R.id.textView)
        weatherLayout = findViewById(R.id.weather_layout)
        greetings = findViewById(R.id.greetings)
        today = findViewById(R.id.today)
        wind = findViewById(R.id.wind)
        cardViewWeather = findViewById(R.id.weatherCardView)
        val chipGroup: ChipGroup =findViewById(R.id.chipsCommand)

        val animationDrawable = weatherLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()

        if (ContextCompat.checkSelfPermission(this@ExploreActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) !==
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@ExploreActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@ExploreActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@ExploreActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }

        for (command in Commands) {
            val chip = Chip(this)
            chip.text = command.toString()
            chip.setPadding(25, 10, 25, 10)
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
            chipGroup.addView(chip)
        }

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Turn on \"Location\"")

            builder.setMessage("\"Location\" is currently turned off. Turn on \"Location\" to enable navigation function in GoogleAssistant")

            builder.setPositiveButton("OK") { _, _ ->

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

            builder.setNegativeButton("Cancel") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            val dialog: AlertDialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.darker_gray)
            dialog.show()
        }

        var city = "Mumbai"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    city = getCityFromCoordinates(latitude, longitude)
                    obtainLocation(city)
                } else {
                    Toast.makeText(this, "Unable to fetch location, using default", Toast.LENGTH_LONG).show()
                }
            }

        cardViewWeather.setOnClickListener {
            obtainLocation(city)
        }

        val c: Calendar = Calendar.getInstance()
        val currentDate = c.time

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val formattedDate = dateFormat.format(currentDate)
        val formattedTime = timeFormat.format(currentDate)

        today.text = "Today | Date: $formattedDate | Time: $formattedTime"

        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                greetings.text = "Good Morning !"
            }
            in 12..15 -> {
                greetings.text = "Good Afternoon !"
            }
            in 16..20 -> {
                greetings.text = "Good Evening !"
            }
            in 21..23 -> {
                greetings.text = "Good Night !"
            }
        }
    }

    private fun getCityFromCoordinates(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val city = addresses[0].locality
                    if (city != null) {
                        return city
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Mumbai"
    }

    private fun obtainLocation(city: String) {
        val queue = Volley.newRequestQueue(this)
        val requestUrl = "$baseUrl?q=$city&appid=$apiKey"
        val stringReq = StringRequest(Request.Method.GET, requestUrl,
            { response ->
                val obj = JSONObject(response)
                val arr = obj.getJSONArray("weather")
                val arr2 = obj.getJSONObject("main")
                val description = arr.getJSONObject(0).getString("description").capitalize()
                val temp = arr2.getDouble("temp") - 273.15
                val tempString = String.format("${temp.roundToInt()}°C")
                textView.text = "$tempString $city"
                wind.text = description
            },
            { Toast.makeText(this, "Couldn't find location", Toast.LENGTH_LONG).show() })
        queue.add(stringReq)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@ExploreActivity,
                                    Manifest.permission.ACCESS_FINE_LOCATION) ===
                                    PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
