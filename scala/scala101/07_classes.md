# 클래스

> **클래스\(class\)** 는 데이터 구조와 함수의 조합으로, 객체지향 언어의 핵심 구성 요소다. 값과 변수로 정의된 클래스는 필요한 만큼 여러 번 인스턴스화될 수 있으며, 각 인스턴스는 자신만의 입력 데이터로 초기화될 수 있다.  
> 클래스는 **상속\(inheritance\)** 으로 다른 클래스로 확장할 수 있어서 서브클래스와 슈퍼클래스의 계층 구조를 생성할 수 있다.  
> **다형성\(polymorphism\)** 은 서브클래스가 부모 클래스를 대신하여 작업하는 것이 가능하게 하며, **캡슐화\(encapsulation\)** 은 클래스의 외관을 관리하는 프라이버시 제어를 제공한다.

```scala
scala> class User
class User

scala> val u = new User
val u: User = User@fe09383

scala> val isAnyRef = u.isInstanceOf[AnyRef]
val isAnyRef: Boolean = true
```

`User` 클래스의 실제 부모 타입은 모든 인스턴스화가 가능한 타입의 루트인 `AnyRef`이다. `User` 클래스를 재셜계하여 좀 더 유용하게 만들어보자.

```scala
scala> class User {
     |  val name: String = "Yubaba"
     |  def greet: String = s"Hello from $name"
     |  override def toString = s"User($name)"
     | }
class User

scala> val u = new User
val u: User = User(Yubaba)

scala> println( u.greet )
Hello from Yubaba
```

이제 값과 메소드, 그리고 실제로 이 인스턴스의 내용을 보여주는 `toString` 메소드를 갖게 되었다.

이번에는 `name` 필드를 고정값에서 매개변수화된 값으로 바꾸어보자.

```scala
scala> class User(n: String) {
     |  val name: String = n
     |  def greet: String = s"Hello from $name"
     |  override def toString = s"User($name)"
     | }
class User

scala> val u = new User("Zeniba")
val u: User = User(Zeniba)

scala> println( u.greet )
Hello from Zeniba
```

클래스 매개변수 `n`은 여기에서 `name` 값을 초기화하기 위해 사용된다. 그러나 이 매개변수는 메소드 내부에서 사용될 수 없다. 클래스 매개변수는 필드를 초기화하거나 함수에 전달하기 위해서는 사용 가능하지만, 클래스가 생성되고 나면 그 매개변수를 사용할 수 없다.

초기화를 위해 크래스 매개변수를 사용하는 대신 필드 중 하나를 클래스 매개변수로 선언할 수 있다. 클래스 매개변수 앞에 `val` 또는 `var` 키워드를 추가하면 클래스 매개변수가 클래스의 필드가 된다.

```scala
scala> class User(val name: String) {
     |  def greet: String = s"Hello from $name"
     |  override def toString = s"User($name)"
     | }
class User

scala> val users = List(new User("Shoto"), new User("Art3mis"), new User("Aesch"))
val users: List[User] = List(User(Shoto), User(Art3mis), User(Aesch))

scala> val sizes = users map (_.name.size)
val sizes: List[Int] = List(5, 7, 5)

scala> val sorted = users sortBy (_.name)
val sorted: List[User] = List(User(Aesch), User(Art3mis), User(Shoto))

scala> val third = users find (_.name contains "3")
val third: Option[User] = Some(User(Art3mis))

scala> val greet = third map (_.greet) getOrElse "hi"
val greet: String = Hello from Art3mis
```

스칼라에서 클래스는 `extends` 키워드를 이용하여 다른 클래스로 확장할 수 있으며, `override` 키워드로 상속받은 메소드의 행위를 재정의할 수 있다. 클래스에서 필드와 메소드는 `this` 키워드를 이용하여 접근할 수 있으며, 그 클래스의 부모 클래스의 필드와 메소드는 `super` 키워드를 이용하여 접근할 수 있다. `super` 키워드는 메소드가 자신이 대체한 부모 클래승의 유사한 메소드에 여전히 접근해야 할 필요가 있을 때 특히 유용하다.

