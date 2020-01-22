package slinky.example.todo.component

import slinky.example.todo.model._
import slinky.example.App
import slinky.example.hooks.Diode.useDiode

import slinky.core.{FunctionalComponent, SyntheticEvent}
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._

import org.scalajs.dom.Event
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.dom.console

@react object TodoList {

  type Props = Unit

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] { _ =>
    val (todos, dispatch) = useDiode(App.diodeContext, AppCircuit.zoomTo(_.todos))

    val filteredTodoList = todos.todoList filter todos.filter.accepts
    val activeCount = todos.todoList count TodoFilter.Active.accepts
    val completedCount = todos.todoList.length - activeCount

    def todoList(): ReactElement =
      section(className := "main")(
        input(
          `type` := "checkbox",
          className := "toggle-all",
          onChange := { e =>
            dispatch(ToggleAll(e.target.asInstanceOf[HTMLInputElement].checked))
          }
        ),
        ul(className := "todo-list")(
          filteredTodoList.map { todo =>
            val editing = todos.editingTodo.contains(todo.id)
            div(key := todo.id.toString)(TodoView(todo, editing))
          }
        )
      )

    def footer(activeCount: Int, completedCount: Int) =
      Footer(
        todos.filter,
        activeCount,
        completedCount
      )

    def handleNewTodoKeyDown: SyntheticEvent[input.tag.RefType, Event] => Unit = e => {
      val title = e.target.value.trim
      if (e.asInstanceOf[KeyboardEvent].keyCode == KeyCode.Enter && title.nonEmpty) {
        console.log("Adding")
        dispatch(AddTodo(title))
      }
    }

    div(
      h1("todos"),
      header(className := "header")(
        input(
          className := "new-todo",
          placeholder := "What needs to be done?",
          onKeyDown := handleNewTodoKeyDown
        )
      ),
      todoList(),
      if (todos.todoList.nonEmpty) Some(footer(activeCount, completedCount)) else None
    )

  }

}
