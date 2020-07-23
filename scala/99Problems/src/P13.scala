object P13 {
    def encodeDirect(l: List[A]): List[(Int, A)] = {
        if (l.isEmpty) Nil
        else {
            val (packed, next) = l span { _ == l.head }
            (packed.length, packed.head) :: encodeDirect(next)
        }
    }

}