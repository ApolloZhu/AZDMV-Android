package apollozhu.github.io.azdmv.Model

import java.net.URL

data class Quiz(val question: String,
                val imageName: String?,
                val reason: String,
                val correctAnswerID: Int,
                val answers: List<QuizAnswer>) {
    val imageURL: URL? = if (imageName == null) null else
        URL("https://dmvstore.blob.core.windows.net/manuals/images/1/$imageName")
}