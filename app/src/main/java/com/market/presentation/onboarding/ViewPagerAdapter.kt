package com.market.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.market.R
import com.market.data.models.get.OnBoardingGet
import org.w3c.dom.Text
import java.util.*

public class ViewPagerAdapter(
    private var context: Context,
    private var onBoardingGet: OnBoardingGet
) : PagerAdapter() {


    private var mLayoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        // return the number of images
        return onBoardingGet.data.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView =
            mLayoutInflater.inflate(R.layout.viewpage_imageslider_item, container, false)

        var imageView = itemView.findViewById<ImageView>(R.id.imageViewMain)

        var title = itemView.findViewById<TextView>(R.id.textView28)

        var content = itemView.findViewById<TextView>(R.id.textView27)


        content.text = onBoardingGet.data[position].content
        title.text = onBoardingGet.data[position].title
        Glide.with(context).load(onBoardingGet.data[position].image).into(imageView)



        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

}