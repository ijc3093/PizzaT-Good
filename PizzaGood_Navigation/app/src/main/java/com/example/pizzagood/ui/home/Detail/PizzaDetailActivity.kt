package com.example.pizzagood.ui.home.Detail

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzagood.R
import com.example.pizzagood.ui.home.Checkout.CheckoutActivity
import kotlinx.android.synthetic.main.activity_pizza_detail.*

class PizzaDetailActivity : AppCompatActivity() {

    //Define the Radio group 1 and 2
    lateinit var myRG1: RadioGroup
    lateinit var myRG2: RadioGroup
    lateinit var small: RadioButton
    lateinit var medium: RadioButton


    //Pizza crust and order option
    lateinit var large: RadioButton
    lateinit var restaurant: RadioButton
    lateinit var deliver: RadioButton
    lateinit var pickup: RadioButton

    // Pizza topping options
    lateinit var myCB1: CheckBox
    lateinit var myCB2: CheckBox
    lateinit var myCB3: CheckBox
    lateinit var myCB4: CheckBox

    // Pizza size
    lateinit var mySB: SeekBar

    // text view for price
    lateinit var tv: TextView
    lateinit var tv1: TextView

    // Variables used to calculate the total cost
    var perInchPrice = 0.05
    var toppingPrice = 0.05
    var progress = 0.0

    var button: Button? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        title = "Detail"

        button = findViewById(R.id.pay_now) as Button
        button!!.setOnClickListener { openNewActivity() }


        var bundle : Bundle? = intent.extras
        imageView2.setImageResource(bundle!!.getInt("image"))
        textView2.text = bundle!!.getString("name")
        textView3.text = bundle!!.getString("des")

        myRG1 = findViewById(R.id.radiogroup1)
        myRG2 = findViewById(R.id.radiogroup2)
        myCB1 = findViewById(R.id.checkBox)
        myCB2 = findViewById(R.id.checkBox2)
        myCB3 = findViewById(R.id.checkBox3)
        myCB4 = findViewById(R.id.checkBox4)
        mySB = findViewById(R.id.seekBar)
        tv = findViewById(R.id.textView5) // pizza size inch
        tv1 = findViewById(R.id.TotalPrice)



        // Setting Radio button group 1
        small = findViewById(R.id.small)
        medium = findViewById(R.id.medium)
        large = findViewById(R.id.large)

        // Setting Radio button group 2
        restaurant = findViewById(R.id.restaurant)
        pickup = findViewById(R.id.pickup)
        deliver = findViewById(R.id.deliver)

        // Setting Radio button group 2
        restaurant = findViewById(R.id.restaurant)
        pickup = findViewById(R.id.pickup)
        deliver = findViewById(R.id.deliver)


        // Radio Group of Crust
        // Radio Group of Crust
        myRG1.setOnCheckedChangeListener { group, checkedId ->
            val currentId = myRG1.checkedRadioButtonId
            val currentRB = findViewById<View>(currentId) as RadioButton
            Toast.makeText(applicationContext, currentRB.text, Toast.LENGTH_SHORT)
            calculatePrice(progress)
        }


        // Radio Group of Togo or no
        // Radio Group of Togo or no
        myRG2.setOnCheckedChangeListener { group, checkedId ->
            val currentId = myRG2.checkedRadioButtonId
            val currentRB = findViewById<View>(currentId) as RadioButton
            Toast.makeText(applicationContext, currentRB.text, Toast.LENGTH_SHORT)
            calculatePrice(progress)
        }


        myCB1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView === myCB1 && isChecked) Toast.makeText(
                applicationContext,
                myCB1.text,
                Toast.LENGTH_SHORT
            ).show()
            calculatePrice(progress)
        }


        myCB2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView === myCB2 && isChecked) Toast.makeText(
                applicationContext,
                myCB2.text,
                Toast.LENGTH_SHORT
            ).show()
            calculatePrice(progress)
        }

        myCB3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView === myCB3 && isChecked) Toast.makeText(
                applicationContext,
                myCB3.text,
                Toast.LENGTH_SHORT
            ).show()
            calculatePrice(progress)
        }

        myCB4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView === myCB4 && isChecked) Toast.makeText(
                applicationContext,
                myCB4.text,
                Toast.LENGTH_SHORT
            ).show()
            calculatePrice(progress)
        }

        mySB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tv.text = "$progress in"
                calculatePrice(progress.toDouble())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Toast.makeText(this@MainActivity, "started", Toast.LENGTH_SHORT)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculatePrice(size: Double) {
        progress = size.toDouble()
        var totalPrice = 0.0
        var totalToppingPrice = 0.0
        val decimalFormat = DecimalFormat(".##")
        if (myRG1.checkedRadioButtonId == small.id) {
            totalPrice = totalPrice + 9.50
        }else if (myRG1.checkedRadioButtonId == medium.id) {
            totalPrice = totalPrice + 10.50
        } else if (myRG1.checkedRadioButtonId == large.id) {
            totalPrice = totalPrice + 11.50
        }
        if (myRG2.checkedRadioButtonId == deliver.id) {
            totalPrice = totalPrice + 3.00
        }
        if (myCB1.isChecked) {
            totalToppingPrice += toppingPrice
        }
        if (myCB2.isChecked) {
            totalToppingPrice += toppingPrice
        }
        if (myCB3.isChecked) {
            totalToppingPrice += toppingPrice
        }
        if (myCB4.isChecked) {
            totalToppingPrice += toppingPrice
        }
        totalPrice += size * totalToppingPrice + size * perInchPrice
        tv1.text = "$" + decimalFormat.format(totalPrice)
    }

    //Click it to next the CheckoutActivity class
    @RequiresApi(Build.VERSION_CODES.N)
    fun openNewActivity() {

        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
        tv1
    }
}