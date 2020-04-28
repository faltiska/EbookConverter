package com.adobe.dp.office.word;

import java.util.Hashtable;
import java.util.Vector;

class AbstractNumberingDefinition {

	// Integer(level) -> NumberingLevelDefinition
    final Hashtable numberingLevelDefinitions = new Hashtable();

	String numStyleLink;

	private boolean instantiated;

	final Vector instances = new Vector();
	
	public Hashtable getNumberingLevelDefinitions(WordDocument doc) {
		if (numStyleLink != null && !instantiated) {
			instantiated = true;
			Style style = (Style) doc.stylesById.get(numStyleLink);
			if (style.type.equals("numbering") && style.paragraphProperties != null
					&& style.paragraphProperties.numberingProperties != null) {
				Integer numId = (Integer) style.paragraphProperties.numberingProperties.get("numId");
				if (numId != null) {
					NumberingDefinitionInstance inst = (NumberingDefinitionInstance) doc.numberingDefinitions
							.get(numId);
					Hashtable from = inst.getNumberingLevelDefinitions();
					NumberingDefinitionInstance.fillDefsFrom(numberingLevelDefinitions, from);
				}
			}
		}
		return numberingLevelDefinitions;
	}

}
