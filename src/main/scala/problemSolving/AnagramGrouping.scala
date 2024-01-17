package problemSolving

object AnagramGrouping {

  def groupAnagrams(strs: Array[String]): List[List[String]] = {
    strs.groupBy(word => word.sorted).values.map(_.toList).toList
  }

  def main(args: Array[String]): Unit = {
    val strs1 = Array("eat", "tea", "tan", "ate", "nat", "bat")
    val result1 = groupAnagrams(strs1)
    //println(result1)

    val strs2 = Array("")
    val result2 = groupAnagrams(strs2)
  //  println(result2)

    val strs3 = Array("a")
    val result3 = groupAnagrams(strs3)
   // println(result3)
  }
}
