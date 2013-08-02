/*******************************************************************************
 * Copyright (c) 2009, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.obeonetwork.dsl.uml2.design.services.internal;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * A switch implementation moving down the element position.
 * 
 * @author Hugo Marchadour <a href="mailto:hugo.marchadour@obeo.fr">hugo.marchadour@obeo.fr</a>
 */
public class MoveDownElementSwitch extends UMLSwitch<List<EObject>> {

	/**
	 * The constructor.
	 */
	public MoveDownElementSwitch() {

	}

	/**
	 * Move down the element position in it feature containment.
	 * 
	 * @param ctx
	 *            the element to move up
	 */
	public void moveDownElement(EObject ctx) {
		doSwitch(ctx);
	}

	@Override
	public List<EObject> caseProperty(Property property) {
		EObject eContainer = property.eContainer();

		if (eContainer instanceof StructuredClassifier) {
			EList<Property> properties = ((StructuredClassifier)eContainer).getOwnedAttributes();

			moveDownList(properties, property);
		}
		return super.caseProperty(property);
	}

	@Override
	public List<EObject> caseOperation(Operation operation) {
		EObject eContainer = operation.eContainer();

		if (eContainer instanceof org.eclipse.uml2.uml.Class || eContainer instanceof Interface) {
			EList<Operation> operations = null;
			if (eContainer instanceof org.eclipse.uml2.uml.Class) {
				operations = ((org.eclipse.uml2.uml.Class)eContainer).getOwnedOperations();
			} else {
				operations = ((Interface)eContainer).getOwnedOperations();
			}
			moveDownList(operations, operation);
		}
		return super.caseOperation(operation);
	}

	private void moveDownList(EList<?> elements, Element element) {
		if (elements.contains(element) && elements.size() > 1) {
			int oldIndex = elements.indexOf(element);
			int newIndex = oldIndex + 1;
			if (newIndex < elements.size()) {
				elements.move(newIndex, oldIndex);
			}
		}
	}
}
