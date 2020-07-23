import P09.pack

object P11 {
    def encodeModified[A](l: List[A]): List[Any] = {
        pack(l) map { a => if (a.size > 1) (a.size, a.distinct(0)) else a.head }
    }
}