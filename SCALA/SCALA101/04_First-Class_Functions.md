# 일급 함수

함수형 프로그래밍의 핵심 가치 중 하나는 함수는 **일급(first-class)** 객체이어야 한다는 것이다. 이 용어는 함수가 선언되고 호출되는 것 외에 다른 데이터 타입처럼 언어의 모든 부분에 사용될 수 있다는 것을 의미한다. **일급 함수(first-class function)** 는 다른 데이터 타입과 마찬가지로 식별자(identifier)에 할당되지 않고도 리터럴 형태로 생성될 수 있으며, 값, 변수 또는 데이터 구조처럼 컨테이너에 저장될 수 있으며, 다른 함수에 매개변수로 사용되거나 다른 함수의 반환 값으로 사용될 수 있다.

다른 함수를 매개변수로 받아들이거나 반환값으로 함수를 사용하는 함수를 **고차 함수(higher-order function)** 이라 한다. 가장 유명한 고차 함수로 `map()`과 `reduce()`가 있다. `map()` 고차 함수는 함수 매개변수를 취하고 이를 사용하여 하나 또는 그 이상의 항목을 새로운 값이나 타입으로 전환한다. `reduce()` 고차 함수는 함수 매개변수를 취하고 이를 사용하여 여러 항목을 가지는 컬렉션을 단일 항목으로 축소한다.

**선언형 프로그래밍(declarative programming)** 은 고차 함수 또는 다른 메커니즘은 단순히 이루어져야 할 작업을 선언하는 데 사용되며 그 작업을 직접 수행하지는 않음을 의미한다. 이 방식의 반대로는 더 일상적인 **명령형 프로그래밍(imperative programming)** 방식이 있다.

스칼라는 일급 함수와 고차 함수 그리고 선언형 프로그래밍의 사용을 전적으로 지원한다. 함수도 자신의 입력 인수와 반환값의 타입에 기반을 둔 타입을 가진다. 함수는 값 또는 변수에 저장되고, 함수에 전달되며, 함수로부터 반환되고, 다른 많은 고차 함수 중 `map()`, `reduce()`, `fold()`, `filter()`를 지원하는 데이터 구조와 함께 사용될 수 있다.

## 함수 타입과 값
함수의 **타입(type)** 은 함수의 입력 타입과 반환값 타입의 단순한 그룹으로, 입력 타입으로부터 출력 타입으로의 방향을 나타내는 화살표로 배열한다.
```scala
([<type>, ...]) => <type>
```
```scala
scala> def double(x: Int): Int = x * 2
def double(x: Int): Int

scala> double(5)
val res0: Int = 10

scala> val myDouble: (Int) => Int = double
val myDouble: Int => Int = $Lambda$1046/1258539949@5c313224

scala> myDouble(5)
val res1: Int = 10

scala> val myDoubleCopy = myDouble
val myDoubleCopy: Int => Int = $Lambda$1046/1258539949@5c313224

scala> myDoubleCopy(5)
val res2: Int = 10
```
-> `myDouble`은 값이지만, 다른 값들과는 달리 호출될 수 있다.  
-> `myDouble`을 함수로 호출하는 것은 `double`을 호출하는 것과 같은 결과를 갖는다.  
-> 함수값을 새로운 값에 할당하는 것은 다른 값들과 마찬가지로 가능하다.  

`myDouble` 값의 명시적 타입은 이를 함수 호출이 아닌 함숫값으로 식별하는 데 필요하다. 함수로 할당된 함숫값을 정의하는 다른 방법법은 와일드카드 연산자 `_`를 사용하는 것이다.
```scala
val <identifier> = <function identifier> _
```
```scala
scala> def double(x: Int): Int = x * 2
def double(x: Int): Int

scala> val myDouble = double _
val myDouble: Int => Int = $Lambda$1049/1101786725@6fc6f68f

scala> val amount = myDouble(20)
val amount: Int = 40
```
이떄 `myDouble`의 명시적 함수 타입은 함수 호출과 구별하기 위해 필요하지 않다. `_`는 미래의 함수 호출에 대한 자리 표시자 역할을 하며, `myDouble`에 저장할 수 있는 함숫값을 반환한다.

