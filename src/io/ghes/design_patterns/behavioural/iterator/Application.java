package io.ghes.design_patterns.behavioural.iterator;

public class Application {

	public static void main(final String[] args) {
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

	}

}
