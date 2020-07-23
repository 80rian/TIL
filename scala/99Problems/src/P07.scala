object P07 {
    def flatten(l: List[Any]): List[Any] = l flatMap {
        case ls: List[_] => flatten(ls)
        case s => List(s)
    }
}