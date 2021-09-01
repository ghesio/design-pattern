package io.ghes.design_patterns.creational.prototype;

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
