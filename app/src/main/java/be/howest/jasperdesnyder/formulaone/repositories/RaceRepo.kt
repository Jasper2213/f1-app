package be.howest.jasperdesnyder.formulaone.repositories
//
//import be.howest.jasperdesnyder.formulaone.R
//
//object RaceRepo {
//    val raceOlds = listOf (
//        RaceOld(
//            title = R.string.bahrain,
//            date = "03 - 05 mar",
//            trackLayoutRes = R.drawable.bahrain,
//            results = listOf(
//                Result(
//                    driver = "M. Verstappen",
//                    team = "Red Bull",
//                    position = 1,
//                    points = 25
//                ),
//                Result(
//                    driver = "S. Perez",
//                    team = "Red Bull",
//                    position = 2,
//                    points = 18
//                ),
//                Result(
//                    driver = "C. Leclerc",
//                    team = "Ferrari",
//                    position = 3,
//                    points = 15
//                ),
//                Result(
//                    driver = "C. Sainz",
//                    team = "Ferrari",
//                    position = 4,
//                    points = 12
//                ),
//                Result(
//                    driver = "L. Hamilton",
//                    team = "Mercedes",
//                    position = 5,
//                    points = 10
//                ),
//                Result(
//                    driver = "G. Russell",
//                    team = "Mercedes",
//                    position = 6,
//                    points = 8
//                ),
//                Result(
//                    driver = "E. Ocon",
//                    team = "Alpine F1 Team",
//                    position = 7,
//                    points = 6
//                ),
//                Result(
//                    driver = "P. Gasly",
//                    team = "Alpine F1 Team",
//                    position = 8,
//                    points = 4
//                ),
//                Result(
//                    driver = "L. Norris",
//                    team = "McLaren",
//                    position = 9,
//                    points = 2
//                ),
//                Result(
//                    driver = "O. Piastri",
//                    team = "McLaren",
//                    position = 10,
//                    points = 1
//                ),
//                Result(
//                    driver = "V. Bottas",
//                    team = "Alfa Romeo",
//                    position = 11
//                ),
//                Result(
//                    driver = "G. Zhou",
//                    team = "Alfa Romeo",
//                    position = 12
//                ),
//                Result(
//                    driver = "K. Magnussen",
//                    team = "Haas F1 Team",
//                    position = 13
//                ),
//                Result(
//                    driver = "N. Hulkenberg",
//                    team = "Haas F1 Team",
//                    position = 14
//                ),
//                Result(
//                    driver = "F. Alonso",
//                    team = "Aston Martin",
//                    position = 15
//                ),
//                Result(
//                    driver = "L. Stroll",
//                    team = "Aston Martin",
//                    position = 16
//                ),
//                Result(
//                    driver = "A. Albon",
//                    team = "Williams",
//                    position = 17
//                ),
//                Result(
//                    driver = "O. Piastri",
//                    team = "Williams",
//                    position = 18
//                ),
//                Result(
//                    driver = "Y. Tsunoda",
//                    team = "AlphaTauri",
//                    position = 19
//                ),
//                Result(
//                    driver = "N. De Vries",
//                    team = "AlphaTauri",
//                    position = 20
//                )
//            )
//        ),
//        RaceOld(
//            title = R.string.saudi_arabia,
//            date = "17 - 19 mar",
//            trackLayoutRes = R.drawable.saudi_arabia
//        ),
//        RaceOld(
//            title = R.string.australia,
//            date = "31 - 02 mar-apr",
//            trackLayoutRes = R.drawable.australia
//        ),
//        RaceOld(
//            title = R.string.azerbaijan,
//            date = "28 - 30 apr",
//            trackLayoutRes = R.drawable.azerbaijan
//        ),
//        RaceOld(
//            title = R.string.miami,
//            date = "05 - 07 may",
//            trackLayoutRes = R.drawable.miami
//        ),
//        RaceOld(
//            title = R.string.imola,
//            date =  "19 - 21 may",
//            trackLayoutRes = R.drawable.imola
//        ),
//        RaceOld(
//            title = R.string.monaco,
//            date = "26 - 28 may",
//            trackLayoutRes = R.drawable.monaco
//        ),
//        RaceOld(
//            title = R.string.spain,
//            date = "02 - 04 jun",
//            trackLayoutRes = R.drawable.spain
//        ),
//        RaceOld(
//            title = R.string.canada,
//            date = "16 - 18 jun",
//            trackLayoutRes = R.drawable.canada
//        ),
//        RaceOld(
//            title = R.string.austria,
//            date = "30 - 02 jun-jul",
//            trackLayoutRes = R.drawable.austria
//        ),
//        RaceOld(
//            title = R.string.great_britain,
//            date = "07 - 09 jul",
//            trackLayoutRes = R.drawable.britain
//        ),
//        RaceOld(
//            title = R.string.hungary,
//            date = "21 - 23 jul",
//            trackLayoutRes = R.drawable.hungary
//        ),
//        RaceOld(
//            title = R.string.belgium,
//            date = "28 - 30 jul",
//            trackLayoutRes = R.drawable.belgium
//        ),
//        RaceOld(
//            title = R.string.netherlands,
//            date = "25 - 27 aug",
//            trackLayoutRes = R.drawable.netherlands
//        ),
//        RaceOld(
//            title = R.string.monza,
//            date = "01 - 03 sep",
//            trackLayoutRes = R.drawable.monza
//        ),
//        RaceOld(
//            title = R.string.singapore,
//            date = "15 - 17 sep",
//            trackLayoutRes = R.drawable.singapore
//        ),
//        RaceOld(
//            title = R.string.japan,
//            date = "22 - 24 sep",
//            trackLayoutRes = R.drawable.japan
//        ),
//        RaceOld(
//            title = R.string.qatar,
//            date = "06 - 08 oct",
//            trackLayoutRes = R.drawable.qatar
//        ),
//        RaceOld(
//            title = R.string.united_states,
//            date = "20 - 22 oct",
//            trackLayoutRes = R.drawable.united_states
//        ),
//        RaceOld(
//            title = R.string.mexico,
//            date = "27 - 29 oct",
//            trackLayoutRes = R.drawable.mexico
//        ),
//        RaceOld(
//            title = R.string.brazil,
//            date = "03 - 05 nov",
//            trackLayoutRes = R.drawable.brazil
//        ),
//        RaceOld(
//            title = R.string.las_vegas,
//            date = "16 - 18 nov",
//            trackLayoutRes = R.drawable.las_vegas
//        ),
//        RaceOld(
//            title = R.string.abu_dhabi,
//            date = "24 - 26 nov",
//            trackLayoutRes = R.drawable.abu_dhabi
//        )
//    )
//}