package com.oscarparty.data

import com.oscarparty.data.nominees.CategoryName

/**
  * In memory cache of the next category coming up.
  */
object NextCategory {
  var nextCategory : CategoryName.Value = CategoryName.BestPicture
}
