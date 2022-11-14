package io.ghes.design_patterns.behavioural.state;

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

	public void startPlayback() {
		System.out.println("Now playing your favorite song!");
	}

	public void nextSong() {
		System.out.println("Skipping to next song");
	}

	public void previousSong() {
		System.out.println("Skipping to previous song");

	}

	public void stopPlayback() {
		System.out.println("Stopped playing");
	}

	public void fastForward() {
		System.out.println("Going fast forward 5 seconds");
	}

	public void rewind() {
		System.out.println("Rewinding 5 seconds");
	}

}
