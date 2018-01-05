package apollozhu.github.io.azdmv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ExpandableListView
import apollozhu.github.io.azdmv.Model.Manual

class MainActivity : AppCompatActivity() {
    lateinit var manual: Manual
    lateinit var listView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manual = Manual(this)
        listView = findViewById(R.id.list)
        listView.setAdapter(MainAdapter(this, manual))
        listView.setOnChildClickListener { _, _, section, subSection, _ ->
            // TODO: Perform Segue
            print("$section.$subSection")
            /*return*/ true
        }
    }
}
