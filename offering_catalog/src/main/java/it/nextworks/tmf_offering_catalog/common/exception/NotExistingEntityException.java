package it.nextworks.tmf_offering_catalog.common.exception;

public class NotExistingEntityException extends Exception {

    public NotExistingEntityException(){ super(); }

    public NotExistingEntityException(String msg){ super(msg); }
}
