import kotlinx.coroutines.flow.*

suspend fun main() {

    val persons = arrayOf("Виктор","Елизавета","Андрей","Сергей","Полина","Павел","Даниил")

    val firstFlow = persons.asFlow()
    val secondFlow = getCardNumber(persons.size).asFlow()
    val thirdFlow = getPass(persons.size).asFlow()

    streamsCombine(firstFlow,secondFlow,thirdFlow).collect { it -> println(it) }

}

fun <T,V,C> streamsCombine(first: Flow<T>, second: Flow<V>, third: Flow<C>): Flow<Person> {

    val combinedFlow = first.zip(second){a,b -> Pair(a,b)}
        .zip(third){pair, c -> Person(pair.first.toString(),pair.second.toString(),c.toString())  }

    return combinedFlow
}

fun getPass(length: Int): MutableList<String>{
    val passArray:MutableList<String> = mutableListOf()
    repeat(length){
        passArray.add((1000..9999).random().toString())
    }
    return passArray
}

fun getCardNumber(length: Int): MutableList<String> {

    val arrayCardNumbers: MutableList<String> = mutableListOf()
    repeat(length){
        val array: MutableList<String> = mutableListOf()
        repeat(4){
            val num = (1000..9999).random().toString()
            array.add(num)
        }
        arrayCardNumbers.add(array.reduce{a,b -> "$a $b"}.trim())
    }
    return arrayCardNumbers

}

data class Person(val name: String, val cardNumber: String, val cardPass: String = "0000"){
    override fun toString(): String {
        return "Сотрудник: $name, Номер карты: $cardNumber, Пароль карты: $cardPass"
    }
}