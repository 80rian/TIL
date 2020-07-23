object P03 {
    def nthBuiltin[A](l: List[A], n: Int): Option[A] = util.Try(l(n)).toOption

    def nthTake[A](l: List[A], n: Int): Option[A] = {
        if (n > l.size) None
        else util.Try(l.take(n + 1).last).toOption
    }

    @annotation.tailrec
    def nthRecursive[A](l: List[A], n: Int): Option[A] = {
        (l, n) match {
            case (head :: _ , 0) => Some(head)
            case (_ :: tail, n) => nthRecursive(tail, n - 1)
            case _ => None
        }
    }
}