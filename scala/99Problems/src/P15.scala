object P15 {
    def duplicate[A](count: Int, l: List[A]): List[A] = l flatMap (List.fill(count)(_))
}