// object S99Int {
//     val primes = Stream.cons(2, Stream.from(3, 2) filter (_.isPrime))
// }

// class S99Int(val start: Int) {
//     def isPrime: Boolean = {
//         (start > 1) && (primes takeWhile (_ <= Math.sqrt(start)) forall (start % _ != 0))
//     }
// }

package arithmetic {
    class S99Int(val start: Int) {
        import S99Int._

        def isPrime: Boolean = {
            (start > 1) && (primes takeWhile (_ <= Math.sqrt(start)) forall (start % _ != 0))
        }
    }

    object S99Int {
        import scala.language.implicitConversions
        implicit def int2S99Int(i: Int): S99Int = new S99Int(i)
        val primes = LazyList.cons(2, LazyList.from(3, 2) filter (_.isPrime))
    }
}