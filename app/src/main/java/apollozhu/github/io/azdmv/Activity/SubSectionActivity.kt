package apollozhu.github.io.azdmv.Activity

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import apollozhu.github.io.azdmv.Model.Manual
import apollozhu.github.io.azdmv.Model.SubSection
import apollozhu.github.io.azdmv.R
import kotlinx.android.synthetic.main.activity_sub_section.*

// Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
// .setAction("Action", null).show()

class SubSectionActivity : AZBaseCompatActivity() {
    private var sectionID = -1
    private var subSectionID = -1
    private lateinit var subSection: SubSection
    lateinit var webview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_section)
        setSupportActionBar(toolbar)

        sectionID = intent.getIntExtra("sectionID", -1)
        subSectionID = intent.getIntExtra("subSectionID", -1)
        subSection = Manual.subsections[sectionID - 1][subSectionID - 1]

        webview = findViewById(R.id.webview)
        webview.loadDataWithBaseURL("", subSection.content,
                "text/html; charset=utf-8", "UTF-8", "")

        if (!subSection.hasQuiz) {
            fab.hide()
        } else {
            fab.setOnClickListener { _ ->
                val intent = Intent(this, QuestionListActivity::class.java)
                intent.putExtra("sectionID", sectionID)
                intent.putExtra("subSectionID", subSectionID)
                startActivity(intent)
            }
        }
    }
}
