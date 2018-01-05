package apollozhu.github.io.azdmv.model

import android.content.Context
import apollozhu.github.io.azdmv.R
import org.json.JSONArray
import org.json.JSONObject


object QuizSet {
    private lateinit var _quizzes: JSONArray
    var needsContext = true
        private set

    fun setContext(ctx: () -> Context) {
        needsContext = false
        _quizzes = JSONArray(ctx().resources.openRawResource(R.raw.manual_quiz).bufferedReader().use { it.readText() })
    }

    fun allQuizIDsIn(section: Int, subSection: Int): MutableList<Int> {
        val ids = mutableListOf<Int>()
        for (key in 0 until _quizzes.length()) {
            val question = _quizzes.getJSONObject(key)
            if (question.getInt("section") == section
                    && question.getInt("subsection") == subSection) {
                ids.add(question.getInt("questionID"))
            }
        }
        return ids
    }

    fun quiz(id: Int): Quiz? {
        val quiz = quizJSON(id) ?: return null
        val images = quiz.getJSONArray("images")
        val imageURL = if (images.length() == 0) null else images.getString(0)

        val answers = arrayOfNulls<QuizAnswer>(4)
        val answersObject = quiz.getJSONArray("answers")
        for (i in 0 until answersObject.length()) {
            val it = answersObject.getJSONObject(i)
            val text = it.getString("text").trim()
            if (text.isBlank()) continue
            val value = it.getInt("value")
            answers[value - 1] = QuizAnswer(value, text)
        }

        return Quiz(
                quiz.getString("question"),
                imageURL,
                quiz.getString("feedback"),
                quiz.getInt("correctAnswer"),
                answers.filterNotNull()
        )
    }

    fun quizJSON(id: Int): JSONObject? {
        for (key in 0 until _quizzes.length()) {
            val question = _quizzes.getJSONObject(key)
            if (question.getInt("questionID") == id) {
                return question
            }
        }
        return null
    }
}
