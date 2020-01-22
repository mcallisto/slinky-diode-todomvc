package slinky.example

import diode.Circuit

import slinky.example.todo.model.{AppCircuit, AppModel}
import slinky.example.todo.component.TodoList

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.{React, ReactContext}
import slinky.web.html.{className, footer, id, p, section}

@react object App {

  type Props = Unit

  val diodeContext: ReactContext[Circuit[AppModel]] = React.createContext[Circuit[AppModel]](AppCircuit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    diodeContext.Provider(AppCircuit)(
      section(id := "todoapp", className := "todoapp")(
        TodoList()
      ),
      footer(className := "info")(
        p("Double-click to edit a todo")
      )
    )
  }
}
