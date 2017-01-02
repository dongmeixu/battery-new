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

import models.api.Jsonable;
import play.data.validation.Required;

import java.sql.Timestamp;

/**
 * Transcation
 */
public class Transcation  implements Jsonable {
  @Required
  private String chaincodeId = null;

  /**
   * type
   */
  public enum ChaincodeTypeEnum {
    @Required
    UNDEFINED("UNDEFINED"),
    
    @Required
    CHAINCODE_DEPLOY("CHAINCODE_DEPLOY"),
    
    @Required
    CHAINCODE_INVOKE("CHAINCODE_INVOKE"),
    
    @Required
    CHAINCODE_QUERY("CHAINCODE_QUERY"),
    
    @Required
    CHAINCODE_TERMINATE("CHAINCODE_TERMINATE");

    private String value;

    ChaincodeTypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @Required
  private ChaincodeTypeEnum chaincodeType = null;

  @Required
  private String uuid = null;

  @Required
  private String cert = null;

  @Required
  private Timestamp timestamp = null;

  @Required
  private String payload = null;

  @Required
  private String signature = null;

  public Transcation chaincodeId(String chaincodeId) {
    this.chaincodeId = chaincodeId;
    return this;
  }

   /**
   * chaincode id
   * @return chaincodeId
  **/

  public String getChaincodeId() {
    return chaincodeId;
  }

  public void setChaincodeId(String chaincodeId) {
    this.chaincodeId = chaincodeId;
  }

  public Transcation chaincodeType(ChaincodeTypeEnum chaincodeType) {
    this.chaincodeType = chaincodeType;
    return this;
  }

   /**
   * type
   * @return chaincodeType
  **/

  public ChaincodeTypeEnum getChaincodeType() {
    return chaincodeType;
  }

  public void setChaincodeType(ChaincodeTypeEnum chaincodeType) {
    this.chaincodeType = chaincodeType;
  }

  public Transcation uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

   /**
   * transcation id
   * @return uuid
  **/

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Transcation cert(String cert) {
    this.cert = cert;
    return this;
  }

   /**
   * Certificate of the transactor. 
   * @return cert
  **/

  public String getCert() {
    return cert;
  }

  public void setCert(String cert) {
    this.cert = cert;
  }

  public Transcation timestamp(Timestamp timestamp) {
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

  public Transcation payload(String payload) {
    this.payload = payload;
    return this;
  }

   /**
   * payload hash
   * @return payload
  **/

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public Transcation signature(String signature) {
    this.signature = signature;
    return this;
  }

   /**
   * Signature of the transactor.
   * @return signature
  **/

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transcation transcation = (Transcation) o;
    return Objects.equals(this.chaincodeId, transcation.chaincodeId) &&
        Objects.equals(this.chaincodeType, transcation.chaincodeType) &&
        Objects.equals(this.uuid, transcation.uuid) &&
        Objects.equals(this.cert, transcation.cert) &&
        Objects.equals(this.timestamp, transcation.timestamp) &&
        Objects.equals(this.payload, transcation.payload) &&
        Objects.equals(this.signature, transcation.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chaincodeId, chaincodeType, uuid, cert, timestamp, payload, signature);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transcation {\n");
    
    sb.append("    chaincodeId: ").append(toIndentedString(chaincodeId)).append("\n");
    sb.append("    chaincodeType: ").append(toIndentedString(chaincodeType)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    cert: ").append(toIndentedString(cert)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    payload: ").append(toIndentedString(payload)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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

