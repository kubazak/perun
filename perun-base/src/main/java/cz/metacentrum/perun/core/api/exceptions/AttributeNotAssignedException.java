package cz.metacentrum.perun.core.api.exceptions;

import cz.metacentrum.perun.core.api.AttributeDefinition;

/**
 * This exception raises when you try to remove an attribute which was not assigned before.
 *
 * @author Slavek Licehammer
 */
public class AttributeNotAssignedException extends EntityNotAssignedException {
	static final long serialVersionUID = 0;

	private AttributeDefinition attribute;

	public AttributeNotAssignedException(String message) {
		super(message);
	}

	public AttributeNotAssignedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttributeNotAssignedException(Throwable cause) {
		super(cause);
	}

	public AttributeNotAssignedException(AttributeDefinition attribute) {
		super(attribute.toString());
		this.attribute = attribute;
	}

	public AttributeDefinition getAttribute() {
		return attribute;
	}
}
