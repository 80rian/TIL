# 그 외의 컬렉션

## 가변적인 컬렉션
`List`, `Set`, `Map`은 생성된 후 변경할 수 없다. 하지만 이들은 새로운 컬렉션으로 변형될 수 있다.
```scala
scala> val m = Map("AAPL" -> 597, "MSFT" -> 40)
val m: scala.collection.immutable.Map[String,Int] = Map(AAPL -> 597, MSFT -> 40)

scala> val n = m - "AAPL" + ("GOOG" -> 521)
val n: scala.collection.immutable.Map[String,Int] = Map(MSFT -> 40, GOOG -> 521)

scala> println(m)
Map(AAPL -> 597, MSFT -> 40)

scala> println(n)
Map(MSFT -> 40, GOOG -> 521)
```
위의 예제에서는 `m`을 생성한 뒤 `m`을 변경해 `n`에 저장하였다. 이것은 정확하게 불변의 데이터가 의미하는 바로, 코드의 안정성을 높이고 버그를 방지하기 위해 데이터와 데이터 구조는 변경되거나 자신의 상태를 바꿀 수 없다.  
그러나 가변적인 데이터가 필요하거나 더 안전한 때가 있다. 예를 들어, 함수 내에서만 사용하는 가변적인 데이터 구조 또는 반환하기 전에 불변의 데이터로 바꿀 가변 데이터를 생성하는 것이 더 안전할 수 있다.

### 새로운 가변 컬렉션 생성하기
불변의 타입 | 가변적인 대응 타입
:---: | :---:
`collection.immutable.List` | `collection.mutable.Buffer`
`collection.immutable.Set` | `collection.mutable.Set`
`collection.immutable.Map` | `collection.mutable.Map`

`collection.immutable` 패키지는 현재 네임스페이스에 자동으로 추가되지만 `collection.mutable`은 그렇지 않기 때문에 가변의 컬렉션을 생성할 때 그 타입의 전체 패키지 이름을 포함하였는지 반드시 확인해야 한다.

`collection.mutable.Buffer`타입은 범용적인 가변의 시퀀스이며, 그 시작과 중간, 끝에 요소들을 추가할 수 있다.
```scala
scala> val nums = collection.mutable.Buffer(1)
val nums: scala.collection.mutable.Buffer[Int] = ArrayBuffer(1)

scala> for (i <- 2 to 10) nums += i

scala> println(nums)
ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

`Map`과 `Set`도 유사한 과정으로 만들 수 있다. 새로운 집합의 타입 매개변수나 새로운 맵의 키와 값의 타입 매개변수를 지정하는 것은 빈 컬렉션을 생성할 때에만 필요하다.  
가변적인 버퍼는 `toList` 메소드를 이용하여 어느 때라도 다시 불변의 리스트로 변환할 수 있다.
```scala
scala> println(nums)
ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

scala> val l = nums.toList
val l: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```
유사하게, `Map`은 `toMap`, `Set`은 `toSet` 메소드를 이용하여 불변의 컬렉션으로 변환할 수 있다.

## 불변의 컬렉션으로부터 가변적인 컬렉션 만들기
`List`, `Map`, `Set` 모두 `toBuffer` 메소드를 이용하여 가변적인 타입인 `collection.mutable.Buffer` 타입으로 변환할 수 있다.
```scala
scala> val m = Map("AAPL" -> 597, "MSFT" -> 40)
val m: scala.collection.immutable.Map[String,Int] = Map(AAPL -> 597, MSFT -> 40)

scala> val b = m.toBuffer
val b: scala.collection.mutable.Buffer[(String, Int)] = ArrayBuffer((AAPL,597), (MSFT,40))

// trimStart는 버퍼의 시작점부터 하나 또는 그 이상의 항목들을 제거한다.
scala> b trimStart 1

scala> b += ("GOOG" -> 521)
val res6: b.type = ArrayBuffer((MSFT,40), (GOOG,521))

scala> val n = b.toMap
val n: scala.collection.immutable.Map[String,Int] = Map(MSFT -> 40, GOOG -> 521)

