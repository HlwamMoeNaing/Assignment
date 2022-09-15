package com.hmn.assignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.hmn.assignment.R
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.views.activity.DetailActivity
import com.squareup.picasso.Picasso

class BannerViewPagerAdapter(val context: Context, val list:List<AllMovieEntity>)
    : PagerAdapter()
{
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout = inflater.inflate(R.layout.banner_image_item, null)
        val slideImg = slideLayout.findViewById<AppCompatImageView>(R.id.image_banner)
        val url =  "https://image.tmdb.org/t/p/w500" + list[position].backdrop_path
        Picasso.get().load(url).into(slideImg)


        slideLayout.setOnClickListener {

            val i = Intent(context, DetailActivity::class.java)

            i.putExtra("id", list[position].mid)
            context.startActivity(i)

        }
        container.addView(slideLayout,0)
        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}