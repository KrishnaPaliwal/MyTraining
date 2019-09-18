package com.myTraining.General_Program;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ProcedureCallback<T> {

	public abstract List<T> callback(CallableStatement stmt, ResultSet rs) throws SQLException;
}
