package app.exception;

import app.domain.Messages;
import app.exception.base.OperadorDeMatrizesException;

public class InvalidSyntaxException extends OperadorDeMatrizesException {
	
	private static final long serialVersionUID = 1L;

	public InvalidSyntaxException(){
		super(Messages.ERROR_INVALID_EXPRESSION_SYNTAX);
	}
	
}
