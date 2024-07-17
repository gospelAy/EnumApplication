package Enum.Application.Enum.App.exceptions;

public class CohortNotFoundException extends RuntimeException{
    public CohortNotFoundException(String message){
        super(message);
    }
}
