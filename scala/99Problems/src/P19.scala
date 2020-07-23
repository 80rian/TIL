object P19 {
    def rotate[A](index: Int, l: List[A]): List[A] = {
        if (index >= 0) {
            val (take, rest) = l splitAt index
            rest ::: take
        } else {
            val (take, rest) = l splitAt (l.size + index)
            rest ::: take
        }
    }

    def rotateMatch[A](index: Int, l: List[A]): List[A] = index match {
        case n if n >= 0 => val t = l splitAt n; t._2 ::: t._1
        case n => val t = l splitAt (l.size + n); t._2 ::: t._1
    }
}