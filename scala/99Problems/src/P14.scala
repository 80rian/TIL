object P14 {
    def duplicate[A](l: List[A]): List[A] = l flatMap (List.fill(2)(_))
}