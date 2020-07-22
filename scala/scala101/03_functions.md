# 함수

> 함수형 프로그래밍 언어는 재사용성이 아주 높고 구성력이 좋은 함수를 지원하고, 개발자들이 자신의 코드 기반을 이 함수를 둘러싸고 구조화할 수 있도록 돕는 데 맞춰져 있다.  
> 스칼라에서 **함수\(function\)** 는 이름을 가진, 재활용 가능한 표현식이다. 함수는 매개변수화할 수도 있으며 값을 반환할 수도 있지만, 이 특징 중 어느 것도 필수사항은 아니다.  
> 함수형 프로그래밍에서 순수 함수는 다음과 같은 특징을 갖는다.
>
> * 하나 또는 그 이상의 입력 매개변수를 가진다.
> * 입력 매개변수만을 가지고 계산을 수행한다.
> * 값을 반환한다.
> * 동일 입력에 대해 항상 같은 값을 반환한다.
> * 함수 외부의 어떤 데이터도 사용하거나 영향을 주지 않는다.
> * 함수 외부 데이터에 영향받지 않는다.
>
> 순수 함수는 상태 정보를 저장하지 않으며, 파일, 데이터베이스, 소켓, 전역변수 및 다른 공유 데이터와 같은 외부 데이터에 관계없이 독립적이다. 본질적으로 순수 함수는 변경될 수 없으며, 순수 로직을 변경하지 않는 표현식이다. 순수 함수만을 포함한 애플리케이션을 만드는 것은 어렵기 때문에 스칼라 개발자는 애플리케이션에서 순수 함수를 배타적으로 사용하는 방법을 알아내려고 노력하는 대신, 일반적으로 어느 정도 수준에서 절충하고 순수하지 않은 함수의 수를 줄이는 방법을 강구한다. 순수하지 않은 함수를 분명하게 명명하고 순수 함수와 쉽게 구분해 낼 수 있는 방식으로 구성하는 것이 스칼라 애플리케이션을 모듈화하고 조직화하는 일반적인 목표다.

```scala
def <identifier>(<identifier>: <type>[, ...]): <type> = <expression>
```

```scala
scala> def multiplier(x: Int, y: Int): Int = { x * y }
def multiplier(x: Int, y: Int): Int

scala> multiplier(6, 7)
val res0: Int = 42
```

함수의 표현식 블록의 마지막에 이르기 전에 종료하고 값을 반환해야 하는 경우에는 `return`을 사용하여 함수의 반환값을 명시적으로 지정하고 함수를 종료할 수 있다.

```scala
scala> def safeTrim(s: String): String = {
     |  if (s == null) return null
     |  s.trim()
     | }
def safeTrim(s: String): String
```

## Procedure

**프로시저\(procedure\)** 는 반환값을 가지지 않는 함수다. `println()` 호출과 같은 문장으로 끝나는 모든 함수 또한 프로시저다. 이 경우에 스칼라 컴파일러는 함수의 반환 타입을 값이 없는 `Unit` 타입으로 추론한다. 한 줄 이상의 프로시저에 대해 명시적으로 타입을 `Unit`으로 지정하면 코드를 읽는 사용자에게 반환값이 없다는 것을 분명하게 나타낼 것이다.

```scala
scala> def log(d: Double) = println(f"Got value $d%.2f")
def log(d: Double): Unit

scala> def log(d: Double): Unit = println(f"Got value $d%.2f")
def log(d: Double): Unit

scala> log(2.23535)
Got value 2.24
```

## 빈 괄호를 가지는 함수

입력 매개변수가 없는 함수를 저의하고 초훌하는 방법으로 빈 괄호를 사용할 수 있다. 이 함수는 빈 괄호를 사용하거나 괄호를 빼고도 호출할 수 있다.

```scala
def <identifier>()[: <type>] = <expression>
```

```scala
scala> def hi(): String = "hi"
def hi(): String

scala> hi()
val res2: String = hi

scala> hi
val res3: String = hi
```

## 표현식 블록을 이용한 함수 호출

단일 매개변수를 사용하여 함수를 호출할 때, 괄호 안에 값을 넣는 대신 중괄호 안에 표현식 블록을 사용하여 매개변수를 전달할 수 있다.

```scala
<function identifier> <expression block>
```

함수 호출을 위해 표현식 블록을 사용하는 것이 더 나은 경우는 함수에 계산된 값을 전달해야 할 때다. 값을 계산하고 그 값을 함수에 전달할 지역값에 저장하는 대신, 그 표현식 블록 내에서 연산을 할 수 있다. 표현식 블록은 함수를 호출하기 전에 평가되며, 그 블록의 반환값은 함수 인수로 사용된다.

