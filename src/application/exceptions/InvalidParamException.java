package application.exceptions;

@SuppressWarnings("serial")
public class InvalidParamException extends Exception{
	public InvalidParamException(String message) {
        super(message);
    }
}
