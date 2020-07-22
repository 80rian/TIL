# 데이터로 작업하기: 리터럴, 값, 변수, 타입

> - `literal`: 숫자 5, 문자 A, 텍스트 'Hello, World' 처럼 소스 코드에 바로 등장하는 데이터
> - `value`: 정의될 때 데이터가 할당될 수 있지만, 절대 재할당 될 수 없는 불변의 타입을 갖는 저장 단위
> - `variable`: 정의 시 데이터를 할당할 수 있으며 언제라도 데이터를 재할당 할 수 있는 가변의 타입을 갖는 저장 단위
> - `type`: 데이터의 종류
  
```scala
scala> val x: Int = 5
val x: Int = 5
```

```scala
scala> var a: Double = 2.72
var a: Double = 2.72

scala> a = 355.0 / 113.0
// mutated a
```
 <br> 
  
## value
value는 불변의, 타입을 갖는 스토리지 단위이며, 관례적으로 데이터를 저장하는 기본적인 방법이다.  
value는 이름과 할당된 데이터 모두 필요하지만, 명시적 타입이 있어야 하는 것은 아니다. 타입이 지정되지 않았다면 컴파일러는할당된 데이터를 기반으로 타입을 추론한다. (type inference)

```scala
scala> val x = 20
val x: Int = 20

scala> val gretting = "Hello, World"
val gretting: String = Hello, World

scala> val atSymbol = '@'
val atSymbol: Char = @
```
타입 추론을 사용하면 명시적으로 값의 타입을 작성할 필요가 없기 떄문에 코드를 작성할 때 유용하지만 누군가 작성한 코드를 읽으면서 그 값의 타입이 무엇인지 알아볼 수 없다면, 값 정의에 명시적 타입을 포함하는 것이 더 낫다.  
타입 추론이 데이터를 저장하는 데 사용할 올바른 타입을 추론하겠지만, 명시적인 타입을 대체하지는 않는다. 초기값과 호환되지 않는 타입으로 값을 정의하면 컴파일 에러가 발생한다.

<br>

## variable
CS에서 variable은 일반적으로 값을 저장하고 그 값을 가져올 수 있또록 할당되거나 예약된 메모리 공간에 대응하는 유일한 식별자를 의미한다. 메모리 공간이 예약되어 있는 동안에는 새로운 값을 계속 할당할 수 있다. 따랏 ㅓ메모리 공간의 내용은 동적이며 가변적이다.  
스칼라에서는 variable보다 value를 선호하는데, 이는 값을 사용하면 소스 코드가 안정적이며 예측할 수 있기 때문이다.
```scala
scala> var x = 5
var x: Int = 5

scala> x = x * 4
// mutated x
```
variable에 재할당이 가능하지만, 지정된 타입을 바꿀 수는 없으므로 그 변수의 타입과 호환되지 않는 타입의 데이터를 재할당할 수는 없다. 하지만 `Double` 타입 변수를 정희하고 여기에 `Int` 값을 할당하면, `Int` 숫자는 자동으로 `Double` 숫자로 전환될 수 있으므로 정상적으로 작동한다.
```scala
scala> var y = 1.5
var y: Double = 1.5

scala> y = 42
// mutated y
```
<br>

## Type
### 1. Numeric Data Types
type | description | size | rank
:---: | :--- | :---: | :---:
Byte | 부호 있는 정수 | 1 byte | 6
Short | 부호 있는 정수 | 2 byte | 5 
Int | 부호 있는 정수 | 4 byte | 4
Long | 부호 있는 정수 | 8 byte | 3
Float | 부호 있는 부동 소수점 | 4 byte | 2 
Double | 부호 있는 부동 소수점 | 8 byte | 1

스칼라는 높은 순위의 데이터 타입이 더 낮은 순위의 타입으로 자동 전환되는 것을 허용하지 않는다.  

```scala
scala> val l: Long = 20
val l: Long = 20

scala> val i: Int = l
                    ^
       error: type mismatch;
        found   : Long
        required: Int

scala> val i: Int = l.toInt
val i: Int = 20
```
<br>

literal | type | description
:---: | :---: | :---
5 | Int | 접두사/접미사 없는 정수 리터럴은 기본적으로 Int다
 0x0f | Int | 접두사 '0x'는 16진수 표기법을 의미한다.
5l | Long | 접미사 'l'은 Long 타입을 의미한다.
5.0 | Double | 젖ㅂ두사/접미사 없는 소수 리터럴은 기본 Double 형이다.
5f | Float | 'f' 접미사는 Float 타입을 나타낸다.
5d | Double | 'd' 접미사는 Double 타입을 나타낸다.

<br>

### 2. String
`String` 타입은 모든 프로그래밍 언어에서 가장 일반적인 핵심 타입 중 하나로, 텍스트의 '문자열'을 나타낸다.
```scala
scala> val hello = "Hello There"
val hello: String = Hello There

scala> val signature = "With Regards, \nYour friend"
val signature: String =
With Regards,
Your friend
```

