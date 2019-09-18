package com.myTraining.General_Program;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcedureCallbackApplication {
	
	ProcedureCallback<Integer> callbackObject = new ProcedureCallback<Integer>(){

		@Override
		public List callback(CallableStatement stmt, ResultSet rs)
				throws SQLException {
			
			return (new ArrayList<Integer>());
		}
		
	};
	
	public void method(ProcedureCallback callback)
	{
		
	}

	
}
