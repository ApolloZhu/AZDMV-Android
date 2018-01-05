package apollozhu.github.io.azdmv.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListView
import apollozhu.github.io.azdmv.Model.Manual
import apollozhu.github.io.azdmv.Model.SubSection
import apollozhu.github.io.azdmv.R

class MainActivity : AZBaseCompatActivity() {
    lateinit var listView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.expandable_list)
        val adapter = MainAdapter(this)
        listView.setAdapter(adapter)
        (0 until adapter.groupCount).forEach { listView.expandGroup(it) }

        listView.setOnChildClickListener { _, _, section, subSection, _ ->
            SubSection.current = Manual.subsections[section][subSection]
            val intent = Intent(this, SubSectionActivity::class.java)
            startActivity(intent)
            /*return*/ true
        }
    }
}
