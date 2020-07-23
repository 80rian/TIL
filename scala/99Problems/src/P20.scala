object P20 {
    def removeAtTakeDrop[A](index: Int, l: List[A]): (List[A], A) = {
        if (index < l.size) (l.take(index) ::: l.drop(index + 1), l(index)) 
        else throw new NoSuchElementException
    }

    def removeAtFilterMap[A](index: Int, l: List[A]): (List[A], A) = {
        if (index < l.size) (l.zipWithIndex filterNot (_._2 == index) map (_._1), l(index)) 
        else throw new NoSuchElementException
    }

    def removeAtMatch[A](index: Int, l: List[A]): (List[A], A) = l splitAt index match {
        case (a, b) => (a ::: b.tail, b.head)
        case _ => throw new NoSuchElementException
    }
}