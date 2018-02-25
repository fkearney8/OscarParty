package com.oscarparty.data

import com.oscarparty.data.nominees.{CategoryName, Nominee}

case class Winner(category: CategoryName.Value, winningNominee: Nominee)
