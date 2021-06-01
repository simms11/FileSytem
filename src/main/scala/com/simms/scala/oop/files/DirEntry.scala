package com.simms.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = {
    val seperatorIfNecessary =
      if(Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPERATOR

    parentPath + seperatorIfNecessary + name
  }

  def asDirectory: Directory

  def asFile: File

  def getType: String

  def isDirectory: Boolean

  def isFile: Boolean
}
