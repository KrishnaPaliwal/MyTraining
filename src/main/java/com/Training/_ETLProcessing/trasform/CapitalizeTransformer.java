package com.myTraining._ETLProcessing.trasform;

import com.myTraining._ETLProcessing.system.exception.SystemException;
/**
 * 
 * @author krishna
 *
 */
public abstract class CapitalizeTransformer implements ITransformer {
	@Override
	public String transform(String input) throws SystemException {
		return input.toUpperCase();
	}
}
