/**
 * Copyright (C) 2016 Thomas Mayer (thomas.mayer@unibw.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vrpsim.core.model.util.exceptions.detail;

import vrpsim.core.model.util.exceptions.StorageException;

public class StorageOutOfStockException extends StorageException {

	private static final long serialVersionUID = 555035396119185257L;
	private final Double missing;
	
	public StorageOutOfStockException(String msg, Double missing) {
		super(msg);
		this.missing = missing;
	}

	public Double getMissing() {
		return this.missing;
	}
}