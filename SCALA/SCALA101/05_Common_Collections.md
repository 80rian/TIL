# 보편적인 컬렉션

> **컬렉션(collection)** 프레임워크는 배열, 리스트, 맵, 집합, 트리와 같이 주어진 타입을 가지는 하나 또는 그 이상의 값을 수집하는 데이터 구조를 제공한다.  
> 스칼라는 고성능의, 객체지향적인, 타입-매개변수화된 컬렉션 프레임워크를 가지고 있다. 그러나 스칼라의 컬렉션은 `map`, `filter`, `reduce`와 같이 짧고 표현력 있는 표현식으로 데이터를 관리하고 처리하는 고차 연산도 가지고 있다.  
> 또한, 가변적인 컬렉션 타입 계층 구조와 불변의 컬렉션 타입 계층구조를 별개로 가지고 있어서 불변의 데이터와 가변적인 데이터 사이의 전환을 쉽게 만들어준다.

<br>

## 리스트, 집합, 그리고 맵
**리스트(List)** 는 불변의 단방향 연결 리스트이며, 함수를 호출함으로써 생성할 수 있다. 호출 시에 그 리스트에 포함될 내용을 쉼표로 구분된 매개변수 형태로 전달한다.
```scala
scala> val numbers = List(32, 95, 24, 21, 17)
val numbers: List[Int] = List(32, 95, 24, 21, 17)

scala> val colors = List("red", "green", "blue")
val colors: List[String] = List(red, green, blue)

scala> println(s"I have ${colors.size} colors: $colors")
I have 3 colors: List(red, green, blue)
```

`head()`와 `tail()` 메소드를 사용하여 리스트의 첫 번째 요소와 나머지 요소에 접근할 수 있다. 직접 단일 요소에 접근하려면, 리스트를 함수로 호출하고 그 요소를 가리키는 0부터 시작하는 인덱스를 그 함수에 전달하면 된다.
```scala
scala> val colors = List("red", "green", "blue")
val colors: List[String] = List(red, green, blue)

scala> colors.head
val res3: String = red

scala> colors.tail
val res4: List[String] = List(green, blue)

scala> colors(1)
val res5: String = green

scala> colors(2)
val res6: String = blue
```
`for` 루프로 컬렉션을 반복할 수 있다.
```scala
scala> val numbers = List(32, 95, 24, 21, 17)
val numbers: List[Int] = List(32, 95, 24, 21, 17)

scala> var total = 0; for (i <- numbers) { total += i }
var total: Int = 189

scala> val colors = List("red", "green", "blue")
val colors: List[String] = List(red, green, blue)

scala> for (c <- colors) { println(c) }
red
green
blue
```

다음은 `List`와 다른 컬렉션에서 사용할 수 있는 고차 함수인 `foreach()`, `map()`, `reduce()`를 보여준다. 각 함수는 리스트를 반복하고, 전환하며, 리스트를 단일 항목으로 축소한다. 각 메소드에는 함수 리터럴이 전달되는데, 이 함수 리터럴에는 괄호로 묶인 입력 매개변수와 함수 본문이 포함되어 있다.
```scala
scala> val colors = List("red", "green", "blue")
val colors: List[String] = List(red, green, blue)

scala> colors.foreach( (c: String) => println(c) )
red
green
blue

scala> val sizes = colors.map( (c: String) => c.size )
val sizes: List[Int] = List(3, 5, 4)

scala> val numbers = List(32, 95, 24, 21, 17)
val numbers: List[Int] = List(32, 95, 24, 21, 17)

scala> val total = numbers.reduce( (a: Int, b: Int) => a + b)
val total: Int = 189
```

`Set`은 유일한 요소들로 이루어진 순서가 없는 불변의 컬렉션이지만, `List`와 유사하게 동작한다. 다음은 중복된 항목들로 `Set`을 생성하는 예제다. `Iterable`의 또 다른 서브타입으로, `Set` 인스턴스는 `List` 인스턴스와 마찬가지로 동일한 연산을 지원한다.
```scala
scala> val unique = Set(10, 20, 30, 20, 20, 10)
val unique: scala.collection.immutable.Set[Int] = Set(10, 20, 30)

scala> val sum = unique.reduce( (a: Int, b: Int) => a + b )
val sum: Int = 60
```

