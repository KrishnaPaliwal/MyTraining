package com.myTraining.General_Program;

import java.util.Map;

public class NumberOfJVMRunning {
	public static void main(final String[] args) {
	    final Map<Integer, LocalVirtualMachine> virtualMachines = LocalVirtualMachine.getAllVirtualMachines();
	    for (final Entry<Integer, LocalVirtualMachine> entry : virtualMachines.entrySet()) {
	        System.out.println(entry.getKey() + " : " + entry.getValue().displayName());
	    }
	}

}
