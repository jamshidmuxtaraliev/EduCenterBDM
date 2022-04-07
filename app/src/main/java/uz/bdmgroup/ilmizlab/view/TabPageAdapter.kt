package uz.bdmgroup.ilmizlab.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.screens.tabLayoutFragments.RatingViewFragment
import uz.bdmgroup.ilmizlab.screens.tabLayoutFragments.CoursesFragment
import uz.bdmgroup.ilmizlab.screens.tabLayoutFragments.NewsByIdFragment

class TabPageAdapter(fm: FragmentManager, var item: EduCenterModel) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Kurslar"
            }
            1 -> {
                return "Yangiliklar"
            }
            2 -> {
                return "Baholar"
            }
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return CoursesFragment(item)
            }
            1 -> {
                return NewsByIdFragment(item)
            }
            2 -> {
                return RatingViewFragment(item)
            }
            else -> {
                return CoursesFragment(item)
            }
        }
    }
}