`Map`은 불변의 키-값 저장소이며, `Map`에 주어진 유일한 키로 저장된 값은 그 키를 이요하여 추출할 수 있다. `Map`을 생성할 때는 관게 연산자 `->` 를 사용하여 튜플을 이용해 기술할 수 있다.
```scala
scala> val colorMap = Map("red" -> 0xFF0000, "green" -> 0xFF00, "blue" -> 0xFF)
val colorMap: scala.collection.immutable.Map[String,Int] = Map(red -> 16711680, green -> 65280, blue -> 255)

scala> val redRGB = colorMap("red")
val redRGB: Int = 16711680

scala> val cyanRGB = colorMap("green") | colorMap("blue")
val cyanRGB: Int = 65535

scala> val hasWhite = colorMap.contains("white")
val hasWhite: Boolean = false

scala> for (pairs <- colorMap) { println(pairs) }
(red,16711680)
(green,65280)
(blue,255)
```

<br>

## 리스트에는 무엇이 있는가?
`List` 또는 다른 타입의 컬렉션을 생성하는 표준 방식은 그 타입을 원하는 내용과 함께 함수로 호출하는 것이다.
```scala
scala> val colors = List("red", "green", "blue")
val colors: List[String] = List(red, green, blue)

scala> val oddsAndEvens = List(List(1, 3, 5), List(2, 4, 6))
val oddsAndEvens: List[List[Int]] = List(List(1, 3, 5), List(2, 4, 6))

scala> val keyValues = List(('A', 65), ('B', 66), ('C', 67))
val keyValues: List[(Char, Int)] = List((A,65), (B,66), (C,67))
```

리스트의 단일 구성 요소에는 그 요소를 가리키는 인덱스 번호와 함께 리스트를 함수로 호출하여 접근할 수 있다.
```scala
scala> val primes = List(2, 3, 5, 7, 11, 13)
val primes: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> val first = primes(0)
val first: Int = 2

scala> val fourth = primes(3)
val fourth: Int = 7
```

리스트를 리스트의 번 번째 항목인 `head`와 이를 제외한 나머지 항목들인 `tail`로 분해할 수 있다.
```scala
scala> val first = primes.head
val first: Int = 2

scala> val remaining = primes.tail
val remaining: List[Int] = List(3, 5, 7, 11, 13)
```

`List`는 불변의 재귀적인 데이터 구조이므로 리스트의 각 요소는 자신만의 헤드와 점진적으로 더 짧아지는 테일을 가진다. 이를 사용하여 헤드로 시작하여 잇따른 테일들이 지나가는 길을 만들면서 `List` 반복자를 만들 수 있다.  
이와 같은 반복자를 만들 때 어려운 부분은 리스트의 마지막에 언제 도달하는지 알아내는 것이다. 이를 위해 우리는 리스트를 순회하지 않아도 되는 `isEmpty` 메소드가 있다.
```scala
scala> val primes = List(2, 3, 5, 7, 11, 13)
val primes: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> var i = primes
var i: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> while(! i.isEmpty) { print(s"${i.head}, "); i = i.tail }
2, 3, 5, 7, 11, 13, 
```
또는 다음과 같이 재귀적 형태로 리스트를 순회하는 함수를 만들 수도 있다.
```scala
scala> val primes = List(2, 3, 5, 7, 11, 13)
val primes: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> def visit(i: List[Int]): Unit = { if (i.size > 0) { print(s"${i.head}, "); visit(i.tail) } }
def visit(i: List[Int]): Unit

scala> visit(primes)
```

