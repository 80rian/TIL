object P12 {
    def decodeLoop[A](l: List[(Int, A)]): List[A] = {
        l flatMap { t => val buf = collection.mutable.Buffer[A](); for (i <- 1 to t._1) buf += t._2; buf.toList }
    }

    def decodeFill[A](l: List[(Int, A)]): List[A] = {
        l flatMap { t => List.fill(t._1)(t._2) }
    }
}