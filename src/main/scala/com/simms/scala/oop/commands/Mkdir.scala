package com.simms.scala.oop.commands

import com.simms.scala.oop.files.{DirEntry, Directory}
import com.simms.scala.oop.filesystem.State

class Mkdir(name:String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.workingDirectory.path, name)

}
