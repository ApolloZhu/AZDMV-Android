package apollozhu.github.io.azdmv.Model

data class SubSection(val manual: Manual,
                      val title: String,
                      val content: String,
                      val sectionID: Int,
                      val subSectionID: Int,
                      val updateTime: String) {
    var hasQuiz = !manual.noQuiz.contains("$sectionID.$subSectionID")
}