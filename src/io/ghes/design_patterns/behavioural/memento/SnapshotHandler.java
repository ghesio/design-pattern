package io.ghes.design_patterns.behavioural.memento;

import java.util.Stack;

public class SnapshotHandler {

	private final Stack<Snapshot> snapshotList = new Stack<>();

	public void backup(final Snapshot snapshot) {
		this.snapshotList.add(snapshot);
	}

	public void undo() {
		System.out.println(" * user pressed CTRL+Z* ");
		if (!this.snapshotList.isEmpty()) {
			final Snapshot pop = this.snapshotList.pop();
			pop.restore();
		}
	}

}