scala> val l = b.toList
val l: List[(String, Int)] = List((MSFT,40), (GOOG,521))

scala> val s = b.toSet
val s: scala.collection.immutable.Set[(String, Int)] = Set((MSFT,40), (GOOG,521))
```
`Buffer` 타입은 범용적으로 사용되는 좋은 가변적인 컬렉션으로 `List`와 유사하지만, 그 내용을 추가하고, 삭제하고, 교체할 수 있다. `Buffer` 타입이 지원하는 전환 메소드는 자신에 대응하는 불변의 타입에 적용하는 `toBuffer` 메소드와 함께 가변적인 데이터로 작업하기에 유용한 메커니즘으로 만들어준다.  
`Buffer`의 유일한 단점은 너무 광범위하게 적용될 수 있다는 것이다. 만약 루프 내에서 단지 컬렉션을 반복적으로 추가하는 것이라면 `Buffer` 대신 `Builder`를 사용하는 것이 더 낫다.

### 컬렉션 빌더 사용하기
`Builder`는 `Buffer`를 단순화한 형태로, 할당된 컬렉션 타입을 생성하고, 추가(append) 연산만을 지원하도록 제한되어 있다.  
특정 컬렉션 타입의 빌더를 생성하려면 해당 타입의 `newBuilder` 메소드를 호출하고, 해당 타입의 컬렉션 구성 요소를 포함하면 된다. 빌더의 `result` 메소드를 호출하면 이를 최종적으로 `Set`로 변환해준다.
```scala
scala> val b = Set.newBuilder[Char]
val b: scala.collection.mutable.Builder[Char,scala.collection.immutable.Set[Char]] = scala.collection.immutable.SetBuilderImpl@2d839402

// 단일 항목 추가
scala> b += 'h'
val res7: b.type = scala.collection.immutable.SetBuilderImpl@2d839402

// 복수 항목 추가
scala> b ++= List('e', 'l', 'l', 'o')
val res8: b.type = scala.collection.immutable.SetBuilderImpl@2d839402

scala> val helloSet = b.result
val helloSet: scala.collection.immutable.Set[Char] = Set(h, e, l, o)
```

## 배열
`Array`는 고정된 크기를 가지며, 내용을 변경할 수 있고, 인덱스를 가지고 있는 컬렉션이다. `Array` 타입은 실제로 자바의 배열 타입을 **묵시적 클래스(implicit class)** 라고 부르는 고급 특징으로 감싼 wrapper다. 묵시적 클래스는 배열을 시퀀스처럼 사용할 수 있도록 해준다.
```scala
scala> val colors = Array("red", "green", "blue")
val colors: Array[String] = Array(red, green, blue)

scala> colors(0) = "purple"

scala> colors
val res10: Array[String] = Array(purple, green, blue)

