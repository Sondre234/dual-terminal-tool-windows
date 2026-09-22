# Changelog

All notable changes to this project will be documented here.

## [Unreleased]

### Fixed

- Replaced deprecated terminal creation and tool-window removal APIs.
- Used the public tool-window registration builder to avoid override-only API usage.
- Created terminal sessions directly under their extra window's lifetime.

## [1.0.2] - 2026-09-11

### Changed

- Made the local build script portable across JetBrains IDE installations.
- Added automatic IDE detection and clearer prerequisite validation.

## [1.0.1] - 2026-09-11

### Added

- One extra terminal window by default, configurable up to four.
- Live addition and removal of extra terminal windows from the IDE settings.
- Independent docking, resizing, floating, and persisted layout.
- Project-root working directory and the shell configured in the IDE.
- Compatibility with JetBrains IDE builds 253 through 262.

[Unreleased]: https://github.com/Sondre234/dual-terminal-tool-windows/compare/v1.0.2...HEAD
[1.0.2]: https://github.com/Sondre234/dual-terminal-tool-windows/releases/tag/v1.0.2
[1.0.1]: https://github.com/Sondre234/dual-terminal-tool-windows/releases/tag/v1.0.1
