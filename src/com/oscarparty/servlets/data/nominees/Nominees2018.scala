package com.oscarparty.servlets.data.nominees

import scala.collection.mutable.ArrayBuffer

object Nominees2018 {
  private val categories = new ArrayBuffer[CategoryNominees]()

  categories += CategoryNominees(CategoryName.BestPicture, Array(
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

  categories += CategoryNominees(CategoryName.Director, Array(
    "Christopher Nolan - Dunkirk",
    "Jordan Peele - Get Out",
    "Greta Gerwig - Lady Bird",
    "Paul Thomas Anderson - Phantom Thread",
    "Guillermo del Toro - The Shape of Water"
  ))

  categories += CategoryNominees(CategoryName.Actor, Array(
    "Timothée Chalamet - Call Me by Your Name as Elio Perlman",
    "Daniel Day-Lewis - Phantom Thread as Reynolds Woodcock",
    "Daniel Kaluuya - Get Out as Chris Washington",
    "Gary Oldman - Darkest Hour as Winston Churchill",
    "Denzel Washington - Roman J. Israel, Esq. as Roman J. Israel"
  ))

  categories += CategoryNominees(CategoryName.Actress, Array(
    "Sally Hawkins - The Shape of Water as Elisa Esposito",
    "Frances McDormand - Three Billboards Outside Ebbing, Missouri as Mildred Hayes",
    "Margot Robbie - I, Tonya as Tonya Harding",
    "Saoirse Ronan - Lady Bird as Christine \"Lady Bird\" McPherson",
    "Meryl Streep - The Post as Katharine Graham"
  ))

  categories += CategoryNominees(CategoryName.SupportingActor, Array(
    "Willem Dafoe - The Florida Project as Bobby Hicks",
    "Woody Harrelson - Three Billboards Outside Ebbing, Missouri as Chief Bill Willoughby",
    "Richard Jenkins - The Shape of Water as Giles",
    "Christopher Plummer - All the Money in the World as J. Paul Getty",
    "Sam Rockwell - Three Billboards Outside Ebbing, Missouri as Officer Jason Dixon"
  ))

  categories += CategoryNominees(CategoryName.SupportingActress, Array(
    "Mary J. Blige - Mudbound as Florence Jackson",
    "Allison Janney - I, Tonya as LaVona Golden",
    "Lesley Manville - Phantom Thread as Cyril Woodcock",
    "Laurie Metcalf - Lady Bird as Marion McPherson",
    "Octavia Spencer - The Shape of Water as Zelda Delilah Fuller"
  ))

  categories += CategoryNominees(CategoryName.OriginalScreenplay, Array(
    "The Big Sick - Written by Emily V. Gordon and Kumail Nanjiani",
    "Get Out - Written by Jordan Peele",
    "Lady Bird - Written by Greta Gerwig",
    "The Shape of Water - Screenplay by Guillermo del Toro and Vanessa Taylor; Story by Guillermo del Toro",
    "Three Billboards Outside Ebbing, Missouri - Written by Martin McDonagh"
  ))

  categories += CategoryNominees(CategoryName.AdaptedScreenplay, Array(
    "Call Me by Your Name",
    "The Disaster Artist",
    "Logan",
    "Molly's Game",
    "Mudbound"
  ))

  categories += CategoryNominees(CategoryName.AnimatedFeatureFilm, Array(
    "The Boss Baby",
    "The Breadwinner",
    "Coco",
    "Ferdinand",
    "Loving Vincent"
  ))

  categories += CategoryNominees(CategoryName.ForeignLanguageFilm, Array(
    "A Fantastic Woman (Chile)) in Spanish",
  "The Insult (Lebanon)) in Arabic",
  "Loveless (Russia)) in Russian",
  "On Body and Soul (Hungary)) in Hungarian",
  "The Square (Sweden)) in Swedish"
  ))

  categories += CategoryNominees(CategoryName.DocumentaryFeature, Array(
    "Abacus: Small Enough to Jail",
    "Faces Places",
    "Icarus",
    "Last Men in Aleppo",
    "Strong Island"
  ))

  categories += CategoryNominees(CategoryName.DocumentaryShortSubject, Array(
    "Edith+Eddie",
    "Heaven Is a Traffic Jam on the 405",
    "Heroin(e))",
    "Knife Skills",
    "Traffic Stop"
  ))

  categories += CategoryNominees(CategoryName.LiveActionShortFilm, Array(
    "DeKalb Elementary",
    "The Eleven O'Clock",
    "My Nephew Emmett",
    "The Silent Child",
    "Watu Wote/All of Us"
  ))

  categories += CategoryNominees(CategoryName.AnimatedShortFilm, Array(
    "Dear Basketball",
    "Garden Party",
    "Lou",
    "Negative Space",
    "Revolting Rhymes"
  ))

  categories += CategoryNominees(CategoryName.OriginalScore, Array(
    "Dunkirk - Hans Zimmer",
    "Phantom Thread - Jonny Greenwood",
    "The Shape of Water - Alexandre Desplat",
    "Star Wars: The Last Jedi - John Williams",
    "Three Billboards Outside Ebbing, Missouri - Carter Burwell"
  ))

  categories += CategoryNominees(CategoryName.OriginalSong, Array(
    "\"Mighty River\" from Mudbound - Music and Lyrics by Mary J. Blige, Raphael Saadiq and Taura Stinson",
    "\"Mystery of Love\" from Call Me by Your Name - Music and Lyrics by Sufjan Stevens",
    "\"Remember Me\" from Coco - Music and Lyrics by Kristen Anderson-Lopez and Robert Lopez",
    "\"Stand Up for Something\" from Marshall - Music by Diane Warren; Lyrics by Common and Diane Warren",
    "\"This Is Me\" from The Greatest Showman - Music and Lyrics by Benj Pasek and Justin Paul"
  ))

  categories += CategoryNominees(CategoryName.SoundEditing, Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categories += CategoryNominees(CategoryName.SoundMixing, Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categories += CategoryNominees(CategoryName.ProductionDesign, Array(
    "Beauty and the Beast",
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "The Shape of Water"
  ))

  categories += CategoryNominees(CategoryName.Cinematography, Array(
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "Mudbound",
    "The Shape of Water"
  ))

  categories += CategoryNominees(CategoryName.MakeupAndHairstyling, Array(
    "Darkest Hour",
    "Victoria & Abdul",
    "Wonder"
  ))

  categories += CategoryNominees(CategoryName.CostumeDesign, Array(
    "Beauty and the Beast",
    "Darkest Hour",
    "Phantom Thread",
    "The Shape of Water",
    "Victoria & Abdul - Consolata Boyle"
  ))

  categories += CategoryNominees(CategoryName.FilmEditing,Array(
    "Baby Driver",
    "Dunkirk",
    "I, Tonya",
    "The Shape of Water",
    "Three Billboards Outside Ebbing, Missouri"
  ))

  categories += CategoryNominees(CategoryName.VisualEffects, Array(
    "Blade Runner 2049",
    "Guardians of the Galaxy Vol. 2",
    "Kong: Skull Island",
    "Star Wars: The Last Jedi",
    "War for the Planet of the Apes"
  ))
}
