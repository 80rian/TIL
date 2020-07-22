# 표현식과 조건문
**표현식(expression)** 은 실행 후 반환하는 코드 단위를 말한다. 한 줄 이상의 코드를 중괄호로 묶으면 하나의 표현식으로 간주하며, 이를 **표현식 블록(expression block)** 이라고 한다.  
표현식은 기존 데이터를 변경하는 대신 값을 반환할 수 있기 때문에 함수형 프로그래밍의 기반이 된다.

## Expression
### 표현식으로 값과 변수 정의하기
```scala
val <identifier> [: <type>] = <expression>
var <identifier> [: <type>] = <expression>
```

### Expression Block
여러 표현식을 중괄호를 사용하여 하나로 묶어 단일 **표현식 블록(expression block)** 을 만들 수 있다. 표현식은 자신만의 **범위(scope)** 를 가지며, 해당 표현식 블록에 국한된 값과 변수를 포함한다. 그 블록의 마지막 표현식이 전체 블록의 반환값이 된다.
```scala
scala> val amount = {
     |  val x = 5 * 20
     |  x + 10
     | }
val amount: Int = 110
```

표현식 블록은 중첩이 가능하며, 표현식 블록의 각 레벨은 자신만의 고유 범위의 값과 변수를 가진다.

### Statement
**문장(statement)** 은 값을 반환하지 않는 표현식이다. 문장의 반환 타입은 값이 없음을 나타내는 `Unit`이다. 스칼라에서 보편적으로 사용되는 문장들에는 `println()` 호출과 값/변수 정의가 포함된다.
```scala
scala> val x = 1
val x: Int = 1
```

**문장 블록(statement block)** 은 표현식 블록과는 다르게 값을 반환하지 않는다. 문장 블록은 결과값이 없어서 보편적으로 기존 데이터를 수정하거나 그 어플리케이션 범위 밖을 변경하는 데 쓰인다.

## If/Else
### If
```scala
if (<Boolean expression>) <expression>
```
```scala
scala> if ( 47 % 3 > 0 ) println("Not a multiple of 3")
Not a multiple of 3
```

### If-Else
```scala
if (<Boolean expression>) <expression>
else <expression>
```
```scala
scala> val x = 10; val y = 20
val x: Int = 10
val y: Int = 20

scala> val max = if (x > y) x else y
val max: Int = 20
```
모든 것이 한 줄에 맞는다면 `if/else` 표현식에서 표현식 블록 없이 단일 표현식을 사용하는 것이 좋다. 하지만 `if/else` 표현식이 한 줄에 쉽게 맞아떨어지지 않는다면, 코드의 가독성을 위해서 표현식 블록 사용을 고려해야 한다. `else`가 없는 `if` 표현식은 언제나 중괄호를 사용해야 하는데, 이는 부작용을 일으키는 문장이 되는 경향이 있기 때문이다.

`if/else` 블록은 조건부 로직을 작성하는 보편적인 방식이지만 스칼라에서는 매치 **표현식(match expression)** 을 사용할 수 있다.

## Match Expression
### 매치 표현식(match expression)
```scala
<expression> match {
    case <pattern match> => <expression>
    [case ...]
}
```

이전 절의 `if/else` 예제를 매치 표현식으로 전환해보자.
```scala
scala> val x = 10; val y = 20
val x: Int = 10
val y: Int = 20

scala> val max = x > y match {
     |  case true => x
     |  case false => y
     | }
val max: Int = 20
```

매치 표현식의 또 다른 예로, 정수 상태 코드를 취해서 그에 가장 적합한 메세지를 반환해보자. 표현식의 입력값에 따라 값을 반환하는 것 외에 추가적인 동작을 할 수 있다.
```scala
scala> val status = 500
val status: Int = 500

scala> val message = status match {
     |  case 200 => "ok"
     |  case 400 => {
     |      println("ERROR - we called the service incorrectly")
     |      "error"
     |  }
     | case 500 => {
     |      println("Error - the service encountered an error")
     |      "error"
     |  }
     | }
Error - the service encountered an error
val message: String = error
```
`case` 블록 내부에 둘 수 있는 문장과 표현식 개수의 제약은 없지만, 마지막 표현식만 매치 표현식의 반환값으로 사용된다.  
여러 패턴을 하나로 결합하여 그 패턴 중 하나라도 일치하면 `case` 블록이 실행되는 **패턴 대안(pattern alternative)** 으로 만들 수 있다.