// toString() 메소드만 호출할 수 있는 println()은 Array를 핸들링하지 못한다.
scala> println("very purple: " + colors)
very purple: [Ljava.lang.String;@766c225c

scala> val files = new java.io.File(".").listFiles
val files: Array[java.io.File] = Array(./ch07, ./ch06, ./ch01, ./ch04, ./ch03, ./ch02, ./ch05)

scala> val scala = files map (_.getName) filter(_ endsWith "scala")
val scala: Array[String] = Array()
```
JVM 코드를 위해 필요한 경우가 아니라면 `Array` 사용을 추천하지 않는다.

## Seq와 시퀀스
`Seq`는 모든 시퀀스의 루트 타입으로, `List` 같은 연결 리스트와 `Vector` 같은 색인 리스트를 포함한다. 루트 타입으로서 `Seq`는 인스턴스화 될 수 없지만 `List`를 생성하는 빠른 방법으로 `Seq`를 호출할 수 있다.
```scala
scala> val inks = Seq('C', 'M', 'Y', 'K')
val inks: Seq[Char] = List(C, M, Y, K)
```

![sequence hierarchy](https://alvinalexander.com/sites/default/files/3-scala-sequences.png)

이름 | 설명
:---: | :---
`Seq` | 모든 시퀀스의 루트, List()의 간단한 방법
`IndexedSeq` | 색인 시퀀스의 루트, Vector()의 손쉬운 방법
`Vector` | 색인된 접근을 위해 Array 인스턴스에 의해 지원받는 리스트
`Range` | 정수의 범위. 데이터를 즉시 생성함
`LineaerSeq` | 선형(연결 리스트) 시퀀스의 루트
`List` | 구성 요소들의 단방향 연결 리스트
`Queue` | 선입선출(FIFO, First-In-First-Out) 리스트
`Stack` | 후입선출(LIFO, Last-In-First-Out) 리스트
`Stream` | 지연(lazy) 리스트. 항목들은 그 항목에 접근할 때 추가됨
`String` | 문자(character)의 컬렉션

## LazyList
`LazyList` 타입은 하나 또는 그 이상의 시작 요소들과 재귀 함수로 생성되는 **지연(lazy)** 컬렉션이다. 다른 불변의 컬렉션들은 그 내용의 100%를 초기화 시점에 받지만, 스트림의 구성 요소들은 최초로 접근될 때 컬렉션에 추가된다. 스크림이 생성한 구성 요소들은 나중에 추출될 때를 대비하여 캐시에 저장되어 각 요소가 한 번만 생성됨을 보장한다.  
`LazyList`은 `List`와 마찬가지로 헤드와 테일로 구성된 재귀적인 데이터 구조다. `LazyList.cons`를 사용하여 헤드와 테일로 새로운 스트림을 구성할 수 있다.  
`Stream`은 스칼라 2.13.0 부터 deprecated 되었고 `LazyList`를 대신 사용한다.
```scala
scala> def inc(i: Int): LazyList[Int] = LazyList.cons(i, inc(i+1))
def inc(i: Int): LazyList[Int]

scala> val s = inc(1)
val s: LazyList[Int] = LazyList(<not computed>)

scala> val l = s.take(5).toList
val l: List[Int] = List(1, 2, 3, 4, 5)

scala> s
val res14: LazyList[Int] = LazyList(1, 2, 3, 4, 5, <not computed>)
```
원래의 `Lazy List` 인스턴스를 출력해보면, 다섯 개의 구성 요소를 포함하고 있으며 더 많은 요소를 추가 생성할 준비가 되어 있음을 보여준다. 우리는 계속해서 20, 200, 2000개의 구성 요소들을 취하여 이 스트림에 덧붙일 수 있다. `LazyList`는 재귀 함수 호출을 포함하고 있어서 이를 이용하여 끝없이 새로운 요소들을 생성할 수 있다.

`LazyList.cons` 연산자 대신 사용할 수 있는 구문으로는 `#::` 연산자가 있다.이 연산자는 오른쪽-결합형 표기법이며, 리슽트의 생성 연산자 `::`를 보완한다.
```scala
scala> def inc(head: Int): LazyList[Int] = head #:: inc(head+1)
def inc(head: Int): LazyList[Int]

scala> inc(10).take(10).toList
val res15: List[Int] = List(10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
```

Bounded LazyList를 만들어보자. 재귀 함수는 두 인수를 받는데, 하나는 새로운 헤드 요소를 지정하며 다른 하나는 추가도리 마지막 요소를 지정한다.
```scala
scala> def to(head: Char, end: Char): LazyList[Char] = (head > end) match {
     |  case true => LazyList.empty
     |  case false => head #:: to((head+1).toChar, end)
     | }
def to(head: Char, end: Char): LazyList[Char]

scala> val hexChars = to('A', 'F').take(20).toList
val hexChars: List[Char] = List(A, B, C, D, E, F)
```
새로운 함수 `to`를 이용하여 16진수를 쓸 때 사용되는, 문자로 구성된 한정된 `LazyList`를 생성할 수 있다. 이 `LazyList`의 `take` 연산은 가능한 요소들만 반환하고, 그 컬렉션을 종료하는 `LazyList.empty`를 위치시킨 다음 종료된다.


## 모나딕 컬렉션
**모나딕 컬렉션(monadic collection)** 은 `Iterable` 연산 같은 변형 연산을 지원하지만, 하나 이상의 요소는 포함할 수 없다.

### Option 컬렉션
크기가 1이 넘지 않는 컬렉션으로 `Option` 타입은 단일 값의 존재 또는 부재를 나타낸다. 이 잠재적으로 누락된 값은 `Option`컬렉션으로 감싸고 그 잠재적인 부재를 분명하게 공시한다.  
일부 개발자들은 `Option`을 `null` 값의 안전한 대체재로 보는데, 사용자에게 그 값이 누락될 수 있음을 알리고 `NullPointerException`을 일으킬 가능성을 줄여주기 때문이다. 다른 개발자들은 연산 체인을 만든느 더 안전한 방법으로 보는데, 연산 체인 내내 오직 유효한 값만 지속되기 때문이다.  
`Option` 타입 그 자체는 구현되어 있지만, 하나의 요소로 구성된 타입-매개변수화된 컬렉션인 `Some`과 빈 컬렉션인 `None`, 이 두 서브타입에 기반하여 구현할 수 있다. `None` 타입은 타입 매개변수가 없는데, 그 안에 어떤 것도 포함되어 있지 않기 때문이다. 이 두 타입을 직접 사용하거나 `Option`을 호출하여 `null` 값을 감지하고 적절한 서브타입을 선택하도록 할 수 있다.
```scala
scala> var x: String = "Indeed"
var x: String = Indeed

scala> var a = Option(x)
var a: Option[String] = Some(Indeed)

scala> x = null
// mutated x

scala> var b = Option(x)
var b: Option[String] = None
```
`isDefinedd`와 `isEmpty`를 사용하여 주어진 `Option`이 각각 `Some`인지 `None`인지 확인할 수 있다.
```scala
scala> println(s"a is defined? ${a.isDefined}")
a is defined? true

scala> println(s"b is not defined? ${b.isEmpty}")
b is not defined? true
```

`Option`값을 반환하는 함수를 정의하여 좀 더 현실적인 에제를 사용해보자. 
```scala
scala> def divide(amt: Double, divisor: Double): Option[Double] = {
     |  if (divisor == 0) None
     |  else Option(amt / divisor)
     | }
def divide(amt: Double, divisor: Double): Option[Double]

scala> val legit = divide(5, 2)
val legit: Option[Double] = Some(2.5)

scala> val illegit = divide(3, 0)
val illegit: Option[Double] = None
```
이렇게 `Option`은 함수 결과를 처리하는 데 타입에 안전한 방식을 제공하며, 이 방식이 누락된 값을 의미하는 `null`값을 반환하는 자바 표준보다 더 안전하다. 더 안전한 방식은 빈 리스트에도 동작할 수 있도록 `Option`에 헤드 요소를 감싸서 반환하는 `headOption`을 사용하는 것이다.
```scala
scala> val odds = List(1, 3, 5)
val odds: List[Int] = List(1, 3, 5)

scala> val firstOdd = odds.headOption
val firstOdd: Option[Int] = Some(1)

scala> val evens = odds filter(_ % 2 == 0)
val evens: List[Int] = List()

scala> val firstEven = evens.headOption
val firstEven: Option[Int] = None
```

컬렉션에서 `Option`의 다른 용도는 `find` 연산으로, 조건자 함수에 맞는 첫 번째 요소를 반환하는 `filter`와 `headOption`의 조합이다.
```scala
scala> val words = List("risible", "scavenger", "gist")
val words: List[String] = List(risible, scavenger, gist)

scala> val uppercase = words find (w => w == w.toUpperCase)
val uppercase: Option[String] = None

scala> val lowercase = words find (w => w == w.toLowerCase)
val lowercase: Option[String] = Some(risible)
```

어떤 의미에서 우리는 리스트 축소 연산을 사용하여 컬렉션을 단일 `Option`으로 축소하였다. 하지만 `Option`자체가 컬렉션이기 때문에 계속해서 이를 변환할 수 있다.
```scala
scala> val filtered = lowercase filter (_ endsWith "ible") map (_.toUpperCase)
val filtered: Option[String] = Some(RISIBLE)

scala> val exactSize = filtered filter (_.size > 15) map (_.size)
val exactSize: Option[Int] = None
```
연산은 현재 값(Some)에 적용되고 누락된 값(None)에는 적용되지 않지만, 결과 타입은 여전히 마지막 연산의 타입과 일치한다.

### Option으로부터 값 추출하기
`Option` 컬렉션은 존재 여부가 확실하지 않은 값을 저장하고 변환하는 안전한 메커니즘과 연산을 제공한다. 그리고 잠재적인 값을 추출하는 안전한 연산도 제공한다.  
추출 연산 중 안전하지 않은 연산 `get()`의 경우 `Some` 인스턴스인 `Option`에 이 메소드를 호출하면 `Option`이 가지고 있는 값을 성공적으로 받지만 `None` 인스턴스에 호출하면 `no such element` 에러가 발생한다. `Option.get()`은 안전하지 않으며, 타입에 안전한 연산의 모든 목표를 무너뜨리고 런타임 에러를 일으킬 수 있기 때문에 피해야 한다. 가능하다면 `fold`나 `getOrElse`처럼 안전한 기본값을 정의할 수 있는 연산을 사용하자.

이름 | 예제 | 설명
:---: | :--- | :---
`fold` | nextOption.fold(-1)(x => x) | Some인 경우 주어진 함수로부터 추출한 값을, None인 경우 시작값을 반환함
`getOrElse` | nextOption getOrElse 5 또는 nextOption getOrElse {println("error!"); -)} | Some의 값을 반환하거나 아니면 이름 매개변수의 결과를 반환함
`orElse` | nextOption orElse nextOption | 실제로 값을 추출하지는 않지만 None인 경우 값을 채우려고 함. Option이 비어 있지 않으면 이 option을 반환하고 그렇지 않은 경우 주어진 이름 매개변수로부터 Option을 반환함
`매치 표현식` | nextOption match { case Some(x) => x; case None => -1 } | 값이 있는 경우 매치 표현식을 사용하여 그 값을 처리함. Some(x) 표현식은 그 데이터를 추출하여 매치 표현식의 결과값으로 사용되거나 또 다른 변환에 재사용할 수 있는 지정된 값 x에 넣음

