package app.exception.base;

public class OperadorDeMatrizesException extends RuntimeException {

    private static final long serialVersionUID = 5650277622695810024L;
    private String details;

    public OperadorDeMatrizesException(String message) {
        super(message);
    }
    
    public OperadorDeMatrizesException(String message, String details){
        super(message);
        this.details = details;
    }
    
    public String getDetails(){
        return details;
    }
}