```scala
scala> class A {
     |  def hi = "Hello from A"
     |  override def toString = getClass.getName
     | }
class A

scala> class B extends A
class B

scala> class C extends B { override def hi = "hi C -> " + super.hi }
class C

scala> val hiA = new A().hi
val hiA: String = Hello from A

scala> val hiB = new B().hi
val hiB: String = Hello from A

scala> val hiC = new C().hi
val hiC: String = hi C -> Hello from A
```

다형성은 클래스가 호환되는 다른 클래스의 모양새를 띄게 해주는 능력이다. '호환되는' 이라는 말 뜻은 서브클래스의 인스턴스가 그 부모 클래스의 인스턴스를 대신해 사용될 수 있으나 그 반대로는 가능하지 않음을 의미한다. 서브클래스는 자신의 부모 클래스를 확장하므로 부모의 필드와 메소드를 100% 지원하지만 그 역은 성립하지 않을 수도 있다.

```scala
scala> val a: A = new A
val a: A = A

scala> val a: A = new B
val a: A = B

scala> val b: B = new A
                  ^
       error: type mismatch;
        found   : A
        required: B

scala> val b: B = new B
val b: B = B
```

스칼라 컴파일러는 `A`의 인스턴스가 `B`의 필요 타입과 호환되지 않음을 정확하게 지적한다. 이 상황에 대해 다른 말로 표현하면, `A`는 `B`의 필요 타입에 **맞지\(conform\)** 않는다. `B` 클래스는 `A`의 **확장\(extension\)** 버전으로, `A`의 필드와 메소드는 `B`의 하위집합이지 그 반대로는 성립하지 않는다.  
인스턴스의 리스트를 생성하려면, 리스트가 확실히 그 클래스 각가의 인스턴스를 포함할 수 있도록 그 리스트의 타입을 정의하여 모든 클래스와 호환되게 해야 한다.

```scala
scala> val misc = List(new C, new A, new B)
val misc: List[A] = List(C, A, B)

scala> val messages = misc.map(_.hi).distinct.sorted
val messages: List[String] = List(Hello from A, hi C -> Hello from A)
```

다행히도 스칼라 컴파일러는 세 인스턴스의 공통 타입으로 부모 클래스인 `A`를 추론하여 리스트의 타입 매개변수를 정확하게 설정할 수 있다.

## 클래스 정의하기

클래스는 타입의 정의로 핵심 타입이나 다른 클래스의 필드를 포함한다. 클래스는 그 필드에 동작하는 함수인 메소드와 중첩된 클래스 정의도 포함한다.

```scala
class <identifier> [extends <identifier>] [{ field, method, class}]
```

클래스 필드를 위한 저장소를 제공하는 메모리 할당인 클래스 **인스턴스\(instance\)** 에서 클래스의 메소드를 호출하거나 그 필드에 접근할 수 있다. 이처럼 클래스의 내용을 할당하기 위해 메모리에 적재하는 작업을 **인스턴스화\(instantition\)** 또는 인스턴스 생성이라고한다. `new` 키워드를 사용하면 이름으로 클래스의 인스턴스를 생성할 수 있으며, 이 때 이름에는 괄호가 있어도 또는 없어도 상관없다.  
클래스가 좀 더 유용하려면 클래스의 다른 필드와 메소드를 초기화하거나 클래스의 필드로 동작하기 위해 사용되는 입력값인 **클래스 매개변수\(class paramter\)** 를 취해야 한다.

```scala
class <identifier> ([val|var] <identifier>: <type> = <expression>[, ... ])
                    [extends <identifier>[type parameter](<input parameter>)]
                    [{ field, method }]
```

입력 매개변수가 있는 클래스는 프로그래머가 여러 인스턴스르 생성해야 하는 이유를 제공하는데, 각 인스턴스가 자신만의 고유 내용을 가질 수 있기 때문이다.

```scala
scala> class Car(val make: String, var reserved: Boolean) {
     |  def reserve(r: Boolean): Unit = { reserved = r }
     | }
class Car

scala> val t = new Car("Toyota", false)
val t: Car = Car@11e7d01

scala> t.reserve(true)

scala> println(s"My ${t.make} is now reserved? ${t.reserved}")
My Toyota is now reserved? true
```

