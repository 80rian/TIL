# 객체, 케이스 클래스, 트레이트

## 객체

**객체\(object\)** 는 하나 이상의 인스턴스를 가질 수 없는 형태의 클래스로, 객체지향 설계에서는 **싱글턴\(singleton\)** 이라 한다. `new` 키워드로 인스턴스를 생성하는 대신 이름으로 직접 해당 객체에 접근한다. 객체는 실행 중인 JVM에서 최초로 접근할 때 자동으로 인스턴스화 된다. 즉, 객체에 처음 접근하기 전까지는 인스턴스화되지 않는다.  
객체는 필드와 메소드를 인스턴스화 가능한 클래스와 분리하여 전역 필드/메소드와 인스턴스 기반의 필드/메소드 간의 차이를 명확히 하고, 더 안전하고 이해하기 쉬운 설계를 제공하는 데 기여한다.  
객체 키워는 `class` 대신 `object`를 사용하여 정의한다. 객체는 어떤 매개변수도 취하지 ㅇ낳지만 필드, 메소드, 내부 클래스를 정의할 수 있다.

```scala
object <identifier> [extends <identifier>] [{filed, method, class}]
```

```scala
scala> object Hello { println("in Hello"); def hi = "hi" }
object Hello

scala> println(Hello.hi)
in Hello
hi

scala> println(Hello.hi)
hi
```

객체의 최상위 레벨의 `println`은 그 객체에 최초 접근하는 시점인 인스턴스화/초기화 시점에 호출된다. 이 객체의 `hi` 메소드를 반복적으로 호출하면 동일한 전역 인스턴스를 재사용하기 때문에 추가적인 초기화는 일어나지 않는다.

표준 클래스 메소드는 그 인스턴스의 필드를 읽어 들이거나 쓰기 위한 것이어서 그 데이터의 접근점과 비즈니스 로직을 보완하는 역할을 한다. 비슷하게, 객체에 가장 잘 어울리는 종류의 메소드는 순수 함수와 IO를 이용하여 동작하는 함수다.

```scala
scala> object HtmlUtils {
     |  def removeMarkup(input: String) = {
     |      input
     |          .replaceAll("""</?\w[^>]*>""", "")
     |          .replaceAll("<.*>", "")
     |  }
     | }
object HtmlUtils

scala> val html = "<html><body><h1>Introduction</h1></body></html>"
val html: String = <html><body><h1>Introduction</h1></body></html>

scala> val text = HtmlUtils.removeMarkup(html)
val text: String = Introduction
```

### Apply 메소드와 동반 객체

**동반 객체\(companion object\)** 는 클래스와 동일한 이름을 공유하며, 동일한 파일 내에서 그 클래스로 함께 정의되는 객체다. 클래스가 동반 객체를 가지는 것은 스칼라에서는 보편적인 패턴이지만, 이들에게 이점을 제공하는 특징도 있다. 동반 객체와 클래스가 접근 제어 관점에서는 하나의 단위로 간주되기 때문에 각각의 `private`와 `protected` 필드와 메소드에 서로 접근할 수 있다.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

class Multiplier(val x: Int) { def product(y: Int) = x * y }

object Multiplier { def apply(x: Int) = new Multiplier(x) }


// Exiting paste mode, now interpreting.

class Multiplier
object Multiplier

scala> val tripler = Multiplier(3)
val tripler: Multiplier = Multiplier@c631be9

scala> val result = tripler.product(13)
val result: Int = 39
```

예제에서 클래스 `Multiplier`는 숫자를 취하고 그 숫자를 다른 수에 곱하는 `product` 메소드를 제공한다. 이 클래스와 동일한 이름을 가지는 동반 객체는 인스턴스와 똑같은 매개변수를 가지는 `apply` 메소드를 가지고 있어서 사용자에게는 이 메소드가 클래스의 팩토리 메소드의 역할을 하고 있음을 명확히 한다.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

object DBConnection {
    private val db_url = "jdbc://localhost"
    private val db_user = "franken"
    private val db_pass = "berry"

    def apply() = new DBConnection
}

class DBConnection {
    private val props = Map(
        "url" -> DBConnection.db_url,
        "user" -> DBConnection.db_user,
        "pass" -> DBConnection.db_pass
    )
    println(s"Created new connection for " + props("url"))
}

// Exiting paste mode, now interpreting.

object DBConnection
class DBConnection

scala> val conn = DBConnection()
Created new connection for jdbc://localhost
val conn: DBConnection = DBConnection@2cc2532b
```

