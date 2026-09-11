# Dual Terminal Windows

Dual Terminal Windows adds independently movable terminal tool windows to
JetBrains IDEs. Keep the built-in terminal and add between one and four extra
shells, then arrange them however your workflow needs.

## Features

- One additional terminal by default, configurable up to four.
- Live count changes under **Settings | Tools | Dual Terminal Windows**.
- Extra sessions named **Extra Terminal 1**, **Extra Terminal 2**, and so on.
- Independent docking, resizing, floating, and hiding.
- Layout persistence through the IDE's standard tool-window system.
- Project-root working directory.
- Uses the shell configured under **Settings | Tools | Terminal**.
- No telemetry, network access, or storage of terminal contents.

## Compatibility

The current beta supports JetBrains IDE builds 253 through 262, including
IntelliJ IDEA, RustRover, CLion, PyCharm, WebStorm, GoLand, DataGrip, RubyMine,
Rider, and DataSpell when the bundled Terminal plugin is available. JetBrains currently
restricts the Reworked Terminal UI to the built-in Terminal tool window, so
these independent windows use the bundled Classic terminal widget. That API is
deprecated and may require changes in a future IDE release.

The Classic terminal inherits console colors from the active editor color
scheme. If the terminal and editor unexpectedly use a light background, select
a complete dark scheme under **Settings | Editor | Color Scheme**.

## Installation

### JetBrains Marketplace

Marketplace installation will be available after the first release is approved.

### Install from disk

1. Download the plugin ZIP from the repository's Releases page.
2. Open **Settings | Plugins** in your JetBrains IDE.
3. Select the gear menu and **Install Plugin from Disk**.
4. Choose the ZIP and restart the IDE when prompted.

Do not extract the ZIP before installing it.

## Building

The portable build uses the Gradle wrapper and downloads the declared
IntelliJ Platform SDK:

```shell
./gradlew buildPlugin
```

The plugin ZIP is written under `build/distributions/`.

For a quick build against an installed JetBrains IDE:

```shell
./build-local.sh
```

Override the detected installation if necessary:

```shell
JETBRAINS_IDE_HOME=/path/to/JetBrains-IDE ./build-local.sh
```

To use an installed IDE with Gradle and avoid downloading another copy:

```shell
./gradlew -PlocalIdePath=/path/to/JetBrains-IDE buildPlugin
```

## Verification

```shell
./gradlew clean buildPlugin verifyPluginStructure verifyPluginProjectConfiguration
```

Compatibility verification can be run with `./gradlew verifyPlugin`.

## Publishing

See [PUBLISHING.md](PUBLISHING.md) for the first Marketplace release and later
automated releases.

## License

[MIT](LICENSE)
