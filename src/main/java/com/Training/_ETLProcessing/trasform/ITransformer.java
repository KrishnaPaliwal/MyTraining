package com.Training._ETLProcessing.trasform;

import com.Training._ETLProcessing.system.exception.SystemException;

public interface ITransformer {
 void transform() throws SystemException;

String transform(String input) throws SystemException;
}
