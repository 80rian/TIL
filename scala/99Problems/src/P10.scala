import P09.pack

object P10 {
    def encode[A](l: List[A]): List[(Int, A)] = {
        pack(l) map { a => (a.size, a.distinct(0)) }
    }
}