다음은 괄호로 감싼 다중 매개변수를 사용하는 명시적 함수 타입으로 정의된 함숫값의 예를 보여준다.
```scala
scala> def max(a: Int, b: Int) = if (a > b) a else b
def max(a: Int, b: Int): Int

scala> val maximize: (Int, Int) => Int = max
val maximize: (Int, Int) => Int = $Lambda$1050/1595782032@78c5ef58

scala> maximize(50, 30)
val res3: Int = 50
```

다음은 입력값이 없는 함수 타입을 보여준다. 빈 괄호는 값이 없음을 나타내는 `Unit` 타입의 리터럴 표현이기도 하다.
```scala
scala> def logStart() = "=" * 50 + "\nStarting NOW\n" + "=" * 50
def logStart(): String

scala> val start: () => String = logStart
val start: () => String = $Lambda$1067/1518922937@5ace902e

scala> println( start() )
==================================================
Starting NOW
==================================================
```

## 고차 함수
**고차(higher-order) 함수** 는 입력 매개변수나 반환값으로 함수 타입의 값을 가지는 함수다.  
다음은 고차 함수를 사용하는 좋은 예로, `String`에서 동작하지만 입력 `String`이 `null`이 아닌 경우에만 동작하는 다른 함수를 호출한다. 
```scala
scala> def safeStringOp(s: String, f: String => String) = {
     |  if (s != null) f(s) else s
     | }
def safeStringOp(s: String, f: String => String): String

scala> def reverser(s: String) = s.reverse
def reverser(s: String): String

scala> safeStringOp(null, reverser)
val res5: String = null

scala> safeStringOp("Ready", reverser)
val res6: String = ydaeR
```

 ## 함수 리터럴
```scala
([<identifier>: <type>, ...]) => <expression>
```
```scala
scala> val doubler = (x: Int) => x * 2
val doubler: Int => Int = $Lambda$1077/1476855945@5c71329f

scala> val doubled = doubler(22)
val doubled: Int = 44
```
 위 예제는 실제 동작하지만 이름이 없는 함수인 **함수 리터럴(function literal)** 을 생성하고 새로운 함숫값에 이를 할당했다. 이 예제에서 함수 리터럴은 구문 `(x: Int) => x * 2`로, 이 구문은 타입을 가진 입력 인수(`x`)와 함수 본문(`x * 2`)를 정의한다. 함수 리터럴은 함숫값과 변수에 저장되거나 고차 함수 호출의 부분으로 정의될 수 있다. 우리는 함수 타입을 받는 모든 곳에 함수 리터럴을 표현할수 있다.

```scala
scala> val greeter = (name: String) => s"Hello, $name"
val greeter: String => String = $Lambda$1091/1067881973@d3767cf

scala> val hi = greeter("World")
val hi: String = Hello, World
```
함수 리터럴은 근본적으로 매개변수화된 표현식이다.

```scala
scala> def max(a: Int, b: Int) = if (a > b) a else b
def max(a: Int, b: Int): Int

scala> val maximize: (Int, Int) => Int = max
val maximize: (Int, Int) => Int = $Lambda$1092/813872234@4b2c1cbf

scala> val maximize = (a: Int, b: Int) => if (a > b) a else b
val maximize: (Int, Int) => Int = $Lambda$1093/813301911@4666f1b5

scala> maximize(84, 96)
val res7: Int = 96
```
```scala
scala> def logStart() = "=" * 50 + "\nStarting NOW\n" + "=" * 50
def logStart(): String

scala> val start = () => "=" * 50 + "\nStarting NOW\n" + "=" * 50
val start: () => String = $Lambda$1094/1994750424@2ffb6cf6

scala> println( start() )
==================================================
Starting NOW
==================================================
```

함수 리터럴은 고차 함수 호출 내부에서 정의될 수 있다.
```scala
scala> def safeStringOp(s: String, f: String => String) = {
     |  if (s != null) f(s) else s
     | }
def safeStringOp(s: String, f: String => String): String

scala> safeStringOp(null, (s: String) => s.reverse)
val res9: String = null

scala> safeStringOp("Ready", (s: String) => s.reverse)
val res10: String = ydaeR
```

