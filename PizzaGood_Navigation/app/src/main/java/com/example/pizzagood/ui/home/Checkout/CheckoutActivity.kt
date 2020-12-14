package com.example.pizzagood.ui.home.Checkout


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Checked
import com.example.pizzagood.R
import com.example.pizzagood.ui.home.Finish
import com.mobsandgeeks.saripaar.annotation.NotEmpty


class CheckoutActivity : AppCompatActivity(), Validator.ValidationListener, View.OnClickListener {
   // @sun.security.util.Length(min = 3, max = 10)
    private var editText_FillName: EditText? = null

    @NotEmpty
    private var editText_EmailAddress: EditText? = null

//    @Min(1)
//    @Max(16)
    private var editText_CardName: EditText? = null


//    @Pattern(regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    private var editTex_ExpireDate: EditText? = null

//    @Min(1)
//    @Max(3)
    private var editText_CD: EditText? = null

//    @NotEmpty
    private var editText_Phone: EditText? = null


    private var editText_FillAddress: EditText? = null

    private var editText_ZipCode: EditText? = null

    @Checked
    private var checkBoxAgree: CheckBox? = null
    private var buttonSave: Button? = null
    private var buttonSubmit: Button? = null
    private var validator: Validator? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        title = "Checkout"
        initView()
        validator = Validator(this)
        validator!!.setValidationListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        editText_FillName = findViewById(R.id.editText_FillName)
        editText_EmailAddress = findViewById(R.id.editText_EmailAddress)
        editText_CardName = findViewById(R.id.editText_CardNumber)
        editTex_ExpireDate = findViewById(R.id.editText_ExpireDate)
        editText_CD = findViewById(R.id.editText_cd)
        editText_Phone = findViewById(R.id.editText_Phone)
        editText_FillAddress = findViewById(R.id.editText_FullAddress)
        editText_ZipCode = findViewById(R.id.editText_ZipCode)
        checkBoxAgree = findViewById(R.id.checkBoxAgree)

        buttonSubmit = findViewById(R.id.Submit)
        buttonSubmit!!.setOnClickListener{openNewActivity()}

        buttonSave = findViewById(R.id.buttonSave)
        buttonSave!!.setOnClickListener{validator?.validate()}
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun buttonSave_onClick(view: View) {
        validator?.validate()
        val fullname = editText_FillName!!.text.toString()
        if (fullname.equals("pmk", ignoreCase = true)) {
            editText_FillName!!.error = getText(R.string.name_already_exists)
        }
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "Successfull!", Toast.LENGTH_SHORT).show()
    }

    fun onValidationFail() {
        Toast.makeText(this, "Pls full out the form up over", Toast.LENGTH_SHORT).show()
    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view: View = error.view
            val message: String = error.getCollatedErrorMessage(this)
            // Display error messages
            if (view is EditText) {
                view.error = message
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun openNewActivity() {
        val intent = Intent(this, Finish::class.java)
        startActivity(intent)
    }

    override fun onClick(view: View) {
        if (view === buttonSave) {
            onValidationFail()
        }
        else if(view === buttonSubmit){
            onValidationFail()
        }
        else{
            onValidationSucceeded()
        }
    }
}