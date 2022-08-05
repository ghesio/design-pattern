package io.ghes.design_patterns.behavioural.iterator;

public interface PodcastCollection {

	public void addPodcast(Podcast podcast);

	public void removePodcast(Podcast podcast);

	public PodcastIterator iterator(Topic topic);

}
