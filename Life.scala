package life

case class Life(cells: Matrix[Boolean]):
  /** Ger true om cellen på plats (row, col) är vid liv annars false.
    * Ger false om indexeringen är utanför universums gränser.
    */
  def apply(row: Int, col: Int): Boolean = 
    row >= 0 && row < cells.dim._1 && 
    col >= 0 && col < cells.dim._2 && 
    cells(row, col) 

  /** Sätter status på cellen på plats (row, col) till value. */
  def updated(row: Int, col: Int, value: Boolean): Life = 
    Life(cells.updated(row, col)(value))

  /** Växlar status på cellen på plats (row, col). */
  def toggled(row: Int, col: Int): Life = 
    val reverse = !cells(row, col)
    Life(cells.updated(row, col)(reverse))

  /** Räknar antalet levande grannar till cellen i (row, col). */
  def nbrOfNeighbours(row: Int, col: Int): Int = 
    var count = 0
    for 
      r <- -1 to 1
      c <- -1 to 1 
      if (r, c) != (0, 0)
    do 
      if apply(row + r, col + c) then count += 1 
    count 

  /** Skapar en ny Life-instans med nästa generation av universum.
    * Detta sker genom att applicera funktionen rule på cellerna.
    */
  def evolved(rule: (Int, Int, Life) => Boolean = Life.defaultRule):Life =
    var nextGeneration = Life.empty(cells.dim)
    cells.foreachIndex( (r,c) =>
      if rule(r, c, this) then nextGeneration = nextGeneration.updated(r,c, true)
    )
    nextGeneration

  /** Radseparerad text där 0 är levande cell och - är död cell. */
  override def toString = 
   s"Current Life:\n${cells.data.map(_.map(if _ then 0 else '-').mkString(" ")).mkString("\n")}"

object Life:
  /** Skapar ett universum med döda celler. */
  def empty(dim: (Int, Int)): Life = 
    Life(Matrix.fill(dim)(false))

  /** Skapar ett universum med slumpmässigt liv. */
  def random(dim: (Int, Int)): Life = 
    var fönster = Matrix.fill(dim)(false)
    fönster.foreachIndex((x, y) => 
      if scala.util.Random.nextInt(2) == 1 then fönster = fönster.updated(x, y)(true))
    Life(fönster)

  /** Implementerar reglerna enligt Conways Game of Life. */
  def defaultRule(row: Int, col: Int, current: Life): Boolean = 
    val grannar = current.nbrOfNeighbours(row, col)
    (current(row, col) && grannar == 2) || grannar == 3

  def highLifeRule(row: Int, col: Int, current: Life): Boolean = 
    val grannar = current.nbrOfNeighbours(row, col)
    (current(row, col) && grannar == 2) || grannar == 3 || (!current(row, col) && grannar == 6)
  