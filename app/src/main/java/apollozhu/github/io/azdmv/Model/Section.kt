package apollozhu.github.io.azdmv.model

data class Section(val symbol: String,
                   val sectionTitle: String,
                   val sectionID: Int) {
    val icon = symbols[symbol]

    companion object {
        val symbols = mapOf(
                "edit" to "\uf044",
                "signIcon" to "\ue606",
                "steeringWheel" to "\ue603",
                "seatBelt" to "\ue607",
                "exclamation-circle" to "\uf06a",
                "license" to "\ue611",
                "info" to "\uf129"
        )
    }
}
