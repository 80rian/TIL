object P21 {
    def insertAtSplitAt[A](item: A, index: Int, l: List[A]): List[A] = l splitAt index match {
        case (pre, post) => pre ::: item :: post
    }
}