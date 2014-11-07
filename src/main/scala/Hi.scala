
object Hi {
  def main(args: Array[String]) = {
    val now = new org.joda.time.DateTime()
    println("Hi! "+now.toString("hh:mm aa"))
  }
}