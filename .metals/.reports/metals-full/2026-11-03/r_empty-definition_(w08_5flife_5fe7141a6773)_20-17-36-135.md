error id: file://<WORKSPACE>/Main.scala:`<none>`.
file://<WORKSPACE>/Main.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -rule.
	 -rule#
	 -rule().
	 -scala/Predef.rule.
	 -scala/Predef.rule#
	 -scala/Predef.rule().
offset: 417
uri: file://<WORKSPACE>/Main.scala
text:
```scala
package life

import life.Life.highLifeRule

object Main:
  val help = """
    Welcome to GAME OF LIFE!

    Click on cell to toggle.
    Press ENTER for next generation.
    Press SPACE to toggle play/stop.
    Press R to create random life.
    Press BACKSPACE to clear life.
    Close window to exit.
  """

  val dim = (30, 40)

  def main(args: Array[String]): Unit =
    if args.mkString == "highlife" then rule@@ = highLifeRule
    println(help)
    val w = new LifeWindow(dim._1, dim._2)
    w.start()

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.