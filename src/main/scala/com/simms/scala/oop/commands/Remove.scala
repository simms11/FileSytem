package com.simms.scala.oop.commands

import com.simms.scala.oop.files.Directory
import com.simms.scala.oop.filesystem.State

class Remove(name: String) extends Command {

  override def apply(state: State): State = {
    //get working dir
    val workingDirectory = state.workingDirectory

    //get absolute path
    val absolutePath =
      if(name.startsWith(Directory.SEPERATOR)) name
      else if(workingDirectory.isRoot) workingDirectory.path + name
      else workingDirectory.path + Directory.SEPERATOR + name
    //do some checks
    if(Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("This is not supported!!")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, path: String): State = {

    //find the entry to remove
    //update structure

    def rmHelper(currentDirectory: Directory, path: List[String]):Directory = {
      if(path.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if(!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if(newNextDirectory == nextDirectory) currentDirectory
         else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }
    val tokens = path.substring(1).split(Directory.SEPERATOR).toList
    val newRoot:Directory = rmHelper(state.root, tokens)

    if(newRoot == state.root)
      state.setMessage(path + ": no such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.workingDirectory.path.substring(1)))

  }
}