```scala
scala> def formatEuro(amt: Double) = f"€$amt%.2f"
def formatEuro(amt: Double): String

scala> formatEuro(3.4645)
val res4: String = €3.46

scala> formatEuro { val rate = 1.32; 0.25 + 0.7123 + rate * 5.32 }
val res5: String = €7.98
```

## Recursive Fuction

**재귀 함수\(recursive function\)** 는 자기 자신을 호출하는 함수로, 되도록 특정 타입의 매개변수 또는 함수 호출이 무한 루프에 빠지는 것을 피하고자 검사할 외부 조건과 함께 호출한다. 재귀 함수는 함수형 프로그래밍에서 매우 보편적인데, 이는 데이터 구조 또는 계산을 가변적인 데이터를 사용하지 않ㄱ ㅗ반복하는 방법을 제공하기 때문이다. 그리고 이것이 가능한 이유는 각 함수 호출이 함수 매개변수를 저장하기 위한 자신만의 **스택\(stack\)** 을 가지기 때문이다.

```scala
scala> def power(x: Int, n: Int): Long = {
     |  if (n >= 1) x * power(x, n-1)
     |  else 1
     | }
def power(x: Int, n: Int): Long

scala> power(2, 8)
val res6: Long = 256

scala> power(2, 1)
val res7: Long = 2

scala> power(2, 0)
val res8: Long = 1
```

재귀 함수를 너무 많이 호출하면 할당된 스택 공간을 모두 소진하게 되면서 **스택 오버플로우\(stack overflow\)** 에러에 빠질 수 있다. 이를 예방하기 위해 스칼라 컴파일러는 재귀적 호출이 추가적인 스택 공간을 사용하지 않도록 **꼬리-재귀\(tail-recursion\)** 를 사용하여 일부 재귀 함수를 최적화 할 수 있다. 꼬재-재귀를 이용하여 재귀 호출을 최적화한 함수를 사용하면, 재귀적 호출이 새로운 스택 공간을 생성하지 않는 대신 현행 함수의 스택 공간을 사용한다. 꼬리-재귀 함수는 함수 애노테이션 `@`를 사용하여 함수 정의 앞 또는 그 전 줄에 `@annotation.tailrec`을 추가하면 된다. 이것을 사용하면 스칼라 컴파일러에게 우리가 이 함수가 꼬리-재귀를 위해 최적화될 것을 기대하고 있음을 알려주며, 만약 최적화할 수 없다면 컴파일러가 에러로 처리한다.

```scala
scala> @annotation.tailrec
     | def power(x: Int, n: Int): Long = {
     |  if (n >= 1) x * power(x, n-1)
     |  else 1
     | }
        if (n >= 1) x * power(x, n-1)
                             ^
On line 3: error: could not optimize @tailrec annotated method power: it contains a recursive call not in tail position
```

이 함수는 최적화될 수 없는데, 재귀적 호출이 함수의 마지막 문장이 아니기 때문이다. 조건문을 바꾸면 가능하다.

```scala
scala> @annotation.tailrec
     | def power(x: Int, n: Int, t: Int = 1): Int = {
     |  if (n < 1) t
     |  else power(x, n-1, x*t)
     | }
def power(x: Int, n: Int, t: Int): Int

scala> power(2, 8)
val res9: Int = 256
```

이렇게 최적화된 함수는 계속된 호출이 더 많은 스택 프레임을 추가하지 않는다.

## Nested Function

```scala
scala> def max(a: Int, b: Int, c: Int) = {
     |  def max(x: Int, y: Int) = if (x > y) x else y
     |  max(a, max(b, c))
     | }
def max(a: Int, b: Int, c: Int): Int

scala> max(42, 181, 19)
val res0: Int = 181
```

**중첩 함수\(nested function\)** 인 `max(Int, Int)` 내부의 로직은 한 번 정의되었지만, 외부 함수 내에서 두 번 사용함으로써 중복된 로직을 줄이고 전체 함수를 단순화한다. 여기에서 중첩된 내부 함수는 외부 함수와 이름이 같지만 두 함수의 매개변수가 다르기 때문에 두 함수 사이의 충돌은 없다. 스칼라 함수는 함수 이름과 매개변수 타입 목록으로 구분된다. 하지만 이름과 매개변수 타입이 똑같더라도 충돌이 일어나지는 않는데, 지역 함수가 외부 함수에 우선하기 때문이다.

## 이름으로 매개변수를 지정하여 함수 호출하기

스칼라에서는 이름으로 매개변수를 호출할 수 있으므로 매개변수를 순서와 관계없이 지정하는 것이 가능하다.

```scala
<function identifier> (<parameter> = <value>)
```

