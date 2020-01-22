package slinky.example.todo.component

import slinky.example.App
import slinky.example.hooks.Diode.useDiode
import slinky.example.todo.model.{AppCircuit, ClearCompleted, TodoFilter}

import slinky.core.{FunctionalComponent, StatelessComponent}
import slinky.core.annotations.react
import slinky.web.html._

@react object Footer {

  case class Props(
      currentFilter: TodoFilter,
      activeCount: Int,
      completedCount: Int
  )

  val component = FunctionalComponent[Props] { props =>
    val dispatch = useDiode(App.diodeContext)

    footer(
      className := "footer"
    )(
      span(className := "todo-count")(
        strong(props.activeCount.toString),
        s" ${if (props.activeCount == 1) "item" else "items"} left"
      ),
      ul(className := "filters")(
        TodoFilter.values.map(f =>
          li(key := f.title)(
            a(className := s"${if (f == props.currentFilter) "selected" else ""}", href := s"#${f.title}")(
              f.title
            )
          )
        )
      ),
      if (props.completedCount > 0)
        button(className := "clear-completed", onClick := { _ =>
          dispatch(ClearCompleted)
        })(
          "Clear completed"
        )
      else None
    )
  }
}
