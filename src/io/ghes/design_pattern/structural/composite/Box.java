package io.ghes.design_pattern.structural.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Box implements ItemInterface {
	
	private final List<ItemInterface> items = new ArrayList<ItemInterface>();
	
	public void addItem(final ItemInterface item) {
		this.items.add(item);
	}
	
	public void clear() {
		this.items.clear();
	}
	
	public void removeItem(final ItemInterface item) {
		this.items.remove(item);
	}
	
	@Override
	public Double getPrice() {
		return this.items.isEmpty() ? 0d : this.items.stream().collect(Collectors.summingDouble(i -> i.getPrice()));
	}

}
