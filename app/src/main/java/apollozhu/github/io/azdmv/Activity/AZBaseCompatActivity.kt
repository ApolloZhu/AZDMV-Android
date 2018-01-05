package apollozhu.github.io.azdmv.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import apollozhu.github.io.azdmv.model.Manual
import apollozhu.github.io.azdmv.model.QuizSet

open class AZBaseCompatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        assignContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignContext()
    }

    fun assignContext() {
        if (Manual.needsContext) Manual.setContext(this)
        if (QuizSet.needsContext) QuizSet.setContext(this)
    }
}