클래스의 필드와 메소드는 인스턴스와 그 필드 또는 메소드를 `.` 으로 구분하는 표준 삽입점 표기법으로 접근할 수 있다. 인스턴스의 단일 매개변수 메소드를 호출할 때도 역시 중위 연산자 표기법이 사용될 수 있다.

만약 클래스가 매개변수를 취하는 클래스를 확장한 것이라면, 그 클래스의 정의에 매개변수가 포함되어 있는지 확인해야 한다. `extends`키워드 다음에 정의된 클래스는 필요에 따라 자신만의 입력 매개변수를 가질 수 있다.

```scala
scala> class Car(val make: String, var reserved: Boolean) {
     |  def reserve(r: Boolean): Unit = { reserved = r }
     | }
class Car

scala> class Lotus(val color: String, reserved: Boolean) extends Car("Lotus", reserved)
class Lotus

scala> val l = new Lotus("Silver", false)
val l: Lotus = Lotus@5f1f59e3

scala> println(s"Requested a ${l.color} ${l.make}")
Requested a Silver Lotus
```

```scala
scala> class Car(val make: String, var reserved: Boolean = true, val year: Int = 2015) {
     |  override def toString = s"$year $make, reserved = $reserved"
     | }
class Car

scala> val a = new Car("Acura")
val a: Car = 2015 Acura, reserved = true

scala> val l = new Car("Lexus", year = 2010)
val l: Car = 2010 Lexus, reserved = true

scala> val p = new Car(reserved = false, make = "Porsche")
val p: Car = 2015 Porsche, reserved = false
```

### 추상 클래스

**추상 클래스\(abstract class\)** 는 다른 클래스들에 의해 확장되도록 설계되었으나 정작 자신은 인스턴스를 생성하지 않는 클래스다. 추상 클래스는 정의할 때 `class` 키워드 앞에 `abstract` 키워드를 두어 표시한다.  
추상 클래스는 그 서브클래스들이 필요로 하는 핵심적인 필드와 메소드를 실제 구현물을 제공하지 않으면서 정의하는 데 사용될 수 있다. 다형성 덕분에 추상 클래스 타입을 가지는 값은 실제로 그 서브클래스 중 하나의 인스턴스를 가리킬 수 있으며, 결국 그 서브클래스에서 호출되는 메소드를 호출하게 된다.

```scala
scala> abstract class Car {
     |  val year: Int
     |  val automatic: Boolean = true
     |  def color: String
     | }
class Car

scala> new Car()
       ^
       error: class Car is abstract; cannot be instantiated

scala> class RedMini(val year: Int) extends Car {
     |  def color = "Red"
     | }
class RedMini

scala> val m: Car = new RedMini(2005)
val m: Car = RedMini@6d293993
```

```scala
scala> class Mini(val year: Int, val color: String) extends Car
class Mini

scala> val redMini: Car = new Mini(2005, "Red")
val redMini: Car = Mini@13fe5bb7

scala> println(s"Got a ${redMini.color} Mini")
Got a Red Mini
```

### 익명 클래스

부모 클래스의 메소드를 구현하는 덜 공식적인 방법은 재사용할 수 없고 이름도 없는 클래스 정의인 **익명 클래스\(anonymous class\)** 를 사용하는 것이다.  
일회적 익명 클래스를 정의하기 위해 부모 클래스의 인스턴스를 생성하고, 클래스명과 매개변수 다음의 중괄호 안에 구현 내용을 포함하면 된다

```scala
scala> abstract class Listener { def trigger: Unit }
class Listener

scala> val myListener = new Listener {
     |  def trigger: Unit = { println(s"Trigger at ${new java.util.Date}") }
     | }
val myListener: Listener = $anon$1@6773bab2

scala> myListener.trigger
Trigger at Wed Jul 15 13:05:10 JST 2020
```

`myListener` 값은 클래스 인스턴스이지만, 그 클래스 정의는 자신의 인스턴스를 생성하는 동일한 표현식의 일부다. 새로운 `myListener`를 생성하려면 익명 클래스를 다시 재정의해야 한다.

다음 예제는 한 줄에 익명 클래스의 인스턴스를 생성하고 이를 다른 줄에 있는 등록 함수에 전달하는 대신, 이 과정을 메소드 호출의 일부로 익명 클래스를 정의하는 하나의 단계로 결합한다.