아래의 예제 중 첫 번째는 매개변수를 정의된 순서대로 지정하는 규칙을 따라 호출하였고, 두 번째는 매개변수 이름으로 값을 할당하여 호출하였다.

```scala
scala> def greet(prefix: String, name: String) = s"$prefix $name"
def greet(prefix: String, name: String): String

scala> val greeting1 = greet("Ms", "Brown")
val greeting1: String = Ms Brown

scala> val greeting2 = greet(name = "Brown", prefix = "Mr")
val greeting2: String = Mr Brown
```

## 기본값을 갖는 매개변수

```scala
def <identifier> (<identifier>: <type> = <value>): <type>
```

```scala
scala> def greet(prefix: String = "", name: String) = s"$prefix$name"
def greet(prefix: String, name: String): String

scala> val greeting1 = greet(name = "Paul")
val greeting1: String = Paul
```

표현 상, 기본값을 갖는 매개변수가 필수 매개변수 다음에 오도록 함수 매개변수를 구조화하는 것이 더 좋다. 이는 필수적인 매개변수의 중요성을 강조하는 동시에 기본 매개변수를 지정하지 않고 함수를 호출하고 매개변수 이름을 사용하지 않아도 되게 해준다.

## 가변 매개변수

스칼라에서는 **가변 매개변수\(varag paramter\)** 를 지원하여 정해지지 않은 개수의 입력 인수들로 함수를 정의할 수 있다. 함수 매개변수를 하나 이상의 입력 인수와 일치하는 것으로 표시하려면, 함수 정의에서 매개변수 타입 뒤에 별표 `*`를 추가하면 된다.

```scala
scala> def sum(items: Int*): Int = {
     |  var total = 0
     |  for (i <- items) total += i
     |  total
     | }
def sum(items: Int*): Int

scala> sum(10, 20, 30)
val res1: Int = 60

scala> sum()
val res2: Int = 0
```

## 매개변수 그룹

스칼라는 매개변수 리스트를 여러 그룹의 매개변수로 나누는 방식을 제공하며, 각 **매개변수 그룹\(parameter group\)** 은 괄호로 구분된다.

```scala
scala> def max(x: Int)(y: Int) = if (x > y) x else y
def max(x: Int)(y: Int): Int

scala> val larger = max(20)(39)
val larger: Int = 39
```

이점이 없는 것처럼 보이지만 함수 리터럴과 함께 사용하면 굉장히 유용하다.

## 타입 매개변수

스칼라에서는 값 매개변수를 보완하기 위해 값 매개변수 또는 반환값에 사용될 타입을 지시하는 **타입 매개변수\(type parameter\)** 를 전달할 수도 있다. 타입 매개변수를 사용하면 이러한 타입들이 고정되어 있던 상태에서 함수 호출자에 의해 설정될 수 있는 상태로 바뀜에 따라 함수의 유연성과 재사용성이 높아질 수 있다.

```scala
def <function identifier>[type](<parameter>: <type>): <type>...
```

```scala
scala> def identity[A](a: A): A = a
def identity[A](a: A): A

scala> val s: String = identity[String]("Hello")
val s: String = Hello

scala> val d: Double = identity[Double](2.717)
val d: Double = 2.717
```

위 함수의 타입 매개변수는 `A`이며, 값 매개변수 `a`처럼 유일한 식별자다. `A`는 값 매개변수 `a`와 함수의 반환 타입을 정의하기 위해 사용된다. 이 함수를 `[String]`으로 호출하여 함수 호출 범위 내에서 값 매개변수의 타입과 반환 타입을 `String`으로 전환할 수 있다.  
스칼라의 타입 추론 덕분에 타입 매개변수와 값의 타입을 전달히자 않을 수도 있다.

```scala
scala> val s = identity("Hello")
val s: String = Hello

scala> val d = identity(2.717)
val d: Double = 2.717
```

## 메소드와 연산자

**메소드\(method\)** 는 클래스에서 정의된 함수로, 클래스의 모든 인스턴스에서 사용할 수 있다. 스칼라에서 메소드를 호출하는 표준 방식은 메소드 이름 앞에 인스턴스의 이름과 점 구분자를 붙이는 **삽입점 표기법\(infix dot notation\)** 을 사용하는 것이다.

```scala
<class instance>.<method>[(parameter)]
```

```scala
scala> val s = "vacation.jpg"
val s: String = vacation.jpg

scala> val isJPEG = s.endsWith(".jpg")
val isJPEG: Boolean = true
```

```scala
scala> val d = 65.642
val d: Double = 65.642

scala> d.round
val res3: Long = 66

scala> d.floor
val res4: Double = 65.0

scala> d.compare(18.0)
val res5: Int = 1

scala> d.+(2.721)
val res6: Double = 68.363
```

