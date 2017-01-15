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
import play.modules.jongo.BaseModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Trade
 */
public class Trade extends BaseModel implements Jsonable {
  @Required
  private String id = null;

  @Required
  private String fromId = null;

  @Required
  private String from = null;

  @Required
  private String to = null;

  @Required
  private String toId = null;

  @Required
  private List<String> productIds = new ArrayList<String>();

  @Required
  private Timestamp timestamp = new Timestamp();

  @Required
  private Block block = null;

  @Required
  private Transcation transaction = null;

  public Trade id(String id) {
    this.id = id;
    return this;
  }

  /**
   * 对象id
   * @return id
   **/
/*
  public String getId() {
    return id;
  }
*/

  public void setId(String id) {
    this.id = id;
  }

  public Trade fromId(String fromId) {
    this.fromId = fromId;
    return this;
  }

  /**
   * 卖家ID
   * @return fromId
   **/

  public String getFromId() {
    return fromId;
  }

  public void setFromId(String fromId) {
    this.fromId = fromId;
  }

  public Trade from(String from) {
    this.from = from;
    return this;
  }

  /**
   * 卖家
   * @return from
   **/

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public Trade to(String to) {
    this.to = to;
    return this;
  }

  /**
   * 买家
   * @return to
   **/

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Trade toId(String toId) {
    this.toId = toId;
    return this;
  }

  /**
   * 买家Id
   * @return toId
   **/

  public String getToId() {
    return toId;
  }

  public void setToId(String toId) {
    this.toId = toId;
  }

  public Trade productIds(List<String> productIds) {
    this.productIds = productIds;
    return this;
  }

  public Trade addProductIdsItem(String productIdsItem) {
    this.productIds.add(productIdsItem);
    return this;
  }

  /**
   * 交易物品ID
   * @return productIds
   **/

  public List<String> getProductIds() {
    return productIds;
  }

  public void setProductIds(List<String> productIds) {
    this.productIds = productIds;
  }

  public Trade timestamp(Timestamp timestamp) {
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

  public Trade block(Block block) {
    this.block = block;
    return this;
  }

  /**
   * Get block
   * @return block
   **/

  public Block getBlock() {
    return block;
  }

  public void setBlock(Block block) {
    this.block = block;
  }

  public Trade transaction(Transcation transaction) {
    this.transaction = transaction;
    return this;
  }

  /**
   * Get transaction
   * @return transaction
   **/

  public Transcation getTransaction() {
    return transaction;
  }

  public void setTransaction(Transcation transaction) {
    this.transaction = transaction;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trade trade = (Trade) o;
    return Objects.equals(this.id, trade.id) &&
            Objects.equals(this.fromId, trade.fromId) &&
            Objects.equals(this.from, trade.from) &&
            Objects.equals(this.to, trade.to) &&
            Objects.equals(this.toId, trade.toId) &&
            Objects.equals(this.productIds, trade.productIds) &&
            Objects.equals(this.timestamp, trade.timestamp) &&
            Objects.equals(this.block, trade.block) &&
            Objects.equals(this.transaction, trade.transaction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fromId, from, to, toId, productIds, timestamp, block, transaction);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Trade {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    fromId: ").append(toIndentedString(fromId)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    toId: ").append(toIndentedString(toId)).append("\n");
    sb.append("    productIds: ").append(toIndentedString(productIds)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    block: ").append(toIndentedString(block)).append("\n");
    sb.append("    transaction: ").append(toIndentedString(transaction)).append("\n");
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