새로운 `DBConnection` 객체는 데이터베이스 연결 데이터를 프라이빗 상수들에게 저장하지만, 동일한 이름의 클래스는 연결을 생성할 때 이 상수들을 ㅇ릭을 수 있다. 상수는 전역 범위를 가지는데, 그 설정이 애플리케이션 전반에서 변하지 않으며, 시스템의 다른 어떤 부분에서 읽더라도 안전하기 때문이다.

### 객체를 가지는 명령줄 애플리케이션

스칼라는 객체에 `main` 메소드를 사용하여 애플리케이션의 진입점으로 애플리캐이션 작업을 지원한다. 명령줄 애플리케이션을 스칼라에서 생성하려면 입력 인수로 문자열 배열을 취하는 `main` 메소드를 추가하면 된다. 코드를 컴파일했다면 이제 객체 이름을 `scala` 명령어와 함께 실행하면 된다.

```bash
❯ cat > Date.scala 
object Date {
    def main(args: Array[String]): Unit = {
        println(new java.util.Date)
    }
}

❯ scalac Date.scala

❯ scala Date                 
Thu Jul 16 09:24:13 KST 2020
```

위 예제는 `Date` 객체를 `.class` 파일로 컴파일한 후에 이 객체를 애플리케이션으로 실행할 수 있게 된다. 이 에제는 명령줄 애플리케이션을 생성, 컴파일, 실행하는 기본을 보여주지만, 실제로 입력 인수 사용을 보여주지는 않는다.

```bash
❯ cat > Cat.scala
object Cat {
    def main(args: Array[String]): Unit = {
        for (arg <- args) {
            println ( io.Source.fromFile(arg).mkString )
        }
    }
}

❯ scalac Cat.scala 

❯ scala Cat Date.scala
object Date {
    def main(args: Array[String]): Unit = {
        println(new java.util.Date)
    }
}
```

이번에는 입력 인수를 사용하였다. 스칼라 라이브러리의 `io.Source` 객체에서 `fromFile` 메소드는 각 파일을 읽기 위해, 컬렉션 메소드 `mkString`는 출력을 위해 한 줄을 단일 `String`으로 전환하는 데 사용된다.

이 절을 요약하자면, 객체는 인스턴스 기반의 클래스에 대한 전역 범위의 대안일 뿐 아니라 명령줄 애플리케이션을 만드는 방법이다. 동반 객체로 클래스와 짝을 이루면 더 깔끔하고, 분리된, 그리고 더 읽기 쉬운 애플리케이션을 만드는 데 있어 새로운 시너지 효과를 낸다.

## 케이스 클래스

**케이스 클래스** 는 자동으로 생성된 메소드 몇 가지를 포함하는 인스턴스 생성이 가능한 클래스다. 또한, 케이스 클래스는 자동으로 생성되는 동반 객체를 포함하는데, 이 동반 객체도 자신만의 자동으로 생성된 메소드를 가지고 있다.클래스와 동반 객체에 있는 이 메소드들은 모두 클래스의 매개변수 목록에 기반을 두며, 이 매개변수들은 모든 필드를 반복적으로 비교하는 `equals` 구현과 클래스명 및 그 클래스의 모든 필드 값을 깔끔하게 출력하는 `toString` 메소드 같은 메소드들을 만드는 데 사용된다.

```scala
case class <identifier> ([var] <identifier>: <type>[, ...])
                         [extends <identifier>(<input parameter>)]
                         [{ field, method}]
```

_케이스 클래스 매개변수의 경우 `val` 키워드가 있다고 가정한다.기본적으로 케이스 클래스는 매개변수를 값 필드로 전환하므로 `val` 키워드를 매개 변수 앞에 붙일 필요는 없다._

| 이름 | 위치 | 설명 |
| :---: | :---: | :--- |
| `apply` | 객체 | 케이스 클래스를 인스턴스화하는 팩토리 메소드 |
| `copy` | 클래스 | 요청받은 변경사항이 반영된 인스턴스의 사본을 반환함 |
| `equals` | 클래스 | 다른 인스턴스의 모든 필드가 이 인스턴스의 모든 필드와 일치하면 true를 반환함. ==로도 호출할 수 있음 |
| `hashCode` | 클래스 | 인스턴스의 필드들의 해시 코드를 반환함 |
| `toString` | 클래스 | 클래스명과 필드들을 String으로 전환함 |
| `unapply` | 객체 | 인스턴스를 그 인스턴스의 필드들의 튜플로 추출하여 패턴 매칭에 케이스 클래스 인스턴스를 사용할 수 있도록 함 |

케이스 클래스를 위해 스칼라 컴파일러가 생성한 이 메소드들은 자동으로 생성된다는 점을 제외하고는 그다지 특별하지 않다. 케이스 클래스가 주는 이점은 편리함에 있는데, 이 모든 메소드를 모든 데이터 기반의 클래스에 대해 정확하게 작성하는 것은 엄청난 작업과 유지 보수가 필요하기 때문이다. 또한 모든 케이스 클래스는 동일한 특징들을 가지고 있어서 일정 수준의 일관성을 더한다.