## Try 컬렉션
`util.Try` 컬렉션은 에러 처리를 컬렉션 관리로 바꿔 놓는다. 이 컬렉션은 주어진 함수 매개변수에서 발생한 에러를 잡아내는 메커니즘을 제공하여 함수가 성공적으로 실행된 경우에는 함수의 결과값을, 그렇지 않은 경우에는 에러를 반환한다.  
스칼라는 메세지 또는 다른 정보를 포함하는 에러 타입인 **예외(exception)** 를 발생시킴으로써 에러를 일으킬 수 있다. 예외를 발생시키기 위해서는 새로운 `Exception` 인스턴스와 함께 키워드 `throw`를 사용하면 된다.
```scala
scala> throw new Exception("No DB connection, exiting... ")
java.lang.Exception: No DB connection, exiting...
  ... 32 elided
```
```scala
scala> def loopAndFail(end: Int, failAt: Int): Int = {
     |  for (i <- 1 to end) {
     |      println(s"$i) ")
     |      if (i == failAt) throw new Exception("Too many iterations")
     |  }
     |  end
     | }
def loopAndFail(end: Int, failAt: Int): Int

scala> loopAndFail(10, 3)
1) 
2) 
3) 
java.lang.Exception: Too many iterations
  at $anonfun$loopAndFail$1(<console>:4)
  at scala.collection.immutable.Range.foreach$mVc$sp(Range.scala:190)
  at loopAndFail(<console>:2)
  ... 32 elided
```

