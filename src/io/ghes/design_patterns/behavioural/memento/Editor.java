package io.ghes.design_patterns.behavioural.memento;

public class Editor {
	private static final Integer LINE_WITDH = 20;

	private String text = "";
	private Integer curX = 0;
	private Integer curY = 0;

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Integer getCurX() {
		return this.curX;
	}

	public void setCurX(final Integer curX) {
		this.curX = curX;
	}

	public Integer getCurY() {
		return this.curY;
	}

	public void setCurY(final Integer curY) {
		this.curY = curY;
	}

	public void type(final String text) {
		System.out.println(" *user is typing* ");
		this.text += text;
		this.curX += text.length() % LINE_WITDH;
		this.curY += text.length() / LINE_WITDH;
	}

	public Snapshot createSnapshot() {
		return new Snapshot(this, this.text, this.curX, this.curY);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		int cc = 0;
		while (cc < this.text.length()) {
			sb.append(this.text.charAt(cc));
			if (cc != 0 && cc % LINE_WITDH == 0) {
				sb.append("\n");
			}
			cc++;
		}
		return sb.toString() + "_";
	}

}
