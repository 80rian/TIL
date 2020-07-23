object P18 {
    def sliceBuiltin[A](start: Int, end: Int, l: List[A]): List[A] = l slice (start, end)

    def sliceTakeDrop[A](start: Int, end: Int, l: List[A]): List[A] = l take end drop start

    def sliceLoop[A](start: Int, end: Int, l: List[A]): List[A] = (for (i <- start until end) yield l(i)).toList
}