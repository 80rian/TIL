object P23 {
    def randomSelectFor[A](count: Int, l: List[A]): List[A] = {
        val r = util.Random
        val buf = collection.mutable.Buffer[A]()
        val  lBuffer = l.toBuffer
        for (_ <- 1 to count) {
            val t = r.nextInt(lBuffer.size)
            buf += lBuffer(t)
            lBuffer -= lBuffer(t)
        }
        buf.toList
    }

    def randomSelectShuffle[A](count: Int, l: List[A]): List[A] = (util.Random.shuffle(0 to l.size - 1) take count map l).toList
}