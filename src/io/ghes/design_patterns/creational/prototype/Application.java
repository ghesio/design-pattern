package io.ghes.design_patterns.creational.prototype;

public class Application {

	public static void main(String[] args) {
		// assume that there is some private logic to fill the class value other than
		// this constructor
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

}
