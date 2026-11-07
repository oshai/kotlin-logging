---
name: release-kotlin-logging
description: Executes a kotlin-logging release across git branches, Sonatype Maven Central CD, GitHub Releases, README snippets, and next-version preparation following RELEASING.md. Use when asked to release, cut, or publish a new version of kotlin-logging.
---

# Release `kotlin-logging`

Follow the canonical release runbook in [RELEASING.md](../../../RELEASING.md) step by step.

## Agent Execution Rules & Approval Gates

1. **Read [RELEASING.md](../../../RELEASING.md) first** to confirm exact branch names, file locations, and command flags.
2. **Execute local preparation autonomously (Steps 1–3)**:
   - Fetch `origin --prune --tags`, fast-forward local `master` to `origin/master`, verify recent `CI` workflow runs on `master` passed, confirm `version = "X.Y.Z"` in `build.gradle.kts`, and rebase local `github-release-8.x` onto `master`.
   - Preview release notes via `gh api --method POST repos/oshai/kotlin-logging/releases/generate-notes` (`target_commitish=github-release-8.x`) and prepare a concise curated draft (preserving feature/fix/doc PRs and `New Contributors`, condensing noisy `@dependabot` CI/test bumps).
3. **Pause for explicit user approval before irreversible remote actions** (unless the user already approved them in the prompt):
   - **Gate 1 — Push `github-release-8.x` (`Step 4`)**: Confirm before running `git push origin github-release-8.x`, since this triggers `.github/workflows/cd.yml` to publish `X.Y.Z` to Sonatype Maven Central.
   - **Gate 2 — Publish GitHub Release (`Step 5`)**: Show the curated release notes for confirmation/edits before running `gh release create "X.Y.Z" --target github-release-8.x --latest ...` (always pass `--target github-release-8.x` so the tag points to the `X.Y.Z` release commit rather than the `N+1` bump commit; use `--latest=false` for `6.x`/`7.x` backports).
4. **Verify CD & finish `master` preparation (`Steps 4 & 6`)**:
   - Watch the `CD` workflow run on `github-release-8.x` (`gh run watch <run_id> --exit-status`) to confirm Maven Central publishing succeeded.
   - Switch back to `master`, update `README.md` Maven/Gradle version snippets to `X.Y.Z`, bump `build.gradle.kts` to `X.Y.(Z+1)`, commit, and push `origin/master` (confirming before `git push` if not already approved).
