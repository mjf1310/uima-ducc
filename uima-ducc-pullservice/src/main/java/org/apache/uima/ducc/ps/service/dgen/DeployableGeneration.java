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
package org.apache.uima.ducc.ps.service.dgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.ducc.ps.service.ServiceConfiguration;
import org.apache.uima.ducc.ps.service.dgen.iface.IDeployableGeneration;
import org.apache.uima.ducc.ps.service.dgen.iface.IDuccGeneratorUimaAggregateComponent;
import org.apache.uima.ducc.ps.service.utils.Utils;

public class DeployableGeneration implements IDeployableGeneration {

  private ServiceConfiguration serviceConfiguration;

  public DeployableGeneration(ServiceConfiguration serviceConfiguration) {
    this.serviceConfiguration = serviceConfiguration;
  }

  private void conditionalAddComponent(
          ArrayList<IDuccGeneratorUimaAggregateComponent> dgenComponents, String descriptor,
          List<String> overrides) {
    if (descriptor != null) {
      DuccUimaAggregateComponent dgenComponent = new DuccUimaAggregateComponent(descriptor,
              overrides);
      dgenComponents.add(dgenComponent);
    }
  }

  private void show(String text) {
    System.out.println(text);
  }

  private void show(String name, String value) {
    show(name + "=" + value);
  }

  private void show(String name, List<String> value) {
    if (value == null) {
      show(name + "=" + value);
    } else {
      show(name + "=" + value.toString());
    }
  }

  private String fabricateTargetDirectoryName(String baseDir, String jobId) {
    StringBuffer sb = new StringBuffer();
    if (baseDir != null) {
      sb.append(baseDir);
      if (!baseDir.endsWith(File.separator)) {
        sb.append(File.separator);
      }
      if (jobId != null) {
        sb.append(jobId);
      }
    }
    return sb.toString();
  }

  @Override
  public String generate(Boolean createUniqueFilename) throws Exception
  // String directory,
  // String id,
  // Integer dgenThreadCount,
  // String dgenFlowController,
  // String cmDescriptor,
  // List<String> cmOverrides,
  // String aeDescriptor,
  // List<String> aeOverrides,
  // String ccDescriptor,
  // List<String> ccOverrides,

  {
    String retVal = null;
    try {
      show("directory", serviceConfiguration.getJobDirectory());// directory);
      show("id", serviceConfiguration.getJobId());// id);
      show("dgenThreadCount", serviceConfiguration.getThreadCount());// dgenThreadCount.toString());
      show("dgenFlowController", serviceConfiguration.getJpFlowController());// dgenFlowController);
      show("cmDescriptor", serviceConfiguration.getCmDescriptor());// cmDescriptor);
      show("cmOverrides", serviceConfiguration.getCmOverrides());// cmOverrides);
      show("aeDescriptor", serviceConfiguration.getAeDescriptor()); // aeDescriptor);
      show("aeOverrides", serviceConfiguration.getAeOverrides()); // aeOverrides);
      show("ccDescriptor", serviceConfiguration.getCcDescriptor());// ccDescriptor);
      show("ccOverrides", serviceConfiguration.getCcOverrides()); // ccOverrides);
      show("createUniqueFilename", createUniqueFilename ? "true" : "false");
      String targetDirectory = fabricateTargetDirectoryName(serviceConfiguration.getJobDirectory(),
              serviceConfiguration.getJobId());
      DeployableGenerator deployableGenerator = new DeployableGenerator(targetDirectory);
      ArrayList<IDuccGeneratorUimaAggregateComponent> dgenComponents = new ArrayList<IDuccGeneratorUimaAggregateComponent>();
      // conditionalAddComponent(dgenComponents, cmDescriptor, cmOverrides);
      // conditionalAddComponent(dgenComponents, aeDescriptor, aeOverrides);
      // conditionalAddComponent(dgenComponents, ccDescriptor, ccOverrides);
      // DuccUimaAggregate configuration = new DuccUimaAggregate(dgenThreadCount,
      // dgenFlowController, dgenComponents);
      // retVal = deployableGenerator.generateAe(configuration, id, createUniqueFilename);

      List<String> cmOverrides = Utils.tokenizeList(serviceConfiguration.getCmOverrides(), true);
      List<String> ccOverrides = Utils.tokenizeList(serviceConfiguration.getCcOverrides(), true);
      List<String> aeOverrides = Utils.tokenizeList(serviceConfiguration.getAeOverrides(), true);

      conditionalAddComponent(dgenComponents, serviceConfiguration.getCmDescriptor(), cmOverrides);
      conditionalAddComponent(dgenComponents, serviceConfiguration.getAeDescriptor(), aeOverrides);
      conditionalAddComponent(dgenComponents, serviceConfiguration.getCcDescriptor(), ccOverrides);
      DuccUimaAggregate configuration = new DuccUimaAggregate(serviceConfiguration, dgenComponents);
      retVal = deployableGenerator.generateAe(configuration, serviceConfiguration.getJobId(),
              createUniqueFilename);

    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.toString());
    }
    return retVal;
  }

  @Override
  public String generate(String directory, String id, Integer dgenThreadCount, String ddName,
          Boolean createUniqueFilename) throws Exception {
    String retVal = null;
    try {
      show("directory", directory);
      show("id", id);
      show("dgenThreadCount", dgenThreadCount.toString());
      show("ddName", ddName);
      show("createUniqueFilename", createUniqueFilename ? "true" : "false");
      String targetDirectory = fabricateTargetDirectoryName(directory, id);
      DeployableGenerator deployableGenerator = new DeployableGenerator(targetDirectory);
      DuccUimaReferenceByName configuration = new DuccUimaReferenceByName(dgenThreadCount, ddName);
      retVal = deployableGenerator.generateDd(configuration, id, createUniqueFilename);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.toString());
    }
    return retVal;
  }
}