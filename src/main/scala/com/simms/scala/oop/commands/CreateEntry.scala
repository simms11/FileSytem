package com.simms.scala.oop.commands

import com.simms.scala.oop.files.{DirEntry, Directory}
import com.simms.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {
    val workingDirectory = state.workingDirectory
    if(workingDirectory.hasEntry(name)){
      state.setMessage("Entry " + name + "already exists!")
    } else if(name.contains(Directory.SEPERATOR)){
      state.setMessage(name + "must not contain seperators!")
    } else if (checkIllegal(name)){
      state.setMessage(name + ": illegal entry name!")
    }else{
      doCreateEntry(state, name)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }

  def doCreateEntry(state: State, name: String): State = {

    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if(path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val workingDirectory = state.workingDirectory

    //all the directories in the full path

    val addDirsInPath = workingDirectory.getAllFoldersInPath

    //create a new directory in the working directory
    //TODO implement this
    val newEntry: DirEntry = createSpecificEntry(state)

    //update the whole directory structure from the root
    val newRoot = updateStructure(state.root, addDirsInPath, newEntry)

    //find new working directory given the working directory's path, in the new directory structure
    val newWorkingDir = newRoot.findDescendant(addDirsInPath)

    State(newRoot, newWorkingDir)
  }

    def createSpecificEntry(state: State): DirEntry

}