```scala
scala> abstract class Listener { def trigger: Unit }
class Listener

scala> class Listening {
     |  var listener: Listener = null
     |  def register(l: Listener):Unit = { listener = l }
     |  def sendNotification(): Unit = { listener.trigger }
     | }
class Listening

scala> notification.register(new Listener {
     |  def trigger: Unit = { println(s"Trigger at ${new java.util.Date}") }
     | })

scala> notification.sendNotification
Trigger at Wed Jul 15 13:11:10 JST 2020
```

### 중복 정의된 메소드

**중복 정의된 메소드\(overloaded method\)** 는 호출자에게 선택권을 제공하는 전략이다. 클래스는 동일한 이름과 반환값을 갖지만, 다른 입력 매개변수를 갖는 둘 또는 그 이상의 메소드를 가질 수 있다. 하나의 메소드 이름에 여러 구현을 중복 정의함으로써 특정 이름으로 메소드를 호출하는 여러 방법을 사용할 수 있다.  
다음 메소드들은 동일한 이름을 가지고 있지만 다른 매개변수를 취한다. 예제 중 두 번째 중복 정의한 메소드는 자신의 매개변수를 적절히 수정한 다음 첫 번째 메소드를 호출한다.

```scala
scala> class Printer(msg: String) {
     |  def print(s: String): Unit = println(s"$msg: $s")
     |  def print(l: Seq[String]): Unit = print(l.mkString(", "))
     | }
class Printer

scala> new Printer("Today's Report").print("Foggy" :: "Rainy" :: "Hot" :: Nil)
Today's Report: Foggy, Rainy, Hot
```

동일한 이름과 입력 매개변수를 가지면서 다른 반환값을 내는 두 개의 메소드가 존재할 수는 없다. 연산자 중복 정의는 유용한 특징일 수 있지만, 많은 스칼라 개발자는 연산자 중복 정의보다는 기본값 매개변수를 사용하는 것을 선호한다.

### apply 메소드

`apply` 메소드는 떄로는 기본 메소드 또는 인젝터 메소드로 불리며, 메소드 이름 없이 호출될 수 있다.

```scala
scala> class Multiplier(factor: Int) {
     |  def apply(input: Int) = input * factor
     | }
class Multiplier

scala> val tripleMe = new Multiplier(3)
val tripleMe: Multiplier = Multiplier@6a8b47ac

scala> val tripled = tripleMe.apply(10)
val tripled: Int = 30

scala> val tripled2 = tripleMe(10)
val tripled2: Int = 30
```

`tripleMe` 인스턴스는 지정된 숫자를 3으로 곱할 때 `apply` 이름 없이도 사용될 수 있다. 메소드를 기본 메소드로 만들 때의 한 가지 잠재적 문제점은 코드가 이상하게 보일 수 있다는 점이다. `apply` 메소드는 리스트의 접근자 메소드처럼 썼을 때 상식적으로 이해가 되는 곳에만 사용하자.

### 지연값

**지연값\(lazy value\)** 은 자신이 처음 인스턴스화 될 때애만 생성된다. 지연값은 값을 정의할 때 `val` 키워드 앞에 `lazy` 키워드를 추가함으로써 만들 수 있다.

```scala
scala> class RandomPoint {
     |  val x = { println("creating x"); util.Random.nextInt }
     |  lazy val y = { println("now y"); util.Random.nextInt }
     | }
class RandomPoint

scala> val p = new RandomPoint()
creating x
val p: RandomPoint = RandomPoint@10b2f8ff

scala> println(s"Location is ${p.x}, ${p.y}")
now y
Location is -1526065182, 1026458100

scala> println(s"Location is ${p.x}, ${p.y}")
Location is -1526065182, 1026458100
```

지연값은 클래스 수명 내에 실행 시간과 성능에 민감한 연산이 한 번만 실행될 수 있음을 보장하는 훌륭한 방식이다. 지연값은 파일 기반의 속성, 데이터베이스 커넥션 열기, 그 외 정말 필요한 경우 단 한 번만 초기화되어야 하는 불변의 데이터와 같은 정보를 저장하는 데 보편적으로 사용된다.  
지연값의 표현식에서 이 데이터를 초기화함으로써 지연값이 클래스 인스턴스 수명 동안 최소 한 번 접근될 경우에만 이 표현식이 동작할 것이라고 확신할 수 있다.