### Pattern Alternative
```scala
case <pattern 1> | <pattern 2> .. => <one or more expression>
```
**패턴 대안** 은 여러 패턴에 대해 동일한 `case` 블록을 재사용함으로써 코드 중복을 방지한다. 다음 예제는 파이프 기호 `|` 를 사용하여 일곱 가지 패턴에 대한  패치 표현식을 두 가지 패턴으로 줄인다.

```scala
scala> val day = "MON"
val day: String = MON

scala> val kind = day match {
     |  case "MON" | "TUE" | "WED" | "THU" | "FRI" =>
     |      "weekday"
     |  case "SAT" | "SUN" =>
     |      "weekend"
     | }
val kind: String = weekday
```

입력 표현식에 일치하지 못하는 매치 표현식은 오류 타입인 `scala.MatchError`가 런타임 에러가 발생한다.
```scala
scala> "match me" match { case "nope" => "sorry" }
scala.MatchError: match me (of class java.lang.String)
  ... 32 elided
```
매치 표현식을 방해하는 에러를 예방하려면 **wildcard match-all** 패턴을 사용하거나, 모든 가능한 입력 패턴을 포괄할 수 있을 만큼 충분한 패턴을 추가해야 한다.

### Wildcard Matching
와일드카드 패턴에는 값 바인딩(value binding)과 와일드카드 연산자가 있다.  
**값 바인딩(valule binding)** 을 이용하면 매치 표현식의 입력 패턴은 로컬 값에 바인딩되어 `case` 블록의 본문에서 사용할 수 있다. 이 패턴은 바인딩되어 있느 ㄴ값의 이름을 포함하고 있기 때문에 매칭할 실제 패턴이 없으며, 따라서 값 바인딩은 어떤 입력값에도 일치하므로 와일드카드 패턴이 된다.
```scala
case <identifier> => <one or more expression>
```
```scala
scala> val status = message match {
     |  case "OK" => 200
     |  case other => {
     |      println(s"Couldn't parse $other")
     |      -1
     |  }
     | }
val status: Int = 200
```
값 `other`는 `case` 블록이 유지되는 동안 정의되며, 매치 표현식의 입력값인 `message`의 값이 할당된다.

**와일드카드 연산자** 는 밑줄 `_` 기로홀 표시되며, 런타임에 표현식의 최종값이 들어갈 자리의 이름을 대신하여 자리표시자 역할을 한다. 값 바인딩과 마찬가지로, 밑줄 연산자는 매칭할 패턴을 제공하지 않기 때문에 어떤 입력값이라도 매칭되는 와일드카드 패턴이 된다.
```scala
case _ => <one or more expression>
```

값 바인딩과는 다르게, 와일드카드는 화살표 오른쪽에서 접근할 수 없다. `case`  블록에서 와일드카드의 값에 접근해야 한다면 값 바인딩을 쓰거나 매치 표현식의 입력값에 접근하는 것을 고려해야 한다.
```scala
scala> val message = "Unauthorized"
val message: String = Unauthorized

scala> val status = message match {
     |  case "OK" => 200
     |  case _ => {
     |      println(s"Couldn't parse $message")
     |      -1
     |  }
     | }
Couldn't parse Unauthorized
val status: Int = -1
```
이 경우 밑줄 연산자는 매치 표현식으 ㅣ실행시간 입력값을 매칭한다. 하지만 `case` 블록 내에서는 바인딩된 값처럼 접근할 수 없으므로 `println` 문장을 생성할 때는 매치 표현식의 입력값을 사용한다.

### Pattern Guard
**패턴 가드(pattern guard)** 는 값 바인딩 패턴에 `if` 표현식을 추가하여 match 표현식에 조건부 로직을 섞어 쓸 수 있게 한다. 패턴 가드를 사용하면 그 패턴은 `if` 표현식이 `true`를 반환할 때에만 매칭된다.
```scala
case <pattern> if <Boolean expression> => <one or more expression>
```
일반적인 `if` 표현식과 달리, 여기에서의 `if` 표현식은 `Boolean expression`을 둘러싸는 괄호가 필요 없다. 
```scala
scala> val response: String = null
val response: String = null

scala> response match {
     |  case s if s != null => println(s"Received '$s'")
     |  case s => println("Error! Received a null response")
     | }
Error! Received a null response
```