리스트의 마지막인지를 검사하기 위해 `isEmpty`를 호출하는 것도 효율적이지만, 또 다른 효율적인 방법도 있다. 모든 리스트는 그 종점으로서 `Nil`의 인스턴스로 끝나기 때문에 반복자는 현재의 항목과  `Nil`을 비교함으로써 리스트의 끝을 확인할 수 있다.
```scala
scala> val primes = List(2, 3, 5, 7, 11, 13)
val primes: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> var i = primes
var i: List[Int] = List(2, 3, 5, 7, 11, 13)

scala> while (i != Nil) { print(s"${i.head}, "); i = i.tail }
2, 3, 5, 7, 11, 13, 
```

`Nil`은 근본적으로 `List[Nothing]`의 싱글턴 인스턴스다. `Nothing` 타입의 리스트는 다른 모든 타입의 리스트와 호환되며, 그들의 종점으로 안전하게 사용될 수 있다.  
새로운 빈 리스트를 생성하면 실제로는 새로 생긴 인스턴스 대신 `Nil`을 반환할 것이다. `Nil`은 불변하는 데이터 구조이기 때문에 근본적으로 새로 생긴 빈 리스트 인스턴스와 차이가 없다. 비슷하게, 단일 항목을 가지는 새로운 리스트를 생성하는 것은 자신의 테일로 `Nil`을 가리키는 단일 리스트 항목을 생성한다.
```scala
scala> val l: List[Int] = List()
val l: List[Int] = List()

scala> l == Nil
val res0: Boolean = true

scala> val m: List[String] = List("a")
val m: List[String] = List(a)

scala> m.head
val res1: String = a

scala> m.tail == Nil
val res2: Boolean = true
```

<br>

### 생성 연산자
리스트를 생성하는 다른 방법으로는 이전 절에서 설명한 `Nil`과의 관계를 이용하는 것이다. `Lisp`를 인정하는 또 다른 의미로, 스칼라는 리스트를 만들기 위해 **생성(cons)** 연산자의 사용을 지원한다. `Nil`을 기반으로 항목들을 결합하기 위해 오른쪽 결합형 생성 연산자 `::`를 사용하여 리스트를 만들 수 있다.
```scala
scala> val numbers = 1 :: 2 :: 3 :: Nil
val numbers: List[Int] = List(1, 2, 3)
```
```scala
scala> val first = 1 :: Nil
val first: List[Int] = List(1)

scala> val second = 2 :: first
val second: List[Int] = List(2, 1)

scala> second.tail == first
val res4: Boolean = true
```
하나의 값을 다른 값에 추가하여 하나의 리스트를 만드는 이 예제는 스칼라의 불변의 리스트가 갖는 재귀적이며 재사용 가능한 특성을 잘 보여준다.

<br>

## 리스트 산술 연산
이름 | 예제 | 설명
:---: | :--- | :---
`::` | 1 :: 2 :: Nil | 리스트에 개별 요소를 덧붙임
`:::` | List(1, 2) ::: List(2, 3) | 이 리스트 앞에 다른 리스트를 추가함
`++` | List(1, 2) ++ Set(3, 4, 5) | 이 리스트에 다른 컬렉션을 덧붙임
`==` | List(1, 2) == List(1, 2) | 두 컬렉션의 타입과 내용이 똑같으면 true를 반환함
`distinct` | List(3, 5, 4, 3, 4).distinct | 중복 요소가 없는 리스트 버전을 반환함
`drop` | List('a', 'b', 'c', 'd') drop 2 | 리스트의 첫 번째 n개의 요소를 뺌
`filter` | List(23, 8, 14, 21) filter (_ > 18) | true/false 함수를 통과한 리스트의 요소들을 반환함
`flatten` | List(List(1, 2), List(3, 4)).flatten | 리스트의 리스트 구조를 단일 리스트로 전환함
`partition` | List(1, 2, 3, 4, 5) partition (_ < 3) | 리스트의 요소들을 true/false 함수의 결과값에 따라 분류하여 두 개의 리스트를 포함하는 튜플로 만듦
`reverse` | List(1, 2, 3).reverse | 리스트 요소들의 순서를 거꾸로 함
`slice` | List(2, 3, 5, 7) slice (1, 3) | 리스트 중 첫 번째 인덱스부터 두 번째 인덱스-1까지에 해당하는 부분을 반환함
`sortBy` | List("apple", "to") sortBy (_.size) | 주어진 함수로부터 반환된 값으로 리스트 순서를 정렬함
`sorted` | List("apple", "to").sorted | 핵심 스칼라 타입의 리스트를 자연값 기준으로 정렬함
`splitAt` | List(2, 3, 5, 7) splitAt 2 | List 요소들을 주어진 인덱스의 앞에 위치하는지 뒤에 위치하는지에 따라 두 리스트의 튜플로 분류함
`take` | list(2, 3, 5, 7, 11, 13) take 3 | 리스트에서 첫 번째 n개의 요소들을 추출함
`zip` | List(1, 2) zip List("a", "b") | 두 리스트를 각 인덱스에 해당하는 요소들끼리 구성된 튜플의 리스트로 결합함

