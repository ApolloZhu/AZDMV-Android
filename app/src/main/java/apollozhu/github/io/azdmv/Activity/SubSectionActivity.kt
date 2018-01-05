package apollozhu.github.io.azdmv.Activity

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import apollozhu.github.io.azdmv.Model.SubSection
import apollozhu.github.io.azdmv.R
import kotlinx.android.synthetic.main.activity_sub_section.*

// Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
// .setAction("Action", null).show()

class SubSectionActivity : AZBaseCompatActivity() {
    private lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_section)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = SubSection.current!!.title

        webview = findViewById(R.id.webview)
        webview.loadDataWithBaseURL("", SubSection.current!!.content,
                "text/html; charset=utf-8", "UTF-8", "")
        if (SubSection.current!!.hasQuiz) {
            fab.setOnClickListener { _ ->
                val intent = Intent(this, QuestionListActivity::class.java)
                startActivity(intent)
            }
        } else {
            fab.hide()
        }
    }
}
