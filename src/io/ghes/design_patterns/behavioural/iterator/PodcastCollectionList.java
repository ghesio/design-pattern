package io.ghes.design_patterns.behavioural.iterator;

import java.util.ArrayList;
import java.util.List;

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
