/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package org.apache.uima.ducc.transport.event.common;

public class DuccPerWorkItemStatistics implements IDuccPerWorkItemStatistics {

	/**
	 * please increment this sUID when removing or modifying a field 
	 */
	private static final long serialVersionUID = 1L;
	
	private double max = 0;
	private double min = 0;
	private double mean = 0;
	private double stddev = 0;
	
	public DuccPerWorkItemStatistics(double max, double min, double mean, double stddev) {
		this.max = max;
		this.min = min;
		this.mean = mean;
		this.stddev = stddev;
	}

	@Override
	public double getMax() {
		return max;
	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public double getMean() {
		return mean;
	}

	@Override
	public double getStandardDeviation() {
		return stddev;
	}
	
}