## 패키징

**패키지\(package\)** 는 스칼라의 코드 체계를 위한 시스템이다. 패키지는 스칼라 코드를 점으로 구분된 경로를 사용하여 디렉토리별로 정리할 수 있게 해준다. 스칼라 소스 파일 맨 위에서 `package` 키워드를 사용하여 그 파일의 모든 클래스가 그 패키지에 포함됨을 선언하면 된다.

```scala
package <identifier>
```

스칼라의 패키지 명명법은 자바 표준을 따르며, 도메인을 거꾸로 작성한 후에 더 분류된 치계를 경로에 추가하면 된다. 예를 들어 Netflix에서 개발된 utilityy 메소드를 제공하는 스칼라 클래스는 'com.netflix.utilities' 이다.  
스칼라 소스 파일은 그 패키지와 일치하는 디렉토리에 저장되어야 한다. 예를 들어 'com.netflix.utilities' 패키지의 `DateUtilities` 클래스는 _com/netflix/utilities/DateUtilities.scala_ 에 저장되어야 한다. 스칼라 컴파일러는 생성된 `.class` 파일을 그 패키지에 일치하는 디렉토리 구조에 저장한다.

패키지 소스 파일을 생성하고 컴파일하여 테스트해보자. 우리는 `scalac` 명령어를 사용하여 소스 파일을 컴파일하고, 현재 디렉토리에 포함된 클래스를 생성할 것이다.

```bash
❯ mkdir -p src/com/oreilly    

❯ cat > src/com/oreilly/Config.scala
package com.oreilly

class Config(val baseUrl: String = "http://localhost")

❯ scalac src/com/oreilly/Config.scala

❯ ls com/oreilly/Config.class
com/oreilly/Config.class
```

`src` 디렉토리는 현재 디렉토리의 다른 항목들과 소스 코드를 구분하는 좋은 방법이지만, 실제로 컴파일러가 사용하지는 않는다. 컴파일러는 소스 파일까지의 상대 경로로 가져와 컴파일하고, 컴파일러를 실행한 데릭토리로부터 상대 경로로 클래스 파일을 생성한다.

### 패키징된 클래스에 접근하기

패키징된 클래스는 점으로 구분된 전체 패키지 경로와 클래스 이름으로 접근할 수 있다. 앞의 `Config` 라는 이름의 클래스는 `com.oreilly.Config`로 접근할 수 있다.  
java.util 패키지에 있는 JDK의 `Date` 클래스에 접근해보자.

```scala
scala> val d = new java.util.Date
val d: java.util.Date = Wed Jul 15 13:44:41 JST 2020
```

다른 패키지의 클래스에 접근하는 더 편리한 방식으로는 패키지를 현재 네임스페이스에 **임포트\(import\)** 하는 것이다. 이 방식으로 클래스는 패키지 접두어가 없어도 접근할 수 있다. 클래스를 임포트하려면 키워드 `import` 뒤에 그 클래스의 전체 경로와 이름을 작성하면 된다.

```scala
import <package>.<class>
```

```scala
scala> import java.util.Date
import java.util.Date

scala> val d = new Date
val d: java.util.Date = Wed Jul 15 13:46:51 JST 2020
```

우리가 인스턴스를 생성한 `Date` 클래스는 여전히 java.util 패키지에 존재하지만, 이제는 현재 네임스페이스의 일부이기도 하다.

`import` 명령어는 값을 반환하지 않는 문장이기 때문에 문장을 사용할 수 있는 곳이라면 어디에서나 등장할 수 있다.

```scala
scala> println("Your new UUID is " + {import java.util.UUID; UUID.randomUUID})
Your new UUID is f1b33db1-c6a0-4b0e-b0e3-ca3e4ee36ed6
```

이번에는 부분 패키지 경로를 이용하여 접근해보자.

```scala
scala> import java.util
import java.util

scala> val d = new util.Date
val d: java.util.Date = Wed Jul 15 13:53:36 JST 2020
```

