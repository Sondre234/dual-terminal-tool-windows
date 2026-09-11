# Dual Terminal Windows

Dual Terminal Windows adds two independent terminal tool windows to RustRover.
Put one shell on the right and another on the bottom, float them, or arrange
them however your workflow needs.

## Features

- Two simultaneous shells: **Terminal A** and **Terminal B**.
- Independent docking, resizing, floating, and hiding.
- Layout persistence through the IDE's standard tool-window system.
- Project-root working directory.
- Uses the shell configured under **Settings | Tools | Terminal**.
- No telemetry, network access, or storage of terminal contents.

## Compatibility

The current beta supports RustRover 2026.2 (`RR-262`). JetBrains currently
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
2. Open **Settings | Plugins** in RustRover.
3. Select the gear menu and **Install Plugin from Disk**.
4. Choose the ZIP and restart RustRover when prompted.

Do not extract the ZIP before installing it.

## Building

The portable build uses the Gradle wrapper and downloads the declared
RustRover SDK:

```shell
./gradlew buildPlugin
```

The plugin ZIP is written under `build/distributions/`.

For a quick build against an installed RustRover copy:

```shell
./build-local.sh
```

Override the detected installation if necessary:

```shell
RUSTROVER_HOME=/path/to/RustRover ./build-local.sh
```

To use an installed IDE with Gradle and avoid downloading another copy:

```shell
./gradlew -PlocalIdePath=/path/to/RustRover buildPlugin
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
