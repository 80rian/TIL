object P04 {
    def lengthBuiltin[A](l: List[A]): Int = l.size

    def lengthLoop[A](l: List[A]): Int = {
        var count = 0
        for (i <- l) count += 1
        count
    }

    def lengthRecursive[A](l: List[A]): Int = {
        @annotation.tailrec
        def lengthSub[A](ls: List[A], count: Int = 0): Int = {
            ls match {
                case Nil => count
                case _ :: t => lengthSub(t, count + 1)
            }
        }
        lengthSub(l)
    }
}