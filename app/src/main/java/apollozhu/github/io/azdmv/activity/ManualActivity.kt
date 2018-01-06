package apollozhu.github.io.azdmv.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apollozhu.github.io.azdmv.R
import apollozhu.github.io.azdmv.model.Manual
import apollozhu.github.io.azdmv.model.SubSection
import kotlinx.android.synthetic.main.activity_manual.*
import kotlinx.android.synthetic.main.fragment_manual.view.*

class ManualActivity : AZBaseCompatActivity() {
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        val curIndex = Manual.flattendSubSections.indexOf(SubSection.current!!)
        container.currentItem = curIndex
        didSelectIndex(curIndex)

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                didSelectIndex(position)
            }
        })
    }

    private fun didSelectIndex(index: Int) {
        val subSection = Manual.flattendSubSections[index]
        SubSection.current = subSection
        supportActionBar!!.title = subSection.title
        if (subSection.hasQuiz) {
            fab.show()
            fab.setOnClickListener { _ ->
                val intent = Intent(this@ManualActivity, QuestionListActivity::class.java)
                startActivity(intent)
            }
        } else {
            fab.hide()
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return Manual.subsectionsCount
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        var i = -1

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_manual, container, false)
            rootView.webview.loadDataWithBaseURL("", Manual.flattendSubSections[i].content,
                    "text/html; charset=utf-8", "UTF-8", "")
            return rootView
        }

        companion object {
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(index: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                fragment.i = index
                return fragment
            }
        }
    }
}
