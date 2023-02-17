package io.ghes.design_patterns.behavioural.state;

public class LockedState extends State {

	public LockedState(final AudioPlayer player) {
		super(player);
	}

	@Override
	public void clickLock(final boolean isKeptPressed) {
		if (this.player.getState()
				.getStateName()
				.equals("playing")) {
			this.player.changeState(new PlayingState(this.player));
		} else {
			this.player.changeState(new ReadyState(this.player));
		}

	}

	@Override
	public void clickPlay(final boolean isKeptPressed) {
		// locked, do nothing
	}

	@Override
	public void clickNext(final boolean isKeptPressed) {
		// locked, do nothing

	}

	@Override
	public void clickPrevious(final boolean isKeptPressed) {
		// locked, do nothing
	}

	@Override
	public String getStateName() {
		return "locked";
	}

}
