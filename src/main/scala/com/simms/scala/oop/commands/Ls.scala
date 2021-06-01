package com.simms.scala.oop.commands

import com.simms.scala.oop.files.DirEntry
import com.simms.scala.oop.filesystem.State

class Ls extends Command {

  override def apply(state: State): State = {
    val contents = state.workingDirectory.contents
    val niceOuput = createNiceOutput(contents)
    state.setMessage(niceOuput)
  }

  def createNiceOutput(contents: List[DirEntry]):String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "] \n" + createNiceOutput(contents.tail)
    }
  }
}
