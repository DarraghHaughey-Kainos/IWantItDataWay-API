package org.kainos.ea.client;

public class JobRoleDoesNotExistException extends Throwable {
    public JobRoleDoesNotExistException(String validationMessage) {
        super(validationMessage);
    }
}
