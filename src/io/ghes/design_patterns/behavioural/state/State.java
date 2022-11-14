package io.ghes.design_patterns.behavioural.state;

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
