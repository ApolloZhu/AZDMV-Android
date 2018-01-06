package apollozhu.github.io.azdmv.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListView
import apollozhu.github.io.azdmv.R
import apollozhu.github.io.azdmv.model.Manual
import apollozhu.github.io.azdmv.model.SubSection
import com.pgyersdk.update.PgyUpdateManager

class MainActivity : AZBaseCompatActivity() {
    private lateinit var listView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PgyUpdateManager.setIsForced(true) //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this)

        setContentView(R.layout.activity_main)
        supportActionBar!!.setTitle(R.string.app_description)

        listView = findViewById(R.id.expandable_list)
        listView.setGroupIndicator(null)
        val adapter = MainAdapter(this)
        listView.setAdapter(adapter)
        (0 until adapter.groupCount).forEach { listView.expandGroup(it) }

        listView.setOnChildClickListener { _, _, section, subSection, _ ->
            SubSection.current = Manual.subSections[section][subSection]
            val intent = Intent(this, ManualActivity::class.java)
            startActivity(intent)
            /*return*/ true
        }
    }
}
