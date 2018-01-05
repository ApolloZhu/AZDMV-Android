package apollozhu.github.io.azdmv.model

data class Quiz(val question: String,
                val imageName: String?,
                val reason: String,
                val correctAnswerID: Int,
                val answers: List<QuizAnswer>) {
    val imageURL: String? = if (imageName == null) null else
        "https://dmvstore.blob.core.windows.net/manuals/images/1/$imageName"
    val normalizedCorrectAnswerID: Int = when (correctAnswerID) {
        in 1..4 -> correctAnswerID - 1
        5, 6 -> correctAnswerID - 5
        7 -> 3
        else -> throw Exception()
    }
}
