import P05.*

object P06 {
    def palindromeBuiltin[A](l: List[A]): Boolean = l == l.reverse

    def palindromeLoop[A](l: List[A]): Boolean = l == reverseLoop(l)

    def palindromeRecursive[A](l: List[A]): Boolean = l == reverseRecursive(l)

    def palindromeFoldLeft[A](l: List[A]): Boolean = l == reverseFoldLeft(l)
}