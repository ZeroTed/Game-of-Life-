package life

import introprog.PixelWindow
import introprog.PixelWindow.Event

object LifeWindow:
  val EventMaxWait = 1 // milliseconds
  var NextGenerationDelay = 200 // milliseconds
  val levande = java.awt.Color(242, 128, 161)
  val svart = java.awt.Color.black
  val cellstorlek = 20
  val gray = java.awt.Color.gray
  var rule = Life.defaultRule
  // lägg till fler användbara konstanter här tex färger etc.

class LifeWindow(rows: Int, cols: Int):
  import LifeWindow.* // importera namn från kompanjon

  var life = Life.empty(rows, cols)
  val window: PixelWindow = PixelWindow(cols * cellstorlek + 1, rows * cellstorlek + 1, "Game of Life", svart)
  var quit = false
  var play = false

  def drawGrid(): Unit = 
    for i <- 0 to rows do 
      window.line(0, i * cellstorlek, cols * cellstorlek, i * cellstorlek, gray, 1)
    for i <- 0 to cols do 
      window.line(i * cellstorlek, 0, i * cellstorlek, rows * cellstorlek, gray, 1)

  def drawCell(row: Int, col: Int): Unit = 
    if life(row, col) then 
      window.fill(col * cellstorlek + 1, row * cellstorlek + 1, cellstorlek - 1, cellstorlek - 1, levande)
    else 
      window.fill(col * cellstorlek + 1, row * cellstorlek + 1, cellstorlek - 1, cellstorlek - 1, svart)


  def update(newLife: Life): Unit =
    val oldLife = life
    life = newLife
    life.cells.foreachIndex{(r, c) =>
        if oldLife(r, c) != life(r, c) then drawCell(r, c) 
       }

  def handleKey(key: String): Unit = 
    def p() = println(key)
    key match
    case "Enter" => play = false; update(life.evolved(rule)); p()
    case " " => play = !play; p()
    case "r" => update(Life.random((rows, cols))); p()
    case "Backspace" => update(Life.empty((rows, cols))); p()
    case _ => ()

  def handleClick(pos: (Int, Int)): Unit = 
    val cell = (((pos._1)/cellstorlek), ((pos._2)/cellstorlek))
    update(life.toggled(cell._2, cell._1))
    println("Click")

  def loopUntilQuit(): Unit = while !quit do
    val t0 = System.currentTimeMillis
    if play then update(life.evolved(rule))
    window.awaitEvent(EventMaxWait)
    while window.lastEventType != PixelWindow.Event.Undefined do
      window.lastEventType match
        case Event.KeyPressed  =>  handleKey(window.lastKey)
        case Event.MousePressed => handleClick(window.lastMousePos)
        case Event.WindowClosed => quit = true
        case _ =>
      window.awaitEvent(EventMaxWait)
    val elapsed = System.currentTimeMillis - t0
    Thread.sleep((NextGenerationDelay - elapsed) max 0)

  def start(): Unit = { drawGrid(); loopUntilQuit() }
