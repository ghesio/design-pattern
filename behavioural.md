# Index
1. [Chain of responsibility](#chain)
2. [Command](#command)
3. [Iterator](#iterator)
4. [Mediator](#mediator)
5. [Memento](#memento)
6. [Observer](#observer)
7. [State](#state)


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

**\+** the order of handling can be controlled

**\+** decouple classes that invoke operations from classes that perform operations

**\+** new handlers can be introduced easily

**\-** some request may be not handled

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

**\+** you can decouple classes that invoke operations from classes that perform these operations

**\+** you can introduce new commands into the app without breaking existing client code

**\+** you can implement undo/redo

**\+** you can implement deferred execution of operations

**\+** you can assemble a set of simple commands into a complex one

**\-** the code becomes more complicated since you’re introducing a whole new layer between senders and receivers

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

**\+** clean up the client code and the collections by extracting bulky traversal algorithms into separate classes

**\+** implement new types of collections and iterators and pass them to existing code without breaking anything

**\+** terate over the same collection in parallel because each iterator object contains its own iteration state

**\+** tne client doesn't need to write traversal algorithm

**\-** useless if using simple collecitons

**\-** less efficient than going through elements of some specialized collections directly
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

**\+**  You can extract the communications between various components into a single place, making it easier to comprehend and maintain

**\+** You can introduce new mediators without having to change the actual components

**\+** You can reduce coupling between various components of a

**\+** You can reuse individual components more easily.

**\-** Over time a mediator can evolve into a God Object.

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

**\+** you can produce snapshots of the object’s state without violating its encapsulation

**\+** you can simplify the originator’s code by letting the caretaker maintain the history of the originator’s state.

**\-** caretakers should track the originator’s lifecycle to be able to destroy obsolete mementos

**\-** possible high ram usage

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

**\+** it's possible to introduce new subscribers without having to change the publisher code (and viceversa)

**\+** you can establish relations between objects at runtime

**\-** caretakers should track the originator’s lifecycle to be able to destroy obsolete mementos

**\-** subscribers are notified in random order

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

**\+** organize the code related to particular states into separate classes

**\+** introduce new states without changing existing state classes or the context

**\+** implify the code of the context by eliminating bulky state machine conditionals

**\-** applying the pattern can be overkill if a state machine has only a few states or rarely changes

## When to use

- Use this pattern when you have an object that behaves differently depending on its current state, there are lot of states and their logic changes frequently. This let you create new states without having too high manteance costs.
- you have a class polluted with massive conditionals that alter how the class behaves according to the current values of the class’s fields
-  you have a lot of duplicate code across similar states and transitions of a condition-based state machine