예외를 잡아내기 위해서는 잠재적으로 예외를 일으킬 가능성이 있는 코드를 `util.Try` 모나딕 컬렉션으로 감싸면 된다. 스칼라는 `try {} ... catch {}` 블록을 지원하며, 이 경우 `catch` 블록에는 발생한 에러를 매칭하기 위한 `case` 문을 포함하고 있다. 하지만 `util.Try()`가 에러를 처리하기에 더 안전하고, 더 표현력 있으며, 완전한 모나딕 접근법을 제공한다.

`Option`처럼 `util.Try` 타입은 구현되어 있지 않지만, 두 개의 구현된 서브타입인 `Success`와 `Failure`를 가지고 있다. `Success` 타입은 예외가 발생하지 않으면 표현식의 반환값을 포함하고, `Failure`는 발생한 `Exception`을 포함한다.
```scala
scala> val t1 = util.Try( loopAndFail(2, 3) )
1) 
2) 
val t1: scala.util.Try[Int] = Success(2)

scala> val t2 = util.Try{ loopAndFail(4, 2) }
1) 
2) 
val t2: scala.util.Try[Int] = Failure(java.lang.Exception: Too many iterations)
```

일반적으로 적용되는 에러 처리 기법은 많지 않으며, 애플리케이션의 요건과 맥락에 따라 적절한 에러 처리 방식의 선택이 달라진다. 아래의 예재와 표로 처리 기법을 알아보자.
```scala
scala> def nextError = util.Try{ 1 / util.Random.nextInt(2) }
def nextError: scala.util.Try[Int]

scala> val x = nextError
val x: scala.util.Try[Int] = Failure(java.lang.ArithmeticException: / by zero)

scala> val y = nextError
val y: scala.util.Try[Int] = Success(1)
```