```scala
scala> val f = List(23, 8, 14, 21) filter (_ > 18)
val f: List[Int] = List(23, 21)

scala> val p = List(1, 2, 3, 4, 5) partition (_ < 3)
val p: (List[Int], List[Int]) = (List(1, 2),List(3, 4, 5))

scala> val s = List("apple", "to") sortBy (_.size)
val s: List[String] = List(to, apple)
```

`filter`, `map`, `partition`처럼 고차 함수인 컬렉션 메소드는 자리표시자 구문을 사용하는 대표적인 예다. 이 메소드 중 하나에 전달된 익명 함수에서의 언더스코어 `_`는 리스트의 각 항목을 나타낸다.  
이 산술 연산 메소드에서 중요한 점은 `::`, `drop`, `take`가 리스트의 앞에서 동작하고, 따라서 성능상의 불이익이 없다는 것이다. `List`는 연결 리스트이기 때문에 그 앞에 항목들을 추가하거나, 그 앞의 항목들을 제거하는 것은 리스트 전체를 순회할 필요가 없다.  
그렇다 하더라도 이 연산들을 리스트의 끝에서 동작하는, 따라서 리스트 전체 순회가 필요한 동반 연산들을 가지고 있다. 메모리 이슈 상, 일반적으로 리스트의 끝이 아니라 앞에서 연산하는 것이 더 바람직하다.  
`::`, `drop`, `take`의 동반 연산자는 `+:`, `dropRight`, `takeRight`다.

```scala
scala> val appended = List(1, 2, 3, 4) :+ 5
val appended: List[Int] = List(1, 2, 3, 4, 5)

scala> val suffix = appended takeRight 3
val suffix: List[Int] = List(3, 4, 5)

scala> suffix dropRight 2
val res10: List[Int] = List(3)
```

<br>

## Mapping Lists
`Map` 메소드는 함수를 취하여 그 함수를 리스트의 모든 요소들에 적용하고, 그 결과를 새로운 리스트에 수집한다.
이름 | 예제 | 설명
:---: | :--- | :---
`collect` | List(0, 1, 0) collect {case 1 => "ok"} | 각 요소를 부분 함수를 사용하여 변환하고, 해당 함수를 적용할 수 있는 요소를 유지함
`flatMap` | List("milk, tea") flatMap (_.split(',')) | 주어진 함수를 이용하여 각 요소를 변환하고, 그 결과 리스트를 이 리스트에 평면화함
`map` | List("milk", "tea") map (_.toUpperCase) | 주어진 함수를 이용하여 각 요소를 변환함

```scala
scala> List(0, 1, 0) collect {case 1 => "ok"}
val res0: List[String] = List(ok)

scala> List("milk, tea") flatMap (_.split(", "))
val res1: List[String] = List(milk, tea)

scala> List("milk", "tea") map (_.toUpperCase)
val res2: List[String] = List(MILK, TEA)
```

<br>

