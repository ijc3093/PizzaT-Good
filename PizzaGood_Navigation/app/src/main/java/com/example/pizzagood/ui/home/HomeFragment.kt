package com.example.pizzagood.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pizzagood.R
import com.example.pizzagood.ui.home.Model.Food
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import com.example.pizzagood.MainActivity
import kotlinx.android.synthetic.main.mylayout.view.*
import com.example.pizzagood.ui.home.Detail.PizzaDetailActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var adapter : FoodAdappter? = null
    val foodlist = ArrayList<Food>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
            GridView()
        })
        return root
    }

    private fun GridView(){
        foodlist.add(Food(
            name = "Detroit Style",
            des = "Detroit-style pizza is a rectangular " +
                    "pizza with a thick crust that is crispy and chewy. It is traditionally " +
                    "topped with Wisconsin brick cheese, then tomato sauce layered on top " +
                    "of the other toppings.",
            image = R.drawable.detroit))


        foodlist.add(Food(
            name = "Greek Style",
            des = "In the cuisine of the United States, Greek pizza is a style of pizza crust and preparation where the pizza is proofed and cooked in a metal pan rather stretched to order and baked on the floor of the pizza oven.",
            image = R.drawable.greek))

        foodlist.add(Food(
            name = "Louis Style",
            des = "St. Louis-style pizza is a distinct type of pizza popular in the Midwestern city of St. Louis, Missouri and surrounding areas.",
            image = R.drawable.louis))


        foodlist.add(Food(
            name = "Margherita",
            des = "Pizza Margherita is a typical Neapolitan pizza, made with San Marzano tomatoes, mozzarella cheese, fresh basil, salt and extra-virgin olive oil.",
            image = R.drawable.margherita))

        foodlist.add(Food(
            name = "Neapolitan",
            des = "Neapolitan pizza also known as Naples-style pizza, is a style of pizza made with tomatoes and mozzarella cheese.",
            image = R.drawable.neapolitan))

        adapter = FoodAdappter(activity!!, foodlist)
        grid_view.adapter = adapter
    }




    class FoodAdappter : BaseAdapter {

        var foodlist = ArrayList<Food>()
        var context: Context? = null

        constructor(context: Context?, foodlist: ArrayList<Food>) : super() {
            this.foodlist = foodlist
            this.context = context
        }


        override fun getCount(): Int {
            return foodlist.size
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
            lateinit var network : MainActivity

            var food = this.foodlist[index]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            var foodView = inflater.inflate(R.layout.mylayout, null)


            foodView.imageView.setImageResource(food.image!!)
            foodView.textView.text = food.name!!
//            foodView.price_Text.text = food.price.toString()!!

            //Detail
                foodView.imageView.setOnClickListener{
                        var intent = Intent(context, PizzaDetailActivity::class.java)

                        intent.putExtra("name", food.name!!)
                        //intent.putExtra("price", food.price.toString()!!)
                        intent.putExtra("des", food.des!!)
                        intent.putExtra("image", food.image!!)

                   // if (network.isNetworkConnected()) {
                        context!!.startActivity(intent)
//                    }else{
//                        AlertDialog.Builder(network)
//                            .setTitle("No Internet Connection")
//                            .setMessage("Please click your internet and try again")
//                            .setPositiveButton(android.R.string.ok){_,_ ->}
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show()
//                    }
                }
            return foodView

        }

    }
}