```scala
scala> case class Character(name: String, isThief: Boolean)
class Character

scala> val h = Character("Hadrian", true)
val h: Character = Character(Hadrian,true)

scala> val r = h.copy(name = "Royce")
val r: Character = Character(Royce,true)

scala> h == r
val res0: Boolean = false

scala> h match {
     |  case Character(x, true) => s"$x is a thief"
     |  case Character(x, false) => s"$x is not a thief"
     | }
val res1: String = Hadrian is a thief
```

케이스 클래스가 부모 클래스의 필드를 고려할 필요가 없다면, 케이스 클래스가 코드 전반에 걸쳐 매우 유용하다. 표준 코드를 작성할 필요가 줄게 되며, 케이스 클래스의 유용한 `toString` 메소드로 디버깅과 로깅이 더 쉬워지며, 전체적으로 객체지향 프로그래밍이 더 즐거워진다.

## 트레이트

**트레이트\(trait\)** 는 다중 상속을 가능하게 하는 클래스 유형 중 하나다. 클래스, 케이스 클래스, 객체, 그리고 트레이트는 모두 하나 이상의 클래스를 확잘할 수 없지만, 동시에 여러 트레이트를 확장할 수는 있다. 그러나 다른 유형과 달리 트레이트는 인스턴스화 될 수 없다.  
트레이트는 다른 유형의 클래스와 똑같아 보이지만 객체와 마찬가지로 클래스 매개변수를 취할 수 없다. 그렇더라도 트레이트는 타입 매개변수를 취할 수 있어 재사용성이 뛰어나다.

```scala
trait <identifier> [extends <identifier>] [{ field, method, class}]
```

```scala
scala> trait HtmlUtils {
     |  def removeMarkup(input: String) = {
     |      input 
     |          .replaceAll("""</?\w[^>]*>""", "")
     |          .replaceAll("<.*>", "")
     |  }
     | }
trait HtmlUtils

scala> class Page(val s: String) extends HtmlUtils {
     |  def asPlainText = removeMarkup(s)
     | }
class Page

scala> new Page("<html><body><h1>Introduction</h1></body></html>").asPlainText
val res2: String = Introduction
```

`Page` 클래스는 이제 객체명을 지정하지 않고도 `removeMarkup` 메소드를 직접 사용할 수 있다. 여기에 새로운 키워드 `with`를 사용해보자.

```scala
scala> trait SafeStringUtils {
     | 
     |  // Option에 감싸여 있는 문자열의 정리된 버전을 반환하거나 정리된 문자열의 값이 비어있다면 None을 반환함
     |  def trimToNone(s: String): Option[String] = {
     |      Option(s) map(_.trim) filterNot(_.isEmpty)
     |  }
     | }
trait SafeStringUtils

scala> class Page(val s: String) extends SafeStringUtils with HtmlUtils {
     |  def asPlainText: String = {
     |      trimToNone(s) map removeMarkup getOrElse "n/a"
     |  }
     | }
class Page

scala> new Page("<html><body><h1>Introduction</h1></body></html>").asPlainText
val res3: String = Introduction

scala> new Page(" ").asPlainText
val res4: String = n/a

scala> new Page(null).asPlainText
val res5: String = n/a
```

새로운, 그리고 내구성이 더 뛰어난 `Page` 클래스는 이제 두 개의 트레이트를 확장하여 `null` 또는 빈 문자열에 메세지 n/a를 반환함으로써 처리할 수 있게 되었다.

이와 같이 상속될 클래스와 트레이트의 수평적인 리스트를 받아서 한 클래스가 다른 클래스를 확장하는 수직적 체인으로 재구성하는 절차를 **선형화\(linearization\)** 이라 한다. 선형화에 대해 이해해야 할 가장 중요한 점은 스칼라 컴파일러가 서로 확장하기 위해 트레이트와 선택적인 클래스를 어떤 순서로 배치하는가다. 다중 상속 순서, 가장 낮은 서브클래스로부터 가장 높은 기반 클래스까지, 즉 오른쪽부터 왼쪽 순으로 배치한다.

### 셀프 타입

**셀프 타입\(self type\)** 은 트레이트 애너테이션으로 그 트레이트가 클래스에 추가될 떄 특정 타입 또는 그 서브타입과 함께 사용되어야 함을 분명히 한다. 셀프 타입을 가지는 트레이트는 지정된 타입을 확장하지 않는 클래스에 추가될 수 없다.  
셀프 타입의 보편적인 용도는 입력 매개변수가 필요한 클래스에 트레이트로 기능을 추가하는 것이다. 트레이트는 입력 매개변수를 취하는 클래스를 쉽게 확장할 수 없는데, 트레이트 자체가 입력 매개변수를 취할 수 없기 때문이다. 그러나 트레이트는 셀프 타입으로 자신을 그 부모 클래스의 서브타입으로 선언한 후에 그 기능을 추가할 수 있다.

