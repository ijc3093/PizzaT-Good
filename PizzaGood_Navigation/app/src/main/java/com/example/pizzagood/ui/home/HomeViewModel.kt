package com.example.pizzagood.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzagood.R
import com.example.pizzagood.ui.home.Detail.PizzaDetailActivity
import com.example.pizzagood.ui.home.Model.Food
import kotlinx.android.synthetic.main.mylayout.view.*

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}