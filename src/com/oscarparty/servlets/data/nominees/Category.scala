package com.oscarparty.servlets.data.nominees

/** The points for each choice in a category. */
sealed case class CategoryPoints(points1st: Int, points2nd: Int, points3rd: Int)
object HighPoints extends CategoryPoints(12, 9, 6)
object PrettyHighPoints extends CategoryPoints(8, 6, 4)
object MediumPoints extends CategoryPoints(5,4,3)
object MinimumPoints extends CategoryPoints(3, 2, 1)

object CategoryName extends Enumeration {
  protected case class Val(displayName: String, points: CategoryPoints = MinimumPoints) extends super.Val

  val BestPicture = Val("Best Picture", HighPoints)
  val Actor = Val("Best Actor", PrettyHighPoints)
  val Actress = Val("Best Actress", PrettyHighPoints)
  val Director = Val("Best Director", PrettyHighPoints)
  val SupportingActor = Val("Best Supporting Actor", MediumPoints)
  val SupportingActress = Val("Best Supporting Actress", MediumPoints)
  val OriginalScreenplay = Val("Best Original Screenplay")
  val AdaptedScreenplay = Val("Best Adapted Screenplay")
  val AnimatedFeatureFilm = Val("Best Animated Feature Film")
  val ForeignLanguageFilm = Val("Best Foreign Language Film")
  val DocumentaryFeature = Val("Best Documentary Feature")
  val DocumentaryShortSubject = Val("Best Documentary Short Subject")
  val LiveActionShortFilm = Val("Best Live Action Short Film")
  val AnimatedShortFilm = Val("Best Animated Short Film")
  val OriginalScore = Val("Best Original Score")
  val OriginalSong = Val("Best Original Song")
  val SoundEditing = Val("Best Sound Editing")
  val SoundMixing = Val("Best Sound Mixing")
  val ProductionDesign = Val("Best Production Design")
  val Cinematography = Val("Best Cinematography")
  val MakeupAndHairstyling = Val("Best Makeup and Hairstyling")
  val CostumeDesign = Val("Best Costume Design")
  val FilmEditing = Val("Best Film Editing")
  val VisualEffects = Val("Best Visual Effects")
}



