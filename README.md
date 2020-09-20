# :tada: Showcase
[![Actions Status](https://img.shields.io/github/workflow/status/Canato/showcase/MainCI)](https://https://github.com/Canato/showcase/actions)

This is [Canato](https://www.linkedin.com/in/vcanato/) personal project for portfolio.

If you want to check the project where I mentoring other developer you can see [Purity Test](https://github.com/Canato/Purity-Test) - WIP

Here you can find a compilation of some best practices of Android Development across my work experience with big projects.

# :round_pushpin: Index 

- [Architecture](#project-architecture)
- [CI](#continuous-integration)
- [Single Activity - Jetpack Navigation](#single-activity) 
- [Material Motion](#material-motion)
- [Gradle.kts](#gradle.kts) 
- [KtLint](#ktlint) 
- [Git Hook](#git-hooks)
- [License](#license)

## :triangular_ruler: Project Architecture

The project is modularised per feature and use the MVP pattern.
You can find more in the talk that I give about it:
- [Video](https://www.youtube.com/watch?v=pxBNyLZiIVI&ab_channel=CanatoVictor)
- [Medium Article](https://medium.com/@vcanato/depop-mvp-architecture-in-android-8a7b2eed5dd2)

The long goal is to use Kotlin as MultiPlatform as extract the Business Logic into submodule that can be used from Android, iOS or even Web. 

## :arrows_counterclockwise: Continuous Integration

The CI is simple and effective.
- Run on Github Actions.
- KtLint, Tests and Assemble
- Can be checked on `.github/workflows/main-workflow.yml`

## :one: Single Activity

The App consist in a [single activity](https://www.youtube.com/watch?v=2k8x8V77CrU&ab_channel=AndroidDevelopers) under the `home_list` domain.
We use the [Jetpack Navigation Component](https://developer.android.com/guide/navigation) using the navigation tool to flow between the fragments.
By now we do not use `<include-dynamic>` for navigation with feature modules because it does not support deep links, so instead we add implement the module in the gradle.kts 

## ▶️ Material Motion

Use the [material motion](https://material.io/develop/android/theming/motion) to make nice animations between fragments. 
Since we are using one single activity with Jetpack Navigation, this animation need a little of work on it, so it can be use from any other place

## :elephant: Gradle.kts

Decide to use [Gradle Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html) because of:

- More Kotlin friendly code
- Avoid Magic Strings*
- Centralized dependencies* 
- Better dependency management (have an issue with library versions - WIP) 

*Both could be done in Groove too, but in kotlin is easier with auto-complete and look better.

## 🔍 KtLint

Convenient task for gradle project that run [ktlint](https://github.com/pinterest/ktlint) checks or do code auto format.
Used to ensure code style, the CI has a `./gradlew ktlintCheck` command and break if the code is not on the right format

- [Github plugin project](https://github.com/JLLeitschuh/ktlint-gradle)

## 🎣 Git Hooks

- [Full Description](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks)

Git hooks automatically run scripts when actions occurs on a git repository.
You can find the project hooks under `Showcase/hooks/`

To use project hooks and not git hooks on the project please run `git config core.hooksPath hooks` on your project folder

### Project Hooks
- **pre-push:** Run [ktlintFormat](#ktlint) command, if fails does not allow to push. If success commit if need and push.

## 📝 License

Usage of google [Including Open Source Notices](https://developers.google.com/android/guides/opensource)