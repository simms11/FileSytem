package com.simms.scala.oop.commands

import com.simms.scala.oop.files.{DirEntry, File}
import com.simms.scala.oop.filesystem.State

class Touch(name: String) extends CreateEntry(name){

  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.workingDirectory.path, name)
}
