package slinky.example.todo.component

import slinky.example.todo.model.{AppCircuit, Delete, EditingDone, StartEditingTodo, Todo, ToggleCompleted, Update}
import slinky.example.App
import slinky.example.hooks.Diode.useDiode

import slinky.core.facade.Hooks.useState
import slinky.core.{FunctionalComponent, SyntheticEvent}
import slinky.core.annotations.react
import slinky.web.html._

import org.scalajs.dom.Event
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.raw.HTMLInputElement

@react object TodoView {

  case class Props(todo: Todo, editing: Boolean)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    val (editText, updateEditText) = useState(props.todo.title)
    val dispatch = useDiode(App.diodeContext)

    def editFieldSubmit(): Unit = {
      if (editText.trim == "")
        dispatch(Delete(props.todo.id))
      else
        dispatch(Update(props.todo.id, editText.trim))
      dispatch(EditingDone(props.todo.id))
    }

    def editFieldKeyDown: SyntheticEvent[input.tag.RefType, Event] => Unit = e => {
      e.asInstanceOf[KeyboardEvent].keyCode match {
        case KeyCode.Escape =>
          updateEditText(props.todo.title)
          dispatch(EditingDone(props.todo.id))
        case KeyCode.Enter => editFieldSubmit()
        case _             =>
      }
    }

    li(
      key := props.todo.id.toString,
      className := s"${if (props.editing) "editing" else ""} ${if (props.todo.isCompleted) "completed" else ""}"
    )(
      div(className := "view")(
        input(
          `type` := "checkbox",
          className := "toggle",
          checked := props.todo.isCompleted,
          onChange := { _ =>
            dispatch(ToggleCompleted(props.todo.id))
          }
        ),
        label(onDoubleClick := { _ =>
          dispatch(StartEditingTodo(props.todo.id))
        })(
          props.todo.title
        ),
        button(
          className := "destroy",
          onClick := { _ =>
            dispatch(Delete(props.todo.id))
          }
        )
      ),
      input(
        className := "edit",
        onBlur := { _ =>
          editFieldSubmit()
        },
        onChange := { e =>
          updateEditText(e.target.asInstanceOf[HTMLInputElement].value)
        },
        onKeyDown := editFieldKeyDown,
        value := editText
      )
    )
  }

}