이제 java.util 패키지의 클래스들은 `util` 패키지만 사용하여 접근할 수 있다.  
또한 스칼라에서는 `_` 연산자를 이용하여 패키지 전체 내용을 한 번에 임포트 할 수 있다.

```scala
scala> import collection.mutable._
import collection.mutable._

scala> val b = new ArrayBuffer[String]
val b: scala.collection.mutable.ArrayBuffer[String] = ArrayBuffer()

scala> b += "Hello"
val res0: b.type = ArrayBuffer(Hello)

scala> val q = new Queue[Int]
val q: scala.collection.mutable.Queue[Int] = Queue()

scala> q.enqueue(3, 4, 5)
val res1: q.type = Queue(3, 4, 5)

scala> val pop = q.dequeue
val pop: Int = 3

scala> println(q)
Queue(4, 5)
```

패키지의 모든 클래스와 하위 패키지를 임포트하는 데에는 잠재적인 단점이 있다. 임포트한 패키지가 네임스페이스에 이미 있는 이름과 중복되는 클래스 이름을 가지고 있다면 네임스페이스에 이미 있던 클래스는 더 이상 접근할 수 없게 된다.  
전체 패키지를 임포트하는 대신 **임포트 그룹\(import group\)** 을 사용할 수 있다. 이 특징을 사용하면 전체 패키지 대신 임포트할 여러 클래스의 이름을 나열할 수 있다.

```scala
import <package>.{<class 1>[, <class 2>, ...]}
```

```scala
scala> import collection.mutable.{Queue, ArrayBuffer}
import collection.mutable.{Queue, ArrayBuffer}

scala> val q = new Queue[Int]
val q: scala.collection.mutable.Queue[Int] = Queue()

scala> val b = new ArrayBuffer[String]
val b: scala.collection.mutable.ArrayBuffer[String] = ArrayBuffer()
```

클래스를 임포트할 때 별칭을 지정할 수도 있다.

```scala
import <package>.{<original name> => <alias>}
```

```scala
scala> import collection.mutable.{Map=>MutMap}
import collection.mutable.{Map=>MutMap}

scala> val m1 = Map(1 -> 2)
val m1: scala.collection.immutable.Map[Int,Int] = Map(1 -> 2)

scala> val m2 = MutMap(2 -> 3)
val m2: scala.collection.mutable.Map[Int,Int] = HashMap(2 -> 3)

scala> m2.remove(2); println(m2)
HashMap()
```

## 프라이버시 제어

코드 패키징은 결국 패키지 접근을 관리하기 위해 **프라이버시 제어\(privacy control\)** 를 사용할 수 있다는 점으로 귀결된다. 기본적으로 스칼라는 프라이버시 제어를 추가하지 않는다. 만일 클래스 내부에서만 처리되어야 할 가변적인 상태 정보처럼 프라이버시 제어를 해야 할 이유가 있다면 클래스에서 필드와 메소드 기반으로 프라이버시 제어를 추가할 수 있다.  
프라이버시 제어 중 하나는 필드와 메소드를 `protected`로 표시하는 것으로, 그 필드와 메소드의 접근을 동일 클래스 또는 그 클래스의 서브클래스의 코드에서만 가능하도록 제한한다. 해당 클래스 또는 서브클래스를 제외한 다른 코드에서는 접근할 수 없다.

```scala
scala> class User { protected val passwd = scala.util.Random.nextString(10) }
class User

scala> class ValidUser extends User { def isValid = !passwd.isEmpty }
class ValidUser

scala> val isValid = new ValidUser().isValid
val isValid: Boolean = true

scala> val passwd = new User().passwd
                               ^
       error: value passwd in class User cannot be accessed as a member of User from class $iw
        Access to protected value passwd not permitted because
        enclosing class $iw is not a subclass of
        class User where target is defined
```

더 엄격한 수준의 보호가 필요한 경우는 필드와 메소드를 `private`으로 표시하고, 그 필드와 메소드의 접근을 이를 정의한 클래스에서만 가능하도록 제한하는 것이다. 클래스 외부의 다른 어떤 코드도, 심지어 서브클래스에서도 해당 필드에 접근할 수 없다.

