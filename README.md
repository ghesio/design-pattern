# Design patterns

As Wikipedia states "in software engineering, a software design pattern is a general, reusable solution to a commonly occurring problem within a given context in software design. It is not a finished design that can be transformed directly into source or machine code. Rather, it is a description or template for how to solve a problem that can be used in many different situations. Design patterns are formalized best practices that the programmer can use to solve common problems when designing an application or system."

They are divided in 3 sub-categories: 
* Creational patterns provide the capability to create objects based on a required criterion and in a controlled way 
* Structural patterns are about organizing different classes and objects to form larger structures and provide new functionality
* Behavioral patterns are about identifying common communication patterns between objects and realize these patterns.

<a name="index"></a>
1. Creational patterns
    1. [Factory pattern](#factory)
    2. [Abstract Factory](#abstractfactory)
    3. [Builder](#builder)
    4. [Prototype](#prototype)
    5. [Singleton](#singleton)

---

## Creational patterns

<a name="factory"></a> 
### Factory pattern [\^](#index)

#### Problem
You have a logistic application - everything is transported by trucks so all the code lives in `Truck` class.

After some time the application becomes pretty popular, so you add the transport by ship method, which will use `Ship` class. Adding this new transportation method requires editing the whole codebase as it's coupled with `Truck` class. And when it's needed to add more transport method?

### Solution
The factory method suggest that the object creation is moved from the constructor to a special method called *factory*.

We write the `TransportInterface` interface with `sendPackage()` method. The `Truck` and `Ship` classes will extend this interface.

**TransportInterface**:
```
public interface TransportInterface {	

	public void sendPackage();

}
```

**Truck**:
```
public class Truck implements TransportInterface {
	
	// field, getters and setters

	@Override
	public void sendPackage() {
		System.out.println("Sending package via truck!");
	}

}
```

**Ship**:
```
public class Ship implements TransportInterface {
	
	// field, getters and setters

	@Override
	public void sendPackage() {
		System.out.println("Sending package via ship!");
	}

}
```
We then define the factory class `TransportFactory` which will be responsible for creating instances of `Transport` concrete implementations.

**TransportFactory**:
```
public class TransportFactory {
	
	// based on which type of transport it's needed return it
	// without the need to call single constructors
	public static TransportInterface getTransport(final String type) {
		switch (type) {
		case "truck": 
			return new Truck();
		case "ship":
			return new Ship();
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
	
}
```

Our application then will work this way:

```
// get a truck
TransportFactory.getTransport("truck").sendPackage();
// get a ship
TransportFactory.getTransport("ship").sendPackage();
```

Which will output:
```
Sending package via truck!
Sending package via ship!

```

### Pros and Cons
**\+** Tight coupling between the creator and the concrete products is avoided
**\+** The product creation is only in one point of the program so it's easier to mantain
**\+** It's possible to introduce new type of products in the program without breaking the existent codebase

**\-** The code may become more complicated since it's needed to create new subclasses to implement the pattern

---

<a name="abstractfactory"></a>
## Abstract Factory [\^](#index)

### Problem
You have a forniture shop which sells sofas and tables. Each of one of these items comes in different flavours, ancient and modern.
Clients get angry when they receive not matching forniture (like a modern sofa and an ancient table).
Vendors often create new items (like chairs) and new flavours (like victorian).
The application needs create forniture of the same type, and every time a new flavour/item is added there must be no need to rewrite core logic.

### Solution
The first thing the Abstract Factory pattern suggests is to explicitly declare interfaces for each distinct product of the product family. 
Then all variants of products follow those interfaces i.e. all chair variants implement the Chair interface, all table variants implement the Table interface and so on.

**ChairInterface**:
```
public interface ChairInterface {

	// print the type of chair
	public void getType();

}
```

**AncientChair**:
```
public class AncientChair implements ChairInterface {

	@Override
	public void getType() {
		System.out.println("This is an ancient chair!");
	}

}
```

**ModernChair**:
```
public class ModernChair implements ChairInterface {

	@Override
	public void getType() {
		System.out.println("This is a modern chair!");
	}

}
```

Table interface and implementation is omitted as it follows the chair one.

A `FornitureAbstractFactory` interface which will be the abstract factory and two factories implementation are created.

**FornitureAbstractFactory**:
```
public interface FornitureAbstractFactory {
	
	public ChairInterface getChair();
	
	public TableInterface getTable();

}
```

**AncientFactory**:
```
public class AncientFactory implements FornitureAbstractFactory {

	@Override
	public ChairInterface getChair() {
		return new AncientChair();
	}

	@Override
	public TableInterface getTable() {
		return new AncientTable();
	}

}
``````

**ModernFactory**:
```
public class ModernFactory implements FornitureAbstractFactory {

	@Override
	public ChairInterface getChair() {
		return new ModernChair();
	}

	@Override
	public TableInterface getTable() {
		return new ModernTable();
	}

}
```

The application will then define which type of factory is needed:

```
public class Application {

	private static FornitureAbstractFactory factory;

	private static void instantiateFactory(final String type) {
		switch (type) {
		case "modern":
			Application.factory = new ModernFactory();
			break;
		case "ancient":
			Application.factory = new AncientFactory();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	public static void main(String[] args) {
		// we read from somewhere (like from a property) which type of factory I want
		final String factoryType = "modern";
		// we instantiate the factory
		Application.instantiateFactory(factoryType);
		// we then create a chair and a table
		Application.factory.getChair().getType();
		Application.factory.getTable().getType();

	}

}
```

Executing this will result in:
```
This is a modern chair!
This is a modern table!

```
while changing the `factoryType` in `ancient` will print:

```
This is an ancient chair!
This is an ancient table!

```

### Pros and Cons

**\+** Products from different factories are compatible with each other
**\+** Tight coupling between the creator and the concrete products is avoided
**\+** The product creation is only in one point of the program so it's easier to mantain
**\+** It's possible to introduce new type of products in the program without breaking the existent codebase

**\-** The code becomes more complicated since it's needed to create new interfaces and factories

---

<a name="builder"></a>
## Builder [\^](#index)

### Problem
Your application needs to build an house. An house can be as much as simple as 4 walls, a roof, windows and a door, or it can have a pool, a garden, a garage and so on...

To manage all of this properties it's needed a field in the `House` object for each of them: `numberOfWindows`, `numberOfRooms`, `numberOfDoors`, `hasGarage`, `hasPool`, etc.. which will result in a series of huge constructors:
```
House myHouse = new House(5,6,7,true,false,null,...);
House myHouse = new House(3,3,2,null,null,null,...);
```

The constructors are very ugly and most of times most field will be unused (the percentage with house with swimming pool is low...)

### Solution
The builder pattern suggests that you extract the object construction code out of its own class and move it to separate objects called *builders*. 

Let's start by defining the house object:
**House**:
```
public class House {

	// some example fields
	private Integer numberOfWindows;
	private Integer numberOfRooms;
	private Integer numberOfDoors; 
	private Boolean hasGarage;
	private Boolean hasPool;
	
	public House() {
		// empty constructor, properties will be set by HouseBuilder
	}

	public void setNumberOfWindows(Integer numberOfWindows) {
		this.numberOfWindows = numberOfWindows;
	}

	public void setNumberOfRooms(Integer numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public void setNumberOfDoors(Integer numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}

	public void setHasGarage(Boolean hasGarage) {
		this.hasGarage = hasGarage;
	}

	public void setHasPool(Boolean hasPool) {
		this.hasPool = hasPool;
	}

	@Override
	public String toString() {
		return "House [numberOfWindows=" + numberOfWindows + ", numberOfRooms=" + numberOfRooms + ", numberOfDoors="
				+ numberOfDoors + ", hasGarage=" + hasGarage + ", hasPool=" + hasPool + "]";
	}

}
```

The `HouseBuilder` has methods to set all the properties of an house and to return it (the object is inaccessible during construction):
**HouseBuilder**:
```
public class HouseBuilder {

	private House house = new House();
	
	public HouseBuilder buildWindows(Integer number) {
		this.house.setNumberOfWindows(number);
		return this;
	}
	
	public HouseBuilder buildRooms(Integer number) {
		this.house.setNumberOfRooms(number);
		return this;
	}
	
	public HouseBuilder buildDoors(Integer number) {
		this.house.setNumberOfDoors(number);
		return this;
	}
	
	public HouseBuilder buildPool() {
		this.house.setHasPool(true);
		return this;
	}
	
	public HouseBuilder buildGarage() {
		this.house.setHasGarage(true);
		return this;
	}
	
	// reset method
	public void reset() {
		this.house = new House();
	}
	
	// get created house and reset
	public House getBuiltHouse() {
		House toReturn = this.house;
		this.reset();
		return toReturn;
	}
	
}
```

Our application then can arbitrarily build what it needs:
```
// instantiate a builder
HouseBuilder builder = new HouseBuilder();
// get a default house
House defaultHouse = builder.getBuiltHouse();
// get an house with a garage
House withGarage = builder.buildWindows(8).buildDoors(10).buildGarage().getBuiltHouse();
// get an house with a pool, lot of rooms but no garage
House bigger = builder.buildWindows(50).buildRooms(20).buildDoors(10).buildPool().getBuiltHouse();
System.out.println(defaultHouse);
System.out.println(withGarage);
System.out.println(bigger);

```

Here we see why every method in the `HouseBuilder` returns `this`. Common build routines can be extracted to a *director*
```
public class HouseDirector {
	
	private static HouseBuilder builder = new HouseBuilder();
	
	public House getHouseWithAPool() {
		return HouseDirector.builder.buildPool().getBuiltHouse();
	}
	
}

```

As the logic increases and become complex, the director is useful because instead of using concrete implementation of the built object and the builder itself we can use interface (instead of `House` use `Building` interface and instead of `HouseBuilder` use `BuildingBuilder` interface for example)

### Pros and Cons

**\+** Object can be constructed step-by-step, construction can be defered or steps can be run recursively
**\+** Same construction code when building various representations of products can be reused
**\+** Complex construction code is isolated from application business logic

**\-** The overall complexity of the code increases since the pattern requires creating multiple new classes.

---

<a name="prototype"></a>
## Prototype [\^](#index)

### Problem

Say you need to create an exact copy of an object. 
How would you do it? First, you have to create a new object of the same class. Then you have to go through all the fields of the original object and copy their values over to the
new object.

There are few problems:
 * not all objects can be copied that way because some of the object’s fields may be private and not visible from outside of the object itself.
* you have to know the object’s class to create a duplicate so your code becomes dependent on that class
* sometimes you only know the interface that the object follows, but not its concrete class, when, for example, a parameter in a method accepts any objects that follow some interface

### Solution

The Prototype pattern delegates the cloning process to the actual objects that are being cloned. 
The pattern declares a common interface for all objects that support cloning. 
This interface lets you clone an object without coupling your code to the class of that object. Usually, such an interface contains just the single `clone` method. In Java simply use the `java.lang.Cloneable` interface.

**Person**:
```
public class Person implements Cloneable {
	
	private int id;
	private String name;
	private String surname;
	private boolean isCloned = false;
	
	public Person(final int id, final String name, final String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		final Person toReturn = new Person(this.id, this.name, this.surname);
		toReturn.isCloned = true;
		return toReturn;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", isCloned=" + isCloned + "]";
	}
	
}
```

**Application**:
```
  // assume that there is some private logic to fill the class values other than this constructor
  Person myPerson = new Person(1, "John", "Doe");
  // clone him
  try {
    Person cloned = (Person) myPerson.clone();
    System.out.println(cloned);
  } catch (CloneNotSupportedException e) {
    System.out.println("Cloning not supported");
  }
  System.out.println(myPerson);
}
```

which will output:
```
Person [id=1, name=John, surname=Doe, isCloned=true]
Person [id=1, name=John, surname=Doe, isCloned=false]
```

### Pros and Cons
**\+** you can clone objects without coupling to their concrete classes
**\+** you can get rid of repeated initialization code in favor of cloning pre-built prototypes
**\+** you can produce complex objects more conveniently

**\-** cloning complex objects that have circular references is very tricky

---

<a name="singleton"></a>
## Singleton [\^](#index)

### The problem

Imagine having a shared resource, like a DB connection or a logger. You don't want to instatiate every time a new instance of the class but use the existent (like the one holding the DB conection or the file hanlder for logging).

### The solution

Singleton pattern comes in:
* Make the default constructor private, to prevent other objects from using the `new` operator with the Singleton class
* Create a static creation method that acts as a constructor. This method calls the private constructor to create an object and saves it in a static field. All following calls
to this method return the cached object.

There are some way to implement this pattern.

#### Eager singleton

The instance is created at class load via a constructor:
```
public class EagerSingleton {
	
	private static EagerSingleton instance = new EagerSingleton();
	
    //private constructor to avoid client applications to use constructor
	private EagerSingleton() {
	}
	
	public static EagerSingleton getInstance(){
        return instance;
    }
}
```

or via a static block which permits exception handling:
```
public class StaticSingleton {

    private static StaticSingleton instance;
    
    //private constructor to avoid client applications to use constructor
    private StaticSingleton(){}
    
    //static block initialization for exception handling
    static{
        try{
            instance = new StaticSingleton();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
    
    public static StaticSingleton getInstance(){
        return instance;
    }
}
```

#### Lazy singleton

The instance is created when it's neede, in other words at the first `getInstance` call.

```
public class LazySingleton {

	private static LazySingleton instance;

	private LazySingleton() {}

	public static LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}
```

This is correct, but it's not thread safe in multithreading enviroment. In that case we use a *
Double-Checked Locking* implementation.

```
public class LazySynchronizedSingleton {

	private static LazySynchronizedSingleton instance;

	private LazySynchronizedSingleton() {}

	public static LazySynchronizedSingleton getInstance(){
	    if(instance == null){
	        synchronized (LazySynchronizedSingleton.class) {
	            if(instance == null){
	                instance = new LazySynchronizedSingleton();
	            }
	        }
	    }
	    return instance;
	}
}
```

#### Other implementation

**Thread-safe, but with overhead**

```
public class ThreadSafeSingleton {

    private static ThreadSafeSingleton instance;
    
    private ThreadSafeSingleton(){}
    
    public static synchronized ThreadSafeSingleton getInstance(){
        if(instance == null){
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
    
}
```
Every time we get an instance we have a useless overhead acquiring the lock (it is useful only for first calls when the instance is still `null`)

**On demand initialization**
```
public class InitOnDemandSingleton {
    private static class InstanceHolder {
        private static final InitOnDemandSingleton INSTANCE = new InitOnDemandSingleton();
    }
    public static InitOnDemandSingleton getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
```

**ENUM implementation**:
```
public enum EnumSingleton {
    INSTANCE;

    // other methods...
}
```

### Pros and Cons

**\+** You can be sure that a class has only a single instance
**\+** You gain a global access point to that instance
**\+** If using the lazy implementation the singleton object is initialized only when it’s requested for the first time

**\-** The Singleton pattern can mask bad design, for instance, when the components of the program know too much about each other
**\-** The pattern requires special treatment in a multithreaded environment so that multiple threads won’t create a singleton object several times
**\-** It may be difficult to unit test the client code of the Singleton because many test frameworks rely on inheritance when producing mock objects. Since the constructor of the singleton class is private and overriding static methods is impossible in most languages, you will need to think of a creative way to mock the singleton. Or just don’t write the tests ;)