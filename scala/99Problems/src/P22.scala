object P22 {
    def rangeLoop(start: Int, end: Int): List[Int] = (for (i <- start to end) yield i).toList

    def rangeRecursive(start: Int, end: Int): List[Int] = (start, end) match {
        case (s, e) if s == e => e :: Nil
        case (s, e) => s :: rangeRecursive(s+1, e)
    }
}