이름 | 예제 | 설명
:---: | :--- | :---
`flatMap` | nextError flatMap { _ => nextError } | Success인 경우 util.Try를 반환하는 함수를 호출함으로써 현재의 반환값을 새로운 내장된 반환값에 매핑함. nextError 함수는 입력값을 취하지 않기 때문에 현재 Success로부터 사용하지 않는 입력값을 나타내는 더스코어를 사용함
`foreach` | nextError foreach(x => println("success!" + x)) | Success인 경우 주어진 함수를 한 번 실행하고, Failure일 때는 실행하지 않음
`getOrElse` | nextError getOrElse 0 | Success에 내장된 값을 반환하거나, Failure인 경우 이름에 의한 매개변수의 결과를 반환함
`orElse` | nextError orElse nextError | flatMap의 반대되는 메소드. Failure인 경우 그 역시 util.Try를 반환하는 함수를 호출함. orElse로 어쩌면 Failure를 Success로 전환할 수 있음
`toOption` | nextError.toOption | util.Try를 Option으로 전환하여 Success는 Some으로, Failure는 None이 됨. 옵션으로 작업하는 것이 더 편한 사용자라면 유용하겠지만, 내장된 Exception을 잃게 되는 단점이 있음
`map` | nextError map (_ * 2) | Success인 경우 새로운 값에 내장된 값을 매핑하는 함수를 호출함
`매치 표현식` | nextError match { case util.Success(x) => x; case util.Failure(error) => -1 } | Success를 반환값으로 또는 Failure를 예외로 처리학 ㅣ위해 매치 표현식을 사용함. 표시되지 않는 작업: 훌륭한 로깅 프레임워크로 에러를 로깅하여 에러 통보 및 추적을 보장함
`아무 일도 하지 않음` | nextError | 가장 쉬운 에러 처리 방식. 이 방식으로 단순히 예외가 잡히거나, 현재의 애플리케이션을 종료시킬 때까지 호출 스택을 타고 전파되도록 그대로 둠. 이 방식은 민감한 경우에 사용하면 문제가 크겠지만, 발생한 예외는 결코 무시되지 않음

많은 개발자들이 처리해야 할 보편적인 예외는 문자열에 저장된 숫자를 검증하는 것이다. 다음은 `orElse` 연산자를 이용하여 문자열에서 숫자를 파싱하고, 성공 시 그 결과를 출력하기 위해 `foreach` 연산을 사용한다.
```scala
scala> val input = " 123 "
val input: String = " 123 "

scala> val result = util.Try(input.toInt) orElse util.Try(input.trim.toInt)
val result: scala.util.Try[Int] = Success(123)

scala> result foreach { r => println(s"Parsed '$input' to $r!") }
Parsed ' 123 ' to 123!

scala> val x = result match {
     |  case util.Success(x) => Some(x)
     |  case util.Failure(ex) => {
     |      println(s"Couldn't parse input '$input'")
     |      None
     |  }
     | }
val x: Option[Int] = Some(123)
```

