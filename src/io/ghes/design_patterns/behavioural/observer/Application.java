package io.ghes.design_patterns.behavioural.observer;

public class Application {

	public static void main(final String[] args) {
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
	}

}
