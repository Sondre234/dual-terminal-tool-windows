# Publishing

## Before the first release

1. Create the public repository `Sondre234/dual-terminal-windows`, or update the
   repository URLs if you choose a different location.
2. Add a screenshot showing both terminal windows in different positions.
3. Run the build and verification commands from the README.
4. Test the resulting ZIP in a clean RustRover 2026.2 installation.
5. Review the beta compatibility note about the Classic terminal API.

## First Marketplace upload

The first version must be uploaded manually:

1. Sign in at <https://plugins.jetbrains.com/>.
2. Create a vendor profile and accept the Marketplace Developer Agreement.
3. Choose **Upload plugin** and upload the ZIP from `build/distributions/`.
4. Select the MIT license and provide the public source repository URL.
5. Use the **beta** release channel for the initial version.
6. Add the description, changelog, icon, and screenshot from this repository.
7. Submit the listing for review.

## Later releases

Create a permanent Marketplace token and provide it as `PUBLISH_TOKEN`. Plugin
signing uses `CERTIFICATE_CHAIN`, `PRIVATE_KEY`, and `PRIVATE_KEY_PASSWORD`.
Never commit any of these values.

After the Marketplace listing exists, publish with:

```shell
PUBLISH_TOKEN=... ./gradlew publishPlugin
```

The Gradle build defaults to the `beta` channel. Change `publishChannel` in
`gradle.properties` when a release is ready for the default stable channel.
