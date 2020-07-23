object P24 {
    def lotto(count:Int, range: Int): List[Int] = (util.Random.shuffle(1 to range) take count).toList
}