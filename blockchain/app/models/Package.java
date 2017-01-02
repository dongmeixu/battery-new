/*
 * 区块链服务API
 * 区块链服务
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package models;

import java.util.Objects;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.api.Jsonable;
import play.data.validation.Required;

/**
 * Package
 */
public class Package implements Jsonable {
  @Required()
  private String packageId = null;

  @Required()
  private String packageSpec = null;

  @Required()
  private List<Module> modules = new ArrayList<Module>();

  @Required()
  private String manufacturer = null;

  @Required()
  private Timestamp timestamp = null;

  public Package packageId(String packageId) {
    this.packageId = packageId;
    return this;
  }

   /**
   * 电池包id
   * @return packageId
  **/
  public String getPackageId() {
    return packageId;
  }

  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }

  public Package packageSpec(String packageSpec) {
    this.packageSpec = packageSpec;
    return this;
  }

   /**
   * 电池包规格
   * @return packageSpec
  **/
  public String getPackageSpec() {
    return packageSpec;
  }

  public void setPackageSpec(String packageSpec) {
    this.packageSpec = packageSpec;
  }

  public Package modules(List<Module> modules) {
    this.modules = modules;
    return this;
  }

  public Package addModulesItem(Module modulesItem) {
    this.modules.add(modulesItem);
    return this;
  }

   /**
   * Module collection
   * @return modules
  **/
  public List<Module> getModules() {
    return modules;
  }

  public void setModules(List<Module> modules) {
    this.modules = modules;
  }

  public Package manufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

   /**
   * 生产厂家
   * @return manufacturer
  **/
  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Package timestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Package _package = (Package) o;
    return Objects.equals(this.packageId, _package.packageId) &&
        Objects.equals(this.packageSpec, _package.packageSpec) &&
        Objects.equals(this.modules, _package.modules) &&
        Objects.equals(this.manufacturer, _package.manufacturer) &&
        Objects.equals(this.timestamp, _package.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packageId, packageSpec, modules, manufacturer, timestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Package {\n");
    
    sb.append("    packageId: ").append(toIndentedString(packageId)).append("\n");
    sb.append("    packageSpec: ").append(toIndentedString(packageSpec)).append("\n");
    sb.append("    modules: ").append(toIndentedString(modules)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