## Future 컬렉션
`concurrent.Future`는 백그라운드 작업을 개시하는 모나딕 컬렉션이다. `Option`과 `Try`와 같이 `Future`는 잠재적 값을 나타내며, 추가 연산을 연결하거나 값을 추출하는 안전한 연산을 제공한다. `Option`과 `Try`와는 달리 퓨처의 값은 즉시 사용하지 못할 수도 있는데, 퓨처를 생성할 때 싲가한 백그라운드 작업이 여전히 작업 중일 수도 있기 때문이다.

기본적으로 스칼라 코드는 JVM의 main 스레드에서 동작하지만, 병행 스레드에서 배그라운드 작업을 실행하도록 지원할 수 있다. `Future`를 함수로 호출하면 현행 스레드가 계속 작업하는 동안 별도의 스레드에서 그 함수를 실행할 것이다. 따라서 `Future`는 그 스레드의 최종 반환값의 모나딕 컨테이너일 뿐 아니라 백그라운드 자바 스레드의 감시자이기도 하다.

메세지를 출력하는 함수로 `Future`를 생성해보자. `Future`를 생성하기 전에 현행 세션 또는 애플리케이션에서 함수를 동시에 실행하기 위한 `context`를 지정해야 한다. 우리는 기본 `global` 컨텍스를 사용하여 이용도의 자바 스레드 라이브러리를 사용할 것이다.
```scala
scala> import concurrent.ExecutionContext.Implicits.global
import concurrent.ExecutionContext.Implicits.global

scala> val f = concurrent.Future { println("hi") }
hi
val f: scala.concurrent.Future[Unit] = Future(<not completed>)
```
백그라운드 작업은 `Future`가 값을 반환하기도 전에 'hi'를 출력했다.

다른 예로, 백그라운드 작업이 여전히 실행하는 동안 `Future`를 받을 수 있게 자바의 `Thread.sleep`을 이용하여 백그라운드 스레드를 재우도록 하자.
```scala
scala> val f = concurrent.Future { Thread.sleep(5000); println("hi") }
val f: scala.concurrent.Future[Unit] = Future(<not completed>)

scala> println("waiting")
waiting

scala> hi
```

`Future`의 작업이 완료될 때 실행할 콜백 함수 또는 추가적인 `Future`를 설정할 수도 있다. 예를 들면, API 호출은 호출자에게 제어권을 넘겨주는 동안 백그라운드에서 중요하지만 시간이 오래 걸리는 작업을 시작할 수 있다. 네트워크 파일 전송 같은 이미 비동기적인 이벤트는 `Future`에서 시작할 수 있으며, 그 작업이 완료될 때까지 또는 시간제한에 도달할 때까지 main 쓰레드가 잠들어 있을 수 있다.  
`Future`는 비동기식으로 관리될 수도 있고 동기식으로도 관리될 수도 있다.

### 비동기식으로 퓨처 처리하기
`Future`는 백그라운드 작업을 개시하는 것 외에 모나딕 컬렉션으로 처리할 수 있다. `Future`가 완료된 다음 실행될 함수 또는 `Future`에 첫 번째 `Future`의 성공적인 결과값을 전달하여 연결할 수 있다. 이 방식으로 처리된 `Future`는 결국 그 함수의 반환값 또는 예외를 포함한 `util.Try`를 반환한다. 성공하면 연결된 함수 또는 `Future`는 반환값에 전달되고, 성공 또는 실패를 반환하는 `Future`로 전환된다. 실패한 경우는 추가적인 함수나 `Future`는 실행되지 않는다. 이렇게 하여 모나딕 컬렉션으로서의 `Future`는 일련의 연산에서 단시 내장된 값을 전달하는 사슬 역할을 한다.

