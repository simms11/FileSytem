package com.simms.scala.oop.commands

import com.simms.scala.oop.filesystem.State

trait Command extends (State  => State)

object Command {

  val LS = "ls"
  val MKDIR = "mkdir"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CHANGEDIRECTORY = "cd"
  val REMOVE = "rm"
  val ECHO = "echo"
  val CAT = "cat"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(name + ": incomplete command")
  }

  def from(input: String): Command ={
    val tokens: Array[String] = input.split(" ")

    if(input.isEmpty || tokens.isEmpty) emptyCommand
    else tokens(0) match {
      case MKDIR =>
        if(tokens.length < 2)  incompleteCommand(MKDIR)
        else new Mkdir(tokens(1))
      case LS => new Ls
      case PWD => new Pwd
      case TOUCH =>
        if(tokens.length < 2)  incompleteCommand(TOUCH)
        else new Touch(tokens(1))
      case CHANGEDIRECTORY =>
        if(tokens.length < 2)  incompleteCommand(CHANGEDIRECTORY)
        new ChangeDirectory(tokens(1))
      case REMOVE =>
        if(tokens.length < 2) incompleteCommand(REMOVE)
        else new Remove(tokens(1))
      case ECHO =>
        if(tokens.length < 2) incompleteCommand(ECHO)
        else new Echo(tokens.tail)
      case CAT =>
        if(tokens.length < 2) incompleteCommand(CAT)
        else new Cat(tokens(1))
      case _ =>new UnknownCommand
    }
  }

}