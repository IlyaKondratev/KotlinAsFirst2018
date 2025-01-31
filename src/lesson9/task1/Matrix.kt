@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
    if ((height <= 0) || (width <= 0)) throw IllegalArgumentException() else MatrixImpl(height, width, e)


/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    val list = MutableList(height) { MutableList(width) { e } }
    override fun get(row: Int, column: Int): E = list[row][column]

    override fun get(cell: Cell): E = list[cell.row][cell.column]

    override fun set(row: Int, column: Int, value: E) {
        list[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        list[cell.row][cell.column] = value
    }

    override fun equals(other: Any?) =
        (other is MatrixImpl<*>) && (other.height == height) && (other.width == width) && (other.list == list)

    override fun toString(): String {
        val builder = StringBuilder()
        for (row in list) {
            builder.append("[ ")
            for (el in row) {
                builder.append("$el ")
            }
            builder.append("]\r\n")
        }
        return builder.toString()
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        return result
    }
}
