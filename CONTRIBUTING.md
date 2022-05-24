# Contributing to Motravel

## Aturan Penulisan Pesan Commit
Aturan penulisan commit didasarkan pada aturan [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

### Struktur
```
<type>[optional scope]: <subject>

[optional body]

[optional footer(s)]
```


### Type
- build: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
- ci: Changes to our CI configuration files and scripts (example - scopes: Travis, Circle, BrowserStack, SauceLabs)
- docs: Documentation only changes
- feat: A new feature
- fix: A bug fix
- perf: A code change that improves performance
- refactor: A code change that neither fixes a bug nor adds a feature
- style: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
- test: Adding missing tests or correcting existing tests


### Subject
The subject contains a succinct description of the change:
- use the imperative, present tense: "change" not "changed" nor "changes"
- don't capitalize the first letter
- no dot (.) at the end


### Scope
- **ml**
- **android**


### Body
Just as in the subject, use the imperative, present tense: "change" not "changed" nor "changes". The body should include the motivation for the change and contrast this with previous behavior.


### Footer
The footer should contain any information about Breaking Changes and is also the place to reference GitHub issues that this commit Closes.

Breaking Changes should start with the word BREAKING CHANGE: with a space or two newlines. The rest of the commit message is then used for this.


### Tools

- Untuk VSCode bisa menggunakan ekstensi [Conventional Commits](https://marketplace.visualstudio.com/items?itemName=vivaxy.vscode-conventional-commits)

### Read more
- https://www.conventionalcommits.org/en/v1.0.0/
- https://github.com/angular/angular/blob/22b96b9/CONTRIBUTING.md#-commit-message-guidelines
