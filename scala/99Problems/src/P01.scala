object P01 {
    def lastBuiltin[A](l: List[A]): Option[A] = util.Try(l.last).toOption

    @annotation.tailrec
    def lastRecursive[A](l: List[A]): Option[A] = {

        l match {
            case head :: Nil => Some(head)
            case head :: tail => lastRecursive(tail)
            case _ => None
        }
    }
}
