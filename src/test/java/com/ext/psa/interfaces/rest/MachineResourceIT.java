package com.ext.psa.interfaces.rest;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;

import com.jayway.restassured.RestAssured;

public class MachineResourceIT extends AbstractSeedIT {

    @Inject
    private MachineService machineService = new MachineService();

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/machines/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }

    @Test
    public void myServiceIsInjectableFailure() {
        Assertions.assertThat(machineService).isNull();
    }// normaly Failure

    @Test
    public void myServiceIsInjectableRun() {
        Assertions.assertThat(machineService).isNotNull();
    }// Run OK

    @Test
    public void basicPingTest() {
        com.jayway.restassured.RestAssured.given().when().get("http://localhost:8080/machines").then().statusCode(200);
    }// Run OK

    @Test
    public void getMachines() {
        com.jayway.restassured.RestAssured.given().when().get("http://localhost:8080/machines/check").then().statusCode(200);
    }// Run OK

    @Test
    public void getfileMachine() {
        com.jayway.restassured.RestAssured.given().contentType("application/json").when().get("http://localhost:8080/machines/check").then()
                .statusCode(200);
    }// Run OK

    @Test
    public void getconsulMachine() {
        Map<String, String> machine = new HashMap<>();
        machine.put("node", "anymachine");
        com.jayway.restassured.RestAssured.given().contentType("application/json").when().body(machine)
                .post("http://localhost:8080/machines/checkstate").then().statusCode(200);
    }// Run OK

}