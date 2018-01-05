package apollozhu.github.io.azdmv.activity

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import apollozhu.github.io.azdmv.R
import apollozhu.github.io.azdmv.model.SubSection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz.view.*

class QuizActivity : AZBaseCompatActivity() {
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
        setContentView(R.layout.activity_quiz)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        container.currentItem = intent.getIntExtra("index", 0)

        fab.setOnClickListener { _ -> onBackPressed() }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            supportActionBar!!.title = "#${SubSection.current!!.quizIDs[position]}"
            return PlaceholderFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return SubSection.current!!.quizIDs.size
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        private lateinit var radioButtons: Array<RadioButton>
        private lateinit var radioGroup: RadioGroup

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_quiz, container, false)
            val quiz = SubSection.current!!.quizzes[arguments.getInt(INDEX)]
            if (quiz.imageURL == null) {
                rootView.quiz_image.visibility = View.GONE
            } else {
                Picasso.with(context).load(quiz.imageURL).into(rootView.quiz_image)
            }
            radioGroup = rootView.quiz_choices
            rootView.quiz_description.text = quiz.question
            radioButtons = arrayOf(rootView.radioButton1, rootView.radioButton2, rootView.radioButton3, rootView.radioButton4)
            for (i in 0 until quiz.answers.size) {
                val button = radioButtons[i]
                button.text = quiz.answers[i].text
                button.setOnClickListener { _ ->
                    if (quiz.normalizedCorrectAnswerID == i) {
                        button.setBackgroundResource(R.color.positive)
                        Snackbar.make(rootView, "Correct!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                        radioButtons.forEach { it.isEnabled = false }
                    } else {
                        val builder = AlertDialog.Builder(context)
                        builder.setMessage(quiz.reason)
                        builder.create().show()
                        button.setBackgroundResource(R.color.negative)
                    }
                    button.setTextColor(Color.WHITE)
                    button.isEnabled = false
                }
            }
            for (i in quiz.answers.size until radioButtons.size) {
                radioButtons[i].visibility = View.GONE
            }
            return rootView
        }

        override fun onResume() {
            super.onResume()
            radioGroup.clearCheck()
            val quiz = SubSection.current!!.quizzes[arguments.getInt(INDEX)]
            for (i in 0 until quiz.answers.size) {
                radioButtons[i].isEnabled = true
                radioButtons[i].setBackgroundColor(Color.TRANSPARENT)
                radioButtons[i].setTextColor(Color.BLACK)
            }
        }

        companion object {
            /**
             * The fragment argument representing its index.
             */
            private val INDEX = "index"

            /**
             * Returns a new instance of this fragment for the given index.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(INDEX, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
