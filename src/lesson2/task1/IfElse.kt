@file:Suppress("UNUSED_PARAMETER", "LanguageDetectionInspection")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val rem10: Int = age % 10
    val rem100: Int = age % 100
    return when {
        ((rem10 == 0) || (rem10 > 4) || (rem100 in 11..14)) -> "$age лет"
        (rem10 == 1) -> "$age год"
        else -> "$age года"
    }
}



/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val s: Double = v1 * t1 + v2 * t2 + v3 * t3
    val s1: Double = v1 * t1
    val s2: Double = v2 * t2
    return when {
        (s1 > s / 2) -> s / (2 * v1)
        ((s2 + s1) > s / 2) -> t1 + ((s / 2) - s1) / v2
        else -> t1 + t2 + ((s / 2) - s1 - s2) / v3
    }
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int = when {
    (kingX != rookX1) && (kingX != rookX2) && (kingY != rookY1) && (kingY != rookY2) -> 0
    ((kingX == rookX1) && (kingY == rookY2)) || ((kingX == rookX2) && (kingY == rookY1)) -> 3
    (kingX == rookX1) || (kingY == rookY1) -> 1
    else -> 2
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int {
    fun doesBishopThreaten (kingX: Int, kingY: Int, bishopX: Int, bishopY: Int): Boolean =
        ((kingY + kingX) == (bishopX + bishopY)) || ((kingY - bishopY) == (kingX - bishopX))

    val threatOfTheBishop = doesBishopThreaten(kingX, kingY, bishopX, bishopY)
    return when {
        (kingX != rookX) && (kingY != rookY) && !threatOfTheBishop -> 0
        ((kingX == rookX) || (kingY == rookY)) && threatOfTheBishop -> 3
        threatOfTheBishop -> 2
        else -> 1
    }
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    val isTriangleExists = (a + b > c) && (a + c > b) && (b + c > a)
    val isTriangleSharp = (a.pow(2) + b.pow(2) > c.pow(2)) &&
            (a.pow(2) + c.pow(2) > b.pow(2)) &&
            (c.pow(2) + b.pow(2) > a.pow(2))
    val isTriangleRight = (a.pow(2) + b.pow(2) == c.pow(2)) ||
            (a.pow(2) + c.pow(2) == b.pow(2)) ||
            (c.pow(2) + b.pow(2) == a.pow(2))
    return when {
        !isTriangleExists -> -1
        isTriangleSharp -> 0
        isTriangleRight -> 1
        else -> 2
    }
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int = when {
    ((c > b) || (a > d)) -> -1
    (c == b) || (a == d) -> 0
    ((c >= a) && (b <= d)) -> b - c
    ((c <= a) && (b >= d)) -> d - a
    (c >= a) -> d - c
    else -> b - a
}
