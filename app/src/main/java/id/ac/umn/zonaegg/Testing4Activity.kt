package id.ac.umn.zonaegg

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.ac.umn.zonaegg.data.Eatery
import id.ac.umn.zonaegg.data.EateryFireStore
import id.ac.umn.zonaegg.databinding.ActivityTesting4Binding
import id.ac.umn.zonaegg.home.HomeExploreCardAdapter
import id.ac.umn.zonaegg.home.HomeExploreListener
import java.util.*
import kotlin.collections.ArrayList


class Testing4Activity : AppCompatActivity() {

    private lateinit var bind: ActivityTesting4Binding
//    private lateinit var list: ArrayList<Eatery>
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTesting4Binding.inflate(layoutInflater)
        setContentView(bind.root)

        var list = ArrayList<Eatery>()
        var temp: Eatery

        db.collection("Kantin UMN")
            .get()
            .addOnSuccessListener { eateries ->
                for (eatery in eateries) {
                    var menus: List<Map<String, Objects>> = eatery.data["zmenu"] as List<Map<String, Objects>>
                    for (menu in menus) {
                        val menuData = menu.toString().replace("{", "").replace("}", "")
                        val serving = hashMapOf(
                            "name" to menuData.substringAfterLast("name="),
                            "price" to menuData.substringAfterLast("price=").substringBefore(","),
                            "photoUrl" to menuData.substringAfterLast("photoUrl=").substringBefore(",")
                        )
                        Log.d("testing4spec", "$serving")
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.d("testing4", "Error getting documents: ", exception)
            }

//        Log.d("testing4", "FINAL : ${list.last().name!!}")
//        Log.d("testing4", list[0].name.toString())

        val testingListener = object : HomeExploreListener {
            override fun onChangeNav(category: String) {
            }
            override fun goToDetailEatery(data: Eatery) {
            }
        }

        bind.testingRv.adapter = HomeExploreCardAdapter(list, testingListener)
        bind.testingRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onStart() {
        super.onStart()

    }

//    private fun EventChangeListener(){
//
//        db= FirebaseFirestore.getInstance()
//        db.collection("").
//                addSnapshotListener(object : EventListener<QuerySnapshot>{
//                    override fun onEvent(
//                        value: QuerySnapshot?,
//                        error: FirebaseFirestoreException?
//                    ){
//
//
//
//                    }
//                })
//    }
}
