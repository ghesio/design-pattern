package io.ghes.design_patterns.behavioural.memento;

public class Snapshot {

	private final Editor editor;
	private final String text;
	private final Integer curX;
	private final Integer curY;

	public Snapshot(final Editor editor, final String text, final Integer curX, final Integer curY) {
		this.editor = editor;
		this.text = text;
		this.curX = curX;
		this.curY = curY;
	}

	public void restore() {
		this.editor.setText(this.text);
		this.editor.setCurX(this.curX);
		this.editor.setCurY(this.curY);
	}

}
