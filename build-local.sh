#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
ide_dir="${JETBRAINS_IDE_HOME:-${RUSTROVER_HOME:-/home/sondre/.local/share/JetBrains/RustRover-2026.2.1}}"
build_dir="$project_dir/build/local"
classes_dir="$build_dir/classes"
plugin_dir="$build_dir/plugin/DualTerminalToolWindows"
plugin_version="$(sed -n 's/^pluginVersion=//p' "$project_dir/gradle.properties")"

if [[ ! -f "$ide_dir/build.txt" || ! -d "$ide_dir/plugins/terminal/lib" ]]; then
    printf 'A JetBrains IDE with the bundled Terminal plugin was not found at %s\n' "$ide_dir" >&2
    printf 'Set JETBRAINS_IDE_HOME to its installation directory.\n' >&2
    exit 1
fi

rm -rf -- "$build_dir"
mkdir -p -- "$classes_dir" "$plugin_dir/lib"

classpath="$(find "$ide_dir/lib" "$ide_dir/plugins/terminal/lib" -type f -name '*.jar' -print | paste -sd: -)"
mapfile -t sources < <(find "$project_dir/src/main/java" -type f -name '*.java' -print)

"$ide_dir/jbr/bin/javac" --release 21 -classpath "$classpath" -d "$classes_dir" "${sources[@]}"
cp -R -- "$project_dir/src/main/resources/." "$classes_dir/"
jar --create --file "$plugin_dir/lib/dual-terminal-tool-windows.jar" -C "$classes_dir" .

mkdir -p -- "$project_dir/build/distributions"
(
    cd -- "$build_dir/plugin"
    zip -qr "$project_dir/build/distributions/dual-terminal-windows-$plugin_version.zip" DualTerminalToolWindows
)

printf 'Built %s\n' "$project_dir/build/distributions/dual-terminal-windows-$plugin_version.zip"