`f`가 이미 타입을 정의하고 있기 때문에 함수 리터럴에서 명시적 타입을 제거할 수 있는데, 컴파일러가 그 타입을 쉽게 추론할 수 있기 때문이다. 또한, 명시적 타입을 제거한다는 것은 우리가 함수 리터럴에서 괄호를 제거할 수 있다는 것을 의미하는데, 타입이 지정되지 않은 단일 입력값이기 때문이다.
```scala
scala> safeStringOp(null, s => s.reverse)
val res11: String = null

scala> safeStringOp("Ready", s => s.reverse)
val res12: String = ydaeR
```
여기에서 명시적 타입과 괄호를 제거한 함수 리터럴에는 함수의 기본적인 본질만 남게 된다. 함수 리터럴은 입력 매개변수를 받아 그 매개변수로 연산을 수행한 결과값으로 반환한다.

## 자리표시자 구문
**자리표시자 구문(placeholder syntax)** 은 함수 리터럴의 축약형으로, 지정된 매개변수를 `y`를 카드 연산자 `_`로 대체한 형태를 가진다. 이 구문은 (a) 함수의 명시적 타입이 리터럴 외부에 지정되어 있고, (b) 매개변수가 한 번 이상 사용되지 않는 경우에 사용한다.
```scala
scala> val doubler: Int => Int = _ * 2
val doubler: Int => Int = $Lambda$1102/1403612295@61a309fe
```
```scala
scala> def safeStringOp(s: String, f: String => String) = {
     |  if (s != null) f(s) else s
     | }
def safeStringOp(s: String, f: String => String): String

scala> safeStringOp(null, _.reverse)
val res13: String = null

scala> safeStringOp("Ready", _.reverse)
val res14: String = ydaeR
```
```scala
scala> def combination(x: Int, y: Int, f: (Int, Int) => Int) = f(x, y)
def combination(x: Int, y: Int, f: (Int, Int) => Int): Int

scala> combination(23, 12, _ * _)
val res15: Int = 276
```
위 예제에 추가적인 자리표시자를 사용하면 에러가 나는데, 자리표시자의 개수는 입력 인수의 개수와 일치해야 하기 때문이다. 만일 하나 또는 세개의 자리표시자로 `reduce` 메소드를 호출한다면, 에러가 발생한다.

```scala
scala> def tripleOp(a: Int, b: Int, c: Int, f: (Int, Int, Int) => Int) = f(a, b, c)
def tripleOp(a: Int, b: Int, c: Int, f: (Int, Int, Int) => Int): Int

scala> tripleOp(23, 92, 14, _ * _ + _)
val res16: Int = 2130
```
이 예제 함수 `tripleOp`는 정수에 제한되어 있다. 이 함수를 두 개의 타입 매개변수로 재정의해보자.
```scala
scala> def tripleOp[A, B](a: A, b: A, c: A, f: (A, A, A) => B) = f(a, b, c)
def tripleOp[A, B](a: A, b: A, c: A, f: (A, A, A) => B): B

scala> tripleOp[Int, Int](23, 92, 14, _ * _ + _)
val res19: Int = 2130

scala> tripleOp[Int, Double](23, 92, 14, 1.0 * _ / _ / _)
val res20: Double = 0.017857142857142856

scala> tripleOp[Int, Boolean](93, 92, 14, _ > _ + _)
val res21: Boolean = false
```
자리표시자 구문은 특히 데이터 구조와 컬렉션으로 작업할 때 유용하다. 수많은 정렬, 필터링, 그 외 다른 데이터 구조 메소드는 일급 함수를 사용하는 경향이 있으며, 자리표시자 구문은 이 메소드들을 호출하는 데 필요한 부가적인 코드의 양을 줄여준다.

## 부분 적용 함수와 커링
함수를 호출하려면 전형적으로 호출문 내에 함수의 매개변수가 모두 지정되어 있어야 한다. 만약 일부 매개변수를 그대로 유지하여 이를 다시 타이핑하는 것을 피하고 싶다면 어떻게 될까?
```scala
scala> def factorOf(x: Int, y: Int) = y % x == 0
def factorOf(x: Int, y: Int): Boolean
```
어떤 매개변수도 유지하지 않고 함수를 호출하려면 와일드카드 연산자 `_`를 사용할 수 있다.
```scala
scala> val f = factorOf _
val f: (Int, Int) => Boolean = $Lambda$1132/1631953829@6aae5a28

scala> val x = f(7, 20)
val x: Boolean = false
```

