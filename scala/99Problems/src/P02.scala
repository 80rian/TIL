object P02 {
    def penultimateBuiltin[A](l: List[A]): Option[A] = util.Try(l.init.last).toOption

    @annotation.tailrec
    def penultimateRecursive[A](l: List[A]): Option[A] = {
        l match {
            case penultimate :: last :: Nil => Some(penultimate)
            case head :: tail => penultimateRecursive(tail)
            case _ => None
        }
    }

    def penultimateIndex[A](l: List[A]): Option[A] = {
        if (l.size < 2) None
        else Some(l(l.size - 2))
    }
}