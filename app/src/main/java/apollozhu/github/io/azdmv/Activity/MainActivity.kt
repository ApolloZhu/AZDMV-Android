package apollozhu.github.io.azdmv.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListView
import apollozhu.github.io.azdmv.R

class MainActivity : AZBaseCompatActivity() {
    lateinit var listView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.expandable_list)
        listView.setAdapter(MainAdapter(this))
        listView.setOnChildClickListener { _, _, section, subSection, _ ->
            val intent = Intent(this, SubSectionActivity::class.java)
            intent.putExtra("sectionID", section + 1)
            intent.putExtra("subSectionID", subSection + 1)
            startActivity(intent)
            /*return*/ true
        }
    }
}
