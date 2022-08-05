# Index
1. [Chain of responsibility](#chain)
2. [Command](#command)
3. [Iterator](#iterator)


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