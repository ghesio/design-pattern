package io.ghes.design_patterns.behavioural.state;

public class PlayingState extends State {

	public PlayingState(final AudioPlayer player) {
		super(player);
	}

	@Override
	public void clickLock(final boolean isKeptPressed) {
		this.player.changeState(new LockedState(this.player));
	}

	@Override
	public void clickPlay(final boolean isKeptPressed) {
		this.player.stopPlayback();
		this.player.changeState(new ReadyState(this.player));
	}

	@Override
	public void clickNext(final boolean isKeptPressed) {
		if (isKeptPressed) {
			this.player.fastForward();
		} else {
			this.player.nextSong();
		}
	}

	@Override
	public void clickPrevious(final boolean isKeptPressed) {
		if (isKeptPressed) {
			this.player.rewind();
		} else {
			this.player.previousSong();
		}
	}

	@Override
	public String getStateName() {
		return "playing";
	}

}