```scala
scala> class User(private var password: String) {
     |  def update(p: String): Unit = {
     |      println("Modifying the password!")
     |      password = p
     |  }
     |  def validate(p: String) = p == password
     | }
class User

scala> val u = new User("1234")
val u: User = User@625a534

scala> val isValid = u.validate("4567")
val isValid: Boolean = false

scala> u.update("4567")
Modifying the password!

scala> val isValid = u.validate("4567")
val isValid: Boolean = true
```

## 프라이버시 접근 변경자

키워드 `private`와 `protected`는 클래스-계층구조 제약을 제공하지만, 클래스 항목들에까지 세밀하게 접근 제어가 필요할 때가 있다. `private`와 `protected` 지정에 더하여 **접근 변경자\(access modifier\)** 를 기술함으로써 이 수준의 제어를 추가할 수 있다. 접근 변경자는 그러한 지정이 패키지, 클래스 또는 인스턴스와 같이 주정진 지점까지만 유효함을 명시하고 그 지점 이내에서는 비활성화된다. 접근 변경자의 또 다른 이점은 클래스의 접근 제어가 가능하다는 것이다. 클래스를 모든 사람에게 있어 `private`하다고 표시하는 것은 그다지 이점이 없지만, 클래스가 _패키지 외부의 모든 것_ 에 대해 `private` 하다고 표시하는 것은 매우 유용하다.

접근 변경자를 명시하기 위해서는 키워드 `private` 또는 `protected` 다음에 `[]` 안에 패키지 또는 클래스의 이름을 작성하거나, 아니면 `this`를 사용하면 된다.

```scala
scala> :paste -raw
// Entering paste mode (ctrl-D to finish)

package com.oreilly {

  private[oreilly] class Config {
    val url = "http://localhost"
  }

  class Authentication {
    private[this] val password = "jason"
    def validate = password.size > 0
  }

  class Test {
    println(s"url = ${new Config().url}")
  }
}


// Exiting paste mode, now interpreting.


scala> val valid = new com.oreilly.Authentication().validate
val valid: Boolean = true

scala> new com.oreilly.Config
                       ^
       error: class Config in package oreilly cannot be accessed as a member of package com.oreilly from class
```

스칼라의 접근 변경자는 `private` 구성원에 대한 엄격한 접근 제어 정책과 `protected` 구성원에 대한 상속 접근 정책의 개념에 유용한 보완책을 제공한다. 패키지 레벨 보호의 경우, 이 정책은 다른 클래스의 근접성에 기반하여 재정의할 수 있ㄷ. 반면, 인스턴스 레벨의 보호는 클래스의 실제 인스턴스를 기반으로 이 정책들에 추가적인 제약을 가중시킨다.

## 종단 클래스와 봉인 클래스

`protected`와 `private` 접근 제어와 변경자는 클래스 또는 그 멤버로의 접근을 전부 또는 위치에 기반하여 제한할 수 있다. 하지만 이 방식은 서브클래스 생성을 제한하는 능력은 없다.  
**종단 클래스\(final class\)** 구성원은 서브클래스에서 결코 재정의할 수 없다. 값, 변수, 메소드를 `final` 키워드로 표시하면 그 구현은 모든 서브클래스에서 사용할 구현임을 보장한다. 클래스 전체를 `final`로 표시할 수도 있는데, 이 경우에는 해당 클래스의 서브클래스를 만들 수 없도록 방지한다.  
만약 종단 클래스가 너무 제한적이라면, 대신 **봉인 클래스\(sealed class\)** 사용을 구려해보자. 봉인 클래스는 클래스의 서브크래스가 부모 클래스와 동일한 파일에 위치하도록 제한한다. 클래스는 클래스 정의 앞에 `class` 키워드와 함께 `sealed` 키워드를 붙임으로써 봉인할 수 있다. 봉인 클래스는 특정 서브클래스를 '알고' 참조하는 추상적인 부모 클래스를 구현하는 데 유용한 방법이다. 같은 파일 외부에 서브플래스를 생성하는 것을 제한함으로써 클래스 구조에 대하여 가정할 수 있으며, 그렇지 않으면 심각한 악영향을 미칠 수 있다.

