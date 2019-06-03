/**
model_cmt01_0.1 * Copyright © 2016 Thomas Mayer (thomas.mayer@unibw.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vrpsim.dynamicvrprep.model.generator.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vrpsim.dynamicvrprep.model.generator.api.IArrivalTimeDetermineStrategy;
import vrpsim.dynamicvrprep.model.generator.api.model.GeneratorModelRequest;
import vrpsim.dynamicvrprep.model.generator.api.model.GeneratorModelRequests;

public class IndependentPeriodicDistributedArrivalTimeDetermineStrategy implements IArrivalTimeDetermineStrategy {

	private static Logger logger = LoggerFactory.getLogger(IndependentPeriodicDistributedArrivalTimeDetermineStrategy.class);
	
	private double edod;
	private int timeHorizon;
	private boolean init = false;
	
	/**
	 * Will additionally initialize the class by calling {@link IArrivalTimeDetermineStrategy#init(double, int)}
	 * 
	 * @param edod
	 * @param timeHorizon
	 */
	public IndependentPeriodicDistributedArrivalTimeDetermineStrategy(int timeHorizon) {
		init(Double.NaN, timeHorizon);
	}
	
	public void init(double edod, int timeHorizon) {
		this.edod = edod;
		this.timeHorizon = timeHorizon;
		this.init = true;
	}
	
	public void determineArrivalTimes(Random random, GeneratorModelRequests allRequests) throws NotInitilizedException {
		
		if(!this.init) {
			throw new NotInitilizedException("IArrivalTimeDetermineStrategy has to be initialized.");
		}
		
		int numberDynamicRequests = allRequests.getDynamicRequests().size();
		double newTimeInterval = timeHorizon/numberDynamicRequests;
		int index = 1;
		for(GeneratorModelRequest request : allRequests.getDynamicRequests()) {
			request.setTime(new Double(newTimeInterval * index).intValue());
			index++;
		}
	}

	@Override
	public double getEDOD() {
		return this.edod;
	}

	@Override
	public int getTimeHorizon() {
		return this.timeHorizon;
	}

}