숫자 타입과 마찬가지로, `String` 타입은 수학 연산자의 사용을 지원한다.
```scala
scala> val greeting = "Hello, " + "World"
val greeting: String = Hello, World

scala> val matched = (greeting == "Hello, World")
val matched: Boolean = true

scala> val theme = "Na " * 16 + "Batman!"
val theme: String = Na Na Na Na Na Na Na Na Na Na Na Na Na Na Na Na Batman!
```

여러 줄의 `String`은 큰따옴표 세 개를 이용하여 생성한다. 여러 줄의 문자열은 리터럴이며, 따라서 특수 문자의 시작을 나타내는 역슬래시를 인지하지 못한다.
```scala
scala> val greeting = """this is
     |     line of code"""
val greeting: String =
this is
    line of code
```

<br>

#### Interpolation
문자열의 첫 큰따옴표 전에 접두사 `s` 를 추가하고 `$`를 사용하여 외부 데이터에 대한 참조를 표시한다. (선택적으로 중괄호를 사용한다.)
```scala
scala> val approx = 355/113f
val approx: Float = 3.141593

scala> println("Pi, using 355/113, is about " + approx + ".")
Pi, using 355/113, is about 3.141593.

scala> println(s"Pi, using 35/113, is about $approx.")
Pi, using 35/113, is about 3.141593.

scala> println(s"Pi, using 35/113, is about ${approx}.")
Pi, using 35/113, is about 3.141593.
```

<br>

#### printf
문자 개수를 세거나 소수점 표시와 같은 데이터 서식을 제어하고자 할 때 쓴다.
```scala
scala> val item = "apple"
val item: String = apple

scala> println(f"I wrote a new $item%.3s today")
I wrote a new app today

scala> println(f"Enjoying this $item ${355/113.0}%.5f times today")
Enjoying this apple 3.14159 times today
```

<br>

#### Regular Expression
name | example | description
:---: | :--- | :---
matches | "Froggy went a courting" matches ".* courting" | 정규 표현식이 전체 문자열과 맞으면 true를 반환
replaceAll | "milk, tea, muck" replaceAll ("m[^ ]+k", "coffee") | 일치하는 문자열을 모두 치환 텍스트로 치환
replaceFirst | "milk, tea, muck" replaceFirst ("m[^ ]+k", "coffee") | 첫 번째로 일치하는 문자열을 치환 텍스트로 치환

<br>

정규 표현식의 고급 처리 기법을 위해선 `r` 연산자를 호출하여 문자열을 정규 표현식 타입으로 전환하면 된다. 이는 캡처 그룹을 지원하는 것과 함께 추가적인 검색/치환 연산을 처리할 수 있는 `Regex` 인스턴스를 반환한다.
```scala
scala> val input = "Enjoying this apple 3.13159 times today"
val input: String = Enjoying this apple 3.13159 times today

scala> val pattern = """.* apple ([\d.]+) times .*""".r
val pattern: scala.util.matching.Regex = .* apple ([\d.]+) times .*

scala> val pattern(amountText) = input
val amountText: String = 3.13159

scala> val amount = amountText.toDouble
val amount: Double = 3.13159
```

<br>
<br>

## Scala Type Hierarchy
![diagram](https://www.oreilly.com/library/view/learning-scala/9781449368814/images/lnsc_0201.png.jpg)

<br>

핵심 비숫자형 타입
type | description | instance
:---: | :--- | :---:
Any | 스칼라에서 모든 타입의 루트 | 불가
AnyVal | 모든 value 타입의 루트 | 불가
AnyRef | 모든 참조(value가 아닌) 타입의 루트 | 불가
Nothing | 모든 타입의 하위 클래스 | 불가
Null | null 값을 의미하는 모든 AnyRef 타입의 하위 클래스  | 불가
Char | 유니코드 문자 | 가능
Boolean | True of False | 가능
String | 문자열 | 가능
Unit | 값이 없음을 나타냄 | 불가


`Char` 리터럴은 작은따옴표로 작성되어 큰따옴표로 작성되는 `String` 리터럴과는 구분된다.

<br>

## Tuple
`tuple`은 둘 이상의 값을 가지는 순서가 있는 컨테이너로, 여기에 포함된 각각의 값은 서로 다른 타입을 가질 수 있다.리스트와 배열과는 달리 튜플의 요소들은 반복할 수 없다.
```scala
scala> val info = (5, "Korben", true)
val info: (Int, String, Boolean) = (5,Korben,true)
```

`tuple`의 각 항목은 1부터 시작하는 인덱스를 이용하여 접근할 수 있다.
```scala
scala> val name = info._2
val name: String = Korben
```

두 개의 항목을 가지는 `tuple`을 생성하는 다른 형식으로는 화살표 연산자(`->`)를 사용하는 방식이 있다. 이 방식은 tuple에서 키-값 쌍을 표현하는 가장 보편적인 방법이다.
```scala
scala> val red = "red" -> "0xff0000"
val red: (String, String) = (red,0xff0000)

scala> val reversed = red._2 -> red._1
val reversed: (String, String) = (0xff0000,red)
```

`tuple`은 데이터를 구조화하는 일반적인 방법을 제공하며, 데이터 처리를 위해 개별 항목들을 모아둘 필요가 있을 때 유용하다.