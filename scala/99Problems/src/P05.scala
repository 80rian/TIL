object P05 {
    def reverseBuiltin[A](l: List[A]): List[A] = l.reverse

    def reverseLoop[A](l: List[A]): List[A] = {
        val buf = collection.mutable.Buffer[A]()
        for (i <- l) i +=: buf
        buf.toList
    }

    def reverseRecursive[A](l: List[A]): List[A] = {
        l match {
            case Nil => Nil
            case h :: t => reverseRecursive(t) :+ h
        }
    }

    def reverseFoldLeft[A](l: List[A]): List[A] = {
        l.foldLeft(List[A]()) { (a, b) => b :: a }
    }
}