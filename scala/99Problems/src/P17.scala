object P17 {
    def splitAt[A](count: Int, l: List[A]): (List[A], List[A]) = l splitAt count

    def splitTakeDrop[A](count: Int, l: List[A]): (List[A], List[A]) = (l.take(count), l.drop(count))
}