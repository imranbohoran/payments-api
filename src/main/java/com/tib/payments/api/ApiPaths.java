package com.tib.payments.api;

public class ApiPaths {
    public static final String VERSION = "/v1";
    public static final String API_ROOT = "/api/payments";

    public static final String PAYMENTS_RESOURCE_PATH = VERSION + API_ROOT;
    public static final String PAYMENTS_PATH_SINGLE_RESOURCE = PAYMENTS_RESOURCE_PATH + "/{payment_id}";

}
