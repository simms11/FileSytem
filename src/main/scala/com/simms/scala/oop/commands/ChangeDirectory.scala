package com.simms.scala.oop.commands

import com.simms.scala.oop.files.{DirEntry, Directory}
import com.simms.scala.oop.filesystem.State

import scala.annotation.tailrec

class ChangeDirectory(dir:String) extends Command {

  override def apply(state: State): State = {

    //find the root
    val root = state.root
    val workingDirectory = state.workingDirectory

    //finding absolute path of the directory to want to cd into
    val absolutePath =
      if(dir.startsWith(Directory.SEPERATOR)) dir
      else if(workingDirectory.isRoot) workingDirectory.path + dir
      else workingDirectory.path + Directory.SEPERATOR + dir


    //find the directory to cd to given the current path
    val destinationDirectory = doFindEntry(root, absolutePath)

    //changing the state given the new directory

    if(destinationDirectory == null || !destinationDirectory.isDirectory)
    state.setMessage(dir + ": no such directory")
    else State(root, destinationDirectory.asDirectory)


  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry =
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }

    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if(path.isEmpty) result
      else if(".".equals(path.head))collapseRelativeTokens(path.tail, result)
      else if("..".equals(path.head)){
        if(result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      }else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    //tokens
    val tokens:List[String] = path.substring(1).split(Directory.SEPERATOR).toList

    //eliminate/collapse tokens

    val newTokens = collapseRelativeTokens(tokens, List())

    //navigate to the correct entry
    if(newTokens == null) null
    else findEntryHelper(root, newTokens)
  }


}
