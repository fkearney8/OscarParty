package com.oscarparty.servlets.data.nominees

import scala.collection.mutable.ArrayBuffer

object AllOscarNominees2018 {
  private val categories = new ArrayBuffer[OscarCategoryNominees]()


  categories += OscarCategoryNominees("Best Picture", Array(
    "Call Me by Your Name",
    "Darkest Hour",
    "Dunkirk",
    "Get Out",
    "Lady Bird",
    "Phantom Thread",
    "The Post",
    "The Shape of Water",
    "Three Billboards Outside Ebbing, Missouri"
  ))

  categories += OscarCategoryNominees("Best Director", Array(
    "Christopher Nolan - Dunkirk",
    "Jordan Peele - Get Out",
    "Greta Gerwig - Lady Bird",
    "Paul Thomas Anderson - Phantom Thread",
    "Guillermo del Toro - The Shape of Water"
  ))

  categories += OscarCategoryNominees("Best Actor", Array(
    "Timothée Chalamet - Call Me by Your Name as Elio Perlman",
    "Daniel Day-Lewis - Phantom Thread as Reynolds Woodcock",
    "Daniel Kaluuya - Get Out as Chris Washington",
    "Gary Oldman - Darkest Hour as Winston Churchill",
    "Denzel Washington - Roman J. Israel, Esq. as Roman J. Israel"
  ))

  categories += OscarCategoryNominees("Best Actress", Array(
    "Sally Hawkins - The Shape of Water as Elisa Esposito",
    "Frances McDormand - Three Billboards Outside Ebbing, Missouri as Mildred Hayes",
    "Margot Robbie - I, Tonya as Tonya Harding",
    "Saoirse Ronan - Lady Bird as Christine \"Lady Bird\" McPherson",
    "Meryl Streep - The Post as Katharine Graham"
  ))

  categories += OscarCategoryNominees("Best Supporting Actor", Array(
    "Willem Dafoe - The Florida Project as Bobby Hicks",
    "Woody Harrelson - Three Billboards Outside Ebbing, Missouri as Chief Bill Willoughby",
    "Richard Jenkins - The Shape of Water as Giles",
    "Christopher Plummer - All the Money in the World as J. Paul Getty",
    "Sam Rockwell - Three Billboards Outside Ebbing, Missouri as Officer Jason Dixon"
  ))

  categories += OscarCategoryNominees("Best Supporting Actress", Array(
    "Mary J. Blige - Mudbound as Florence Jackson",
    "Allison Janney - I, Tonya as LaVona Golden",
    "Lesley Manville - Phantom Thread as Cyril Woodcock",
    "Laurie Metcalf - Lady Bird as Marion McPherson",
    "Octavia Spencer - The Shape of Water as Zelda Delilah Fuller"
  ))

  categories += OscarCategoryNominees("Best Original Screenplay", Array(
    "The Big Sick - Written by Emily V. Gordon and Kumail Nanjiani",
    "Get Out - Written by Jordan Peele",
    "Lady Bird - Written by Greta Gerwig",
    "The Shape of Water - Screenplay by Guillermo del Toro and Vanessa Taylor; Story by Guillermo del Toro",
    "Three Billboards Outside Ebbing, Missouri - Written by Martin McDonagh"
  ))

  categories += OscarCategoryNominees("Best Adapted Screenplay", Array(
    "Call Me by Your Name",
    "The Disaster Artist",
    "Logan",
    "Molly's Game",
    "Mudbound"
  ))

  categories += OscarCategoryNominees("Best Animated Feature Film", Array(
    "The Boss Baby",
    "The Breadwinner",
    "Coco",
    "Ferdinand",
    "Loving Vincent"
  ))

  categories += OscarCategoryNominees("Best Foreign Language Film", Array(
    "A Fantastic Woman (Chile)) in Spanish",
  "The Insult (Lebanon)) in Arabic",
  "Loveless (Russia)) in Russian",
  "On Body and Soul (Hungary)) in Hungarian",
  "The Square (Sweden)) in Swedish"
  ))

  categories += OscarCategoryNominees("Best Documentary Feature", Array(
    "Abacus: Small Enough to Jail",
    "Faces Places",
    "Icarus",
    "Last Men in Aleppo",
    "Strong Island"
  ))

  categories += OscarCategoryNominees("Best Documentary Short Subject", Array(
    "Edith+Eddie",
    "Heaven Is a Traffic Jam on the 405",
    "Heroin(e))",
    "Knife Skills",
    "Traffic Stop"
  ))

  categories += OscarCategoryNominees("Best Live Action Short Film", Array(
    "DeKalb Elementary",
    "The Eleven O'Clock",
    "My Nephew Emmett",
    "The Silent Child",
    "Watu Wote/All of Us"
  ))

  categories += OscarCategoryNominees("Best Animated Short Film", Array(
    "Dear Basketball",
    "Garden Party",
    "Lou",
    "Negative Space",
    "Revolting Rhymes"
  ))

  categories += OscarCategoryNominees("Best Original Score", Array(
    "Dunkirk - Hans Zimmer",
    "Phantom Thread - Jonny Greenwood",
    "The Shape of Water - Alexandre Desplat",
    "Star Wars: The Last Jedi - John Williams",
    "Three Billboards Outside Ebbing, Missouri - Carter Burwell"
  ))

  categories += OscarCategoryNominees("Best Original Song", Array(
    "\"Mighty River\" from Mudbound - Music and Lyrics by Mary J. Blige, Raphael Saadiq and Taura Stinson",
    "\"Mystery of Love\" from Call Me by Your Name - Music and Lyrics by Sufjan Stevens",
    "\"Remember Me\" from Coco - Music and Lyrics by Kristen Anderson-Lopez and Robert Lopez",
    "\"Stand Up for Something\" from Marshall - Music by Diane Warren; Lyrics by Common and Diane Warren",
    "\"This Is Me\" from The Greatest Showman - Music and Lyrics by Benj Pasek and Justin Paul"
  ))

  categories += OscarCategoryNominees("Best Sound Editing", Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categories += OscarCategoryNominees("Best Sound Mixing", Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categories += OscarCategoryNominees("Best Production Design", Array(
    "Beauty and the Beast",
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "The Shape of Water"
  ))

  categories += OscarCategoryNominees("Best Cinematography", Array(
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "Mudbound",
    "The Shape of Water"
  ))

  categories += OscarCategoryNominees("Best Makeup and Hairstyling", Array(
    "Darkest Hour",
    "Victoria & Abdul",
    "Wonder"
  ))

  categories += OscarCategoryNominees("Best Costume Design", Array(
    "Beauty and the Beast",
    "Darkest Hour",
    "Phantom Thread",
    "The Shape of Water",
    "Victoria & Abdul - Consolata Boyle"
  ))

  categories += OscarCategoryNominees("Best Film Editing", Array(
    "Baby Driver",
    "Dunkirk",
    "I, Tonya",
    "The Shape of Water",
    "Three Billboards Outside Ebbing, Missouri"
  ))

  categories += OscarCategoryNominees("Best Visual Effects", Array(
    "Blade Runner 2049",
    "Guardians of the Galaxy Vol. 2",
    "Kong: Skull Island",
    "Star Wars: The Last Jedi",
    "War for the Planet of the Apes"
  ))
}
