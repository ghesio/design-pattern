package io.ghes.design_patterns.creational.abstract_factory.factories;

import io.ghes.design_patterns.creational.abstract_factory.models.ChairInterface;
import io.ghes.design_patterns.creational.abstract_factory.models.TableInterface;
import io.ghes.design_patterns.creational.abstract_factory.models.impl.AncientChair;
import io.ghes.design_patterns.creational.abstract_factory.models.impl.AncientTable;

public class AncientFactory implements FornitureAbstractFactory {

	@Override
	public ChairInterface getChair() {
		return new AncientChair();
	}

	@Override
	public TableInterface getTable() {
		return new AncientTable();
	}

}
