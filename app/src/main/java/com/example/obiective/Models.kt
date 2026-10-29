package com.example.obiective

enum class Category(val label: String) {
    SPORT("Sport"), MUZEE("Muzee"), RESTAURANTE("Restaurante")
}

data class Place(
    val id: Int,
    val name: String,
    val category: Category,
    val locality: String,
    val description: String
)

object PlacesRepository {
    val places = listOf(
        Place(1, "Muzeul Satului Maramureșean", Category.MUZEE, "Sighetu Marmației",
            "Muzeu în aer liber cu gospodării tradiționale și biserici de lemn din Maramureș."),
        Place(2, "Memorialul Victimelor Comunismului și al Rezistenței", Category.MUZEE, "Sighetu Marmației",
            "Memorial găzduit într-o fostă închisoare politică, dedicat victimelor regimului comunist."),
        Place(3, "Muzeul Județean de Istorie și Arheologie Maramureș", Category.MUZEE, "Baia Mare",
            "Colecții de istorie și arheologie ale județului Maramureș."),
        Place(4, "Domeniul schiabil Cavnic", Category.SPORT, "Cavnic",
            "Domeniu pentru schi și snowboard, deschis în sezonul rece."),
        Place(5, "Stațiunea Borșa (schi, drumeții)", Category.SPORT, "Borșa",
            "Stațiune montană cu pârtii iarna și trasee de drumeție vara."),
        Place(6, "Parcul Național Munții Rodnei (trasee montane)", Category.SPORT, "Borșa / Moisei",
            "Parc național cu trasee montane, peisaje alpine și zone protejate."),
        // TODO: înlocuiți cu restaurante reale din orașul vostru și verificați datele
        Place(7, "Restaurant 1 (de înlocuit)", Category.RESTAURANTE, "Localitatea voastră", "Descriere de completat."),
        Place(8, "Restaurant 2 (de înlocuit)", Category.RESTAURANTE, "Localitatea voastră", "Descriere de completat."),
        Place(9, "Restaurant 3 (de înlocuit)", Category.RESTAURANTE, "Localitatea voastră", "Descriere de completat.")
    )

    fun byId(id: Int): Place? = places.firstOrNull { it.id == id }
}
