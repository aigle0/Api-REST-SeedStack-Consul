package com.ext.psa.interfaces.rest;

import javax.xml.bind.annotation.XmlRootElement;


	@XmlRootElement
	public class Machine {

	    public String nameMachine;
	    public String statusMachine;

	    public Machine() {
	    }

	    public Machine(String nameMachine, String statusMachine) {

	        this.nameMachine = nameMachine;
	        this.statusMachine = statusMachine;

	    }

	    public String getStatus() {
	        return statusMachine;
	    }

	    public void setStatus(String statusMachine) {
	        this.statusMachine = statusMachine;
	    }

	    public String getNameMachine() {
	        return nameMachine;
	    }

	    public void setNameMachine(String nameMachine) {
	        this.nameMachine = nameMachine;
	    }

	    public String toString() {
	        return String.format("name of the Machine:%s, status of the machine:%s ", nameMachine, statusMachine);
	    }

	

}
