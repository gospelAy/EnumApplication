package Enum.Application.Enum.App.exceptions;

public class InvalidException extends ProgramNotFoundException{
    public InvalidException(String message){
        super(message);
    }
}