만약 매개변수 중 일부를 유지하기를 원한다면, 매개변수 중 하나의 자리를 대신하는 와일드카드 연산자를 사용하여 그 함수를 부분적으로 적용할 수 있다. 여기에서의 와일드카드 연산자는 명시적인 타입을 필요로 하는데, 이 명시적 타입이 선언된 입력 타입으로 함숫값을 생성하는 데 사용되기 때문이다.
```scala
scala> val multipleOf3 = factorOf(3, _: Int)
val multipleOf3: Int => Boolean = $Lambda$1133/1469325187@7f42ffb7

scala> val y = multipleOf3(78)
val y: Boolean = true
```

**커링(currying)** 을 하면 하나의 매개변수 목록을 적용 매개변수와 미적용 매개변수로 나누는 대신, 한 목록의 매개변수를 적용하고 다른 목록은 적용하지 않게 된다.
```scala
scala> def factorOf(x: Int)(y: Int) = y % x == 0
def factorOf(x: Int)(y: Int): Boolean

scala> val isEven = factorOf(2) _
val isEven: Int => Boolean = $Lambda$1141/1696867122@5f827585

scala> val z = isEven(32)
val z: Boolean = true
```
함수 타입 관점에서 다중 매개변수 목록을 가지는 함수는 다중 함수의 체인으로 간주한다. 각 분리된 매개변수 목록은 별도의 함수 호출로 간주한다.

## 이름에 의한 호출 매개변수
함수 타입 매개변수의 다른 형태로, 값이나 결국에는 값을 반환하는 함수를 취할 수 있는 **이름에 의한(by-name)** 호출 매개변수가 있다. 값, 함수 둘 중 어떤 것으로도 호출할 수 있도록 지원함으로써, 이름에 의한 매개변수를 취하는 함수는 이 중 어느 것을 사용할지에 대한 결정을 함수 호출자의 몫으로 남긴다.
```scala
<identifier>: => <type>
```
이름에 의한 매개변수를 사용하게 되는 주요 이점은, 값 또는 함수 매개변수와는 반대로 유연성이 높아진다는 것이다. 이름에 의한 매개변수를 취하는 함수는 값을 사용할 수 있을 때, 그리고 값 대신 함수를 사용해야 할 때 이용될 수 있다. 다중 매개변수 접근이 함수 매개변수의 다중 호출을 의미하지만, 그 반대 또한 성립한다. 이름에 의한 매개변수에 전달된 함수는 그 매개변수에 접근하지 않으면 호출되지 않으며, 따라서 필요하다면 비용이 따르는 함수 호출을 피할 수 있다.
```scala
scala> def doubles(x: => Int) = {
     |  println("Now doubling " + x)
     |  x * 2
     | }
def doubles(x: => Int): Int

scala> doubles(5)
Now doubling 5
val res22: Int = 10

scala> def f(i: Int) = { println(s"Hellow from f($i)"); i}
def f(i: Int): Int

scala> doubles( f(8) )
Hellow from f(8)
Now doubling 8
Hellow from f(8)
val res23: Int = 16
```

## 부분 함수
자신의 입력 데이터 중 일부에만 적용할 수 있는 함수를 **부분 함수(partial function)** 이라고 부른다. 스칼라의 부분 함수는 일련의 `case` 패턴을 자신의 입력값에 적용하는 함수 리터럴로, 입력값이 주어진 패턴 중 최소 하나는 일치할 것을 요구한다. 이 `case`패턴 중 하나도 만족시키지 못하는 데이터로 부분 함수 중 하나를 호출하면 스칼라 에러가 일어난다.
```scala
scala> val statusHandler: Int => String = {
     |  case 200 => "Okay"
     |  case 400 => "Your Error"
     |  case 500 => "Our error"
     | }
val statusHandler: Int => String = $Lambda$1157/1132910047@111c3d38

scala> statusHandler(401)
scala.MatchError: 401 (of class java.lang.Integer)
  at $anonfun$statusHandler$1(<console>:1)
  at $anonfun$statusHandler$1$adapted(<console>:1)
  ... 32 elided
```
이러한 에러를 방지하는 한 가지 방법으로는 모든 다른 에러를 잡아내는 와일드카드 패턴을 마지막에 추가하는 것이지만, 그 경우엔 실제로 부분 함수라고 할 수 없을 것이다.  
부분 함수는 컬렉션과 패턴 매칭으로 작업할 때 더 유용하다.

