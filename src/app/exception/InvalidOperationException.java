package app.exception;

import app.domain.Messages;
import app.exception.base.OperadorDeMatrizesException;

public class InvalidOperationException extends OperadorDeMatrizesException {

	private static final long serialVersionUID = 1L;

	public InvalidOperationException() {
		super(Messages.ERROR_INVALID_OPERATION);
	}

}