### 패턴 변수를 이용한 타입 매칭
입력 표현식의 타입을 매칭하여 패턴 매칭을 할 수도 있다. 매칭된다면 **패턴 변수(pattern variable)** 는 입력값을 다른 타입의 값으로 전환할 수 있다. 새로운 값과 타입은 `case`블록에서 사용할 수 있다.
```scala
case <identifier>: <type> => <one or more expressions>
```
패턴 변수는 반드시 소문자로 시작해야 한다.
```scala
scala> val x: Int = 12180
val x: Int = 12180

scala> val y: Any = x
val y: Any = 12180

scala> y match {
     |  case x: String => s"'x'"
     |  case x: Double => f"$x%.2f"
     |  case x: Float => f"$x%.2f"
     |  case x: Long => s"${x}l"
     |  case x: Int => s"${x}i"
     | }
val res3: String = 12180i
```
매치 표현식에 주어진 값이 `Any` 타입을 가지더라도 그 값이 가지고 있는 데이터가 `Int`로 생성되었다. 매치 표현식은 그 값에 주어진 타입 뿐 아니라 그 값의 실제 타입을 기반으로 매칭할 수 있다.

## Loop
**루프(loop)** 는 하나의 작업을 반복적으로 수행하는 것을 나타내는 용어로, 일정 범위의 데이터를 반복하거나 조건이 `false`를 반환할 때까지 반복한다.  
스칼라에서 가장 중요한 루프 구조는 `for` 루프로, **for-comprehension** 이라고도 한다. `for` 루프는 일정 범위의 데이터를 반복하며, 반복할 때마다 표현식을 실행한다. 그리고 선택적으로 그 실행문의 반환값들을 `collection`으로 돌려준다.  
먼저, `Range`라는 새로운 데이터 구조를 알아보자. `Range`는 시작과 끝을 나타내는 정수와 함께 `to` 또는 `until`연산자를 이용하여 생성할 수 있는데, `to`는 끝을 나타내는 정수를 모두 포함하는 리스트를 만드는 반면, `until`은 끝을 나타내는 정수를 포함하지 않는 리스트를 만든다.
```scala
<starting integer> [to|until] <ending integer> [by increment]
```

다음은 `for` 루프의 기본 정의를 나타낸다.
```scala
for (<identifier> <- <iterator>) [yield] [<expression>]
```
표현식과 함께 `yield`가 쓰였다면 호출된 모든 표현식의 반환값은 컬렉션으로 반환된다. 만일 `yield` 없이 표현식이 기술되어 있다면 그 표현식이 호출되기는 하지만, 그 반환값에는 접근할 수 없다.
```scala
scala> for (x <- 1 to 7) { println(s"Day $x:") }
Day 1:
Day 2:
Day 3:
Day 4:
Day 5:
Day 6:
Day 7:
```
만약 "Day X:" 메세지의 컬렉션이 필요하다면 `yield`를 사용할 수 있다.
```scala
scala> val dayList = for (x <- 1 to 7) yield { s"Day $x:" }
val dayList: IndexedSeq[String] = Vector(Day 1:, Day 2:, Day 3:, Day 4:, Day 5:, Day 6:, Day 7:)
```
`dayList`는 `collection`이기 때문에 다른 `for` 로프에서 반복자로 사용할 수 있다.
```scala
scala> for (day <- dayList) print(day + ", ")
Day 1:, Day 2:, Day 3:, Day 4:, Day 5:, Day 6:, Day 7:, 
```

### Itrator Guard
매치 표현식에서의 패턴 가드와 마찬가지로, **반복자 가드(iterator guard)** 또는 **필터(filter)** 는 반복자에 `if` 표현식을 추가한다.
```scala
for (<identifier> <- <iterator> if <Boolean expression>) ...
```
```scala
scala> val threes = for (i <- 1 to 20 if i % 3 == 0) yield i
val threes: IndexedSeq[Int] = Vector(3, 6, 9, 12, 15, 18)
```

