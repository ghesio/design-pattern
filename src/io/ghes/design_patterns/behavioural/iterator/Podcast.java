package io.ghes.design_patterns.behavioural.iterator;

public class Podcast {

	String author;
	Topic topic;

	public Podcast() {
	}

	public Podcast(final String author, final Topic topic) {
		this.author = author;
		this.topic = topic;
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

	@Override
	public String toString() {
		return "Podcast [author=" + this.author + ", topic=" + this.topic + "]";
	}

}
