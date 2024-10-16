# Index
Patterns:
1. [Chain of responsibility](#chain)
2. [Command](#command)
3. [Iterator](#iterator)
4. [Mediator](#mediator)
5. [Memento](#memento)
6. [Observer](#observer)
7. [State](#state)
8. [Strategy](#strategy)
9. [Template](#template)
10. [Visitor](#visitor)

[Conclusions and differences among patterns](#conclusions)



<a name="chain"></a>
# Chain of responsibility [\^](#index)

## Description
Chain of Responsibility is a behavioral design pattern that lets you pass requests along a chain of handlers. 
Upon receiving a request, each handler decides either to process the request or to pass it to the next handler in the chain.

## The problem
Our application needs to mock how an ATM machine works. Our application tells us how many bills for a given money amount must be given.

Initially, only 100 and 50 € bills are emitted, so it's pretty simple. A couple of `if` should do. But, then I'm adding 20 € bills, and from a couple of `if` we get a lot a branching instructions. When I'm adding 10 € bills the complexity explodes.

Another example of CoR pattern is the SpringBoot interceptor logic and Java native try-multicatch block.

## The solution
The chain of responsibility pattern comes to help, because it transforms particular behaviours in stand-alone objects called handlers - passing the request along the chain along with its data.

Let's start by defining the `AbstracHandler` interface, which has a method that set the next handler and a method that dispense bills:

```java
public interface AbstracHandler {

	void setNextHandler(final AbstracHandler nextHandler);

	void dispense(final int money);

}
```

And all the hanlders, where the 100 € one will be the first:

```java
public class HoundredEuroHandler implements AbstracHandler {

	private AbstracHandler nextHandler;

	@Override
	public void setNextHandler(final AbstracHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void dispense(final int money) {
		if (money >= 100) {
			final int num = money / 100;
			final int remainder = money % 100;
			System.out.println("Dispensing " + num + " 100€ bill");
			if (remainder != 0) {
				this.nextHandler.dispense(remainder);
			}
		} else {
			this.nextHandler.dispense(money);
		}
	}

}
```

```java
public class FiftyEuroHandler implements AbstracHandler {

	private AbstracHandler nextHandler;

	@Override
	public void setNextHandler(final AbstracHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void dispense(final int money) {
		if (money >= 50) {
			final int num = money / 50;
			final int remainder = money % 50;
			System.out.println("Dispensing " + num + " 50€ bill");
			if (remainder != 0) {
				this.nextHandler.dispense(remainder);
			}
		} else {
			this.nextHandler.dispense(money);
		}
	}

}
```

```java
public class TwentyEuroHandler implements AbstracHandler {

	private AbstracHandler nextHandler;

	@Override
	public void setNextHandler(final AbstracHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public void dispense(final int money) {
		if (money >= 20) {
			final int num = money / 20;
			final int remainder = money % 20;
			System.out.println("Dispensing " + num + " 20€ bill");
			if (remainder != 0) {
				this.nextHandler.dispense(remainder);
			}
		} else {
			this.nextHandler.dispense(money);
		}
	}

}
```

```java
public class TenEuroHandler implements AbstracHandler {

	@Override
	public void setNextHandler(final AbstracHandler nextHandler) {
		//
	}

	@Override
	public void dispense(final int money) {
		final int num = money / 10;
		System.out.println("Dispensing " + num + " 10€ bill");
	}

}
```

Our application will then create the chain:
```java
final HoundredEuroHandler houndred = new HoundredEuroHandler();
		final FiftyEuroHandler fifty = new FiftyEuroHandler();
		final TwentyEuroHandler twenty = new TwentyEuroHandler();
		final TenEuroHandler ten = new TenEuroHandler();
		houndred.setNextHandler(fifty);
		fifty.setNextHandler(twenty);
		twenty.setNextHandler(ten);
```

And call it from the first handler:
```java
houndred.dispense(280);
```
which displays:
```
Dispensing 2 100€ bill(s)
Dispensing 1 50€ bill(s)
Dispensing 1 20€ bill(s)
Dispensing 1 10€ bill(s)
```

Another example:
```java
houndred.dispense(130);
```
which displays:
```
Dispensing 1 100€ bill(s)
Dispensing 1 20€ bill(s)
Dispensing 1 10€ bill(s)
```

Why is this cool? Beacause every handler decides itself to emit the bill or pass along the chain. 
In the first example all of the handlers are called, while in the second handlers that cannot handle the amount (50 € bill handler) simply pass along the chain the request.

## Pros and Cons

✔ the order of handling can be controlled

✔ decouple classes that invoke operations from classes that perform operations

✔ new handlers can be introduced easily

❌ some request may be not handled

## When to use

- there is the need to process different kinds of requests in various ways, but the exact types of requests and their sequences are
unknown
- the order of handlers is critical to be in a given order
- the order of handlers can change at runtime 

---

<a name="command"></a>
# Command [\^](#index)

## Description
Command is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request.
This transformation lets you parameterize methods with different requests, delay or queue a request’s execution, and support undoable operations.
Command design pattern is used to implement loose coupling in a request-response model.

## The problem
Let’s say we want to provide a File System utility with methods to open, write and close file. 
This file system utility should support multiple operating systems such as Windows and Unix. To implement our File System utility, first of all we need to create the receiver classes that will actually do all the work.

I can use a class for every OS:
- WindowsFileUtility
- UnixFileUtility

This is fine. But if tomorrow I have to implement a custom behaviour for different flavour of operating systems? The number of subclass will likely explode:
- Windows11FileUtility
- Windows10FileUtility
- Windows7FileUtility
- UbuntuFileUtility
- CentOSFileUtility
- ...

We cannot reuse the same code across all the instances (likely the command for opening a file will work very well for all the Linux flavors).

## The solution
The command pattern comes in help to loose the coupling of the command from its implementations.

Let's start by defining a FilySystemReceiver:

```java
public interface FileSystemReceiver {

	void openFile();
	void writeFile();
	void closeFile();
}
```

The we move on defining two implementations:
```java
public class UnixFileSystemReceiver implements FileSystemReceiver {

	@Override
	public void openFile() {
		System.out.println("Opening file in unix OS");
	}

	@Override
	public void writeFile() {
		System.out.println("Writing file in unix OS");
	}

	@Override
	public void closeFile() {
		System.out.println("Closing file in unix OS");
	}

}
```
```java
public class WindowsFileSystemReceiver implements FileSystemReceiver {

	@Override
	public void openFile() {
		System.out.println("Opening file in Windows OS");
		
	}

	@Override
	public void writeFile() {
		System.out.println("Writing file in Windows OS");
	}

	@Override
	public void closeFile() {
		System.out.println("Closing file in Windows OS");
	}

}
```
In command pattern, the `Receiver` is the entity that actual execute the command.

The command itself it's an object:
```java
public interface Command {

	void execute();
}
```
with all the concrete implementation (in our case 3):
```java
public class OpenFileCommand implements Command {

	private FileSystemReceiver fileSystem;
	
	public OpenFileCommand(FileSystemReceiver fs){
		this.fileSystem=fs;
	}
	@Override
	public void execute() {
		//open command is forwarding request to openFile method
		this.fileSystem.openFile();
	}

}
```

```java
public class CloseFileCommand implements Command {

	private FileSystemReceiver fileSystem;
	
	public CloseFileCommand(FileSystemReceiver fs){
		this.fileSystem=fs;
	}
	@Override
	public void execute() {
		this.fileSystem.closeFile();
	}

}
```
```java
public class WriteFileCommand implements Command {

	private FileSystemReceiver fileSystem;
	
	public WriteFileCommand(FileSystemReceiver fs){
		this.fileSystem=fs;
	}
	@Override
	public void execute() {
		this.fileSystem.writeFile();
	}

}
```

These two entities are encapsulated by the `Invoker` class.

```java
FileSystemReceiver fs = FileSystemReceiverUtil.getUnderlyingFileSystem();

//creating command and associating with receiver
OpenFileCommand openFileCommand = new OpenFileCommand(fs);

//Creating invoker and associating with Command
FileInvoker file = new FileInvoker(openFileCommand);

//perform action on invoker object
file.execute();

WriteFileCommand writeFileCommand = new WriteFileCommand(fs);
file = new FileInvoker(writeFileCommand);
file.execute();

CloseFileCommand closeFileCommand = new CloseFileCommand(fs);
file = new FileInvoker(closeFileCommand);
file.execute();
```

Our application:
```java
public class Application {

	public static void main(final String[] args) {
		final FileSystemReceiver fs = getUnderlyingFileSystem();

		// creating command and associating with receiver
		final OpenFileCommand openFileCommand = new OpenFileCommand(fs);

		// Creating invoker and associating with Command
		FileInvoker file = new FileInvoker(openFileCommand);

		// perform action on invoker object
		file.execute();

		final WriteFileCommand writeFileCommand = new WriteFileCommand(fs);
		file = new FileInvoker(writeFileCommand);
		file.execute();

		final CloseFileCommand closeFileCommand = new CloseFileCommand(fs);
		file = new FileInvoker(closeFileCommand);
		file.execute();
	}

	private static FileSystemReceiver getUnderlyingFileSystem() {
		final String osName = System.getProperty("os.name");
		System.out.println("Underlying OS is: " + osName);
		if (osName.contains("Windows")) {
			return new WindowsFileSystemReceiver();
		}
		return new UnixFileSystemReceiver();
	}

}

```

shows us how the pattern works:
```
Underlying OS is: Windows 10
Opening file in Windows OS
Writing file in Windows OS
Closing file in Windows OS

```

A real world analogy is going to the restaurant: you tell the waiter that you want a nice pizza and a cold beer. 
The waiter notes the order on a piece of paper which is then read by the chef who then proceeds to bake the pizza.

The piece of paper behaves like the `Command` interface: you don't need the implementation details of a command (how to bake the pizza) to use the results (eating the pizza). The waiter is the `Invoker` which dispatch the command to the `Receiver` (the chef)
On the other way the chef doesn't need to ask you details of the pizza (thus decoupling the request - you ordering the pizza - from the response), by also implementing queue mechanism.

Thread pools and Java runnable use the command pattern

## Pros and Cons

✔ you can decouple classes that invoke operations from classes that perform these operations

✔ you can introduce new commands into the app without breaking existing client code

✔ you can implement undo/redo

✔ you can implement deferred execution of operations

✔ you can assemble a set of simple commands into a complex one

❌ the code becomes more complicated since you’re introducing a whole new layer between senders and receivers

## When to use

- you want to parametrize objects with operations
- there is the need to queue operations, schedule their execution, or execute them remotely
- when you want to implement reversible operations

---

<a name="iterator"></a>
# Iterator [\^](#index)

## Description
Iterator is a behavioral design pattern that lets you traverse elements of a collection without exposing its underlying representation (list, stack, tree, etc.).

## The problem
Collections are the most used data types in programming: the collection is just a container for a group of elements.
Every collection uses a different logic to store data, like:
-  a list
-  a tree
-  a graph
-  a stack
-  a queue
-  ...

No matter how the collection implements the grouping of element but it must provide a way to access its elements.
This is simple if you have a list: simply loop the elements, but it's more complex for a tree: you want to traverse the tree depth-first this week, next month breath-first?

Adding more and more traversal algorithms to the collection gradually blurs its primary responsibility, which is efficient data storage. 
Additionally, some algorithms might be tailored for a specific application, so including them into a generic collection class would be weird.

On the other hand, the client code that’s supposed to work with various collections don't care how they store their elements. 
However, since collections all provide different ways of accessing their elements, you have no option other than to couple your code to the specific collection classes.
## The solution
The main idea of the Iterator pattern is to extract the traversal behavior of a collection into a separate object called an iterator.

In addition to implementing the algorithm itself, an iterator object encapsulates all of the traversal details, such as the current position, the next one and how many elements are left till the end.
Because of this, several iterators can go through the same collection at the same time, independently of each other.

Let's consider this example.

My application it's managing a list of podcasts. Every `Podcast` object has a `Topic`, representend in an enum:
```java
public enum Topic {

	TECH, NATURE, DIY, ALL

}
```

```java
public class Podcast {

	String author;
	Topic topic;

	public Podcast() {
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(final Topic topic) {
		this.topic = topic;
	}

}

```

We start by wrapping the podcast in a podcast collection and declaring a `PodcastIterator` interface:

```java
public interface PodcastCollection {

	public void addPodcast(Podcast c);
	
	public void removePodcast(Podcast c);
	
	public PodcastIterator iterator(Topic topic);
	
}

```
```java
public interface PodcastIterator {

	public boolean hasNext();

	public Podcast next();

}
```

We are giving the client the tools for iterating the collection so he doesn't need to bother to write logic to iterate the collection.

Let's define a list implementation:

```java
public class PodcastCollectionList implements PodcastCollection {

	private final List<Podcast> podcastList;

	public PodcastCollectionList() {
		this.podcastList = new ArrayList<>();
	}

	@Override
	public void addPodcast(final Podcast podcast) {
		this.podcastList.add(podcast);

	}

	@Override
	public void removePodcast(final Podcast podcast) {
		this.podcastList.remove(podcast);

	}

	@Override
	public PodcastIterator iterator(final Topic topic) {
		return new PodcastIteratorImpl(topic, this.podcastList);
	}

	private class PodcastIteratorImpl implements PodcastIterator {

		private final Topic topic;
		private final List<Podcast> podcasts;
		private int position;

		public PodcastIteratorImpl(final Topic topic, final List<Podcast> podcastList) {
			this.topic = topic;
			this.podcasts = podcastList;
		}

		@Override
		public boolean hasNext() {
			while (this.position < this.podcasts.size()) {
				final Podcast c = this.podcasts.get(this.position);
				if (c.getTopic().equals(this.topic) || this.topic.equals(Topic.ALL)) {
					return true;
				}
				this.position++;
			}
			return false;
		}

		@Override
		public Podcast next() {
			final Podcast c = this.podcasts.get(this.position);
			this.position++;
			return c;
		}

	}

}

```

We use the private inner class to avoid exposing this iterator for other implementations of our podcast collection.

Now, onto the client code:
```java
final PodcastCollection podcasts = new PodcastCollectionList();

podcasts.addPodcast(new Podcast("Mario Rossi", Topic.NATURE));
podcasts.addPodcast(new Podcast("Giuseppe Verdi", Topic.TECH));
podcasts.addPodcast(new Podcast("John Doe", Topic.TECH));
podcasts.addPodcast(new Podcast("Bogus Binted", Topic.ALL));

final PodcastIterator natureIterator = podcasts.iterator(Topic.NATURE);
final PodcastIterator techIterator = podcasts.iterator(Topic.TECH);
final PodcastIterator allIterator = podcasts.iterator(Topic.ALL);

while (natureIterator.hasNext()) {
	final Podcast p = natureIterator.next();
	System.out.println(p.toString());
}
System.out.println("------");
while (techIterator.hasNext()) {
	final Podcast p = techIterator.next();
	System.out.println(p.toString());
}
System.out.println("------");
while (allIterator.hasNext()) {
	final Podcast p = allIterator.next();
	System.out.println(p.toString());
}
```
Which outputs:
```
Podcast [author=Mario Rossi, topic=NATURE]
------
Podcast [author=Giuseppe Verdi, topic=TECH]
Podcast [author=John Doe, topic=TECH]
------
Podcast [author=Mario Rossi, topic=NATURE]
Podcast [author=Giuseppe Verdi, topic=TECH]
Podcast [author=John Doe, topic=TECH]
Podcast [author=Bogus Binted, topic=ALL]
```


## Pros and Cons

✔ clean up the client code and the collections by extracting bulky traversal algorithms into separate classes

✔ implement new types of collections and iterators and pass them to existing code without breaking anything

✔ terate over the same collection in parallel because each iterator object contains its own iteration state

✔ tne client doesn't need to write traversal algorithm

❌ useless if using simple collecitons

❌ less efficient than going through elements of some specialized collections directly
## When to use

- your collection has a complex data structure under the hood, but you want to hide its complexity from clients 
- reduce duplication of the traversal code across your app
- ou want your code to be able to traverse different data structures or when types of these structures are unknown beforehand

---

<a name="mediator"></a>
# Mediator [\^](#index)

## Description
Mediator is a behavioral design pattern that lets you reduce chaotic dependencies between objects. 
The pattern restricts direct communications between the objects and forces them to collaborate only via a mediator object.

## The problem
Let's assume our application manages a simple cooling system, made by a fan, a power supply and a button.
To turn on the fan, we need to press the button, but before the fan is turned on we need to turn on the power supply.

Let's see this an example implementation:
```java
public class Button {
    private Fan fan;

    // constructor, getters and setters

    public void press(){
        if(fan.isOn()){
            fan.turnOff();
        } else {
            fan.turnOn();
        }
    }
}
```
```java
public class Fan {
    private Button button;
    private PowerSupplier powerSupplier;
    private boolean isOn = false;

    // constructor, getters and setters

    public void turnOn() {
        powerSupplier.turnOn();
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
        powerSupplier.turnOff();
    }
}
```
```java
public class PowerSupplier {
    public void turnOn() {
        // implementation
    }

    public void turnOff() {
        // implementation
    }
}
```

This works good; but all the components are too coupled. Adding logic or changing something (by adding another power supply for example), we need to change the `Fan` class.
Also it's difficult to reuse the button class. 

## The solution
The Mediator pattern suggests that you should cease all direct communication between the components which you want to make independent of each other. 
Instead, these components must collaborate indirectly, by calling a special mediator object that redirects the calls to appropriate components. 
As a result, the components depend only on a single mediator class instead of being coupled to others.

We then define the `CoolingSystemMediator`:

```java
public class CoolingSystemMediator {
    private Button button;
    private Fan fan;
    private PowerSupplier powerSupplier;

    // constructor, getters and setters

    public void press() {
        if (fan.isOn()) {
            fan.turnOff();
        } else {
            fan.turnOn();
        }
    }

    public void start() {
        powerSupplier.turnOn();
    }

    public void stop() {
        powerSupplier.turnOff();
    }
}
```
and edit the other classes:
```java
public class Button {
    private CoolingSystemMediator mediator;

    // constructor, getters and setters

    public void press() {
        mediator.press();
    }
}
```
```java
public class Fan {
    private CoolingSystemMediator mediator;
    private boolean isOn = false;

    // constructor, getters and setters

    public void turnOn() {
        mediator.start();
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
        mediator.stop();
    }
}
```

Our application so will be:
```java
		final Fan fan = new Fan();
		final PowerSupplier powerSupplier = new PowerSupplier();
		final Button button = new Button();

		final CoolingSystemMediator mediator = new CoolingSystemMediator();
		mediator.setButton(button);
		mediator.setFan(fan);
		mediator.setPowerSupplier(powerSupplier);
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");
		button.press();
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");
		button.press();
		System.out.print("Fan is ");
		System.out.println(fan.isOn() ? "on" : "off");
```

and output:
```
Fan is off
Pressing the button
Turning on the power
Fan is on
Pressing the button
Turning off the power
Fan is off
```


## Pros and Cons

✔  You can extract the communications between various components into a single place, making it easier to comprehend and maintain

✔ You can introduce new mediators without having to change the actual components

✔ You can reduce coupling between various components of a

✔ You can reuse individual components more easily.

❌ Over time a mediator can evolve into a God Object.

## When to use

- Use the Mediator pattern when it’s hard to change some of the classes because they are tightly coupled to a bunch of other classes: all the relationships between classes are extracted into a separate class, isolating any changes to a specific component from the rest of the components.

- Use the Mediator when you find yourself creating tons of component subclasses just to reuse some basic behavior in various contexts.

---

<a name="memento"></a>
# Memento [\^](#index)

## Description
Memento is a behavioral design pattern that lets you save and restore the previous state of an object without revealing the details of its implementation.

## The problem
Let's assume you are writing a text editor. Every editor that you have used implements functionality for undoing and restoring edits.

The simple approach is, before performing any operation, let the editor records the state of all objects and saves it in some storage. 
Later, when a user decides to revert an action, the editor fetches the snapshots from the history and uses it to restore the state of all objects.

This may work if the objects involved in this backup and restore operation have relaxed access to fields that need to be backed up (i.e. all fields are public) which usually don't happen.

## The solution
The Memento pattern delegates creating the state snapshots to the actual owner of that state, the `originator` object. 
Hence, instead of other objects trying to copy the editor’s state from the “outside,” the editor class itself can make the snapshot since it has full access to its own state.

The object’s state is copied in a special object called `memento`. 
The contents of the memento aren’t accessible to any other object except the one that produced it. 

Other objects must communicate with mementos using a limited interface which may allow fetching the snapshot’s metadata (creation time, the name of the performed operation, etc.), but not the original object’s state contained in the snapshot.

Such a restrictive policy lets you store mementos inside other objects, usually called caretakers. 
Since the caretaker works with the memento only via the limited interface, it’s not able to tamper with the state stored inside the memento.

At the same time, the originator has access to all fields inside the memento, allowing it to restore its previous state at will.

Let's implement our editor:
```java
public class Editor {
	private static final Integer LINE_WITDH = 20;

	private String text = "";
	private Integer curX = 0;
	private Integer curY = 0;

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Integer getCurX() {
		return this.curX;
	}

	public void setCurX(final Integer curX) {
		this.curX = curX;
	}

	public Integer getCurY() {
		return this.curY;
	}

	public void setCurY(final Integer curY) {
		this.curY = curY;
	}

	public void type(final String text) {
		System.out.println(" *user is typing* ");
		this.text += text;
		this.curX += text.length() % LINE_WITDH;
		this.curY += text.length() / LINE_WITDH;
	}

	public Snapshot createSnapshot() {
		return new Snapshot(this, this.text, this.curX, this.curY);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		int cc = 0;
		while (cc < this.text.length()) {
			sb.append(this.text.charAt(cc));
			if (cc != 0 && cc % LINE_WITDH == 0) {
				sb.append("\n");
			}
			cc++;
		}
		return sb.toString() + "_";
	}

}
```

and our snapshot class
```java
public class Snapshot {

	private final Editor editor;
	private final String text;
	private final Integer curX;
	private final Integer curY;

	public Snapshot(final Editor editor, final String text, final Integer curX, final Integer curY) {
		this.editor = editor;
		this.text = text;
		this.curX = curX;
		this.curY = curY;
	}

	public void restore() {
		this.editor.setText(this.text);
		this.editor.setCurX(this.curX);
		this.editor.setCurY(this.curY);
	}

}
```

To help us, we implement a `SnapshotHandler` to help us manage snapshots:

```java
public class SnapshotHandler {

	private final Stack<Snapshot> snapshotList = new Stack<>();

	public void backup(final Snapshot snapshot) {
		this.snapshotList.add(snapshot);
	}

	public void undo() {
		System.out.println(" * user pressed CTRL+Z* ");
		if (!this.snapshotList.isEmpty()) {
			final Snapshot pop = this.snapshotList.pop();
			pop.restore();
		}
	}

}
```
Now, our app that mocks the text editor:
```java
final Editor editor = new Editor();
final SnapshotHandler backupHandler = new SnapshotHandler();
backupHandler.backup(editor.createSnapshot());
editor.type("Hello,");
System.out.println(editor);
backupHandler.backup(editor.createSnapshot());
editor.type(" there!");
System.out.println(editor);
backupHandler.undo();
System.out.println(editor);
backupHandler.undo();
System.out.println(editor);

```
which will output:
```
 *user is typing* 
Hello,_
 *user is typing* 
Hello, there!_
 * user pressed CTRL+Z* 
Hello,_
 * user pressed CTRL+Z* 
_

```
## Pros and Cons

✔ you can produce snapshots of the object’s state without violating its encapsulation

✔ you can simplify the originator’s code by letting the caretaker maintain the history of the originator’s state.

❌ caretakers should track the originator’s lifecycle to be able to destroy obsolete mementos

❌ possible high ram usage

## When to use

- when you want to produce snapshots of the object’s state to be able to restore a previous state of the object
- direct access to the object’s fields/getters/setters violates its encapsulation

---

<a name="observer"></a>
# Observer [\^](#index)

## Description
Observer is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they’re observing.
## The problem
Let's assume that you have two types of objects: a `Customer` and a `Store`.

The customer is interested in a particular object, like the new iPhone which will be available in the store very soon.

The customer could visit the store twice a day but while the product is still en route most of the trip will be pointless.

The store could, on the other hand, send tons of letters or email to all the customers when the iPhone is available, but this will result in spam for the customer not interested in the new iPhone

## The solution

The store in this scenario becomes the `Subject` to which the customers (the `Observer`s) will subscribe.

Let's define the `Subject` and `Observer` interfaces.

```java
public interface Subject {

	// methods to register and unregister observers
	public void register(Observer obj);

	public void unregister(Observer obj);

	// method to notify observers of change
	public void notifyObservers();

	// method to get updates from subject
	public Object getUpdate(Observer obj);

}
```
```java
public interface Observer {
	//method to update the observer, used by subject
	public void update();
	
	//attach with subject to observe
	public void setSubject(Subject sub);
}
```

The `Subject` interface has methods to register and unregister the observers, other the a method to notify the observers and to let them get updates from himself.

We will then implement the `Store` class.

```java
public class Store implements Subject {

	private final String storeName;
	private final List<Observer> customers;
	private String message;
	private boolean changed;
	private final Object mutex = new Object();

	public Store(final String storeName) {
		this.storeName = storeName;
		this.customers = new ArrayList<>();
	}

	@Override
	public void register(final Observer obj) {
		synchronized (this.mutex) {
			if (!this.customers.contains(obj)) {
				this.customers.add(obj);
			}
		}
	}

	@Override
	public void unregister(final Observer obj) {
		synchronized (this.mutex) {
			this.customers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> customersToBeNotified = null;
		// synchronization is used to make sure any observer registered after message is
		// received is not notified
		synchronized (this.mutex) {
			if (!this.changed) {
				return;
			}
			customersToBeNotified = new ArrayList<>(this.customers);
			this.changed = false;
		}
		for (final Observer obj : customersToBeNotified) {
			obj.update();
		}
	}

	@Override
	public Object getUpdate(final Observer obj) {
		return this.message;
	}

	public void postMessage(final String msg) {
		System.out.println(this.storeName + " posted " + msg);
		this.message = msg;
		this.changed = true;
		this.notifyObservers();
	}

}
```
The `mutex` object is used to synchronize the access to the list of customers.

The `Customer` class insted will be written this way:

```java
public class Customer implements Observer {

	private final String name;
	private Subject store;

	public Customer(final String customerName) {
		this.name = customerName;
	}

	@Override
	public void update() {
		final String msg = (String) this.store.getUpdate(this);
		if (msg == null) {
			System.out.println(this.name + " has no new message");
		} else {
			System.out.println(this.name + " received " + msg);
		}
	}

	@Override
	public void setSubject(final Subject sub) {
		this.store = sub;
	}

}
```

Both classes can be extended to have multiple topics in the `Subject` (think about a news feed, with lot of different topics) and to let the `Observer` subscribe to multiple subjects (like to multiple news feed).

More logic can be introduce to destroy the message once the observer consumed it.


```java
public class Store implements Subject {

	private final String storeName;
	private final List<Observer> customers;
	private String message;
	private boolean changed;
	private final Object mutex = new Object();

	public Store(final String storeName) {
		this.storeName = storeName;
		this.customers = new ArrayList<>();
	}

	@Override
	public void register(final Observer obj) {
		synchronized (this.mutex) {
			if (!this.customers.contains(obj)) {
				this.customers.add(obj);
			}
		}
	}

	@Override
	public void unregister(final Observer obj) {
		synchronized (this.mutex) {
			this.customers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> customersToBeNotified = null;
		// synchronization is used to make sure any observer registered after message is
		// received is not notified
		synchronized (this.mutex) {
			if (!this.changed) {
				return;
			}
			customersToBeNotified = new ArrayList<>(this.customers);
			this.changed = false;
		}
		for (final Observer obj : customersToBeNotified) {
			obj.update();
		}
	}

	@Override
	public String getUpdate(final Observer obj) {
		return this.message;
	}

	public void postMessage(final String msg) {
		System.out.println("-> " + this.storeName + " posted " + msg);
		this.message = msg;
		this.changed = true;
		this.notifyObservers();
		this.message = null;
	}

	@Override
	public String getId() {
		return this.storeName;
	}

}
```

```java
public class Customer implements Observer {

	private final String name;
	private List<Subject> stores;

	public Customer(final String customerName) {
		this.name = customerName;
	}

	@Override
	public void update() {
		for (final Subject store : this.stores) {
			final String msg = store.getUpdate(this);
			if (msg == null) {
				System.out.println(this.name + " has no new message from " + store.getId());
			} else {
				System.out.println("<- " + this.name + " received " + msg);
			}
		}
	}

	@Override
	public void addSubject(final Subject subject) {
		if (this.stores == null) {
			this.stores = new ArrayList<>();
		}
		this.stores.add(subject);
	}

}
```

Our application then will work this way:

```java
// create subject
final Store apple = new Store("Apple store");
final Store android = new Store("Android store");

// create observers
final Observer marco = new Customer("Marco");
final Observer mario = new Customer("Mario");
final Observer lorenzo = new Customer("Lorenzo");

// register observers to the subjects
apple.register(marco);
apple.register(mario);
apple.register(lorenzo);

android.register(marco);

// attach observer to subject
marco.addSubject(apple);
marco.addSubject(android);
mario.addSubject(apple);
lorenzo.addSubject(apple);

// check if any update is available
marco.update();
System.out.println("---");
// now send message to subjects
apple.postMessage("The new iPhone is here!");
System.out.println("---");
android.postMessage("The new Google Pixel is here!");
System.out.println("---");
// check if any update is available
marco.update();
```

that will output:
```
Marco has no new message from Apple store
Marco has no new message from Android store
---
-> Apple store posted The new iPhone is here!
<- Marco received The new iPhone is here!
Marco has no new message from Android store
<- Mario received The new iPhone is here!
<- Lorenzo received The new iPhone is here!
---
-> Android store posted The new Google Pixel is here!
Marco has no new message from Apple store
<- Marco received The new Google Pixel is here!
---
Marco has no new message from Apple store
Marco has no new message from Android store
```

## Pros and Cons

✔ it's possible to introduce new subscribers without having to change the publisher code (and viceversa)

✔ you can establish relations between objects at runtime

❌ caretakers should track the originator’s lifecycle to be able to destroy obsolete mementos

❌ subscribers are notified in random order

## When to use

- when changes to the state of one object may require changing other objects, and the actual set of objects is unknown beforehand or changes dynamically
-  some objects in your app must observe others, but only for a limited time or in specific cases

---

<a name="State"></a>
# State [\^](#index)

## Description

State is a behavioral design pattern that lets an object alter its behavior when its internal state changes. 
## The problem
Let's say you are designing a simple audio player app, that must supports the basic functions:
- start and stop the playback
- go to next/previous song
- lock or unlock the GUI

controlled by 4 buttons:
- play button
- pause button
- lock button

Let's assume our `Player` class has a structure like this:
```java
if(this.playButton.isPressed()){
	if(this.currentSong.isPlaying()){
		// do nothing
	} else {
		this.currentSong.play();
	}
} else if(this.pauseButton.isPressed()){
	if(this.currentSong.isPlaying()){
		this.currentSong.stop();
	} else {
		// do nothing
	}
} else ...

```
It's easy where this is going: every new button or condition bloat the logic of the player and reduce the maintenability of the code.

## The solution

We resort to the state pattern by using an analogy to FSM (Finite State Machine): each operation of the player is represented as a transition between internal states. For this example we will assume that the states are:
- playing
- ready (unlocked)
- locked

Instead of implementing all behaviors on its own, the original object, called context, stores a reference to one of the state objects that represents its current state, and delegates all the
state-related work to that object.

Our context will be the `AudioPlayer`:
```java
public class AudioPlayer {

	private State state;

	// other fields, like playlist(s), current song, volume, ...

	public AudioPlayer() {
		System.out.println("Starting up the player");
		this.state = new ReadyState(this);
	}

	public void changeState(final State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}

	// accessors for states logic

	public void clickLock(final boolean isKeptPressed) {
		this.state.clickLock(isKeptPressed);
	}

	public void clickPlay(final boolean isKeptPressed) {
		this.state.clickPlay(isKeptPressed);
	}

	public void clickNext(final boolean isKeptPressed) {
		this.state.clickNext(isKeptPressed);
	}

	public void clickPrevious(final boolean isKeptPressed) {
		this.state.clickPrevious(isKeptPressed);
	}

	protected void startPlayback() {
		System.out.println("Now playing your favorite song!");
	}

	protected void nextSong() {
		System.out.println("Skipping to next song");
	}

	protected void previousSong() {
		System.out.println("Skipping to previous song");
	}

	protected void stopPlayback() {
		System.out.println("Stopped playing");
	}

	protected void fastForward() {
		System.out.println("Going fast forward 5 seconds");
	}

	protected void rewind() {
		System.out.println("Rewinding 5 seconds");
	}

}
```

The context provides other than the logic (the methods `nextSong`, `fastForward`, ecc.) accessors for manipulating the internal private state via the buttons. 

Our states will implemente the `State` interface:
```java
public abstract class State {

	protected final AudioPlayer player;

	protected State(final AudioPlayer player) {
		this.player = player;
	}

	public abstract void clickLock(final boolean isKeptPressed);

	public abstract void clickPlay(final boolean isKeptPressed);

	public abstract void clickNext(final boolean isKeptPressed);

	public abstract void clickPrevious(final boolean isKeptPressed);

	public abstract String getStateName();

}
```

and every state will be implementing this interface.

Let's see for example the playing state implementation:
```java
public class PlayingState extends State {

	public PlayingState(final AudioPlayer player) {
		super(player);
	}

	@Override
	public void clickLock(final boolean isKeptPressed) {
		this.player.changeState(new LockedState(this.player));
	}

	@Override
	public void clickPlay(final boolean isKeptPressed) {
		this.player.stopPlayback();
		this.player.changeState(new ReadyState(this.player));
	}

	@Override
	public void clickNext(final boolean isKeptPressed) {
		if (isKeptPressed) {
			this.player.fastForward();
		} else {
			this.player.nextSong();
		}
	}

	@Override
	public void clickPrevious(final boolean isKeptPressed) {
		if (isKeptPressed) {
			this.player.rewind();
		} else {
			this.player.previousSong();
		}
	}

	@Override
	public String getStateName() {
		return "playing";
	}

}
```
The state handles what happens when one of the button is pressed, by calling logic of the player if needed and by changing state.

There are also cases in which no operation or state change is needed: for example if the player is locked if I press play button nothing happens:
```java
public class LockedState extends State {

	public LockedState(final AudioPlayer player) {
		super(player);
	}

	@Override
	public void clickLock(final boolean isKeptPressed) {
		if (this.player.getState().getStateName().equals("playing")) {
			this.player.changeState(new PlayingState(this.player));
		} else {
			this.player.changeState(new ReadyState(this.player));
		}

	}

	@Override
	public void clickPlay(final boolean isKeptPressed) {
		// locked, do nothing
	}

	@Override
	public void clickNext(final boolean isKeptPressed) {
		// locked, do nothing

	}

	@Override
	public void clickPrevious(final boolean isKeptPressed) {
		// locked, do nothing
	}

	@Override
	public String getStateName() {
		return "locked";
	}

}
```

## Pros and Cons

✔ organize the code related to particular states into separate classes

✔ introduce new states without changing existing state classes or the context

✔ implify the code of the context by eliminating bulky state machine conditionals

❌ applying the pattern can be overkill if a state machine has only a few states or rarely changes

## When to use

- Use this pattern when you have an object that behaves differently depending on its current state, there are lot of states and their logic changes frequently. This let you create new states without having too high manteance costs.
- you have a class polluted with massive conditionals that alter how the class behaves according to the current values of the class’s fields
-  you have a lot of duplicate code across similar states and transitions of a condition-based state machine

---

<a name="Strategy"></a>
# Strategy [\^](#index)
## Description

Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable

## The problem
You are developing for your company a map app  (in a parallel universe where Google or Apple Maps don't exists).

The first version of the app includes the map of the city. Users love this map, so the business ask you to add a new feature: an automatic route planner from point A to B.

You decide to implement the route planner only for cars: but not everybody owns a car and another feature to be added is the option of planning bike routes.

The business likes it even more, and after this feature they need you to add a route planning for sighstseeing by walking.

While from a business perspective the app was a success, the technical part caused you many headaches. 

Each time you added a new routing algorithm, the main class of the navigator increased in size. At some point, the beast became too hard to maintain.

## The solution

The strategy pattern suggests that a class that does something specific in different ways and extract all of these algorithms into separate strategies.

The original class, called context, must have a field for referencing one of the strategies.

The context delegate the work to the strategy instead of executing on its own.

Let's define our simple `Context` class

```java
public class Context {

	private Strategy strategy;

	public Context() {
	}

	public void setStrategy(final Strategy strategy) {
		this.strategy = strategy;
	}

	public void execute() {
		this.strategy.execute();
	}

}
```

This class contains the reference to the `Strategy` interface and the execution of the `execute` method from the strategy.

```java
public interface Strategy {

	void execute();

}
```

Our differente algorithms will be an implementation of this interface:

```java
public class WalkingStrategy implements Strategy {

	public WalkingStrategy() {
	}

	@Override
	public void execute() {
		System.out.println("Walking around the city sightseeing!");
	}

}
```
```java
public class DrivingStrategy implements Strategy {

	public DrivingStrategy() {
	}

	@Override
	public void execute() {
		System.out.println("Driving because in a hurry.");
	}

}
```

Our application will instantiate the context, set the desired strategy/strategies and execute it/them:
```java
public class Application {

	public static void main(final String[] args) {
		// instantiate the context
		final Context context = new Context();
		// set the first strategy
		context.setStrategy(new WalkingStrategy());
		// execute the strategy
		context.execute();
		// set another strategy
		context.setStrategy(new DrivingStrategy());
		// execute the strategy
		context.execute();
	}

}
```

```
Walking around the city sightseeing!
Driving because in a hurry.
```

The logic behind the instantiation of the strategy, if needed can be removed from the client and hidden in the context - by using something like a factory pattern.

## Pros and Cons

✔ you can swap algorithms used inside an object at runtime

✔ you can isolate the implementation details of an algorithm from the code that uses it

✔ implify the code of the context by eliminating bulky state machine conditionals

✔ you can replace inheritance with composition

❌ if you only have a couple of algorithms and they rarely change, there’s no real reason to overcomplicate the program 

❌ clients must be aware of the differences between strategies to be able to select a proper one

## When to use
- you want to use different variants of an algorithm within an object and be able to switch from one algorithm to another during runtime - this pattern lets you indirectly alter the object’s behavior at runtime by associating it with different sub-objects which can perform specific sub-tasks in different ways.
- you have a lot of similar classes that only differ in the way they execute some behavior.
- it's needed to isolate the business logic of a class from the implementation details of algorithms
  
<a name="template"></a>
# Template [\^](#index)
## Description

Template Method is a behavioral design pattern that defines the skeleton of an algorithm in the superclass but lets subclasses override specific steps of the algorithm without changing its structure.

## The problem

The application you are developing right now is a information extraction/retriever of informations from documents. 
In the first implementation only CSV files are supported.

The operations done by the algorithm are:
- open the file
- extract data
- parse the data
- analyze the data
- send the analisys to an index for retrieving
- close the file

Then the business requires to extract information from PDF, so the algorithm will be same, except for a couple of steps:
- open the file - same as the CSV
- extract data - customized for the PDF
- parse the data - customized for the PDF
- analyze the data - same as the CSV
- send the analisys to an index for retrieving - same as the CSV
- close the file - same as the CSV

By adding another type of file, i.e. a DOCX file, the same thing happens, leading to duplicating most of the steps.

## The solution

The Template Method pattern suggests that you break down an algorithm into a series of steps, turn these steps into methods, and put a series of calls to these methods inside a single "template method".

The steps may either be abstract, or have some default implementation (optional steps). 

To use the algorithm, the client is supposed to provide its own subclass, implement all abstract steps, and override some of the optional ones if needed.

Another type of step is called hook: an optional step with empty body ready for extension.

We start by defining the template class:

```java
public abstract class InformationExtractor {

	private final String filePath;

	protected InformationExtractor(final String filePath) {
		this.filePath = filePath;
	}

	// ----

	public void runExtraction() {
		this.openFile();
		this.extractData();
		this.parseData();
		this.analyzeData();
		this.sendDataToIndex();
		this.optionalStep();
		this.hook();
		this.closeFile();
	}

	// ----

	protected void openFile() {
		System.out.println("-----------------");
		System.out.println("Opening file @ " + this.filePath);
	}

	protected abstract void extractData();

	protected abstract void parseData();

	protected void analyzeData() {
		System.out.println("Analyzing data");
	}

	protected void sendDataToIndex() {
		System.out.println("Sending data to index");
	}

	protected void optionalStep() {
		System.out.println("Some operation in optional step");
	}

	protected void hook() {
	}

	protected void closeFile() {
		System.out.println("Closing file @ " + this.filePath);
	}
}
```

And the two implementations:

```java
public class PDFExtractor extends InformationExtractor {

	public PDFExtractor(final String filePath) {
		super(filePath);
	}

	@Override
	protected void extractData() {
		System.out.println("Extracting data from the PDF with its algorithm");
	}

	@Override
	protected void parseData() {
		System.out.println("Parsing the PDF read data");
	}

	@Override
	protected void hook() {
		System.out.println("Hook implemented by PDFExtractor");
	}

	@Override
	protected void optionalStep() {
		System.out.println("No operation in optional step by PDFExtractor");
	}
}
```

```java
public class CSVExtractor extends InformationExtractor {

	public CSVExtractor(final String filePath) {
		super(filePath);
	}

	@Override
	protected void extractData() {
		System.out.println("Extracting data from CSV with its algorithm");
	}

	@Override
	protected void parseData() {
		System.out.println("Parsing the CSV read data");
	}

	@Override
	protected void optionalStep() {
		super.optionalStep();
		System.out.println("Extending the optional step");
	}

}
```

As it's possible to see, only the `optionalStep` and the `hook` and the abstract method are reused, thus reusing most of the code.

Our application will simply call one of the extractor:

```java
public class Application {

	public static void main(final String[] args) {
		// let's extract data from PDF file
		final String pathToPdfFile = "./my_pdf.pdf";
		extractDataBasedOnFileType(pathToPdfFile);
		// let's extract data from PDF file
		final String pathToCsvFile = "./my_csv.csv";
		extractDataBasedOnFileType(pathToCsvFile);

	}

	private static void extractDataBasedOnFileType(final String filePath) {
		InformationExtractor extractor = null;
		if (filePath.endsWith("pdf")) {
			extractor = new PDFExtractor(filePath);
		}
		if (filePath.endsWith("csv")) {
			extractor = new CSVExtractor(filePath);
		}
		if (extractor == null) {
			throw new InvalidParameterException("Unknown file type");
		}
		extractor.runExtraction();
	}

}
```

```
-----------------
Opening file @ ./my_pdf.pdf
Extracting data from the PDF with its algorithm
Parsing the PDF read data
Analyzing data
Sending data to index
No operation in optional step by PDFExtractor
Hook implemented by PDFExtractor
Closing file @ ./my_pdf.pdf
-----------------
Opening file @ ./my_csv.csv
Extracting data from CSV with its algorithm
Parsing the CSV read data
Analyzing data
Sending data to index
Extending the optional step
Some operation in optional step
Closing file @ ./my_csv.csv

```

## Pros and Cons

✔ You can let clients override only certain parts of a large algorithm, making them less affected by changes that happen to other parts of the algorithm

✔ You can pull the duplicate code into a superclass

❌ Some clients may be limited by the provided skeleton of an algorithm

❌ Template methods tend to be harder to maintain the more steps they have

## When to use
- use the Template Method pattern when you want to let clients extend only particular steps of an algorithm, but not the whole algorithm or its structure
- use the pattern when you have several classes that contain almost identical algorithms with some minor differences

<a name="visitor"></a>
# Visitor [\^](#index)
## Description

Visitor is a behavioral design pattern that lets you separate algorithms from the objects on which they operate.

## The problem

Your application manages different shapes - each of them represented by its own class - `Circle`, `Dot`, `Square`, etc. - and there's the need to export the collection of shape in XML format.

A solution could be to add a new method in the `Shape` interface - `exportToXml` - and call it in the client.

```java
for(Shape shape : shapes){
	shape.exportToXml();
}
```

Being a good developer you think, what if a need to implement a method for exporting in `json`, `html`, `jpeg` and so on? The `Shape` interface would be bloated with logic.

## The solution
The visitor pattern suggests to implement a `Visitor` which will handle the incoming class `Shape` in our case.

There are 2 ways: one where the class handling lies in the visitor class and letting the client decide which class.

```java
public class Visitor {

	public Visitor() {

	}

	public void exportCircle(final Circle circle) {
		// export the circle
	}

	public void exportDot(final Dot dot) {
		// export dot
	}

	public void exportSquare(final Square square) {
		// export square
	}

}
```
```java
// ...
for(Shape shape : shapes){
	if(shape instanceof Circle){
		exportCircle((Circle) shape);
	} else if(shape instanceof Dot){
		exportDot((Dot) shape);
	} else if(shape instanceof Square){
		exportSquare((Square) square);
	} 
}
// ...
```

This approach works. but bloats the clients. 

A better approach is to add a method to the `Shape` class that accept the visitor.

```java
// Circle.java

public accept(Visitor visitor){
	visitor.exportCircle(this);
}

```

It's possible to define more visitors by declaring an interface for them - so I can instantiate different visitor inheriting from the same interface and thus having no need to modify the code in the `Shape` class. 

Our application will work this way:
```java
	// instantiate a list of shapes (any collection works
	final List<Shape> shapes = List.of(new Circle(), new Square(), new Dot());
	// instantiate the XML visitor
	final Visitor xmlVisitor = new XmlVisitor();
	// visit the shapes
	for (final Shape shape : shapes) {
		shape.accept(xmlVisitor);
	}
	System.out.println("-----------------");
	// instantiate the json visitor
	final Visitor jsonVisitor = new JsonVisitor();
	// visit the shapes
	for (final Shape shape : shapes) {
		shape.accept(jsonVisitor);
	}
```
```
Exporting circle as XML
Exporting square as XML
Exporting dot as XML
-----------------
Exporting circle as JSON
Exporting square as JSON
Exporting dot as JSON

```

## Pros and Cons

✔ you can introduce a new behavior that can work with objects of different classes without changing these classes

✔  you can move multiple versions of the same behavior into the same class

✔ a visitor object can accumulate some useful information while working with various objects. This might be handy when you want to traverse some complex object structure, such as an object tree, and apply the visitor to each object of this structure.

❌ You need to update all visitors each time a class gets added to or removed from the element hierarchy

❌ visitors might lack the necessary access to the private fields
and methods of the elements that they’re supposed to
work with

## When to use
- use the visitor when you need to perform an operation on all elements of a complex object structure (for example, an object tree)
- use the Visitor to clean up the business logic of auxiliary behaviors
- use the pattern when a behavior makes sense only in some classes of a class hierarchy, but not in others.


<a name="conclusions"></a>
# Conclusions and differences among patterns [\^](#index)
Behavioral design patterns focus on the interaction and communication between objects. Some of these patterns may seem similar because they provide solutions to problems of communication or coordination between objects, but each has a specific role.

Below is a comparison table that highlights the similarities and differences between the most similar behavioral patterns.

| **Pattern**                 | **Purpose**                                                                                                                        | **When to use it**                                                                                                       | **Similarities**                                                    | **Differences**                                                                                             |
|:---------------------------:|:----------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------:|
| **Chain of Responsibility** | Allows multiple objects to handle a request, passing the request along a chain of handlers.                                        | When a request can be handled by multiple handlers and you don't know in advance which one will handle the request.      | Similar to Command in separating the request from the handler.      | Handlers can be dynamic, and the request passes through a chain.                                            |
| **Command**                 | Encapsulates a request as an object, allowing operations like delay, cancellation, or undo.                                        | When you want to decouple the request sender from its receiver and support operations like undo.                         | Similar to Chain of Responsibility in separating the request.       | Each command is a separate object that can be stored and invoked later.                                     |
| **Observer**                | Defines a one-to-many dependency between objects, where one changes and automatically notifies the others.                         | When a state change in one object needs to notify other interested objects.                                              | Similar to Mediator, both facilitate communication between objects. | Observer is direct: the observed object sends notifications directly to the observers.                      |
| **Mediator**                | Defines a central object that controls and coordinates communication between objects.                                              | When you want to avoid a network of dependencies between objects by centralizing communication.                          | Similar to Observer in coordinating interactions.                   | Mediator centralizes communication, while Observer allows direct communication.                             |
| **State**                   | Allows an object to change its behavior when its internal state changes.                                                           | When an object's behavior depends on its internal state, and you want to avoid lengthy conditional statements (if-else). | Similar to Strategy, both allow dynamic behavior change.            | State is tied to internal states, while Strategy focuses on interchangeable algorithms.                     |
| **Strategy**                | Defines a family of interchangeable algorithms and separates the algorithm from the context where it is used.                      | When you have interchangeable algorithms that can be selected at runtime.                                                | Similar to State in changing an object's behavior.                  | Strategy focuses on algorithms, while State focuses on internal state changes.                              |
| **Template Method**         | Defines the structure of an algorithm, delegating some steps to subclasses.                                                        | When you want to allow subclasses to redefine certain steps of an algorithm without changing the overall structure.      | Similar to Strategy in defining interchangeable algorithms.         | Template Method uses inheritance, while Strategy uses composition (you can change the strategy at runtime). |
| **Visitor**                 | Separates an algorithm from the structure of the objects it operates on, allowing new operations without modifying object classes. | When you want to add new operations to a structure of objects without changing their classes.                            | Similar to Strategy in separating behavior from the object.         | Visitor traverses a structure of objects, while Strategy applies an algorithm to a single object.           |

## Key Comparisons:

### Chain of Responsibility vs. Command:
**Similarity**: Both separate the request from the object that handles it.

**Difference**: Chain of Responsibility passes the request along a chain of potentially multiple handlers, while Command handles the request as a specific object.

### Observer vs. Mediator:
**Similarity**: Both coordinate communication between objects.

**Difference**: Observer establishes a one-to-many relationship between objects, while Mediator centralizes all communication in a mediator object.

### State vs. Strategy:
**Similarity**: Both allow changing the behavior of an object based on dynamic conditions.

**Difference**: State is tied to the internal state change of an object, while Strategy allows dynamic changes in the algorithm or behavior used by an object.

### Strategy vs. Template Method:
**Similarity**: Both manage families of algorithms.

**Difference**: Strategy uses composition to dynamically change algorithms, while Template Method uses inheritance to allow subclasses to redefine specific steps of an algorithm.

### Strategy vs. Visitor:
**Similarity**: Both separate behavior from structure.

**Difference**: Visitor is designed to add new operations without changing the object classes and traverses a structure of objects, while Strategy focuses on dynamically choosing an algorithm for a single object.