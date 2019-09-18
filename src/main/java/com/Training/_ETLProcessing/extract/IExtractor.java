package com.myTraining._ETLProcessing.extract;

import com.myTraining._ETLProcessing.system.exception.SystemException;

/**
 * Classes which can implement this can extract data
 * @author krishna
 *
 */
public interface IExtractor {
	void extract() throws SystemException;
}
