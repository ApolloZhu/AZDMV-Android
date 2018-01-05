package apollozhu.github.io.azdmv.Model

data class SubSection(val manual: Manual,
                      val title: String,
                      val content: String,
                      val sectionID: Int,
                      val subSectionID: Int,
                      val updateTime: String) {

    var hasQuiz = !manual.noQuiz.contains("$sectionID.$subSectionID")

    val quizIDs: List<Int> by lazy {
        QuizSet.allQuizIDsIn(sectionID, subSectionID).sorted()
    }

    val quizzes: List<Quiz> by lazy {
        quizIDs.map { QuizSet.quiz(it) }.requireNoNulls()
    }

    companion object {
        var current: SubSection? = null
    }
}