## 함수 리터럴 블록으로 고차 함수 호출하기
```scala
scala> def safeStringOp(s: String, f: String => String) = {
     |  if (s != null) f(s) else s
     | }
def safeStringOp(s: String, f: String => String): String

scala> val uuid = java.util.UUID.randomUUID.toString
val uuid: String = 837f8668-6d5d-4aea-a65a-61e92eade953

scala> val timedUUID = safeStringOp(uuid, { s => 
     |  val now = System.currentTimeMillis
     |  val timed = s.take(24) + now
     |  timed.toUpperCase
     | })
val timedUUID: String = 837F8668-6D5D-4AEA-A65A-1594558033037
```
이 예제에서 여러 줄의 함수 리터럴이 값 매개변수와 함께 함수에 전달된다. 이 방식도 제대로 동작하지만, 이들을 같은 괄호 안에 포함시키는 것은 다루기 불편하다.  
우리는 `safeStringOp`의 매개변수를 두 개의 별도 그룹으로 구분함으로써 이를 개선할 수 있다. 이렇게 되면 함수 타입을 포함하는 두 번째 매개변수 그룹은 표현식 블록 구문으로 호출될 수 있다.
```scala
scala> def safeStringOp(s: String)(f: String => String) = {
     |  if (s != null) f(s) else s
     | }
def safeStringOp(s: String)(f: String => String): String

scala> val timedUUID = safeStringOp(uuid) { s =>
     |  val now = System.currentTimeMillis
     |  val timed = s.take(24) + now
     |  timed.toUpperCase
     | }
val timedUUID: String = 837F8668-6D5D-4AEA-A65A-1594558227189
```
이제 괄호로 값 매개변수를, 그리고 독립된 함수 리터럴 블록으로 함수 매개변수를 전달함으로써 `safeStringOp` 호출이 더 깔끔해졌다.
또 다른 얘제로, 이름에 의한 매개변수 하나를 취하는 함수가 있다. 이 함수를 이름에 의한 매개변수 반환 타입과 메인 함수의 반환 타입을 위해 사용될 타입 매개변수를 이용하여 더 일반적으로 만들 것이다.
```scala
scala> def timer[A](f: => A): A = {
     |  def now = System.currentTimeMillis
     |  val start = now; val a = f; val end = now
     |  println(s"Executed in ${end - start} ms")
     |  a
     | }
def timer[A](f: => A): A

scala> val veryRandomAmount = timer {
     |  util.Random.setSeed(System.currentTimeMillis)
     |  for (i <- 1 to 100000) util.Random.nextDouble
     |  util.Random.nextDouble
     | }
Executed in 12 ms
val veryRandomAmount: Double = 0.05738026315615774
```
여기에서 `timer` 함수는 개별 단위의 코드를 감싸기 위해 사용되지만, 또한 기존 코드 베이스에 통합될 수도 있따. 이 `timer` 함수로 함수의 마지막 부분을 감싸서 반드시 함수의 반환값이 코드 블록으로부터 `timer`를 통해 전달되고, 그 함수에 의해 반환되도록 하면 그 함수의 성능을 측정할 수 있다.  
이 방법으로 어떠한 코드 블록도 유틸리티로 감쌀 수 있는 함수는 고차 함수를 *표현식-블록* 형태로 호출하는 주요 이점이다. 이 호출 형태를 사용하는 다른 예에는 다음도 포함된다.
- 데이터베이스 트랜잭션 관리에서 고차 함수는 세션을 열고, 함수 매개변수를 호출하고, 커밋 또는 롤백하여 트랜잭션을 종료함
- 오류를 내지 않을 때까지 횟수만큼 함수 매개변수를 호출함으로써 재시도로 예상한 오류 처리하기
- 지역, 전역 또는 외부 값에 기반하여 조건부로 함수 매개변수 호출하기

