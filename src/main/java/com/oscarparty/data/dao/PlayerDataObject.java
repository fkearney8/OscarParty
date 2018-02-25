package com.oscarparty.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static com.oscarparty.data.dao.PlayerDataObject.PLAYER_TABLE;

@DynamoDBTable(tableName = PLAYER_TABLE)
public class PlayerDataObject {

  public static final String PLAYER_TABLE = "Players";
  public static final String ID_ATTRIBUTE = "id";
  public static final String NAME_ATTRIBUTE = "name";

  @DynamoDBHashKey(attributeName = ID_ATTRIBUTE)
  private int id;

  @DynamoDBAttribute(attributeName = NAME_ATTRIBUTE)
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
