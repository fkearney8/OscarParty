package com.oscarparty.servlets.data.nominees

import scala.collection.mutable.ArrayBuffer

object AllOscarNominees2017 {
  private val categories = new ArrayBuffer[OscarCategoryNominees]()
  categories += new OscarCategoryNominees("Best Picture",
    Array (
      "Arrival",
      "Fences",
      "Hacksaw Ridge",
      "Hell or High Water",
      "Hidden Figures",
      "La La Land",
      "Lion",
      "Manchester by the Sea",
      "Moonlight"
    ),
    Array (12, 9, 6))
  categories += new OscarCategoryNominees("Best Actor in a Leading Role",
    Array (
      "Casey Affleck (Manchester by the Sea)",
      "Andrew Garfield (Hacksaw Ridge)",
      "Ryan Gosling (La La Land)",
      "Viggo Mortensen (Captain Fantastic)",
      "Denzel Washington (Fences)"
    ),
    Array(8,6,4))
  categories += new OscarCategoryNominees("Best Actress in a Leading Role",
    Array (
      " Isabelle Huppert (Elle)",
      " Ruth Negga (Loving)",
      " Natalie Portman (Jackie)",
      " Emma Stone (La La Land)",
      " Meryl Streep (Florence Foster Jenkins)"
    ),
    Array(8,6,4))
  categories += new OscarCategoryNominees("Best Actor in a Supporting Role",
    Array ("Mahershala Ali (Moonlight)",
      "Jeff Bridges (Hell or High Water)",
      "Lucas Hedges (Manchester by the Sea)",
      "Dev Patel (Lion)",
      "Michael Shannon (Nocturnal Animals)"
    ),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Actress in a Supporting Role",
    Array (
      "Viola Davis (Fences)",
      "Naomie Harris (Moonlight)",
      "Nicole Kidman (Lion)",
      "Octavia Spencer (Hidden Figures)",
      "Michelle Williams (Manchester by the Sea)"
    ),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Director",
    Array (
      "Arrival - Denis Villeneuve",
      "Hacksaw Ridge - Mel Gibson",
      "La La Land - Damien Chazelle",
      "Manchester by the Sea - Kenneth Lonergan",
      "Moonlight - Barry Jenkins"
    ),
    Array(5,4,3))
  categories += new OscarCategoryNominees("Best Animated Feature",
    Array (
      "Kubo and the Two Strings",
      "Moana",
      "My Life as a Zucchini",
      "The Red Turtle",
      "Zootopia"
    ))
  categories += new OscarCategoryNominees("Best Cinematography",
    Array (
      "Arrival - Bradford Young",
      "La La Land - Linus Sandgren",
      "Lion - Greig Fraser",
      "Moonlight - James Laxton",
      "Silence - Rodrigo Prieto"
    ))
  categories += new OscarCategoryNominees("Best Costume Design",
    Array (
      "Allied - Joanna Johnston",
      "Fantastic Beasts and Where to Find Them - Colleen Atwood",
      "Florence Foster Jenkins - Consolata Boyle",
      "Jackie - Madeline Fontaine",
      "La La Land - Mary Zophres"
    ))
  categories += new OscarCategoryNominees("Best Documentary Feature",
    Array (
      "Fire at Sea",
      "I Am Not Your Negro",
      "Life, Animated",
      "O.J.: Made in America",
      "13th"
    ))
  categories += new OscarCategoryNominees("Best Documentary Short",
    Array (
      "Extremis",
      "4.1 Miles",
      "Joe's Violin",
      "Watani: My Homeland",
      "The White Helmets"
    ))
  categories += new OscarCategoryNominees("Best Film Editing",
    Array (
      "Arrival",
      "Hacksaw Ridge",
      "Hell or High Water",
      "La La Land",
      "Moonlight"
    ))
  categories += new OscarCategoryNominees("Best Foreign Language Film",
    Array (
      "Land of Mine (Denmark)",
      "A Man Called Ove (Sweden)",
      "The Salesman (Iran)",
      "Tanna (Australia)",
      "Toni Erdmann (Germany)"
    ))
  categories += new OscarCategoryNominees("Best Makeup and Hairstyling",
    Array (
      "A Man Called Ove",
      "Star Trek Beyond",
      "Suicide Squad"
    ))
  categories += new OscarCategoryNominees("Best Original Score",
    Array (
      "Jackie",
      "La La Land",
      "Lion",
      "Moonlight",
      "Passengers"
    ))
  categories += new OscarCategoryNominees("Best Original Song",
    Array (
      "Audition (The Fools Who Dream) - La La Land; Music by Justin Hurwitz; Lyric by Benj Pasek and Justin Paul",
      "Can't Stop The Feeling - Trolls; Music and Lyric by Justin Timberlake, Max Martin and Karl Johan Schuster",
      "City of Stars - La La Land; Music by Justin Hurwitz; Lyric by Benj Pasek and Justin Paul",
      "The Empty Chair - Jim: The James Foley Story; Music and Lyric by J. Ralph and Sting",
      "How Far I'll Go - from Moana; Music and Lyric by Lin-Manuel Miranda"
    ))
  categories += new OscarCategoryNominees("Best Production Design",
    Array (
      "Arrival",
      "Fantastic Beasts and Where to Find Them",
      "Hail, Caesar!",
      "La La Land",
      "Passengers"
    ))
  categories += new OscarCategoryNominees("Best Animated Short Film",
    Array (
      "Blind Vaysha",
      "Borrowed Time",
      "Pear Cider and Cigarettes",
      "Pearl",
      "Piper"
    ))
  categories += new OscarCategoryNominees("Best Live Action Short Film",
    Array (
      "Ennemis Intérieurs",
      "La Femme et le TGV",
      "Silent Nights",
      "Sing",
      "Timecode"
    ))
  categories += new OscarCategoryNominees("Best Sound Editing",
    Array (
      "Arrival",
      "Deepwater Horizon",
      "Hacksaw Ridge",
      "La La Land",
      "Sully"
    ))
  categories += new OscarCategoryNominees("Best Sound Mixing",
    Array (
      "Arrival",
      "Hacksaw Ridge",
      "La La Land",
      "Rogue One: A Star Wars Story",
      "13 Hours: The Secret Soldiers of Benghazi"
    ))
  categories += new OscarCategoryNominees("Best Visual Effects",
    Array (
      "Deepwater Horizon",
      "Doctor Strange",
      "The Jungle Book",
      "Kubo and the Two Strings",
      "Rogue One: A Star Wars Story"
    ))
  categories += new OscarCategoryNominees("Best Adapted Screenplay",
    Array (
      "Arrival",
      "Fences",
      "Hidden Figures",
      "Lion",
      "Moonlight"
    ))
  categories += new OscarCategoryNominees("Best Original Screenplay",
    Array (
      "Hell or High Water",
      "La La Land",
      "The Lobster",
      "Manchester by the Sea",
      "20th Century Women"
    ))
}
