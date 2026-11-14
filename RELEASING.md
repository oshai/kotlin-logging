# Releasing `kotlin-logging`

This document describes the step-by-step playbook for cutting a new `kotlin-logging` release (`8.x` mainline or older release branches).

## Prerequisites

- Push access to `https://github.com/oshai/kotlin-logging`.
- Authenticated GitHub CLI (`gh auth status`) with `repo` / `contents:write` and `actions:read` permissions.
- Repository secrets configured on GitHub Actions (`SONATYPE_USERNAME_2`, `SONATYPE_PASSWORD_2`, `GPG_PRIVATE_KEY`, `GPG_PRIVATE_PASSWORD`) used by [`.github/workflows/cd.yml`](.github/workflows/cd.yml).

---

## Standard Release Flow (`8.x` from `master`)

Assume `X.Y.Z` is the version being released (e.g., `8.0.5`), `PREV_TAG` is the previous release tag (e.g., `8.0.4`), and `NEXT_VERSION` is the next patch version (e.g., `8.0.6`).

### 1. Update `master` from Remote

Fetch all remote branches and tags, switch to `master`, fast-forward to `origin/master`, and ensure the working tree is clean:

```bash
git fetch origin --prune --tags
git checkout master
git merge --ff-only origin/master
git status --short
```

Verify recent CI checks on `master` passed:

```bash
gh run list --repo oshai/kotlin-logging --branch master --workflow ci.yml --limit 3
```

### 2. Verify `version` in `build.gradle.kts`

Inspect `version = "..."` in [`build.gradle.kts`](build.gradle.kts):

- If `version` is already `"X.Y.Z"`, proceed to Step 3.
- If `version` does not match `"X.Y.Z"`, update `build.gradle.kts` on `master`, commit (`bump version to X.Y.Z`), and push `origin/master` **before** rebasing the release branch so the release commit itself carries `version = "X.Y.Z"`.

### 3. Rebase `github-release-8.x` onto `master`

Check out `github-release-8.x` tracking `origin/github-release-8.x` and rebase onto `master`:

```bash
git checkout -B github-release-8.x --track origin/github-release-8.x
git rebase master
```

*(In normal mainline releases `origin/github-release-8.x` is an ancestor of `master`, so this is a clean fast-forward.)*

### 4. Push `github-release-8.x` to Remote (Triggers Maven Central CD)

> **Approval / Irreversible Action**: Pushing `github-release-8.x` immediately triggers [`.github/workflows/cd.yml`](.github/workflows/cd.yml) (`./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository`), publishing `X.Y.Z` to Sonatype Maven Central.

```bash
git push origin github-release-8.x
```

Monitor the `CD` workflow run until completion:

```bash
gh run list --repo oshai/kotlin-logging --branch github-release-8.x --workflow cd.yml --limit 1
gh run watch <run_id> --repo oshai/kotlin-logging --exit-status
```

### 5. Generate Changelog & Publish GitHub Release

1. Preview GitHub's auto-generated release notes from `PREV_TAG` to `github-release-8.x`:

   ```bash
   gh api --method POST repos/oshai/kotlin-logging/releases/generate-notes \
     -f tag_name="X.Y.Z" \
     -f target_commitish="github-release-8.x" \
     -f previous_tag_name="PREV_TAG"
   ```

2. Review and optionally curate the notes (e.g., keep feature/fix/doc PRs and `New Contributors`, and optionally condense routine `@dependabot` CI/test bumps into a single summary bullet).
3. Publish the release as `Latest` **targeting `github-release-8.x`** (always target `github-release-8.x` rather than `master` so tag `X.Y.Z` points to the exact release commit where `version = "X.Y.Z"`, not the post-release `N+1` bump commit):

   ```bash
   gh release create "X.Y.Z" \
     --repo oshai/kotlin-logging \
     --target github-release-8.x \
     --title "X.Y.Z" \
     --latest \
     --notes-file <notes_file>
   ```

   Verify it appears at `https://github.com/oshai/kotlin-logging/releases`.

### 6. Post-Release Preparation on `master`

Switch back to `master`, update documentation snippets to the newly released `X.Y.Z`, bump `build.gradle.kts` to `NEXT_VERSION` (`X.Y.(Z+1)`), commit, and push:

1. Switch to `master`:

   ```bash
   git checkout master
   ```

2. Update released version examples to `X.Y.Z` across:
   - [`README.md`](README.md) (Gradle Kotlin DSL, Version Catalog `libs.versions.toml`, Multiplatform `commonMain`, Groovy, and Maven `<version>X.Y.Z</version>` snippets)
   - [`llms.txt`](llms.txt) (`8.x` dependency coordinate snippets)
   - [`docs/openrewrite/rewrite.yml`](docs/openrewrite/rewrite.yml) (`newVersion: X.Y.Z`) and [`docs/openrewrite/README.md`](docs/openrewrite/README.md)
3. Update [`build.gradle.kts`](build.gradle.kts) `version = "NEXT_VERSION"`.
4. Commit and push to `origin/master`:

   ```bash
   git add build.gradle.kts README.md llms.txt docs/openrewrite/rewrite.yml docs/openrewrite/README.md
   git commit -m "chore: bump version to NEXT_VERSION and update docs to X.Y.Z"
   git push origin master
   ```

---

## Backport Releases (`github-release-6.x` / `github-release-7.x`)

For patch releases on older major branches:

1. Open a PR directly targeting `github-release-{6,7}.x` containing the backported fix and setting `version = "N.Y.Z"` in `build.gradle.kts` (`ci.yml` runs on the PR; merging triggers `cd.yml`).
2. Watch `cd.yml` on `github-release-{6,7}.x`.
3. Publish the GitHub Release with `--target github-release-{6,7}.x --latest=false` so the older major does not replace the `8.x` `Latest` badge.
