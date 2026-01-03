# Contributing to kotlin-logging

We welcome and appreciate all contributions, from bug fixes to new features. To ensure a smooth and effective process for everyone, please take a moment to review these guidelines.

## Guiding Principles

*   **Discuss First:** Before starting any significant work, please open an issue to discuss your proposed changes. This allows us to align on the problem and the proposed solution before you invest your time in writing code. This is especially important for new features, refactorings, or complex changes.
*   **Small, Incremental Changes:** We strongly prefer small, focused pull requests (PRs) that address a single issue or add a single, well-defined piece of functionality.
*   **Embrace Iteration:** Large features or refactorings should be broken down into a series of smaller, logical PRs that can be reviewed and merged independently.

### Why Small Pull Requests?

*   **Easier to Review:** A small, focused PR is much faster and easier for maintainers to review thoroughly.
*   **Lower Risk:** Smaller changes are less likely to introduce unintended side effects and are easier to revert if necessary.
*   **Faster Merging:** It is much easier to get a small change approved and merged, delivering value to users more quickly.

## Submitting a Pull Request

1.  **Fork the repository** and create your branch from `master`.
2.  **Open an issue** to discuss your proposed changes (if one doesn't already exist).
3.  **Make your changes** in a new git branch.
4.  **Write Tests:** Ensure your changes are covered by new or existing tests.
5.  **Ensure the test suite passes:** Run `./gradlew clean build` locally.
6.  **Check and apply formatting:** Run `./gradlew spotlessApply`.
7.  **Commit your changes** and push to your fork.
8.  **Open a PR** to the `master` branch. In the PR description, please link to the issue you are addressing.

Thank you for helping make kotlin-logging better!

# Building locally

`./gradlew clean build`

To check formatting:

`./gradlew spotlessCheck`

To fix formatting:

`./gradlew spotlessApply`

## Troubleshooting Build

If you encounter issues resolving dependencies (e.g., from `artifactory.apigee.net` or other internal repositories), you can force the use of Maven Central by using the provided init script:

`./gradlew clean build -I fix_repo.gradle`

This script (`fix_repo.gradle`) overrides repository configurations to ensure artifacts are resolved from Maven Central.