`Future`의 최종 결과를 받기 위해 콜백 함수를 지정할 수 있따. 콜백 함수는 최종 성공적인 값 또는 예외를 받아 그 `Future`를 생성했던 원래 코드를 해제하여 다른 작업으로 넘어갈 수 있도록 해준다.
```scala
scala> import concurrent.ExecutionContext.Implicits.global
import concurrent.ExecutionContext.Implicits.global

scala> import concurrent.Future
import concurrent.Future

scala> def nextFtr(i: Int = 0) = Future {
     |  def rand(x: Int) = util.Random.nextInt(x)
     | 
     |  Thread.sleep(rand(5000))
     |  if (rand(3) > 0) (i + 1) else throw new Exception
     | }
def nextFtr(i: Int): scala.concurrent.Future[Int]
```

이름 | 예제 | 설명
:---: | :--- | :---
`fallbackTo` | nextFtr(1) fallbackTo nextFtr(2) | 두 번째 Future를 첫 번째에 연결하고 새로운 종합적인 Future를 반환함. 첫 번째 Future가 성공적이지 않다면 두 번째가 호출됨
`flatMap` | nextFtr(1).flatMap(int => nextFtr()) | 두 번째 Future를 첫 번째에 연결하고 새로운 종합적인 Future를 반환함. 첫 번째가 성공적이라면 그 반환값이 두 번째를 호출하는데 사용됨
`map` | nextFtr(1) map (_ * 2) | 주어진 함수를 Future에 연결하고 새로운 종함적인 Future를 반환함. Future가 성공적이라면 그 반환값이 해당 함수를 호출할 때 사용됨
`onComplete` | nextFtr() onComplete { _ getOrElse 0 } | Future의 작업이 완료된 후 주어진 함수가 값 또는 예외를 포함한 util.Try를 이용하여 호출됨
`Future.sequence` | concurrent.Future sequence List(nextFtr(1), nextFtr(5)) | 주어진 시퀀스에서 Future를 병행으로 실행하여 새로운 Future를 반환함. 시퀀스 내의 모든 Future가 성공하면 이들의 반환값의 리스트가 반환됨. 그렇지 않으면 그 시퀀스 내에서 처음으로 발생한 예외가 반환됨.

Future가 유용하게 사용되려면 생성, 관리 외에도 추출이 가능해야 한다. Future의 좀 더 현실적인 예제를 통해 Future로 작업하는 방법을 처음부터 끝까지 알아보자.  
아래의 예제에서는 URL로부터 내용을 읽어오기 위해 스칼라 라이브러리 연산인 `io.Source.fromURL(url: String)`를 사용하는데, 이는 `+io.Source` 인스턴스를 반환한다.  
OpenWeatherMap API를 사용하여 두 도시의 현재 온도를 확인하고 어느 도시가 더 따듯한지를 알려줄 것이다. 원격 API를 호출하는 일은 시간 집약적인 작업이 도리 수 있으므로 우리는 병행식 `Future`로 API를 호출하여 메인 스레드와 동시에 실행할 것이다.
```scala
scala> import concurrent.ExecutionContext.Implicits.global
import concurrent.ExecutionContext.Implicits.global

scala> import concurrent.Future
import concurrent.Future

scala> def cityTemp(name: String): Double = {
     |  val url = "http://api.openweathermap.org/data/2.5/weather"
     |  val cityUrl = s"$url?APPID=652ee92adf9ce39f9566d457442ea02d=$name"
     |  val json = io.Source.fromURL(cityUrl).mkString.trim
     |  val pattern = """.*"temp":([\d.]+).*""".r
     |  val pattern(temp) = json
     |  temp.toDouble
     | }
     | 
def cityTemp(name: String): Double

scala> val cityTemps = Future sequence Seq(
     |  Future(cityTemp("Fresno")), Future(cityTemp("Tempe"))
     | )
val cityTemps: scala.concurrent.Future[Seq[Double]] = Future(<not completed>)
```

Future는 아직 이해도 안되고 너무 어려우니 나중에 알아보자.