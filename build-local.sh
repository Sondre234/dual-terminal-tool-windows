#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
build_dir="$project_dir/build/local"
classes_dir="$build_dir/classes"
plugin_dir="$build_dir/plugin/DualTerminalToolWindows"
plugin_version="$(sed -n 's/^pluginVersion=//p' "$project_dir/gradle.properties")"

is_compatible_ide() {
    local candidate="$1"
    [[ -f "$candidate/build.txt" \
        && -x "$candidate/jbr/bin/javac" \
        && -f "$candidate/plugins/terminal/lib/terminal.jar" ]]
}

detect_ide() {
    local user_home="${HOME:?HOME is not set}"
    local -a search_roots=(
        "$user_home/.local/share/JetBrains"
        "$user_home/Library/Application Support/JetBrains/Toolbox/apps"
        "/Applications"
        "/opt"
    )
    local -a candidates=()
    local root build_file candidate build_number

    for root in "${search_roots[@]}"; do
        [[ -d "$root" ]] || continue
        while IFS= read -r build_file; do
            candidate="${build_file%/build.txt}"
            if is_compatible_ide "$candidate"; then
                build_number="$(tr -d '\r\n' < "$build_file")"
                candidates+=("$build_number|$candidate")
            fi
        done < <(find "$root" -maxdepth 6 -type f -name build.txt -print 2>/dev/null)
    done

    ((${#candidates[@]} > 0)) || return 1
    printf '%s\n' "${candidates[@]}" | sort -t '|' -k1,1V | tail -n 1 | cut -d '|' -f 2-
}

if [[ -n "${JETBRAINS_IDE_HOME:-}" ]]; then
    ide_dir="$JETBRAINS_IDE_HOME"
elif ! ide_dir="$(detect_ide)"; then
    printf 'Could not find a compatible JetBrains IDE installation.\n' >&2
    printf 'Set JETBRAINS_IDE_HOME to the IDE installation directory.\n' >&2
    exit 1
fi

if ! is_compatible_ide "$ide_dir"; then
    printf 'The directory is not a compatible JetBrains IDE installation: %s\n' "$ide_dir" >&2
    printf 'It must include a bundled JDK and the Terminal plugin.\n' >&2
    exit 1
fi

printf 'Using JetBrains IDE at %s (%s)\n' "$ide_dir" "$(tr -d '\r\n' < "$ide_dir/build.txt")"

for tool in jar zip; do
    if ! command -v "$tool" >/dev/null 2>&1; then
        printf 'Required build tool is not on PATH: %s\n' "$tool" >&2
        exit 1
    fi
done

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
