package com.oscarparty.servlets.data.nominees

import scala.collection.mutable.ArrayBuffer

class AllOscarNominees2014 {

  private val categories = new ArrayBuffer[OscarCategoryNominees]()
  categories += new OscarCategoryNominees("Best Picture",
    Array ("American Hustle",
      "Captain Phillips",
      "Dallas Buyers Club",
      "Gravity",
      "Her",
      "Nebraska",
      "Philomena",
      "12 Years a Slave",
      "The Wolf of Wall Street"),
    Array (15, 12, 9))
  categories += new OscarCategoryNominees("Best Actor in a Leading Role",
    Array (
      "Christian Bale (American Hustle)",
      "Bruce Dern (Nebraska)",
      "Leonardo DiCaprio (The Wolf of Wall Street)",
      "Chiwetel Ejiofor (12 Years a Slave)",
      "Matthew McConaughey (Dallas Buyers Club)"),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Actress in a Leading Role",
    Array (
      "Amy Adams (American Hustle)",
      "Cate Blanchett (Blue Jasmine)",
      "Sandra Bullock (Gravity)",
      "Judi Dench (Philomena)",
      "Meryl Streep (August: Osage County)"),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Actor in a Supporting Role",
    Array (
      "Barkhad Abdi (Captain Phillips)",
      "Bradley Cooper (American Hustle)",
      "Michael Fassbender (12 Years a Slave)",
      "Jonah Hill (The Wolf of Wall Street)",
      "Jared Leto (Dallas Buyers Club)"),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Actress in a Supporting Role",
    Array (
      "Sally Hawkins (Blue Jasmine)",
      "Jennifer Lawrence (American Hustle)",
      "Lupita Nyong'o (12 Years a Slave)",
      "Julia Roberts (August: Osage County)",
      "June Squibb (Nebraska)"),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Director",
    Array (
      "David O. Russell (American Hustle)",
      "Alfonso Cuarón (Gravity)",
      "Alexander Payne (Nebraska)",
      "Steve McQueen (12 Years a Slave)",
      "Martin Scorsese (The Wolf of Wall Street)"),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Animated Feature",
    Array (
      "The Croods ",
      "Despicable Me 2 ",
      "Ernest & Celestine ",
      "Frozen ",
      "The Wind Rises "))
  categories += new OscarCategoryNominees("Best Cinematography",
    Array (
      "The Grandmaster (Philippe Le Sourd)",
      "Gravity (Emmanuel Lubezki)",
      "Inside Llewyn Davis (Bruno Delbonnel)",
      "Nebraska (Phedon Papamichael)",
      "Prisoners (Roger A. Deakins)"))
  categories += new OscarCategoryNominees("Best Costume Design",
    Array (
      "American Hustle (Michael Wilkinson)",
      "The Grandmaster (William Chang Suk Ping)",
      "The Great Gatsby (Catherine Martin)",
      "The Invisible Woman (Michael O'Connor)",
      "12 Years a Slave (Patricia Norris)"))
  categories += new OscarCategoryNominees("Best Documentary Feature",
    Array (
      "The Act of Killing",
      "Cutie and the Boxer",
      "Dirty Wars",
      "The Square",
      "20 Feet from Stardom"))
  categories += new OscarCategoryNominees("Best Documentary Short",
    Array (
      "CaveDigger",
      "Facing Fear",
      "Karama Has No Walls",
      "The Lady in Number 6: Music Saved My Life",
      "Prison Terminal: The Last Days of Private Jack Hall"))
  categories += new OscarCategoryNominees("Best Film Editing",
    Array (
      "American Hustle",
      "Captain Phillips",
      "Dallas Buyers Club",
      "Gravity",
      "12 Years a Slave"))
  categories += new OscarCategoryNominees("Best Foreign Language Film",
    Array (
      "The Broken Circle Breakdown (Belgium)",
      "The Great Beauty (Italy)",
      "The Hunt (Denmark)",
      "The Missing Picture (Cambodia)",
      "Omar (Palestine)"))
  categories += new OscarCategoryNominees("Best Makeup and Hairstyling",
    Array (
      "Dallas Buyers Club",
      "Jackass Presents: Bad Grandpa",
      "The Lone Ranger"))
  categories += new OscarCategoryNominees("Best Original Score",
    Array (
      "The Book Thief (John Williams)",
      "Gravity (Steven Price)",
      "Her (William Butler, Owen Pallett)",
      "Philomena (Alexandre Desplat)",
      "Saving Mr. Banks (Thomas Newman)"))
  categories += new OscarCategoryNominees("Best Original Song",
    Array (
      "\"Happy\" Despicable Me 2",
      "\"Let It Go\" Frozen",
      "\"The Moon Song\" Her",
      "\"Ordinary Love\" Mandela: Long Walk to Freedom"))
  categories += new OscarCategoryNominees("Best Production Design",
    Array (
      "American Hustle",
      "Gravity",
      "The Great Gatsby",
      "Her",
      "12 Years a Slave"))
  categories += new OscarCategoryNominees("Best Animated Short Film",
    Array (
      "Feral",
      "Get a Horse!",
      "Mr. Hublot",
      "Possessions",
      "Room on the Broom"))
  categories += new OscarCategoryNominees("Best Live Action Short Film",
    Array (
      "Aquel No Era Yo (That Wasn't Me)",
      "Avant Que De Tout Perdre (Just Before Losing Everything)",
      "Helium",
      "Pitääkö Mun Kaikki Hoitaa? (Do I Have to Take Care of Everything?)",
      "The Voorman Problem"))
  categories += new OscarCategoryNominees("Best Sound Editing",
    Array (
      "All Is Lost",
      "Captain Phillips",
      "Gravity",
      "The Hobbit: The Desolation of Smaug",
      "Lone Survivor"))
  categories += new OscarCategoryNominees("Best Sound Mixing",
    Array (
      "Captain Phillips",
      "Gravity",
      "The Hobbit: The Desolation of Smaug",
      "Inside Llewyn Davis",
      "Lone Survivor"))
  categories += new OscarCategoryNominees("Best Visual Effects",
    Array (
      "Gravity",
      "The Hobbit: The Desolation of Smaug",
      "Iron Man 3",
      "The Lone Ranger",
      "Star Trek Into Darkness"))

  categories += new OscarCategoryNominees("Best Adapted Screenplay",
    Array (
      "Before Midnight",
      "Captain Phillips",
      "Philomena",
      "12 Years a Slave",
      "The Wolf of Wall Street"))

  categories += new OscarCategoryNominees("Best Original Screenplay",
    Array (
      "American Hustle",
      "Blue Jasmine",
      "Dallas Buyers Club",
      "Her",
      "Nebraska"))
}
