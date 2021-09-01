package io.ghes.design_patterns.creational.abstract_factory.factories;

import io.ghes.design_patterns.creational.abstract_factory.models.ChairInterface;
import io.ghes.design_patterns.creational.abstract_factory.models.TableInterface;
import io.ghes.design_patterns.creational.abstract_factory.models.impl.ModernChair;
import io.ghes.design_patterns.creational.abstract_factory.models.impl.ModernTable;

public class ModernFactory implements FornitureAbstractFactory {

	@Override
	public ChairInterface getChair() {
		return new ModernChair();
	}

	@Override
	public TableInterface getTable() {
		return new ModernTable();
	}

}
