package org.sumerge.careerpackageservice.Exception;

public abstract class CareerPackageException extends RuntimeException {
    public CareerPackageException(String message) {
        super(message);
    }
}
