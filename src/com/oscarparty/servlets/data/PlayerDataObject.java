package com.oscarparty.servlets.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static com.oscarparty.servlets.data.PlayerDataObject.PLAYER_TABLE;

@DynamoDBTable(tableName = PLAYER_TABLE)
public class PlayerDataObject {

  public static final String PLAYER_TABLE = "Players";
  public static final String ID_ATTRIBUTE = "id";
  public static final String NAME_ATTRIBUTE = "name";

  @DynamoDBAttribute(attributeName = ID_ATTRIBUTE)
  private int id;

  @DynamoDBAttribute(attributeName = NAME_ATTRIBUTE)
  private String name;

}
