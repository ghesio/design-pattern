package io.ghes.design_patterns.behavioural.memento;

public class Application {

	public static void main(final String[] args) {
		final Editor editor = new Editor();
		final SnapshotHandler backupHandler = new SnapshotHandler();
		backupHandler.backup(editor.createSnapshot());
		editor.type("Hello,");
		System.out.println(editor);
		backupHandler.backup(editor.createSnapshot());
		editor.type(" there!");
		System.out.println(editor);
		backupHandler.undo();
		System.out.println(editor);
		backupHandler.undo();
		System.out.println(editor);
	}

}
