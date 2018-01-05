package apollozhu.github.io.azdmv.Activity

import android.os.Bundle
import android.widget.ListView
import apollozhu.github.io.azdmv.R

class QuestionListActivity : AZBaseCompatActivity() {
    lateinit var questionList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        questionList = findViewById(R.id.question_list)
        questionList.adapter = QuestionListAdapter(this,
                intent.getIntExtra("sectionID", -1),
                intent.getIntExtra("subSectionID", -1))
    }
}
