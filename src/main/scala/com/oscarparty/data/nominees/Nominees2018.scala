package com.oscarparty.data.nominees

import scala.collection.mutable.ArrayBuffer

object Nominees2018 {

  lazy val categoryNominees: Map[CategoryName.Value, CategoryNominees] = {
    categoryNomineesForInput.map { eachCatNoms =>
      val catName = eachCatNoms.categoryName
      val noms = CategoryNominees(eachCatNoms.categoryName, eachCatNoms.nominees)
      catName -> noms
    }.toMap
  }

  def nomineesForCategory(category: CategoryName.Value): CategoryNominees = categoryNominees(category)

  private def input(categoryName: CategoryName.Value, nomineeNames: Seq[String]): CategoryNominees = {
    val nomNameWithIndex: Seq[(String, Int)] = nomineeNames.zipWithIndex
    CategoryNominees(categoryName,
      nomNameWithIndex.map {
        case (nomName, index) => Nominee(nomName, index)
      })
  }

  private val categoryNomineesForInput = new ArrayBuffer[CategoryNominees]()

  categoryNomineesForInput += input(CategoryName.BestPicture, Array(
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

  categoryNomineesForInput += input(CategoryName.Director, Array(
    "Christopher Nolan - Dunkirk",
    "Jordan Peele - Get Out",
    "Greta Gerwig - Lady Bird",
    "Paul Thomas Anderson - Phantom Thread",
    "Guillermo del Toro - The Shape of Water"
  ))

  categoryNomineesForInput += input(CategoryName.Actor, Array(
    "Timothée Chalamet - Call Me by Your Name as Elio Perlman",
    "Daniel Day-Lewis - Phantom Thread as Reynolds Woodcock",
    "Daniel Kaluuya - Get Out as Chris Washington",
    "Gary Oldman - Darkest Hour as Winston Churchill",
    "Denzel Washington - Roman J. Israel, Esq. as Roman J. Israel"
  ))

  categoryNomineesForInput += input(CategoryName.Actress, Array(
    "Sally Hawkins - The Shape of Water as Elisa Esposito",
    "Frances McDormand - Three Billboards Outside Ebbing, Missouri as Mildred Hayes",
    "Margot Robbie - I, Tonya as Tonya Harding",
    "Saoirse Ronan - Lady Bird as Christine \"Lady Bird\" McPherson",
    "Meryl Streep - The Post as Katharine Graham"
  ))

  categoryNomineesForInput += input(CategoryName.SupportingActor, Array(
    "Willem Dafoe - The Florida Project as Bobby Hicks",
    "Woody Harrelson - Three Billboards Outside Ebbing, Missouri as Chief Bill Willoughby",
    "Richard Jenkins - The Shape of Water as Giles",
    "Christopher Plummer - All the Money in the World as J. Paul Getty",
    "Sam Rockwell - Three Billboards Outside Ebbing, Missouri as Officer Jason Dixon"
  ))

  categoryNomineesForInput += input(CategoryName.SupportingActress, Array(
    "Mary J. Blige - Mudbound as Florence Jackson",
    "Allison Janney - I, Tonya as LaVona Golden",
    "Lesley Manville - Phantom Thread as Cyril Woodcock",
    "Laurie Metcalf - Lady Bird as Marion McPherson",
    "Octavia Spencer - The Shape of Water as Zelda Delilah Fuller"
  ))

  categoryNomineesForInput += input(CategoryName.OriginalScreenplay, Array(
    "The Big Sick - Emily V. Gordon and Kumail Nanjiani",
    "Get Out - Jordan Peele",
    "Lady Bird - Greta Gerwig",
    "The Shape of Water - Guillermo del Toro and Vanessa Taylor",
    "Three Billboards Outside Ebbing, Missouri - Martin McDonagh"
  ))

  categoryNomineesForInput += input(CategoryName.AdaptedScreenplay, Array(
    "Call Me by Your Name",
    "The Disaster Artist",
    "Logan",
    "Molly's Game",
    "Mudbound"
  ))

  categoryNomineesForInput += input(CategoryName.AnimatedFeatureFilm, Array(
    "The Boss Baby",
    "The Breadwinner",
    "Coco",
    "Ferdinand",
    "Loving Vincent"
  ))

  categoryNomineesForInput += input(CategoryName.ForeignLanguageFilm, Array(
    "A Fantastic Woman (Chile)) in Spanish",
  "The Insult (Lebanon)) in Arabic",
  "Loveless (Russia)) in Russian",
  "On Body and Soul (Hungary)) in Hungarian",
  "The Square (Sweden)) in Swedish"
  ))

  categoryNomineesForInput += input(CategoryName.DocumentaryFeature, Array(
    "Abacus: Small Enough to Jail",
    "Faces Places",
    "Icarus",
    "Last Men in Aleppo",
    "Strong Island"
  ))

  categoryNomineesForInput += input(CategoryName.DocumentaryShortSubject, Array(
    "Edith+Eddie",
    "Heaven Is a Traffic Jam on the 405",
    "Heroin(e))",
    "Knife Skills",
    "Traffic Stop"
  ))

  categoryNomineesForInput += input(CategoryName.LiveActionShortFilm, Array(
    "DeKalb Elementary",
    "The Eleven O'Clock",
    "My Nephew Emmett",
    "The Silent Child",
    "Watu Wote/All of Us"
  ))

  categoryNomineesForInput += input(CategoryName.AnimatedShortFilm, Array(
    "Dear Basketball",
    "Garden Party",
    "Lou",
    "Negative Space",
    "Revolting Rhymes"
  ))

  categoryNomineesForInput += input(CategoryName.OriginalScore, Array(
    "Dunkirk - Hans Zimmer",
    "Phantom Thread - Jonny Greenwood",
    "The Shape of Water - Alexandre Desplat",
    "Star Wars: The Last Jedi - John Williams",
    "Three Billboards Outside Ebbing, Missouri - Carter Burwell"
  ))

  categoryNomineesForInput += input(CategoryName.OriginalSong, Array(
    "\"Mighty River\" from Mudbound - Mary J. Blige",
    "\"Mystery of Love\" from Call Me by Your Name - Sufjan Stevens",
    "\"Remember Me\" from Coco - Kristen Anderson-Lopez and Robert Lopez",
    "\"Stand Up for Something\" from Marshall - Diane Warren",
    "\"This Is Me\" from The Greatest Showman - Benj Pasek and Justin Paul"
  ))

  categoryNomineesForInput += input(CategoryName.SoundEditing, Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categoryNomineesForInput += input(CategoryName.SoundMixing, Array(
    "Baby Driver",
    "Blade Runner 2049",
    "Dunkirk",
    "The Shape of Water",
    "Star Wars: The Last Jedi"
  ))

  categoryNomineesForInput += input(CategoryName.ProductionDesign, Array(
    "Beauty and the Beast",
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "The Shape of Water"
  ))

  categoryNomineesForInput += input(CategoryName.Cinematography, Array(
    "Blade Runner 2049",
    "Darkest Hour",
    "Dunkirk",
    "Mudbound",
    "The Shape of Water"
  ))

  categoryNomineesForInput += input(CategoryName.MakeupAndHairstyling, Array(
    "Darkest Hour",
    "Victoria & Abdul",
    "Wonder"
  ))

  categoryNomineesForInput += input(CategoryName.CostumeDesign, Array(
    "Beauty and the Beast",
    "Darkest Hour",
    "Phantom Thread",
    "The Shape of Water",
    "Victoria & Abdul - Consolata Boyle"
  ))

  categoryNomineesForInput += input(CategoryName.FilmEditing,Array(
    "Baby Driver",
    "Dunkirk",
    "I, Tonya",
    "The Shape of Water",
    "Three Billboards Outside Ebbing, Missouri"
  ))

  categoryNomineesForInput += input(CategoryName.VisualEffects, Array(
    "Blade Runner 2049",
    "Guardians of the Galaxy Vol. 2",
    "Kong: Skull Island",
    "Star Wars: The Last Jedi",
    "War for the Planet of the Apes"
  ))

}
