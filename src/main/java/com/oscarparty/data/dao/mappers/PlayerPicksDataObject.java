package com.oscarparty.data.dao.mappers;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static com.oscarparty.data.dao.mappers.PlayerPicksDataObject.PLAYER_PICKS_TABLE;

@DynamoDBTable(tableName = PLAYER_PICKS_TABLE)
public class PlayerPicksDataObject {

  public static final String PLAYER_PICKS_TABLE = "PlayerPicks";

  public static final String PLAYER_ID_ATTR = "playerId";
  @DynamoDBHashKey(attributeName = PLAYER_ID_ATTR)
  private String playerId;

  /** Uses a range key so that we can have a composite primary key with both player and category. */
  public static final String CATEGORY_ATTR = "category";
  @DynamoDBRangeKey(attributeName = CATEGORY_ATTR)
  private String category;

  public static final String FIRST_PICK_ATTR = "firstPick";
  @DynamoDBAttribute(attributeName = FIRST_PICK_ATTR)
  private int firstPick;

  public static final String SECOND_PICK_ATTR = "secondPick";
  @DynamoDBAttribute(attributeName = SECOND_PICK_ATTR)
  private int secondPick;

  public static final String THIRD_PICK_ATTR = "thirdPick";
  @DynamoDBAttribute(attributeName = THIRD_PICK_ATTR)
  private int thirdPick;

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getFirstPick() {
    return firstPick;
  }

  public void setFirstPick(int firstPick) {
    this.firstPick = firstPick;
  }

  public int getSecondPick() {
    return secondPick;
  }

  public void setSecondPick(int secondPick) {
    this.secondPick = secondPick;
  }

  public int getThirdPick() {
    return thirdPick;
  }

  public void setThirdPick(int thirdPick) {
    this.thirdPick = thirdPick;
  }
}
