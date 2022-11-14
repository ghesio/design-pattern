package io.ghes.design_patterns.behavioural.state;

public class Application {

	public static void main(final String[] args) {
		// initializing the player
		final AudioPlayer audioPlayer = new AudioPlayer();
		// as soon as the player is started we lock him
		audioPlayer.clickLock(false);
		// by clicking other buttons nothing happens
		audioPlayer.clickNext(false);
		audioPlayer.clickPlay(false);
		audioPlayer.clickPrevious(false);
		// we then unlock the player
		audioPlayer.clickLock(false);
		// start a song
		audioPlayer.clickPlay(false);
		audioPlayer.clickNext(true);
		audioPlayer.clickNext(false);
		// stop it
		audioPlayer.clickPlay(false);

	}

}
