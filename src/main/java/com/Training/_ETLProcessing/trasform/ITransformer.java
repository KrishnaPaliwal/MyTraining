package com.myTraining._ETLProcessing.trasform;

import com.myTraining._ETLProcessing.system.exception.SystemException;

public interface ITransformer {
 void transform() throws SystemException;

String transform(String input) throws SystemException;
}
