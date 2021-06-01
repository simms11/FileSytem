package com.simms.scala.oop.filesystem

import com.simms.scala.oop.commands.Command
import com.simms.scala.oop.files.Directory

object FileSystem extends App {

  val root = Directory.ROOT

  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState, newLine) => {
    currentState.show
    val newState = Command.from(newLine).apply(currentState)
    newState
  })
}
