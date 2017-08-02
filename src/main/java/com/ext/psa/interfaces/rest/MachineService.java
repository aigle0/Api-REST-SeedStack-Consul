package com.ext.psa.interfaces.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MachineService {

    private Map<String,Machine> machines = DatabaseClass.getMachines();
    
    

    public MachineService() {

    }

    public List<Machine> getAllMachines() {

        return new ArrayList<Machine>(machines.values());
    }

    public Machine getMachine(String name) {
        return machines.get(name);
    }

    /*
     * public Machine addProfile(Machine machine) {
     * 
     * machines.put(machine.getNameMachine(), machine); return machine; }
     */

}