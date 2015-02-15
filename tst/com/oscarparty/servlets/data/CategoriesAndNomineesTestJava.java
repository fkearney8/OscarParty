package com.oscarparty.servlets.data;

import com.oscarparty.servlets.data.nominees.AllOscarNominees2015;
import com.oscarparty.servlets.data.nominees.Category;
import com.oscarparty.servlets.data.nominees.Nominee;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test mostly to see what kind of objects we get back from the Categories and Nominees listings
 * to check what will happen on the JPS.
 */
public class CategoriesAndNomineesTestJava {
  @Test
  public void testCategoryNomineesWithJava() throws Exception {
    AllOscarNominees2015 aon = new AllOscarNominees2015();
    Category bestVis = aon.findCategoryByName("Best Visual Effects");
    Assert.assertEquals("Best Visual Effects", bestVis.name());

    //bestVis has the expecte nominees
    List<Nominee> bestVisNoms = aon.categoryNomineesJava(bestVis);
    Assert.assertEquals("Correct number of nominees", 5, bestVisNoms.size());

    boolean foundNom = false;
    for (Nominee eachNominee: bestVisNoms) {
      if ("Interstellar".equals(eachNominee.name())) {
        foundNom = true;
        break;
      }
    }
    Assert.assertTrue("Found the nominee.", foundNom);

  }

}
