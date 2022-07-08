@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
//fun main(args: Array<String>) {
//    println("Введите время в формате ЧЧ:ММ:СС")
//    val line = readLine()
//    if (line != null) {
//        val seconds = timeStrToSeconds(line)
//        if (seconds == -1) {
//            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
//        } else {
//            println("Прошло секунд с начала суток: $seconds")
//        }
//    } else {
//        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
//    }
//}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */

fun isLeapYear(year: Int): Boolean = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

fun daysInMonth(month: Int, year: Int): Int = when {
    (month == 2) && isLeapYear(year) -> 29
    (month == 2) -> 28
    (month <= 7) && (month % 2 == 0) -> 30
    (month <= 7) -> 31
    (month % 2 == 0) -> 31
    else -> 30
}

fun dateStrToDigit(str: String): String {
    val parsed = str.split(" ")
    if (parsed.size != 3) return ""
    try {
        val day = parsed[0].toInt()
        val year = parsed[2].toInt()

        val mapOfMonths = mapOf(
                "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5, "июня" to 6,
                "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12
        )

        val month = mapOfMonths[parsed[1]] ?: return ""
        if (year <= 0) return ""
        val isDayValid = day in 1..daysInMonth(month, year)
        if (!isDayValid) return ""

        return String.format("%02d.%02d.%4d", day, month, year)

    } catch (e: Exception) {
        return ""
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parsed = digital.split(".")
    if (parsed.size != 3) return ""
    try {
        val day = parsed[0].toInt()
        val monthInt = parsed[1].toInt()
        val year = parsed[2].toInt()

        val mapOfMonths = mapOf(
                1 to "января", 2 to "февраля", 3 to "марта", 4 to "апреля", 5 to "мая", 6 to "июня",
                7 to "июля", 8 to "августа", 9 to "сентября", 10 to "октября", 11 to "ноября", 12 to "декабря"
        )

        val monthStr = mapOfMonths[monthInt] ?: return ""
        if (year <= 0) return ""
        val isDayValid = day in 1..daysInMonth(monthInt, year)
        if (!isDayValid) return ""

        return String.format("%d %s %d", day, monthStr, year)

    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val setOfValidChars = setOf('-', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ')
    val setOfUsefulChars = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    val phoneOut = mutableListOf<Char>()

    val charPhone = phone.trim().toMutableList()
    if (charPhone[0] == '+') {
        phoneOut.add('+')
        charPhone.removeFirst()
    } else if (charPhone.contains('+')) {
        return ""
    }

    if (charPhone.any { !setOfValidChars.contains(it) }) return ""
    phoneOut.addAll(charPhone.filter { setOfUsefulChars.contains(it) })

    return phoneOut.joinToString(separator = "")
}

fun flattenPhoneNumber2(phone: String): String {
    val phoneOut = mutableListOf<Char>()

    val charPhone = phone.trim().toMutableList()
    if (charPhone[0] == '+') {
        phoneOut.add('+')
        charPhone.removeFirst()
    } else if (charPhone.contains('+')) {
        return ""
    }

    val regexValidChars = Regex("""[\d()\-\s]""")
    val regexUsefulChars = Regex("""\d""")

    if (charPhone.any { regexValidChars.find(it.toString()) == null }) return ""
    phoneOut.addAll(charPhone.filter { regexUsefulChars.find(it.toString()) != null })

    return phoneOut.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val tries = jumps.split(" ")
    val symbolsToDelete = setOf("-", "%")
    return try {
        val goodTries = tries.filter { !symbolsToDelete.contains(it) }.map { el -> el.toInt() }
        goodTries.max()
    } catch (e: Exception) {
        -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val tries = jumps.split(" ")
    val meaningfulSymbols = setOf('-', '%', '+')
    val mapOfResults = mutableMapOf<Int, Set<Char>>()
    try {
        for (i in tries.indices - 1) {
            if (i % 2 == 0) {
                mapOfResults[tries[i].toInt()] = tries[i + 1].toSet()
            }
        }
        val goodTries = mutableListOf<Int>()
        for ((key, value) in mapOfResults) {
            when {
                value.any { !meaningfulSymbols.contains(it) } -> return -1
                value.contains('+') -> goodTries.add(key)
                else -> continue
            }
        }
        return goodTries.max()
    } catch (e: Exception) {
        return -1
    }

}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val inputList = expression.trim().split(" ").toMutableList()
    val outList = mutableListOf<Int>()
    if (inputList.any { it.contains(Regex("""[+-]\d+|\d+[+-]""")) }) throw IllegalArgumentException("")
    try {
        outList.add(inputList[0].toInt())
        inputList.removeFirst()
        for (i in inputList.indices) {
            if (i % 2 == 0) {
                if (inputList[i] == "+") outList.add(inputList[i + 1].toInt())
                else if (inputList[i] == "-") outList.add(-inputList[i + 1].toInt())
                else throw IllegalArgumentException("")
            }
        }
        return outList.sum()
    } catch (e: Exception) {
        throw IllegalArgumentException("")
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val inputStr = str.trim().lowercase()
    val regExp = Regex("""([а-я]+)\s\1""")
    return regExp.find(inputStr)?.range?.first ?: -1
}

fun firstDuplicateIndex2(str: String): Int = Regex("""([а-я]+)\s\1""").find(str.trim().lowercase())?.range?.first ?: -1



/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String = TODO()

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
