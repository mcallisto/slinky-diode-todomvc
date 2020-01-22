# Slinky with Diode TodoMVC example
Refactoring of [TodoMVC](https://github.com/tastejs/todomvc) coded in [Scala](https://scala-lang.org/) through [Scala.js](https://www.scala-js.org), [Slinky](https://slinky.dev) and [Diode](https://github.com/suzaku-io/diode).

## Demo
See it hosted [here](https://mcallisto.github.io/slinky-demos/diode-todomvc/).

## Requirements
Make sure you have [sbt](https://www.scala-sbt.org) and [npm](https://www.npmjs.com/) installed.

## How to
### Run in development

Compile and start a web server with

```sh
$ sbt dev
```

then open http://localhost:8080 to see example running.

### Deploy to production

Create a minified bundle with 

```sh
$ sbt build
```

and find it in the `build` folder.

### Test

Launch all tests with 

```sh
$ sbt test
```

## Acknowledgements
`slinky-diode-todomvc` is essentially just the merging (and updating) of two already existing repos, see:

* [nicopolyptic](https://github.com/nicopolyptic)'s [todoMVC-slinky](https://github.com/nicopolyptic/todoMVC-slinky)
* [ryneflood](https://github.com/ryneflood)'s [slinky-diode-example](https://github.com/ryneflood/slinky-diode-example)
