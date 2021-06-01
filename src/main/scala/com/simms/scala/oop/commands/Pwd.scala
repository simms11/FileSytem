package com.simms.scala.oop.commands

import com.simms.scala.oop.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.workingDirectory.path)
}
