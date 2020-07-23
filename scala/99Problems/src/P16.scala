object P16 {
    def dropYield[A](count: Int, l: List[A]): List[A] = (for (i <- 1 to l.size if i % count != 0) yield l(i-1)).toList

    def dropZip[A](count: Int, l: List[A]): List[A] = l.zipWithIndex filter { x => (x._2 + 1) % count != 0 } map (_._1)
}