## Reducing Lists
**리스트 축소(reducing)** 는 리스트를 단일 값으로 축소하는 것이다.
이름 | 예제 | 설명
:---: | :--- | :---
`max` | List(41, 59, 26).max | 리스트의 최대값 구하기
`min` | List(10.9, 32.5, 4.23, 5.67) | 리스트의 최소값 구하기
`product` | List(5, 6, 7).product | 리스트의 숫자들을 곱하기
`sum` | List(11.3, 23.5, 7.2).sum | 리스트의 숫자들을 합산하기
`contains` | List(34, 29, 18) contains 29 | 리스트가 이 요소를 포함하고 있는지를 검사함
`endsWith` | List(0, 4, 3) endsWith List(4, 3) | 리스트가 주어진 리스트로 끝나는지를 검사함
`exists` | List(24, 17, 32) exists (_ < 18) | 리스트에서 최소 하나의 요소에 대해 조건자가 성립하는지를 검사함
`forall` | List(24, 17, 32) for all (_ < 18) | 리스트의 모든 요소에 대해 조건자가 성립하는지를 검사함
`startsWith` | List(0, 4, 3) startsWith List(0) | 리스트가 주어진 리스트로 시작하는지를 테스트함

```scala
scala> val validations = List(true, true, false, true, true, true)
val validations: List[Boolean] = List(true, true, false, true, true, true)

scala> val valids1 = !(validations contains false)
val valids1: Boolean = false

scala> val valid2 = validations forall (_ == true)
val valid2: Boolean = false

scala> val valids3 = validations.exists(_ == false) == false
val valids3: Boolean = false
```

<br>

## Folding Lists
**List Folding** 은 리스트를 입력 함수 기반으로 축소하는 고차 함수이다.
이름 | 예제 | 설명
:---: | :--- | :---
`fold` | List(4, 5, 6).fold(0)(_ + _) | 주어진 시작값과 축소 함수로 리스트를 축소
`foldLeft` | List(4, 5, 6).foldLeft(0)(_ + _) | 주어진 시작값과 축소 함수로 리스트를 왼쪽에서 오른쪽으로 축소함
`foldRight` | List(4, 5, 6).foldRight(0)(_ + _) | 주어진 시작값과 축소 함수로 리스트를 오른쪽에서 왼쪽으로 축소함
`reduce` | List(4, 5, 6).reduce(_ + _) | 리스트의 첫 번째 요소를 시작으로, 주어진 축소 함수로 리스트를 축소함
`reduceLeft` | List(4, 5, 6).reductLeft(_ + _) | 리스트의 첫 번째 요소를 시작으로, 주어진 축소 함수로 리스트를 왼쪽부터 오른쪽으로 축소함
`reduceRight` | List(4, 5, 6).reduceRight(_ + _) | 리스트의 마지막 요소를 시작으로, 주어진 축소 함수로 리스트를 오른쪽부터 왼쪽으로 축소함
`scan` | List(4, 5, 6).scan(0)(_ + _) | 시작값과 축소 함수를 취하여 각각의 누계값의 리스트를 반환함
`scanLeft` | List(4, 5, 6).scanLeft(0)(_ + _) | 시작값과 축소 함수를 취하고, 왼쪽부터 오른쪽으로 각각의 누계값의 리스트를 반환함
`scanRight` | List(4, 5, 6).scanRight(0)(_ + _) | 시작값과 축소 함수를 취하고, 오른쪽부터 왼쪽으로 각각의 누계값의 리스트를 반환함

각 연산의 왼쪽/오른쪽 방향성에 따른 변형과 방향성 없는 연산 간의 차이가 `fold`, `reduce`, `scan`간의 차이보다 더 중요하다. `fold`, `reducd`, `scan`은 모두 리스트 요소와 동일한 타입의 값을 반환해야 하는 반면, 각 연산의 왼쪽/오른쪽 변형은 고유의 반환 타입을 지원한다.  
따라서 정수 리스트에 `forall` bool 연산을 `foldLeft`로는 구현할 수 있으나 `fold`로는 할 수 없다.

