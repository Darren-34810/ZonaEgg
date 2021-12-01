package id.ac.umn.zonaegg.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.umn.zonaegg.R
import id.ac.umn.zonaegg.data.Eatery
import id.ac.umn.zonaegg.databinding.FragmentHomeExploreBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeExploreFragment : Fragment() {

    private lateinit var bind: FragmentHomeExploreBinding
    private val eateryCategory = arrayOf("All","Canteen","Restaurant","Warteg")
    private val eateryDataRaw : ArrayList<Eatery> = arrayListOf(
        Eatery("Pondok", "Pondok", "Canteen", "4.8", 5f, R.drawable.sangkyuu_somuch_besto_frendo),
        Eatery("Pondok Lagi", "Pondok Lagi", "Warteg", "4.3", 4.5f, R.drawable.pepatah_kakek_kura_kura),
        Eatery("Restototo", "Restototo", "Restaurant", "4.9", 4.1f, R.drawable.swaq_face),
        Eatery("Lazada", "Lazada", "Canteen", "4.0", 5f, R.drawable.swaq_face)
    )
    private val cardAllAdapter: HomeExploreCardAdapter by lazy { HomeExploreCardAdapter(eateryDataRaw) }
    private val cardCanteenAdapter: HomeExploreCardAdapter by lazy {
        HomeExploreCardAdapter(eateryDataRaw.filter {
            it.category.lowercase(Locale.getDefault()) == "canteen"
        } as ArrayList<Eatery>)
    }
    private val cardRestaurantAdapter: HomeExploreCardAdapter by lazy {
        HomeExploreCardAdapter(eateryDataRaw.filter {
            it.category.lowercase(Locale.getDefault()) == "restaurant"
        } as ArrayList<Eatery>)
    }
    private val cardWartegAdapter: HomeExploreCardAdapter by lazy {
        HomeExploreCardAdapter(eateryDataRaw.filter {
            it.category.lowercase(Locale.getDefault()) == "warteg"
        } as ArrayList<Eatery>)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHomeExploreBinding.inflate(inflater, container, false)

        val exploreListener = object : HomeExploreListener {
            override fun onChangeNav(category: String) {
                Log.d("Explore", category)
                when (category.lowercase(Locale.getDefault())){
                    "all" -> bind.rvExploreCard.swapAdapter(cardAllAdapter, false)
                    "canteen" -> bind.rvExploreCard.swapAdapter(cardCanteenAdapter, false)
                    "restaurant" -> bind.rvExploreCard.swapAdapter(cardRestaurantAdapter, false)
                    "warteg" -> bind.rvExploreCard.swapAdapter(cardWartegAdapter, false)
                }
            }
        }

        val navAdapter = HomeExploreNavAdapter(eateryCategory, exploreListener)
        bind.rvExploreNav.adapter = navAdapter
        bind.rvExploreNav.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        bind.rvExploreCard.adapter = cardAllAdapter
        bind.rvExploreCard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return bind.root
    }
}

