package apollozhu.github.io.azdmv.model

import android.content.Context
import apollozhu.github.io.azdmv.R
import com.pgyersdk.crash.PgyCrashManager
import org.json.JSONArray
import org.json.JSONObject

object Manual {
    val name = "Driver's Study Guide"

    private lateinit var manual: JSONArray
    private lateinit var manual_tboc: JSONObject
    private lateinit var context: () -> Context
    var needsContext = true
        private set

    fun setContext(ctx: () -> Context) {
        needsContext = false
        context = ctx
        manual = JSONArray(ctx().resources.openRawResource(R.raw.manual).bufferedReader().use { it.readText() })
        manual_tboc = JSONObject(ctx().resources.openRawResource(R.raw.manual_tboc).bufferedReader().use { it.readText() })
    }

    val sections: Array<Section> by lazy {
        val rawSections = manual_tboc.getJSONArray("sections")
        // For all sections
        val sections = arrayOfNulls<Section>(manual_tboc.getInt("totalSections") + 1)
        for (key in 0 until rawSections.length()) {
            val subJSON = rawSections.getJSONObject(key)
            try {
                val sectionID = subJSON.getInt("sectionID")
                sections[sectionID - 1] = Section(
                        subJSON.getString("symbol"),
                        subJSON.getString("sectionTitle"),
                        sectionID
                )
            } catch (_: Exception) {
                // We'll ignore the table of contents.
            }
        }
        /*return*/ sections.requireNoNulls()
    }

    val subsections: List<List<SubSection>> by lazy {
        val subSections = (1..sections.size).map { mutableListOf<SubSection>() }
        for (key in 0 until manual.length()) {
            val subJSON = manual.getJSONObject(key)
            val sectionID = subJSON.getInt("section")
            subSections[sectionID - 1].add(SubSection(this,
                    subJSON.getString("subSectionTitle"),
                    subJSON.getString("copy"),
                    sectionID,
                    subJSON.getInt("subSectionID"),
                    try {
                        subJSON.getString("update")
                    } catch (e: Exception) {
                        PgyCrashManager.reportCaughtException(context(), e)
                        ""
                    }
            ))
        }
        /*return*/ subSections.map { it.sortedBy { it.subSectionID } }
    }

    val noQuiz: List<String> by lazy {
        val rawArray = manual_tboc.getJSONArray("noQuiz")
        /*return*/ (0 until rawArray.length()).map { rawArray.getString(it) }
    }
}
