package Enum.Application.Enum.App.exceptions;

import Enum.Application.Enum.App.dto.request.ProgramRegistrationRequest;

public class ProgramNotFoundException extends CohortNotFoundException{
    public ProgramNotFoundException(String message){
        super(message);
    }
}
