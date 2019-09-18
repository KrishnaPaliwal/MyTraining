package com.myTraining._ETLProcessing.processor;

import java.util.List;

import com.myTraining._ETLProcessing.extract.IExtractor;
import com.myTraining._ETLProcessing.system.exception.IOETLException;
import com.myTraining._ETLProcessing.system.exception.NotDirectoryETLException;
import com.myTraining._ETLProcessing.system.exception.SystemException;
import com.myTraining._ETLProcessing.trasform.ITransformer;
/**
 * This is ETL for file
 * @author krishna
 *
 */
public class ETLProcessor {
	IExtractor extractor;
	List<ITransformer> transformers;
	// Loader loader;
	public IExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(IExtractor extractor) {
		this.extractor = extractor;
	}

	public List<ITransformer> getTransformers() {
		return transformers;
	}

	public void setTransformers(List<ITransformer> transformers) {
		this.transformers = transformers;
	}


	public ETLProcessor(IExtractor extractor, List<ITransformer> transformers) {
		this.extractor = extractor;
		this.transformers = transformers;
	}

	public void runETL() {
		try {
			extractor.extract();
			for (ITransformer tranformer : transformers) {
				tranformer.transform();
			}

		} catch (NotDirectoryETLException | IOETLException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
}
