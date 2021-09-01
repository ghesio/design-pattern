package io.ghes.design_patterns.creational.abstract_factory.factories;

import io.ghes.design_patterns.creational.abstract_factory.models.ChairInterface;
import io.ghes.design_patterns.creational.abstract_factory.models.TableInterface;

public interface FornitureAbstractFactory {
	
	public ChairInterface getChair();
	
	public TableInterface getTable();

}
