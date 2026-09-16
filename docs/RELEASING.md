# Releasing StateLayout

## Preparation

1. Ensure `CHANGELOG.md` describes the release and no incompatible change is hidden in a minor version.
2. Change the project version in `statelayout/build.gradle` from a snapshot to the final version.
3. Run:

   ```bash
   ./gradlew clean testDebugUnitTest lintDebug assembleRelease \
     publishReleasePublicationToMavenLocal
   ```

4. Inspect the local repository under `~/.m2/repository/io/github/wangyuyan666/statelayout/` and test the artifact from a separate consumer project.
5. Verify the AAR, sources JAR, documentation JAR, Gradle metadata, and POM.

## Maven Central prerequisites

Before the first central release, the maintainer must verify ownership of the `io.github.wangyuyan666` namespace, configure Central Portal credentials and in-memory signing keys in CI, and add a secret-backed publishing workflow. Credentials must never be committed.

The repository intentionally does not contain an unverified Central Portal endpoint or placeholder secrets. Add the publishing endpoint only after the namespace is approved, following the then-current official Central Portal documentation.

## Release

After the Central staging deployment succeeds:

1. Resolve the final dependency from a clean environment.
2. Tag the verified commit as `v<version>`.
3. Create a GitHub Release using the matching changelog section.
4. Update README installation instructions only after the public artifact resolves.
5. Restore the next development version with a `-SNAPSHOT` suffix.

## Rollback

Maven releases are immutable. If validation fails before publication, drop the deployment. If a bad version is already public, document the problem, publish a corrected patch version, and mark the affected GitHub Release as problematic; never rewrite a published tag.