반복자 가드는 반복자와 구분하여 별도의 줄로 등장할 수도 있다.
```scala
scala> for {
     |  t <- quote.split(",")
     |  if t != null
     |  if t.size > 0
     | }
     | { println(t) }
Faith
Hope
Charity
```

### Nested Iterator
**중첩된 반복자(nested iterator)** 는 `for` 루프에 추가된 부가적인 반복자로, 전체 반복 횟수를 자신의 반복 횟수만큼 반복한다.
```scala
scala> for {
     |  x <- 1 to 2
     |  y <- 1 to 3
     | }
     | { print(s"($x,$y) ") }
(1,1) (1,2) (1,3) (2,1) (2,2) (2,3) 
```

### Value Binding
`for` 루프에서 일반적인 전략은 현행 반복을 기반으로 하는 표현식 블록 내에 임시 값 또는 변수를 정의하는 것이다. 스칼라에서 이에 대응하는 다른 방법으로는 `for` 루프 정의에서 **값 바인딩(value binding)** 을 하는 것이다. 이는 동일한 작업을 하지만, 표현식 블록의 크기와 복잡도를 최소화할 수 있다.
```scala
for (<identifier> <- <iterator>; <identifier> = <expression>)
```

다음 예제에서 0부터 8까지 2의 거듭제곱 값을 계산하기 위해 `Int`에 '왼쪽 이동' 이항 연산자 `<<`를 사용하였다. 연산자에 들어갈 인수는 2의 곱셈을 효과적으로 하기 위해 1비트 씩 왼쪽으로 이동하는 횟수다.
```scala
scala> val powersOf2 = for (i <- 0 to 8; pow = 1 << i) yield pow
val powersOf2: IndexedSeq[Int] = Vector(1, 2, 4, 8, 16, 32, 64, 128, 256)
```
값 `pow`는 루프에서 반복할 때마다 정의되고 할당된다. 그 값은 `for` 루프에 의해 생성되므로 결과는 각 반복마다 산출된 `pow` 값의 `collection`이다.

## While과 Do/While Loop
스칼라에서는 `while`과 `do/while` 루프를 지원하지만 보편적으로 사용하지는 않는데, 이 루프가 표현식이 아니며 값을 생성(yield)하는 데에는 사용할 수 없기 때문이다.

### While
```scala
while (<Boolean Expression>) expression
```
```scala
scala> var x = 10; while (x > 0) x -= 1
var x: Int = 0
```

### Do/While
`do/while` 로프는 `while`과 유사하지만, 조건식을 처음 평가하기 전에 문장이 먼저 실행횐다.
```scala
scala> val x = 0
val x: Int = 0

scala> do println(s"Here I am, x = $x") while (x > 0)
Here I am, x = 0
```

<br>
<br>

## 연습문제
#### 1. 문자열 name이 주어졌을 때 빈 문자열이 아니면 동일한 문자열을 반환하고, 그렇지 않은 경우 "n/a" 문자열을 반환하는 매치 표현식을 작성해보자.
```scala
:load practice_1.scala
```
#### 2. double 타입의 amount가 주어졌을 때 그 값이 0보다 크면 "greater"를, 0과 같은 경우 "same"을, 0보다 작은 경우 "less"를 반환하는 표현식을 작성하라. if/else 블록과 매치 표현식 둘 다 작성해보자.


#### 3. 입력값 cyan, magenta, yellow 중 하나를 문자열 형태의 여섯 문자의 16진수로 전환하는 표현식을 작성하라. 에러 조건을 처리하기 위해 어떤 일을 할 수 있는가?


#### 4. 1부터 100까지 숫자를 출력하되, 각 줄에 다섯 숮자씩 묶어서 출력하라.


#### 5. 1부터 100까지의 숫자를 출력하되, 3의 배수일 경우에는 "type"을 출력하고, 5의 배수인 경우에는 "safe"를, 그리고 3과 5의 배수인 경우에는 "typesafe"를 출력하는 표현식을 작성하라.

#### 6. 5번 문제의 답을 한 줄에 맞게 작성하라.