다른 주요 차이점은 순서에 있다. `foldLeft`와 `foldRight`는 리스트를 반복하는 방향을 지정하지만, `fold`는 반복의 순서를 지정하지 않는다. 이는 어느 방향으로 사용될 것인지 명확하지 않기 때문에 문제가 생길 수 있다. 만약 컬렉션이 순차적이지 않고 여러 다른 컴퓨터에 분산되어 있거나, 모두 한 컴퓨터에 있지만 `fold` 연산이 비용이 너무 많이 들어 병렬로 돌리고 싶다면 리스트를 순차적으로 반복하는 접기 연산과 접기 연산을 구현한 컬렉션을 기반으로 필요한 순서에 따라 동작하는 접기 연산을 구분하는 것이 당연하다.

<br>

## 컬렉션 전환하기
이름 | 예제 | 설명
:---: | :--- | :---
`mkString` | List(24, 99, 104).mkString(", ") | 주어진 구분자를 사용하여 컬렉션을 String으로 만듦
`toBuffer` | List('f', 't').toBuffer | 불변의 컬렉션을 가변적인 컬렉션으로 전환
`toList` | Map("a" -> 1, "b" -> 2).toList | 컬렉션을 List로 전환
`toMap` | Set(1 -> true, 3 -> true).toMap | 두 요소로 구성된 튜플의 컬렉션을 Map으로 전환
`toSet` | List(2, 5, 5, 3, 2).toSet | 컬렉션을 Set으로 전환
`toString` | List(2, 5, 5, 3, 2).toString | 컬렉션을 String으로 컬렉션의 타입을 포함하여 만듦

<br>

## 컬렉션으로 패턴 매칭하기
```scala
scala> val statuses = List(500, 404)
val statuses: List[Int] = List(500, 404)

scala> val msg = statuses.head match {
     |  case x if x < 500 => "okay"
     |  case _ => "whoah, an error"
     | }
val msg: String = whoah, an error
```
컬렉션은 등호 연산자를 지원하므로 컬렉션이 패턴 매칭을 지원한다. 전체 컬렉션을 매칭하기 위해 패턴으로 새로운 컬렉션을 사용해보자.
```scala
scala> val statuses = List(500, 404)
val statuses: List[Int] = List(500, 404)

scala> val msg = statuses match {
     |  case x if x contains (500) => "has error"
     |  case _ => "okay"
     | }
val msg: String = has error
```

패턴 가드에서 컬렉션의 일부 또는 모든 요소에 값을 바인딩 할 수 있다.
```scala
scala> val statuses = List(500, 404)
val statuses: List[Int] = List(500, 404)

scala> val msg = statuses match {
     |  case List(500, x) => s"Error followed by $x"
     |  case List(e, x) => s"$e was followed by $x"
     | }
val msg: String = Error followed by 404
```

리스트는 헤드 요소와 테일로 분해할 수 있다. 동일하게, 패턴으로서 리스트는 헤드와 테일 요소에 매칭될 수 있다.
```scala
scala> val head = List('r', 'g', 'b') match {
     |  case x :: xs => x
     |  case Nil => ' '
     | }
val head: Char = r
```

공식적으로 컬렉션은 아니지만 튜플도 패턴 매칭과 값 바인딩을 지원한다. 단일 튜플은 다른 타입의 값을 지원하기 때문에 튜플의 패턴 매칭이 때로는 컬렉션의 패턴 매칭보다 더 유용할 때가 있다.
```scala
scala> val code = ('h', 204, true) match {
     |  case (_, _, false) => 501
     |  case ('c', _, true) => 302
     |  case ('h', x, true) => x
     |  case (c, x, true) => {
     |      println(s"Did not expect code $c")
     |      x
     |  }
     | }
val code: Int = 204
```
패턴 매칭은 스칼라 표준 컬렉션 라이브러리의 그저 평범한 연산이 아니라 이 언어의 핵심 특징이다. 패턴 매칭은 스칼라 데이터 구조에 광범위하게 적용될 수 있으며, 현명하게 사용한다면 다른 언어에서는 광범위한 작업이 필요한 로직을 짧고 간단하게 만들 수 있다.