```scala
trait ..... { <identifier>: <type> => ...}
```

```scala
scala> class A { def hi = "hi" }
class A

scala> trait B { self: A =>
     |  override def toString = "B: " + hi
     | }
trait B

scala> class C extends B
                       ^
       error: illegal inheritance;
        self-type C does not conform to B's selftype B with A

scala> class C extends A with B
class C

scala> new C()
val res7: C = B: hi
```

```scala
scala> class TestSuite(suitename: String) { def start(): Unit = {} }
class TestSuite

scala> trait RandomSeeded { self: TestSuite =>
     |  def randomStart(): Unit = {
     |      util.Random.setSeed(System.currentTimeMillis)
     |      self.start()
     |  }
     | }
trait RandomSeeded

scala> class idSpec extends TestSuite("ID TESTS") with RandomSeeded {
     |  def testId(): Unit = { println(util.Random.nextInt != 1) }
     |  override def start(): Unit = { testId() }
     | 
     |  println("Starting...")
     |  randomStart()
     | }
class idSpec
```

셀프 타입으로, 트레이트는 입력 매개변수 지정하지 않고도 클래스를 확장할 수 있다. 이 방식은 트레이트에 제약사항이나 요구사항을 추가할 때 특정 맥락에서만 사용되는 것을 보장하는 안전한 방식이다.

### 트레이트를 이용하여 인스턴스화

트레이트에 의해 구현되었든, 그 트레이트의 서브타입으로부터 상속되었든 트레이트를 확장한 클래스는 그 트레이트의 필드와 메소드를 얻게 된다.  
트레이트를 사용하는 다른 방식으로는 **클래스가 인스턴스화 될 때** 클래스에 트레이트를 추가하는 것이다. 주어진 트레이트에 대한 종속성이 없거나 해당 트레이트에 대해 알지 못한 상태에서 정의된 클래스도 트레이트의 기능을 사용할 수 있다. 클래스 인스턴스화 시점에 추가된 트레이트는 그 클래스를 확장한다. 트레이트를 클래스에 추가할 때 `with` 키워드를 사용하고, `extends` 키워드는 사용할 수 없는데, 클래스는 실제로 그 트레이트를 확장하는 것이 아니라 트레이트에 의해 확장되기 때문이다.

```scala
scala> class A
class A

scala> trait B { self: A => }
trait B

scala> val a = new A with B
val a: A with B = $anon$1@207d6c18
```

이 클래스는 공식적으로 명명된 클래스 정의를 포함하지 않는 클래스와 트레이트의 조합을 포함하기 때문에 인스턴스의 클래스는 익명 클래스이다. 더 중요한 것은 트레이트 B가 클래스 A를 확장하는 인스턴스를 만들었다는 것이다.

트레이트로 인스턴스화하는 것의 실제 가치는 기존 클래스에 새로운 기능이나 설정을 추가하는 데 있다. 이 특징은 일반적으로 **종속성 주입\(dependency injection\)** 이라 알려져 있는데, 부모 클래스가 의존하는 실제 기능이 클래스 정의 시점 이후까지도 추가되지 않다가 클래스가 인스턴스화될 때에야 그 특징이 클래스에 주입되기 때문이다.

```scala
scala> class User(val name: String) {
     |  def suffix = ""
     |  override def toString = s"$name$suffix"
     | }
class User

scala> trait Attorney { self: User => override def suffix = ", esq." }
trait Attorney

scala> trait Wizard { self: User => override def suffix = ", Wizard" }
trait Wizard

scala> trait Reverser { override def toString = super.toString.reverse }
trait Reverser

scala> val h = new User("Harry P") with Wizard
val h: User with Wizard = Harry P, Wizard

scala> val g = new User("Ginny W") with Attorney
val g: User with Attorney = Ginny W, esq.

scala> val l = new User("Luna L") with Wizard with Reverser
val l: User with Wizard with Reverser = draziW ,L anuL
```

인스턴스화 시 클래스에 트레이트를 추가하는 것은 동일한 작업을 수행하는 클래스를 정의하는 것에 대한 일종의 손쉬운 대체 작업이다. 인스턴스화 트레이트로 유연성과 단순성을 얻게 되었으며, 불필요한 코드를 작성하는 것을 피하게 되었다. 인스턴스화 시점에 트레이트를 추가함으로써 무한대의 기능 조합이 가능하게 되었다.

