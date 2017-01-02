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

import models.api.Jsonable;
import play.data.validation.Required;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Car
 */
public class Car  implements Jsonable {
  @Required
  private String carId = null;

  @Required
  private String vin = null;

  @Required
  private String packageId = null;

  @Required
  private String carSpec = null;

  @Required
  private String manufacturer = null;

  @Required
  private Timestamp timestamp = null;

  public Car carId(String carId) {
    this.carId = carId;
    return this;
  }

   /**
   * 电池模组id
   * @return carId
  **/

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public Car vin(String vin) {
    this.vin = vin;
    return this;
  }

   /**
   * 车架号
   * @return vin
  **/

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public Car packageId(String packageId) {
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

  public Car carSpec(String carSpec) {
    this.carSpec = carSpec;
    return this;
  }

   /**
   * 规格
   * @return carSpec
  **/

  public String getCarSpec() {
    return carSpec;
  }

  public void setCarSpec(String carSpec) {
    this.carSpec = carSpec;
  }

  public Car manufacturer(String manufacturer) {
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

  public Car timestamp(Timestamp timestamp) {
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
    Car car = (Car) o;
    return Objects.equals(this.carId, car.carId) &&
        Objects.equals(this.vin, car.vin) &&
        Objects.equals(this.packageId, car.packageId) &&
        Objects.equals(this.carSpec, car.carSpec) &&
        Objects.equals(this.manufacturer, car.manufacturer) &&
        Objects.equals(this.timestamp, car.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carId, vin, packageId, carSpec, manufacturer, timestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Car {\n");
    
    sb.append("    carId: ").append(toIndentedString(carId)).append("\n");
    sb.append("    vin: ").append(toIndentedString(vin)).append("\n");
    sb.append("    packageId: ").append(toIndentedString(packageId)).append("\n");
    sb.append("    carSpec: ").append(toIndentedString(carSpec)).append("\n");
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
