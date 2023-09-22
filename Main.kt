import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

fun separation(tmp: Boolean, array: IntArray, begin: Int, last: Int): Int {
    var counter = begin
    for (i in begin until last) {
        if (if (tmp) array[i] < array[last] else array[i] > array[last]) {
            val temp = array[counter]
            array[counter] = array[i]
            array[i] = temp
            counter++
        }
    }
    val temp = array[last]
    array[last] = array[counter]
    array[counter] = temp
    return counter
}

fun quickSort(tmp: Boolean, array: IntArray, begin: Int, end: Int) {
    if (end <= begin) return
    val stem = separation(tmp, array, begin, end)
    quickSort(tmp, array, begin, stem - 1)
    quickSort(tmp, array, stem + 1, end)
}

fun show(listArr: List<IntArray>) {
    val count = AtomicInteger()
    listArr.forEach(Consumer { item: IntArray ->
        println("Номер массива: " + count.getAndIncrement() + ", Количество значений: " + item.size)
        for (i in item) {
            print("$i, ")
        }
        println()
    })
}

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    print("Введите количество массивов: ")
    val n = scanner.nextInt()
    val maxSizeArray = 30
    val maxElement = 20
    val random = Random()
    val listArray: MutableList<IntArray> = ArrayList()
    val randomElement: MutableList<Int> = ArrayList()
    for (i in 0 until n) {
        var tempArray: IntArray
        var m = 1 + random.nextInt(maxSizeArray)
        if (maxSizeArray >= n) {
            while (randomElement.contains(m)) m = 1 + random.nextInt(maxSizeArray)
            randomElement.add(m)
        }
        tempArray = IntArray(m)
        for (j in 0 until m) tempArray[j] = random.nextInt(maxElement)
        listArray.add(tempArray)
    }
    println("Массивы до сортировки:")
    show(listArray)
    val counter = AtomicInteger()
    listArray.forEach(Consumer<IntArray> { item: IntArray ->
        quickSort(counter.getAndIncrement() % 2 == 0, item, 0, item.size - 1)
        println()
    })
    println("\nОтсортированные массивы:")
    show(listArray)
}