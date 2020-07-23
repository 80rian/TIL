object P08 {
    def compressBuiltin[A](l: List[A]): List[A] = l.distinct

    def compressSet[A](l: List[A]): List[A] = {
        val mutSet = collection.mutable.Set[A]()
        for (i <- l) mutSet.add(i)
        mutSet.toList
    }

    def compressRecursive[A](l: List[A]): List[A] = l match {
        case Nil => Nil
        case h :: tail => compressBuiltin(tail.dropWhile(_ == h))
    }
}