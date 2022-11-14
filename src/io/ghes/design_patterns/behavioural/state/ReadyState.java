package io.ghes.design_patterns.behavioural.state;

public class ReadyState extends State {

	public ReadyState(final AudioPlayer player) {
		super(player);
	}

	@Override
	public void clickLock(final boolean isKeptPressed) {
		this.player.changeState(new LockedState(this.player));
	}

	@Override
	public void clickPlay(final boolean isKeptPressed) {
		this.player.startPlayback();
		this.player.changeState(new PlayingState(this.player));
	}

	@Override
	public void clickNext(final boolean isKeptPressed) {
		this.player.nextSong();
	}

	@Override
	public void clickPrevious(final boolean isKeptPressed) {
		this.player.previousSong();
	}

	@Override
	public String getStateName() {
		return "ready";
	}

}
