package com.oscarparty.data.dao.mappers;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import static com.oscarparty.data.dao.mappers.WinnerDataObject.WINNER_TABLE;

@DynamoDBTable(tableName = WINNER_TABLE)
public class WinnerDataObject {

  public static final String WINNER_TABLE = "Winners";
  public static final String CATEGORY_ATTR = "category";
  public static final String NOMINEE_ATTR = "winningNominee";

  @DynamoDBHashKey(attributeName = CATEGORY_ATTR)
  private String category;

  @DynamoDBAttribute(attributeName = NOMINEE_ATTR)
  private int winningNominee;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getWinningNominee() {
    return winningNominee;
  }

  public void setWinningNominee(int winningNominee) {
    this.winningNominee = winningNominee;
  }
}
