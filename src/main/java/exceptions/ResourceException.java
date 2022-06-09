package exceptions;

public class ResourceException extends RuntimeException{
    public ResourceException(String key) {
        super("resource not found "+ key);
    }
}