package app.exception;

import app.domain.Messages;
import app.exception.base.OperadorDeMatrizesException;

public class MissingVariableException extends OperadorDeMatrizesException {

	private static final long serialVersionUID = 1L;

	public MissingVariableException() {
		super(Messages.ERROR_MISSING_VARIABLE);
	}

}
