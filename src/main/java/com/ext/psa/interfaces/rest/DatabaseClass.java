package com.ext.psa.interfaces.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseClass {

    private static Map<String, Machine> machines;

    public static Map<String, Machine> getMachines() {
        return machines;
    }
	
}
