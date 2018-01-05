package apollozhu.github.io.azdmv.Model

import android.content.Context
import apollozhu.github.io.azdmv.R
import org.json.JSONArray
import org.json.JSONObject

data class Manual(val ctx: Context) {

    companion object {
        val name = "Driver's Study Guide"
    }

    private var _contents = JSONArray(ctx.resources.openRawResource(R.raw.manual).bufferedReader().use { it.readText() })
    private var _tboc = JSONObject(ctx.resources.openRawResource(R.raw.manual_tboc).bufferedReader().use { it.readText() })

    val sections: Array<Section>
        get() {
            val rawSections = _tboc.getJSONArray("sections")
            // For all sections
            var sections = arrayOfNulls<Section>(_tboc.getInt("totalSections") + 1)
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
            return sections.requireNoNulls()
        }

    val subsections: List<List<SubSection>>
        get() {
            var subSections = (1..sections.size).map { mutableListOf<SubSection>() }
            for (key in 0 until _contents.length()) {
                val subJSON = _contents.getJSONObject(key)
                val sectionID = subJSON.getInt("section")
                subSections[sectionID - 1].add(SubSection(this,
                        subJSON.getString("subSectionTitle"),
                        subJSON.getString("copy"),
                        sectionID,
                        subJSON.getInt("subSectionID"),
                        try {
                            subJSON.getString("update")
                        } catch (_: Exception) {
                            ""
                        }
                ))
            }
            return subSections.map { it.sortedBy { it.subSectionID } }
        }

    internal val noQuiz: List<String>
        get() {
            val rawArray = _tboc.getJSONArray("noQuiz")
            return (0 until rawArray.length()).map { rawArray.getString(it) }
        }
}